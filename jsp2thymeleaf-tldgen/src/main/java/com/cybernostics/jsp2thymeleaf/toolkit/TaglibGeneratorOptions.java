package com.cybernostics.jsp2thymeleaf.toolkit;

/*-
 * #%L
 * jsp2thymeleaf-tldgen
 * %%
 * Copyright (C) 1992 - 2017 Cybernostics
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrSubstitutor;

/**
 *
 * @author jason
 */
public class TaglibGeneratorOptions
{

    private static URL tldFile;

    private static File jarFileFromGav(String container)
    {
        Map<String, String> map = new TreeMap<>();
        map.put("home", System.getProperty("user.home"));
        String[] bits = container.split(":");
        if (bits.length != 3)
        {
            throw new RuntimeException("Bad container format expected Group:Artifact:Version but was :" + container);
        }
        map.put("groupId", bits[0].replace(".", "/"));
        map.put("artifactId", bits[1]);
        map.put("version", bits[2]);

        String filePathTemplate = "${home}/.m2/repository/${groupId}/${artifactId}/${version}/${artifactId}-${version}.jar";
        StrSubstitutor sub = new StrSubstitutor(map);
        final File jarFile = new File(sub.replace(filePathTemplate));
        if (jarFile.exists())
        {
            return jarFile;
        }
        throw new RuntimeException(new FileNotFoundException(jarFile.getAbsolutePath()));
    }
    private String packageName;
    private Path projectParentFolder;

    public static TaglibGeneratorOptions parse(String... args)
    {
        try
        {
            Options options = new Options();
            options.addOption("h", "help", false, "Show help");
            options.addOption("p", "package", true, "Package name for the taglib converter");
            options.addOption("c", "container", true, "Jar (group:artifact:version or jar file name) containing the tld - which is assumed to be under META-INF");
            options.addOption("f", "folder", true, "Parent Folder for the taglib converter project");
            final TaglibGeneratorOptions config = new TaglibGeneratorOptions();

            Parser parser = new PosixParser();
            final CommandLine parsedArgs = parser.parse(options, args);
            boolean showHelp = parsedArgs.hasOption("h");
            if (!showHelp && parsedArgs.getArgList().isEmpty())
            {
                System.err.println("Missing taglib argument. What TLD converter do you want to generate?");
                showHelp = true;
            }

            if (showHelp)
            {
                InputStream inputStream = TaglibGeneratorOptions.class.getClassLoader().getResourceAsStream("help/readme.md");
                IOUtils.copy(inputStream, System.out);
                options
                        .getOptions()
                        .stream()
                        .forEach((o) -> TaglibGeneratorOptions.showOption((Option) o));
                System.exit(-1);

            } else
            {
                final String tldFilename = (String) parsedArgs.getArgList().get(0);
                System.out.println("taglib file:" + tldFilename);
                if (parsedArgs.hasOption("container"))
                {
                    final String container = parsedArgs.getOptionValue("container");
                    tldFile = getFileFromContainer(tldFilename, container);
                } else
                {
                    tldFile = TaglibGeneratorOptions.class.getClassLoader().getResource(tldFilename);
                }
                config.packageName = parsedArgs.hasOption("package") ? parsedArgs.getOptionValue("package") : "com.cybernostics.converters." + tldFile.getFile().replaceAll(".tld", "").toLowerCase();
                String parentFolderString = parsedArgs.hasOption("folder") ? parsedArgs.getOptionValue("folder") : System.getProperty("user.dir");
                config.projectParentFolder = Paths.get(parentFolderString);
            }
            return config;
        } catch (ParseException ex)
        {
            Logger.getLogger(TaglibGeneratorOptions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(TaglibGeneratorOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void showOption(Option o)
    {
        System.out.println(String.format("     -%s,--%-10s - %s", o.getOpt(), o.getLongOpt(), o.getDescription()));
    }

    public URL getTldFile()
    {
        return tldFile;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public Path getProjectParentFolder()
    {
        return projectParentFolder;
    }

    private static URL getFileFromContainer(String tldFilename, String container)
    {
        if (container.endsWith(".jar"))
        {
            File jarFile = Paths.get(container).toFile();
            return readTLDEntryFromJar(jarFile, tldFilename);
        }

        //assume gav
        return readTLDEntryFromJar(jarFileFromGav(container), tldFilename);
    }

    private static URL readTLDEntryFromJar(File jarFile, String tldFilename)
    {
        try
        {
            return new URL("jar:file:" + jarFile.getAbsolutePath() + "!/META-INF/" + tldFilename);
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(TaglibGeneratorOptions.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException(ex);
        }
    }

}
