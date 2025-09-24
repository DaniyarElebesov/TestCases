package com.gucci.layers.web.page.headerMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class ProductsPage extends BasePage<ProductsPage> {

    public SelenideElement searchProductInput = $(By.id("search_product"));
    public SelenideElement submitSearchBtn = $(By.id("submit_search"));
    public SelenideElement brandsOnLeftSideBar = $x("//div[@class='brands-name']");
    public SelenideElement featuresItemsForm = $(".features_items");
    public ElementsCollection products = featuresItemsForm.$$x("div[class='col-sm-4']");
    public SelenideElement viewProductBtn1 = $x("//a[@href='/product_details/1']");
    public SelenideElement searchInput = $("input[name='search']");
    public SelenideElement searchBtn = $("input[name='search'] + button");
    public SelenideElement searchedProductsHeader = $x("//h2[text()='Searched Products']");
    public ElementsCollection searchProductList = featuresItemsForm.$$("col-sm-4");
    public SelenideElement searchedProductName = $x("//div[@class='overlay-content']/p");
    public SelenideElement allProductsIsVisible = $x("//h2[@class='title text-center']");
    public SelenideElement firstProductAddToCart = $x("//div[@class='productinfo text-center']/a[@data-product-id='1']");
    public SelenideElement continueShoppingBtn = $x("//button[@class='btn btn-success close-modal btn-block']");
    public SelenideElement secondProductAddToCart = $x("//div[@class='productinfo text-center']/a[@data-product-id='2']");
    public SelenideElement viewCartBtn = $x("//u[text()='View Cart']");
    public ElementsCollection allProducts = $$x("//ul[@class='nav nav-pills nav-justified']/../preceding-sibling::div/div/p");
    public ElementsCollection allProductsInTheCart = $$x("//tbody//h4/a");
    public ElementsCollection allProductsInTheCartPrice = $$x("//td[@class='cart_price']");
    public ElementsCollection allProductsInTheCartQuantity = $$x("//td[@class='cart_quantity']");
    public ElementsCollection totalPrice = $$x("//p[@class='cart_total_price']");


    @Override
    public ProductsPage waitForPageLoaded() {
        allProductsIsVisible.shouldHave(Condition.exactText("All Products"));
        return this;
    }

    @Step("List of all products")
    public ProductsPage printAllProducts() {
        allProducts.forEach(product -> System.out.println(product.getText()));
        return this;
    }

    @Step("Click 1st view product button")
    public ProductDetailsPage clickViewProductButton() {
        elementManager.clickWithScroll(viewProductBtn1);
        return Selenide.page(ProductDetailsPage.class);
    }

    @Step("Input in the search field and click search button")
    public ProductsPage inputSearch(String text) {
        elementManager.input(searchInput, text);
        searchBtn.click();
        return this;
    }

    @Step("Visibility of header 'Searched Products'")
    public ProductsPage searchedProductsIsVisible() {
        searchedProductsHeader.shouldBe(Condition.visible);
        return this;
    }

    @Step("Hover over first product and click 'Add to cart'")
    public ProductsPage clickFirstProductAddToCart() {
        elementManager.clickWithScroll(firstProductAddToCart);
        return this;
    }

    @Step("Click 'Continue Shopping' button")
    public ProductsPage clickContinueShoppingButton() {
        elementManager.clickWithScroll(continueShoppingBtn);
        return this;
    }

    @Step("Hover over second product and click 'Add to cart'")
    public ProductsPage clickSecondProductAddToCart() {
        elementManager.clickWithScroll(secondProductAddToCart);
        return this;
    }

    @Step("Click 'View Cart' button")
    public ProductsPage clickViewCartButton() {
        elementManager.clickWithScroll(viewCartBtn);
        return this;
    }

    @Step("List of products in the cart")
    public ProductsPage listOfProductsInTheCart() {
        allProductsInTheCart.forEach(productsInTheCart -> System.out.println(productsInTheCart.getText()));
        return this;
    }

    @Step("Verify their prices, quantity and total price")
    public ProductsPage verifyPriceQtyTotalPrice() {
        allProductsInTheCartPrice.forEach(price -> System.out.println("Price: " + price.getText()));
        allProductsInTheCartQuantity.forEach(quantity -> System.out.println("Quantity: " + quantity.getText()));
        totalPrice.forEach(totalPrize -> System.out.println("Total price: " + totalPrize.getText()));
        return this;
    }

}
