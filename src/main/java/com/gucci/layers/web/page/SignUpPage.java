package com.gucci.layers.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.entities.User;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class SignUpPage extends BasePage <SignUpPage>{

    public SelenideElement enterAccountInformationIsVisible = $x("//b[text()='Enter Account Information']");
    public SelenideElement password = $(By.id("password"));
    public SelenideElement selectDay = $(By.id("days"));
    public SelenideElement selectMonth = $(By.id("months"));
    public SelenideElement selectYear  = $(By.id("years"));
    public SelenideElement checkBoxNewsletter = $(By.id("newsletter"));
    public SelenideElement checkboxOption = $(By.id("optin"));
    public SelenideElement inputFirstname = $(By.id("first_name"));
    public SelenideElement inputLastname = $(By.id("last_name"));
    public SelenideElement inputCompany = $(By.id("company"));
    public SelenideElement inputAddress = $(By.id("address1"));
    public SelenideElement inputAddress2 = $(By.id("address2"));
    public SelenideElement inputCountry = $(By.id("country"));
    public SelenideElement inputState = $(By.id("state"));
    public SelenideElement inputCity = $(By.id("city"));
    public SelenideElement inputZip = $(By.id("zipcode"));
    public SelenideElement inputNumber = $(By.id("mobile_number"));
    public SelenideElement clickCreateAccount = $x("//button[text()='Create Account']");
    public SelenideElement name = $(By.id("name"));
    public SelenideElement email = $(By.id("email"));




    @Override
    public SignUpPage waitForPageLoaded() {
        enterAccountInformationIsVisible.shouldHave(Condition.exactText("Enter Account Information"));
        return this;
    }

    public AccountCreatedPage signUpNewUser(User user) {
        elementManager.click($x("//input[@value='" + user.getTitle() + "']"))
                .verifyAttributeValue(name,"value", user.getName())
                .verifyAttributeValue(email, "value", user.getEmail())
                .input(password, user.getPassword());
        selectDateMonthYearCalendar(user.getDateOfBirth());

        elementManager.input(inputFirstname, user.getFirstName())
                .input(inputLastname, user.getLastName())
                .input(inputCompany, user.getCompany())
                .input(inputAddress, user.getAddress1())
                .input(inputAddress2, user.getAddress2())
                .input(inputCountry, user.getCountry())
                .input(inputState, user.getState())
                .input(inputCity, user.getCity())
                .input(inputZip, user.getZipcode())
                .input(inputNumber, user.getMobileNumber())
                .click(clickCreateAccount);

        return Selenide.page(AccountCreatedPage.class);
    }

    public SignUpPage selectDateMonthYearCalendar(String dateMonthYear) {
        String[] dateMonthYearParts = dateMonthYear.split("/");
        String day = dateMonthYearParts[0];
        String month = dateMonthYearParts[1];
        String year = dateMonthYearParts[2];

        elementManager.selectByValue(selectYear, year);
        elementManager.selectByValue(selectMonth, month);
        elementManager.selectByValue(selectDay, day);
        return this;
    }
}
