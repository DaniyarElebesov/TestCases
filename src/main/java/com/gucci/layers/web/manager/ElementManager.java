package com.gucci.layers.web.manager;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

public class ElementManager {
    private final int DELAY = 30;

    public ElementManager click(SelenideElement element) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldBe(enabled, Duration.ofSeconds(DELAY))
                .shouldNotHave(attribute("disabled"), Duration.ofSeconds(DELAY))
                .shouldBe(clickable, Duration.ofSeconds(DELAY))
                .click();
        return this;
    }

    public ElementManager clickWithScroll(SelenideElement element) {
        element
                .scrollTo()
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldBe(enabled, Duration.ofSeconds(DELAY))
                .shouldNotHave(attribute("disabled"), Duration.ofSeconds(DELAY))
                .shouldBe(clickable, Duration.ofSeconds(DELAY))
                .click();
        return this;
    }

    public ElementManager scroll(SelenideElement element) {
        element
                .scrollTo();
        return this;
    }

    public ElementManager input(SelenideElement element, String text) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldBe(enabled, Duration.ofSeconds(DELAY))
                .scrollTo()
                .sendKeys(text);
        return this;
    }

    public String getText(SelenideElement element) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldNotBe(empty, Duration.ofSeconds(DELAY));
        return element.getText();
    }

    public ElementManager verifyAttributeValue(SelenideElement element, String attribute, String expectedValue) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldNotBe(empty, Duration.ofSeconds(DELAY))
                .shouldHave(Condition.attribute(attribute, expectedValue));
        return this;
    }

    public ElementManager selectByValue(SelenideElement element, String value) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .selectOption(value);
        return this;
    }

    public ElementManager selectByText(SelenideElement element, String text) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .selectOption(text);
        return this;
    }
}
