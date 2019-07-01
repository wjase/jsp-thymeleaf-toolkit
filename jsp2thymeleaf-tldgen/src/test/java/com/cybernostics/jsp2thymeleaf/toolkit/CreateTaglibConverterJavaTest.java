/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author jason
 */
@RunWith(Parameterized.class)
public class CreateTaglibConverterJavaTest
{

    private static final Logger LOG = Logger.getLogger(CreateTaglibConverterJavaTest.class.getName());
    private static final ClassLoader loader = CreateTaglibConverterJavaTest.class.getClassLoader();
    private final String name;
    private final URL TLDFile;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public CreateTaglibConverterJavaTest(String name, URL TLDFile)
    {
        this.name = name;
        this.TLDFile = TLDFile;
    }

    @Test
    public void shouldGenerate()
    {
        String packageName = "com.yourcompany";
        try
        {
            URL expectedFileContent = new URL(TLDFile.toString().replaceAll(".tld$", ".java.expected.txt"));
            String expectedContent = IOUtils.toString(expectedFileContent, Charset.defaultCharset());

            TaglibGenerator generator = new TaglibGenerator();
            generator.setPackageName(packageName);
            generator.setTaglib(TLDFile);
            generator.setProjectParentFolder(Paths.get(folder.getRoot().toURI()));
            generator.init();

            ClassPathResource classPathResource = new ClassPathResource("project/src/main/java/package_name/taglib_name_TldConverterRegistration.java.vm");
            Path outputPath = generator.transform(Paths.get(classPathResource.getURI()));
            generator.generate(classPathResource);

            final File outputFile = outputPath.toFile();
            assertThat("Generated output file", outputFile.exists(), is(true));

            final String convertedContent = FileUtils.readFileToString(outputFile, Charset.defaultCharset());
            LOG.info("\n" + convertedContent);
            assertThat(convertedContent.replaceAll("\\s+", " ").trim(), is(expectedContent.replaceAll("\\s+", " ").trim()));
        } catch (IOException ex)
        {
            LOG.log(Level.SEVERE, null, ex);
            Assert.fail("exception thrown");
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data()
    {
        return getTestFiles().
                map(eachFile -> new Object[]
        {
            (Object) eachFile.getName(), (Object) toURL(eachFile)
        })
                .collect(Collectors.toList());
    }

    public static URL toURL(File file)
    {
        try
        {
            return file.toURL();
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(CreateTaglibConverterJavaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Stream<File> getTestFiles()
    {
        final URL resource = loader.getResource("tld-happy-case");
        final File file = new File(resource.getFile());
        return Arrays.asList(file.listFiles())
                .stream()
                .filter(it -> it.getName().contains("tld"))
                //                .filter(it -> it.getName().contains("fort"))
                .sorted();
    }
}
