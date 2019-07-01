package com.cybernostics.jsp2thymeleaf.toolkit;

/*-
 * #%L
 * * TaglibGenerator.java - jsp2thymeleaf-tldgen
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
import com.cybernostics.jsp2thymeleaf.toolkit.model.Taglib;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.URLResourceLoader;
import org.apache.velocity.util.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 *
 * @author jason
 */
@Component
public class TaglibGenerator implements CommandLineRunner
{

    private static final Logger LOG = Logger.getLogger(TaglibGenerator.class.getCanonicalName());

    private String rootUrl;

    private final VelocityEngine velocityEngine;
    private VelocityContext velocityContext;
    private String packageName;
    private Taglib parsedTaglib;
    private Path projectParentFolder;

    private URL taglib;

    public TaglibGenerator()
    {
        /*  first, get and initialize an engine  */
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "url");
        velocityEngine.setProperty("url.resource.loader.description", URLResourceLoader.class.getCanonicalName());
        velocityEngine.setProperty("url.resource.loader.class", URLResourceLoader.class.getCanonicalName());
        velocityEngine.setProperty("url.resource.loader.root", getClass().getClassLoader().getResource("project").toExternalForm());
        velocityEngine.init();

    }

    public void init()
    {
        try
        {
            rootUrl = TaglibGenerator.class.getClassLoader().getResource("project").toString();

            System.out.println("Taglib converter generator running:");
            System.out.println("package: " + packageName);
            System.out.println("taglib url: " + String.valueOf(taglib));
            parsedTaglib = Taglib.parse(taglib.openStream());
            System.out.println("taglib name: " + String.valueOf(parsedTaglib.getName()));
            System.out.println("taglib description: " + String.valueOf(parsedTaglib.getDescription()));
            System.out.println("root url: " + rootUrl.toString());
            System.out.println("");

            velocityContext = new VelocityContext();
            velocityContext.put("packageName", packageName);
            velocityContext.put("taglib", parsedTaglib);
            velocityContext.put("stringutils", StringUtils.class);
            packagePath = packageName.replace(".", "/");
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(TaglibGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (IOException ex)
        {
            Logger.getLogger(TaglibGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generateProject()
    {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        final Resource[] resources;
        try
        {
            resources = resolver.getResources("classpath:/project/**/*.*");

            Arrays.stream(resources)
                    .filter(TaglibGenerator::isReadable)
                    .forEach(it -> generate(it));
        } catch (IOException ex)
        {
            Logger.getLogger(TaglibGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean isReadable(Resource r)
    {
        return r.isReadable();
    }

    private static Path asPath(Resource r)
    {
        try
        {
            return Paths.get(r.getURI());
        } catch (Exception ex)
        {
            throw new RuntimeException("Could not get resource as Path:" + r.toString(), ex);
        }
    }

    @Override
    public void run(String... args) throws Exception
    {
        try
        {
            final TaglibGeneratorOptions options = TaglibGeneratorOptions.parse(args);
            setPackageName(options.getPackageName());
            setTaglib(options.getTldFile());
            setProjectParentFolder(options.getProjectParentFolder());
            init();
            generateProject();

        } catch (RuntimeException exception)
        {
            LOG.severe(exception.getLocalizedMessage());
        } catch (Throwable t)
        {
            LOG.severe(t.getMessage());
        }
    }

    public void setPackageName(String packageName)
    {
        assert (packageName != null);
        this.packageName = packageName;
    }

    public void setTaglib(URL taglib)
    {
        assert (taglib != null);
        this.taglib = taglib;
    }

    public void setProjectParentFolder(Path projectParentFolder)
    {
        assert (projectParentFolder != null);
        this.projectParentFolder = projectParentFolder;
    }

    void generate(Resource resource)
    {
        try
        {

            String templatePath = resource.getURI().toString();
            //        final String templatePath = srcPath.toString().substring(rootUrl.length());
            if (templatePath.startsWith(rootUrl))
            {
                templatePath = templatePath.substring(rootUrl.length());
            }
            if (velocityEngine.resourceExists(templatePath))
            {
                /*  next, get the Template  */
                Template t = velocityEngine.getTemplate(templatePath);

                Path newItemPath = transform(Paths.get(templatePath.substring(1)));
                try (FileWriter fileWriter = new FileWriter(newItemPath.toString()))
                {
                    System.out.println("generating: " + newItemPath.toString());
                    /* now render the template into a StringWriter */
                    t.merge(velocityContext, fileWriter);
                    fileWriter.flush();
                } catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }

            }
        } catch (IOException ex)
        {
            Logger.getLogger(TaglibGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Path transform(Path srcPath)
    {
        try
        {
            String rootUrlAsString = rootUrl.toString();

            final Path srcParentPath = srcPath.getParent();
            String srcPathAsString = srcParentPath != null ? srcParentPath.toString() : "";
            if (srcPathAsString.startsWith("/"))
            {
                if (srcPathAsString.startsWith(rootUrlAsString))
                {
                    srcPathAsString = srcPathAsString.substring(rootUrlAsString.length());
                }
                if (rootUrlAsString.startsWith("file:"))
                {

                    String rootUrlWithoutPrefix = rootUrlAsString.substring(5);
                    if (srcPathAsString.startsWith(rootUrlWithoutPrefix))
                    {
                        srcPathAsString = srcPathAsString.substring(rootUrlWithoutPrefix.length());
                    }
                }
            }
            String destPath = transform(srcPathAsString);
            final Path destFolder = projectParentFolder.resolve(destPath);
            destFolder.toFile().mkdirs();
            return destFolder.resolve(transform(srcPath.getFileName().toString().replaceAll(".vm$", "")));
        } catch (Exception ex)
        {
            Logger.getLogger(TaglibGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private String transform(String pathComponent)
    {
        return pathComponent
                .replace("/package_name", "/" + packagePath)
                .replace("taglib_name_", StringUtils.capitalizeFirstLetter(parsedTaglib.getName()))
                .replaceFirst("^/", "");
    }
    private String packagePath;

}
