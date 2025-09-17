package com.gucci.layers.web.page.headerMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class TestCasesPage extends BasePage<TestCasesPage> {

    public SelenideElement verifyTestCasesPage = $x("//b[text()='Test Cases']");

    @Override
    public TestCasesPage waitForPageLoaded() {
verifyTestCasesPage.shouldHave(Condition.exactText("Test Cases"));
        return this;
    }
}
