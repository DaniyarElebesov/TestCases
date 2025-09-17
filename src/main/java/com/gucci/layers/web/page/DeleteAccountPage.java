package com.gucci.layers.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class DeleteAccountPage extends BasePage<DeleteAccountPage> {

    public SelenideElement deleteAccount = $x("//b[text()='Account Deleted!']");
    public SelenideElement clickContinueBtn = $x("//a[@data-qa='continue-button']");

    @Override
    public DeleteAccountPage waitForPageLoaded() {
        deleteAccount.shouldHave(Condition.exactText("ACCOUNT DELETED!"));
        return this;
    }

    @Step("click continue button after deletion of account")
    public DeleteAccountPage clickContinueButton(){
        clickContinueBtn.click();
        return this;
    }
}
