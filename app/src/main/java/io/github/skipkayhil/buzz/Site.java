package io.github.skipkayhil.buzz;

public class Site {
    private String name;
    private String defaultUrl;
    private String loginUrl;

    public Site(String name, String defaultUrl, String loginUrl) {
        this.name = name;
        this.defaultUrl = defaultUrl;
        this.loginUrl = loginUrl;
    }

    public String getName() {
        return name;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }
}
