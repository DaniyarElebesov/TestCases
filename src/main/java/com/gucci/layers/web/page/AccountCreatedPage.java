package com.gucci.layers.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class AccountCreatedPage extends BasePage<AccountCreatedPage> {

    public SelenideElement verifyAccountCreated = $x("//b[normalize-space()='Account Created!']");
    public SelenideElement clickContinueBtn = $x("//a[@data-qa='continue-button']");

    @Override
    public AccountCreatedPage waitForPageLoaded() {
        verifyAccountCreated.shouldHave(Condition.exactText("Account Created!"));
        return this;
    }

    @Step()
    public HomePage clickContinueButton() {
        elementManager.click(clickContinueBtn);
        return Selenide.page(HomePage.class);
    }
}
