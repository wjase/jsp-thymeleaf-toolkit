package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp.parser.JSPParser;
import com.cybernostics.jsp.parser.JSPParser.HtmlAttributeValueContext;
import com.cybernostics.jsp.parser.JSPParser.JspElementContext;
import com.cybernostics.jsp2thymeleaf.api.common.Namespaces;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.TH;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.XMLNS;
import static com.cybernostics.jsp2thymeleaf.api.util.SetUtils.setOf;
import com.cybernostics.jsp2thymeleaf.api.util.SimpleStringTemplateProcessor;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import org.apache.commons.collections.ListUtils;
import static org.apache.commons.collections.ListUtils.EMPTY_LIST;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.Text;
import static com.cybernostics.jsp2thymeleaf.api.elements.ScopedJspElementNodeContext.nodeContext;
import com.cybernostics.jsp2thymeleaf.api.util.EntryUtils;
import com.cybernostics.jsp2thymeleaf.api.util.PrefixedName;
import java.util.Map.Entry;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public class JspTagElementConverter extends CopyElementConverter implements TagConverter, MultipleTagConverter {

    protected String convertsTag;
    protected String[] alsoConvertsTags;
    protected String newElementName;
    private Namespace newNamespace = TH;
    protected Set<String> attributesToRemove = setOf();
    protected List<AttributeCreator> newAttributeBuilders = new ArrayList<>();
    protected String newTextContent = "";
    protected BiFunction<JSPParser.JspElementContext, JSPElementNodeConverter, List<Content>> childContentSupplier = (node, context) -> {
        if (newTextContent.length() == 0) {
            return super.getNewChildContent(node, context);
        }
        Map<String, Object> attMap = getAttributeMap(node, context);
        return Arrays.asList(new Text(SimpleStringTemplateProcessor.generate(newTextContent, attMap)));

    };
    protected List<ContentCreator> appendedContentBuilders = EMPTY_LIST;
    private Optional<Function<ScopedJspElementNodeContext, Map<String, String>>> childAttributeSource = Optional.empty();
    private Optional<String> attributeToUseWhenQuoted = Optional.empty();
    private boolean shouldRemoveElement = false;
    private List<AttributeRename> renames = EMPTY_LIST;

    public JspTagElementConverter() {
    }

    public JspTagElementConverter(String appliesTo, String... alsoAppliesTo) {
        this.convertsTag = appliesTo;
        this.alsoConvertsTags = alsoAppliesTo != null ? alsoAppliesTo : new String[0];
    }

    public static JspTagElementConverter ignore(String tagName) {
        return new JspTagElementConverter(tagName) {
            @Override
            public List<Content> process(JspElementContext node, JSPElementNodeConverter context) {
                final Element element = new Element("deleteme");
                return Arrays.asList(element);
            }

        };
    }

    public static JspTagElementConverter converterFor(String tagName, String... alsoConverts) {

        return new JspTagElementConverter(tagName, alsoConverts);
    }

    public JspTagElementConverter withNewName(String name, Namespace namespace) {
        newElementName = name;
        newNamespace = namespace;
        return this;
    }

    public JspTagElementConverter that(Function<JspTagElementConverter, JspTagElementConverter> func) {
        return func.apply(this);
    }

    public JspTagElementConverter withNamespace(Namespace namespace) {
        newNamespace = namespace;
        return this;
    }

    public JspTagElementConverter stripsNamespace() {
        newNamespace = Namespaces.XMLNS;
        return this;
    }

    public JspTagElementConverter withChildElementAtributes(Function<ScopedJspElementNodeContext, Map<String, String>> attSource) {
        childAttributeSource = Optional.of(attSource);
        return this;
    }

    public JspTagElementConverter whenQuotedInAttributeReplaceWith(String attToUse) {
        this.attributeToUseWhenQuoted = Optional.of(attToUse);
        return this;
    }

    public JspTagElementConverter withNewName(String name) {
        newElementName = name;
        return this;
    }

    public JspTagElementConverter withNewTextContent(String contentFormat) {
        newTextContent = contentFormat;
        return this;
    }

    public JspTagElementConverter removeElement() {
        return withNewName("deleteme", XMLNS);
    }

    public JspTagElementConverter withCustomChildContent(BiFunction<JSPParser.JspElementContext, JSPElementNodeConverter, List<Content>> supplier) {
        this.childContentSupplier = supplier;
        return this;
    }

    public JspTagElementConverter withCustomChildContent(ContentCreator... appendedElementBuilders) {
        this.childContentSupplier = (context, converter) -> {
            Map<String, Object> attributeMap = getAttributeMap(context, converter);
            return asList(appendedElementBuilders)
                    .stream()
                    .flatMap(c -> c.apply(attributeMap).stream())
                    .collect(toList());
        };
        return this;
    }

    public JspTagElementConverter appendsContent(ContentCreator... appendedElementBuilders) {
        this.appendedContentBuilders = asList(appendedElementBuilders);
        return this;
    }

    public JspTagElementConverter replaceWith(ContentCreator... appendedElementBuilders) {
        removeElement();
        this.appendedContentBuilders = asList(appendedElementBuilders);
        return this;
    }

    @Override
    protected List<Content> getNewChildContent(JSPParser.JspElementContext node, JSPElementNodeConverter context) {
        return childContentSupplier.apply(node, context);
    }

    @Override
    public boolean canHandle(JSPParser.JspElementContext node) {
        return node.name.getText().equals(getApplicableTag());
    }

    public JspTagElementConverter removesAttributes(String... names) {
        attributesToRemove = setOf(names);
        return this;
    }

    public JspTagElementConverter removesAttributes(Set<String> names) {
        attributesToRemove = names;
        return this;
    }

    public JspTagElementConverter renamesAttributes(AttributeRename... renames) {
        this.removesAttributes(Stream.of(renames).map(r -> r.getFrom()).collect(toSet()));
        this.renames = asList(renames);
        return this;
    }

    public JspTagElementConverter addsAttributes(AttributeCreator... attributeBuilders) {
        newAttributeBuilders.addAll(Arrays.asList(attributeBuilders));
        return this;
    }

    public JspTagElementConverter renamesAttribute(String oldName, String newName, Namespace namespace) {
        removesAttributes(oldName);

        addsAttributes((currentValues)
                -> currentValues.containsKey(oldName)
                ? Arrays.asList(new Attribute(newName,
                        currentValues.get(oldName).toString(), namespace))
                : ListUtils.EMPTY_LIST);
        return this;
    }

    @Override
    protected List<Attribute> convertAttributes(JspElementContext node, JSPElementNodeConverter context) {

        final Map<String, String> renamesMap = renames.stream().collect(toMap(r -> r.getFrom(), r -> r.getTo()));
        final Map<String, String> emptyMap = new HashMap<>();
        Map<String, Object> attMap = new HashMap<>();
        final List<Attribute> sourceAtributes = super.convertAttributes(node, context);
        final List<Attribute> attributes = sourceAtributes
                .stream()
                .filter((eachAttribute)
                        -> {
                    attMap.put(eachAttribute.getName(), eachAttribute.getValue());
                    return !attributesToRemove.contains(eachAttribute.getName());
                })
                .collect(Collectors.toList());

        attributes.forEach(a -> {
            if (renamesMap.containsKey(a.getName())) {
                a.setName(renamesMap.get(a.getName()));
            }
        });

        Map<String, String> childMap = childAttributeSource
                .map(i -> i.apply(nodeContext(node, context)))
                .orElse(emptyMap);

        if (!childMap.isEmpty()) {
            attMap.put("_childAtts", childMap);
        }

        attMap.put("__tagname__", convertsTag);

        final List<Attribute> createdAttributes = newAttributeBuilders.stream()
                .flatMap(eachBuilder -> eachBuilder.buildNewAttributes(attMap).stream())
                .collect(Collectors.toList());

        for (Attribute createdAttribute : createdAttributes) {
            ActiveNamespaces.add(createdAttribute.getNamespace());
        }

        final List<Attribute> allAttributes = ListUtils.union(createdAttributes, attributes);
        if (isElementEmbeddedInAttribute(node)) {
            if (this.attributeToUseWhenQuoted.isPresent()) {
                String attributeToUse = attributeToUseWhenQuoted.get();
                allAttributes.stream()
                        .filter(it -> it.getName().equals(attributeToUse))
                        .forEach(it -> it.setName("data-replace-parent-attribute-value"));
            }

        }
        return allAttributes;
    }

    private static boolean isElementEmbeddedInAttribute(JspElementContext node) {
        return node.parent instanceof JSPParser.HtmlAttributeValueContext;
    }

    @Override
    protected Namespace newNamespaceForElement(JSPParser.JspElementContext node) {
        return newNamespace;
    }

    @Override
    protected String newNameForElement(JSPParser.JspElementContext node) {

        String name
                = StringUtils.defaultIfEmpty(newElementName,
                        PrefixedName.prefixedNameFor(node.name.getText()).getName());
        return name;
    }

    @Override
    public String getApplicableTag() {
        return convertsTag;
    }

    protected Map<String, Object> getAttributeMap(JspElementContext node, JSPElementNodeConverter context) {
        Stream<Entry<String, Object>> attrs = node.atts.stream().map(e -> EntryUtils.entryOf(e.name.getText(), e.value.getText()));
        return Stream.concat(attrs, Stream.of(EntryUtils.entryOf("__tagname__", convertsTag)))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }

    @Override
    protected Optional<Element> createElement(JspElementContext node, JSPElementNodeConverter context) {
        Optional<Element> element = super.createElement(node, context);
        if (element.isPresent()) {
            if (isElementEmbeddedInAttribute(node)) {

                HtmlAttributeValueContext parent = (HtmlAttributeValueContext) node.parent;
                String parentAttributeToReplace = getAttributeNamefor(parent);
                Element el = element.get();
                el.setAttribute("data-replace-parent-attribute-name", parentAttributeToReplace);
                el.setName("deleteme");

            }
        }
        return element;
    }

    private String getAttributeNamefor(HtmlAttributeValueContext attributeValueNode) {
        return attributeValueNode.getParent().children.get(0).getText();
    }

    @Override
    public List<TagConverter> getOtherApplicableTagConverters() {
        return asList(alsoConvertsTags)
                .stream()
                .map(tagName -> cloneConverterForTag(tagName))
                .collect(toList());
    }

    @Override
    protected List<Content> getNewAppendedContent(JspElementContext node, JSPElementNodeConverter context) {
        Map<String, Object> attributeMap = getAttributeMap(node, context);
        return appendedContentBuilders
                .stream()
                .flatMap(b -> b.apply(attributeMap).stream())
                .filter(p -> p != null)
                .collect(toList());
    }

    private JspTagElementConverter cloneConverterForTag(String tagName) {
        JspTagElementConverter converter = converterFor(tagName);
        converter.newElementName = this.newElementName;
        converter.newNamespace = this.newNamespace;
        converter.attributesToRemove = this.attributesToRemove;
        converter.newAttributeBuilders = this.newAttributeBuilders;
        converter.appendedContentBuilders = this.appendedContentBuilders;
        converter.newTextContent = this.newTextContent;
        converter.childContentSupplier = this.childContentSupplier;
        converter.childAttributeSource = this.childAttributeSource;
        converter.attributeToUseWhenQuoted = this.attributeToUseWhenQuoted;
        converter.alsoConvertsTags = this.alsoConvertsTags;
        converter.renames = renames.stream().collect(toList());
        return converter;
    }

}
