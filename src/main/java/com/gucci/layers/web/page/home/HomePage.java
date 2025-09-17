package com.gucci.layers.web.page.home;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.*;
import com.gucci.layers.web.page.headerMenu.ContactUsPage;
import com.gucci.layers.web.page.headerMenu.ProductsPage;
import com.gucci.layers.web.page.headerMenu.TestCasesPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends BasePage<HomePage> {
    public SelenideElement header = $(By.id("header"));
    public SelenideElement features_items = $(".features_items");
    public SelenideElement left_sidebar = $(".left-sidebar");
    public ElementsCollection brands = left_sidebar.$$x(".//div[@class='brands-name']//li");

    public SelenideElement homeOrange = $x("//a[@href='/' and contains(@style, 'orange')]");
    public SelenideElement loginSignUpBtn = $x("//a[@href='/login']");
    public SelenideElement loggedInAsUsernameIsVisible = $x("//a[text()=' Logged in as ']");
    public SelenideElement deleteAccount = $x("//a[@href='/delete_account']");
    public SelenideElement logoutBtn = $x("//a[@href='/logout']");
    public SelenideElement contactUsBtn = $x("//i[@class='fa fa-envelope']");
    public SelenideElement testCasesBtn = $x("//a[@href='/test_cases']");
    public SelenideElement productsBtn = $x("//a[@href='/products']");
    public SelenideElement subscriptionIsVisible = $x("//h2[text()='Subscription']");
    public SelenideElement inputSubscriptionEmail = $(By.id("susbscribe_email"));
    public SelenideElement subscriptionBtn = $(By.id("subscribe"));
    public SelenideElement subscriptionSuccessTextIsVisible = $x("//div[@class='alert-success alert']");

    @Override
    public HomePage waitForPageLoaded() {
        homeOrange.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("click sign up/login button")
    public LoginPage clickSignupLoginButton() {
        elementManager.click(loginSignUpBtn);
        return Selenide.page(LoginPage.class);
    }

    public <T> T switchBtwSection(String section, Class<T> pageClass) {
        SelenideElement sectionElement = header.$(By.xpath(
                ".//ul[@class='nav navbar-nav']//a[normalize-space(text())='" + section + "']"
        ));
        elementManager.click(sectionElement);
        return Selenide.page(pageClass);
    }


    @Step("verify Logged in as username is visible {0}")
    public HomePage verifyLoggedInAsUsername(String user) {
        loggedInAsUsernameIsVisible.shouldHave(Condition.exactText("Logged in as " + user));
        return this;
    }

    @Step("forms list of brands")
    public List<String> getBrands() {
        List<String> brandsList = new ArrayList<>();
        for (SelenideElement element : brands) {
            brandsList.add(elementManager.getText(element));
        }
        return brandsList;
    }

    @Step("click delete button")
    public DeleteAccountPage clickDeleteAccountBtn() {
        deleteAccount.click();
        return new DeleteAccountPage();
    }

    @Step("click logout button")
    public LoginPage clickLogoutButton() {
        logoutBtn.click();
        return new LoginPage();
    }

    @Step("click contact us button")
    public ContactUsPage clickContactUsButton() {
        contactUsBtn.click();
        return Selenide.page(ContactUsPage.class);
    }

    @Step("click test cases button")
    public TestCasesPage clickTestCasesButton() {
        testCasesBtn.click();
        return Selenide.page(TestCasesPage.class);
    }

    @Step("click 'Products' button")
    public ProductsPage clickProductsButton() {
        productsBtn.click();
        return Selenide.page(ProductsPage.class);
    }

    @Step("Verify 'subscription' text is visible")
    public HomePage visibilityOfSubscription() {
        subscriptionIsVisible.scrollTo();
        subscriptionIsVisible.shouldHave(Condition.exactText("Subscription"));
        return this;
    }

    @Step("Input email in 'subscription' field")
    public HomePage inputEmailSubscription(String text) {
       elementManager.input(inputSubscriptionEmail, text);
        return this;
    }

    @Step("click subscription button")
    public HomePage clickSubscriptionButton() {
        subscriptionBtn.click();
        return this;
    }

    @Step("Visibility of successful subscription text 'You have been successfully subscribed!'")
    public HomePage visibilityOfSuccessfulSubscription() {
        subscriptionSuccessTextIsVisible.shouldHave(Condition.exactText("You have been successfully subscribed!"));
        System.out.println(subscriptionSuccessTextIsVisible.getText());
        return this;
    }

}
