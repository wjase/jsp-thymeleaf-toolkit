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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jason
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CreateProjectTest
{

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void shouldGenerateProject() throws IOException, URISyntaxException
    {
        final URL tldFile = getClass().getClassLoader().getResource("tld-happy-case/spring.tld");
        //  create temp folder in which to gen file
        final File projectFolder = folder.newFolder();
        TaglibGenerator gen = new TaglibGenerator();
        gen.setPackageName("blah");
        gen.setProjectParentFolder(Paths.get(projectFolder.toURI()));
        gen.setTaglib(tldFile.toURI().toURL());
        gen.init();

        System.out.println(projectFolder.toString());
        // generate a tld converter in that folder and compare results
        gen.generateProject();
        final File[] generatedFiles = projectFolder.listFiles();
        Assert.assertThat(generatedFiles.length, not(0));
        System.out.println("Done!");

    }
}
