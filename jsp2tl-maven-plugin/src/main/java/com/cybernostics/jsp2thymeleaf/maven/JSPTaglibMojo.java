package com.cybernostics.jsp2thymeleaf.maven;

import com.cybernostics.jsp2thymeleaf.toolkit.TaglibGenerator;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;
import org.twdata.maven.mojoexecutor.MojoExecutor.ExecutionEnvironment;

/**
 * Goal which creates am empty TAGLIB converter project.
 *
 * You still need to tell the converter how to transform each node into a
 * Thymeleaf node. It just saves all the copy paste necessary to create the
 * skeleton converter code.
 *
 * run it like this (assuming the plugin is at version 1.0): (replace barry.tld
 * with the full path to the tld to process)
 *
 * mvn com.cybernostics:jsp2tl-maven-plugin:1.0:taglib -Dtaglib=barry.tld
 *
 */
@Mojo(name = "taglib", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresProject = false)
public class JSPTaglibMojo
        extends AbstractMojo
{

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;

    @Component
    private BuildPluginManager pluginManager;

    /**
     * Location of the generated sources.
     */
    @Parameter(property = "taglib", required = true)
    private String taglib;

    @Parameter(property = "taglibContainer", required = false)
    /**
     * The jar containing the taglib, assumed to be under
     * META-INF/taglibName.tld
     */
    private String taglibContainer;

    public String getTaglib()
    {
        return taglib;
    }

    public void execute()
            throws MojoExecutionException

    {
        final String[] gavBits = taglibContainer.split(":");
        try
        {
            File containerJar = getJarContainer(gavBits[0], gavBits[1], gavBits[2]);
            TaglibGenerator taglibGenerator = new TaglibGenerator();
            taglibGenerator.run("-c", containerJar.getAbsolutePath(), taglib);
        } catch (Exception ex)
        {
            Logger.getLogger(JSPTaglibMojo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private File getJarContainer(String groupId, String artifactId, String versionId)
    {
        try
        {
            Path tempPath = Paths.get(System.getProperty("java.io.tmpdir", "/tmp"));
            final Path jarPath = tempPath.resolve(groupId.replace(".", "_") + artifactId + "-" + versionId + ".jar");

            final ExecutionEnvironment executionEnvironment = executionEnvironment(
                    mavenProject,
                    mavenSession,
                    pluginManager
            );
            final Properties userProperties = executionEnvironment.getMavenSession().getUserProperties();
            userProperties.setProperty("groupId", groupId);
            userProperties.setProperty("artifactId", artifactId);
            userProperties.setProperty("version", versionId);
//            userProperties.setProperty("dest", versionId);

            executeMojo(
                    plugin(
                            groupId("org.apache.maven.plugins"),
                            artifactId("maven-dependency-plugin"),
                            version("3.0.0")
                    ),
                    goal("get"),
                    configuration( //                            element(name("groupId"), groupId),
                    //                            element(name("artifactId"), artifactId),
                    //                            element(name("version"), versionId),
                    //                            element(name("dest"), jarPath.toString())
                    ),
                    executionEnvironment
            );
            System.out.println("Fetched:" + jarPath.toString());
            return jarPath.toFile();
        } catch (MojoExecutionException ex)
        {
            Logger.getLogger(JSPTaglibMojo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
