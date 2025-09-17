package com.gucci.layers.web.page.headerMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class ContactUsPage extends BasePage<ContactUsPage> {

    public SelenideElement getInTouchIsVisible = $x("//h2[text()='Get In Touch']");
    public SelenideElement inputName = $x("//input[@placeholder='Name']");
    public SelenideElement inputEmail = $x("//input[@placeholder='Email']");
    public SelenideElement inputSubject = $x("//input[@placeholder='Subject']");
    public SelenideElement inputMessage = $x("//textarea[@placeholder='Your Message Here']");
    public SelenideElement clickFileToUpload = $x("//input[@type='file']");
    public SelenideElement clickSubmitBtn = $x("//input[@data-qa='submit-button']");
    public static SelenideElement successMessageIsVisible = $x("//div[@class='status alert alert-success']");
    public SelenideElement clickHomeBtn = $x("//i[@class='fa fa-angle-double-left']");
    public SelenideElement viewProduct1 = $x("(//ul[@class='nav nav-pills nav-justified'])[1]");

    @Override
    public ContactUsPage waitForPageLoaded() {
        getInTouchIsVisible.shouldHave(Condition.exactText("Get In Touch"));
        return this;
    }

    @Step("Input name into 'Name' field {0}")
    public ContactUsPage enterName(String name) {
        elementManager.input(inputName, name);
        return this;
    }

    @Step("Input email into 'Email' field {0}")
    public ContactUsPage enterEmail(String email) {
        elementManager.input(inputEmail, email);
        return this;
    }

    @Step("Input subject into 'Subject' field {0}")
    public ContactUsPage enterSubject(String subject) {
        elementManager.input(inputSubject, subject);
        return this;
    }

    @Step("Input message into 'Message' field {0}")
    public ContactUsPage enterMessage(String text) {
        elementManager.input(inputMessage, text);
        return this;
    }

    @Step("Input message into 'Message' field")
    public ContactUsPage clickSubmitButton() {
        elementManager.click(clickSubmitBtn);
        return this;
    }

    @Step("Visibility of message 'Success!'")
    public ContactUsPage successMessageIsVisible() {
        successMessageIsVisible.shouldHave(Condition.exactText("Success! Your details have been submitted successfully."));
        return this;
    }

    @Step("Click home button")
    public HomePage clickHomeButton() {
        clickHomeBtn.click();
        return Selenide.page(HomePage.class);
    }



}
