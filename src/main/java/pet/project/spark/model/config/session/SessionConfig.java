package pet.project.spark.model.config.session;

public class SessionConfig {
    private long ttl; // seconds
    private String prefix;
    private String cookieName;

    public SessionConfig(long ttl, String prefix, String cookieName) {
        this.ttl = ttl;
        this.prefix = prefix;
        this.cookieName = cookieName;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}
