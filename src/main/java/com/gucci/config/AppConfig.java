package com.gucci.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:app.properties"})
public interface AppConfig extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("headless.mode")
    boolean headless();
}
