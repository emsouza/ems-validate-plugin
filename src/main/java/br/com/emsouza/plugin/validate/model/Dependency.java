package br.com.emsouza.plugin.validate.model;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 09/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class Dependency {

    private String groupId;

    private String artifactId;

    private String version;

    private String scope;

    private String description;

    public Dependency(String groupId, String artifactId, String version, String scope, String description) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.scope = scope;
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getScope() {
        return scope;
    }

    public String getDescription() {
        return description;
    }

    // ----------------------------------------------------------------------
    // Object overrides
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getGroupId() != null) {
            sb.append(getGroupId());
            sb.append(":");
        }
        if (getArtifactId() != null) {
            sb.append(getArtifactId());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Dependency)) {
            return false;
        }
        Dependency a = (Dependency) o;

        if (!a.getGroupId().equals(groupId)) {
            return false;
        } else if (!a.getArtifactId().equals(artifactId)) {
            return false;
        }

        // We don't consider the version range in the comparison, just the
        // resolved version
        return true;
    }
}