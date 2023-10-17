package io.quarkus.deployment.pkg.builditem;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import io.quarkus.builder.item.SimpleBuildItem;
import io.quarkus.maven.dependency.ArtifactKey;

/**
 * The location that output artifacts should be created in
 *
 * TODO: should we just create them in temp directories, and leave it up to the integration to move them where they want?
 */
public final class OutputTargetBuildItem extends SimpleBuildItem {

    private final Path outputDirectory;
    private final String baseName;
    private final String originalBaseName;
    private final boolean rebuild;
    private final Properties buildSystemProperties;
    private final Optional<Set<ArtifactKey>> includedOptionalDependencies;

    /**
     * @deprecated in favor of {@link #OutputTargetBuildItem(Path, String, String, boolean, Properties, Optional)}
     *
     * @param outputDirectory build output directory
     * @param baseName base runner name
     * @param rebuild indicates whether the application is being re-built
     * @param buildSystemProperties build system properties
     * @param includedOptionalDependencies included optional dependencies
     */
    @Deprecated(forRemoval = true)
    public OutputTargetBuildItem(Path outputDirectory, String baseName, boolean rebuild, Properties buildSystemProperties,
            Optional<Set<ArtifactKey>> includedOptionalDependencies) {
        this(outputDirectory, baseName, baseName, rebuild, buildSystemProperties, includedOptionalDependencies);
    }

    public OutputTargetBuildItem(Path outputDirectory, String baseName, String originalBaseName, boolean rebuild,
            Properties buildSystemProperties,
            Optional<Set<ArtifactKey>> includedOptionalDependencies) {
        this.outputDirectory = outputDirectory;
        this.baseName = baseName;
        this.originalBaseName = originalBaseName;
        this.rebuild = rebuild;
        this.buildSystemProperties = buildSystemProperties;
        this.includedOptionalDependencies = includedOptionalDependencies;
    }

    public Path getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Base name for the Quarkus application runner file.
     *
     * @return base name for the Quarkus application runner file
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * The base name (not including the extension) of the original JAR generated by the build system.
     * This name could be different from the value of {@ #getBaseName()}, which will be used for the Quarkus runner file.
     *
     * @return name of the original JAR generated by the build system
     */
    public String getOriginalBaseName() {
        return originalBaseName;
    }

    public boolean isRebuild() {
        return rebuild;
    }

    public Properties getBuildSystemProperties() {
        return buildSystemProperties;
    }

    public Optional<Set<ArtifactKey>> getIncludedOptionalDependencies() {
        return includedOptionalDependencies;
    }
}
