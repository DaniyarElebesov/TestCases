package com.gucci.layers.web.page;

import com.gucci.layers.web.manager.ElementManager;

public abstract class BasePage <T extends BasePage> {

    public abstract T waitForPageLoaded();
    protected final ElementManager elementManager = new ElementManager();

}
