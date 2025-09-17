package com.gucci.layers.web.page.headerMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class ProductDetailsPage extends BasePage<ProductDetailsPage> {

    public SelenideElement productDetailIsVisible = $x("//img[@class='newarrival']");
    public SelenideElement productName = $x("//h2[text()='Blue Top']");
    public SelenideElement productCategory = $x("//h2[text()='Blue Top']/following-sibling::p[1]");
    public SelenideElement productAvailability = $x("//h2[text()='Blue Top']/following-sibling::p[2]");
    public SelenideElement productCondition = $x("//h2[text()='Blue Top']/following-sibling::p[3]");
    public SelenideElement productBrand = $x("//h2[text()='Blue Top']/following-sibling::p[4]");
    public SelenideElement productPrice = $x("//h2[text()='Blue Top']/following-sibling::span/span");

    @Override
    public ProductDetailsPage waitForPageLoaded() {
        productDetailIsVisible.shouldBe(Condition.visible);
        return this;
    }

    @Step("Product name, category, price, availability, condition, brand is visible")
    public ProductDetailsPage visibilityOfDetails() {
        System.out.println("__________________________________");
        productName.shouldBe(Condition.visible);
        System.out.println(productName.getText());
        productCategory.shouldBe(Condition.visible);
        System.out.println(productCategory.getText());
        productPrice.shouldBe(Condition.visible);
        System.out.println(productPrice.getText());
        productAvailability.shouldBe(Condition.visible);
        System.out.println(productAvailability.getText());
        productCondition.shouldBe(Condition.visible);
        System.out.println(productCondition.getText());
        productBrand.shouldBe(Condition.visible);
        System.out.println(productBrand.getText());
        return this;
    }
}
