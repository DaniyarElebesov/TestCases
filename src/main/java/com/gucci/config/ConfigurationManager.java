package com.gucci.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    public static AppConfig getAppConfig(){
        return ConfigCache.getOrCreate(AppConfig.class);
    }
}
