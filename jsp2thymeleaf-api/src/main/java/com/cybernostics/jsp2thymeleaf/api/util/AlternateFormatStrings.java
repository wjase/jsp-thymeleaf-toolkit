/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;

/**
 * Sometimes you want to generate one out of a list of possible formats,
 * depending on the context variables available. ie if you have firstName and
 * lastName, then this. If you only have firstName then do that.
 *
 * @author jason
 */
public class AlternateFormatStrings
{

    public static final String startOptional = "_<_";
    public static final String endOptional = "_>_";

    public static Function<Map<String, Object>, String> when(FormatCondition... conditions)
    {
        return (values) ->
        {
            return asList(conditions)
                    .stream()
                    .filter(i -> i.matches(values))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No condition found matching attributes" + asString(values)))
                    .apply(values);
        };
    }

    public static Function<Map<String, Object>, String> chooseFormat(String... formats)
    {
        final AlternateFormatStrings afs = new AlternateFormatStrings(formats);
        return (values) ->
        {
            return afs.format(values);
        };
    }

    public static Function<Map<String, Object>, String> constant(String value)
    {
        return (values) ->
        {
            return value;
        };
    }

    private static String asString(Map<String, Object> values)
    {
        return values.entrySet().stream().map(i -> i.getKey() + "=" + i.getValue().toString()).collect(joining());
    }

    private List<CandidateFormat> candidateFormats;

    public AlternateFormatStrings(String... formatStrings)
    {
        candidateFormats = Arrays.stream(formatStrings)
                .map(item -> parse(item))
                .collect(Collectors.toList());

    }

    public Optional<CandidateFormat> formatWhichUsesValues(Map<String, Object> attributeMap)
    {
        Set<String> availableValues = attributeMap.keySet();
        return candidateFormats.stream()
                .filter((item) -> availableValues.containsAll(item.getRequiredAttributes()))
                .findFirst();
    }

    private static CandidateFormat parse(String formatString)
    {
        CandidateFormat candidateFormat = new CandidateFormat(formatString);
        return candidateFormat;
    }

    private static Set<String> getRequiredValuesForFormat(String candidateFormat)
    {
        Set<String> requiredVariables = new HashSet<>();
        StrSubstitutor ss = SimpleStringTemplateProcessor.getSubstitutor(new StrLookup<String>()
        {
            @Override
            public String lookup(String key)
            {
                final ValueDefaultAndFilter keyDefaultFilter = ValueDefaultAndFilter.parse(key);
                if (keyDefaultFilter.isRequired())
                {
                    requiredVariables.add(keyDefaultFilter.getName());
                }
                return "";
            }
        });
        ss.replace(candidateFormat); // do a dummy replace to find out what's needed
        return requiredVariables;
    }

    private String format(Map<String, Object> values)
    {
        Optional<CandidateFormat> formatForValues = formatWhichUsesValues(values);//.orElseThrow(err(values));
        if(!formatForValues.isPresent() ){
          throw err(values).get();
        }
        
      Set<String> keySet = values.keySet();
        return formatForValues.orElse(new CandidateFormat(""))
          .getParts()
          .stream()
          .filter(p -> p.isRequired() || keySet.containsAll(p.getRequiredAttributes()))
          .map(p -> SimpleStringTemplateProcessor.generate(p.getPart(), values))
          .collect(joining(""));
    }

    private Supplier<RuntimeException> err(Map<String, Object> values)
    {
        return () ->
        {
            StringWriter message = new StringWriter();
            message.write("Could not select format from candidates:");
            message.write(candidateFormats.stream().map(it -> it.toString()).collect(Collectors.joining(",")));
            message.write('\n');
            message.write("Given Attributes:");
            message.write(values.keySet().stream().collect(Collectors.joining(",")));
            return new RuntimeException(message.toString());
        };
    }

    private static class CandidateFormat
    {

        private final String format;
        private final Set<String> requiredAttributes = new TreeSet<>();
        private final List<FormatPart> parts;

        private CandidateFormat(String formatString)
        {
            format = formatString;
            parts = splitParts(format);
        }

        @Override
        public String toString()
        {
            StringWriter stringWriter = new StringWriter();
            stringWriter.write("Format:");
            stringWriter.write(format);
            stringWriter.write('\n');
            stringWriter.write("Uses:");
            stringWriter.write(requiredAttributes.stream().collect(Collectors.joining(",")));
            stringWriter.write('\n');
            return stringWriter.toString();
        }

        private List<FormatPart> splitParts(String format)
        {
            boolean required = true;
            int partBegin = 0;
            int partEnd = format.indexOf(startOptional);
            ArrayList<FormatPart> parts = new ArrayList<>();
            do
            {
                partEnd = (partEnd != -1) ? partEnd : format.length();
                final FormatPart formatPart = new FormatPart(format.substring(partBegin, partEnd), required);
                if (formatPart.isRequired())
                {
                    requiredAttributes.addAll(formatPart.getRequiredAttributes());
                }
                parts.add(formatPart);
                required = !required;
                partBegin = partEnd + 3; // move past delimiter
                partEnd = format.indexOf(required ? startOptional : endOptional, partBegin);
            } while (partBegin != -1 && partBegin <= format.length());
            return parts;
        }

        private Collection<?> getRequiredAttributes()
        {
            return requiredAttributes;
        }

        public List<FormatPart> getParts()
        {
            return parts;
        }

    }

    public static FormatCondition valuesMatch(Function<Map<String, Object>, Boolean> matcher, Function<Map<String, Object>, String> applyFn)
    {
        return new FormatCondition(matcher, applyFn);
    }

    public static class FormatCondition implements Function<Map<String, Object>, String>
    {

        private Function<Map<String, Object>, Boolean> condition = (map) -> false;
        private Function<Map<String, Object>, String> applyFn = (map) -> "";

        public FormatCondition(Function<Map<String, Object>, Boolean> condition, Function<Map<String, Object>, String> applyFn)
        {
            this.condition = condition;
            this.applyFn = applyFn;
        }

        public boolean matches(Map<String, Object> values)
        {
            return condition.apply(values);
        }

        @Override
        public String apply(Map<String, Object> values)
        {
            return applyFn.apply(values);
        }

    }

    /**
     * A sub string of a format which, if not required, will only by included if
     * the values are present.
     */
    private static class FormatPart
    {

        private final String part;
        private final boolean required;
        private Set<String> requiredAttributes;

        public FormatPart(String part, boolean required)
        {
            this.part = part;
            this.required = required;
            requiredAttributes = getRequiredValuesForFormat(part);
        }

        public String getPart()
        {
            return part;
        }

        public boolean isRequired()
        {
            return required;
        }

        public Set<String> getRequiredAttributes()
        {
            return requiredAttributes;
        }
    }

}
