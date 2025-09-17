package com.gucci.layers.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage<LoginPage> {

    public SelenideElement signUpHeaderIsVisible = $x("//h2[text()='New User Signup!']");
    public SelenideElement loginToAccountIsVisible = $x("//h2[text()='Login to your account']");
    public SelenideElement inputSignupName = $x("//input[@placeholder='Name']");
    public SelenideElement inputSignupEmail = $x("//form[@action='/signup']/input[@placeholder='Email Address']");
    public SelenideElement signUpBtn = $x("//form[@action='/signup']/button");
    public SelenideElement inputLoginEmail = $x("//input[@data-qa='login-email']");
    public SelenideElement inputLoginPassword = $x("//input[@data-qa='login-password']");
    public SelenideElement clickLoginButton = $x("//button[@data-qa='login-button']");
    public SelenideElement incorrectHeaderIsVisible = $x("//p[text()='Your email or password is incorrect!']");
    public SelenideElement verifyEmailAddressAlreadyExist = $x("//p[text()='Email Address already exist!']");


    @Override
    public LoginPage waitForPageLoaded() {
        loginToAccountIsVisible.shouldHave(Condition.exactText("Login to your account"));
        return this;
    }

    @Step("input username {0}")
    public LoginPage fillName(String name) {
        elementManager.input(inputSignupName, name);
        return this;
    }

    @Step("input user email {0}")
    public LoginPage fillEmail(String email) {
        elementManager.input(inputSignupEmail, email);
        return this;
    }

    @Step("click signup button")
    public SignUpPage clickSingUpButton(Class<SignUpPage> signUpPageClass) {
        elementManager.click(signUpBtn);
        return Selenide.page(SignUpPage.class);
    }

    @Step("click signup button for existing email")
    public LoginPage clickSingUpButtonForExistingEmail() {
        elementManager.click(signUpBtn);
        return this;
    }

    @Step("fill email to login {0}")
    public LoginPage fillEmailToLogin(String email) {
        elementManager.input(inputLoginEmail, email);
        return this;
    }

    @Step("fill password to login {0}")
    public LoginPage fillPasswordToLogin(String password) {
        elementManager.input(inputLoginPassword, password);
        return this;
    }

    @Step("click login button to login")
    public HomePage clickLoginBtnToLogin() {
        elementManager.click(clickLoginButton);
        return new HomePage();
    }

    @Step("click login button to login")
    public LoginPage clickLoginBtnToLoginIncorrect() {
        elementManager.click(clickLoginButton);
        return this;
    }

    @Step("Verify error 'Your email or password is incorrect!' is visible")
    public HomePage verifyIncorrectLoginCredentials() {
        elementManager.getText(incorrectHeaderIsVisible);
        return new HomePage();
    }

    @Step("Verify error 'This email address is take' is visible")
    public LoginPage verifyEmailAddressIsTaken() {
        elementManager.getText(verifyEmailAddressAlreadyExist);
        return this;
    }
}
