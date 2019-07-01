package com.cybernostics.jsp2thymeleaf.toolkit;

/*-
 * #%L
 * * TaglibGeneratorOptionsTest.java - jsp2thymeleaf-tldgen
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
import java.io.IOException;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 *
 * @author jason
 */
public class TaglibGeneratorOptionsTest
{

    public TaglibGeneratorOptionsTest()
    {
    }

    @Test
    public void shouldGetTLDFromLocalMavenArtifact() throws IOException
    {
        final TaglibGeneratorOptions opts = TaglibGeneratorOptions.parse("-c", "org.apache.taglibs:taglibs-standard-impl:1.2.5", "c.tld");
        assertThat(opts, notNullValue());
        assertThat(opts.getTldFile(), notNullValue());
        assertThat(opts.getTldFile().openStream(), notNullValue());

    }

    @Test
    public void shouldGetTLDFromLocalJar() throws IOException
    {
        String homeDir = System.getenv("HOME");
        final String localJar = homeDir+ "/.m2/repository/org/apache/taglibs/taglibs-standard-impl/1.2.5/taglibs-standard-impl-1.2.5.jar";
        final TaglibGeneratorOptions opts = TaglibGeneratorOptions.parse("-c", localJar, "c.tld");
        assertThat(opts, notNullValue());
        assertThat(opts.getTldFile(), notNullValue());
        assertThat(opts.getTldFile().openStream(), notNullValue());

    }
}
