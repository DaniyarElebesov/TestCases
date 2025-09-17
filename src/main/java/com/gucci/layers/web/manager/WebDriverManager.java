package com.gucci.layers.web.manager;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.gucci.config.ConfigurationManager.getAppConfig;

public class WebDriverManager {

    public static void configureBasicWebDriver() {
        Configuration.browser = "chrome";
        Configuration.headless = getAppConfig().headless();
        ChromeOptions options = new ChromeOptions();

        if (Configuration.headless) {
            options.addArguments("--window-size=1900,1000");
        } else {
            options.addArguments("--start-maximized");
        }

        Configuration.browserSize = null;
        Configuration.browserCapabilities = options;
    }
}
