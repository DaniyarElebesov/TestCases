package com.gucci.layers.web.page.headerMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CartPage extends BasePage {

    public SelenideElement shoppingCartIsVisible = $x("//li[@class='active']");
    public SelenideElement subscriptionIsVisible = $x("//h2[text()='Subscription']");
    public SelenideElement subscriptionEmailInput = $(By.id("susbscribe_email"));
    public SelenideElement subscriptionArrow = $x("//i[@class='fa fa-arrow-circle-o-right']");
    public SelenideElement subscriptionSuccessText = $(By.id("success-subscribe"));

    @Override
    public BasePage waitForPageLoaded() {
        shoppingCartIsVisible.shouldHave(Condition.exactText("Shopping Cart"));
        return this;
    }

    @Step("Scroll down to footer")
    public CartPage scrollToFooter() {
        elementManager.scroll(subscriptionIsVisible);
        return this;
    }

    @Step("Enter email address")
    public CartPage inputEmailInSubscriptionField() {
        elementManager.input(subscriptionEmailInput, "daniyar@gmail.comm");
        return this;
    }

    @Step("Click arrow button")
    public CartPage clickSubscriptionArrow() {
        elementManager.click(subscriptionArrow);
        return this;
    }


}
