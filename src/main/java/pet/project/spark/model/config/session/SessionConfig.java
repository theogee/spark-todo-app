package pet.project.spark.model.config.session;

import com.google.gson.annotations.SerializedName;

public class SessionConfig {
    @SerializedName("session_ttl") private long ttl; // seconds
    @SerializedName("session_prefix") private String prefix;
    @SerializedName("cookie_name") private String cookieName;

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
