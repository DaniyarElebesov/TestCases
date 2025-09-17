package com.gucci.data.MockDataService;


import net.datafaker.Faker;

import java.util.Random;

public class MockDataService {

    Faker faker = new Faker();
    Random random = new Random();

    public String generateRandomName() {
        return faker.name().firstName();
    }

    public String generateRandomLastName() {
        return faker.name().lastName();
    }

    public String generateRandomEmail() {
        return faker.internet().emailAddress();
    }

    public String generateRandomAddress() {
        return faker.address().fullAddress();
    }

    public String generateRandomState() {
        return faker.address().state();
    }

    public String generateRandomZipcode() {
        return faker.address().zipCode();
    }

    public String generateRandomCountry() {
        return faker.country().name();
    }

    public String generateRandomAge() {
        return faker.numerify("39");
    }

    public String generateRandomSalary() {
        return faker.numerify("350000");
    }

    public String generateRandomNumber() {
        return "0" + (550 + random.nextInt(5)) + (100 + random.nextInt(800)) + "" + (100 + random.nextInt(800));
    }

    public String generateRandomPassword() {
        return faker.harryPotter().character();
    }

    public String generateRandomCompany() {
        return faker.company().name();
    }

    public String generateRandomCity() {
        return faker.country().capital();
    }

    public String generateRandomText() {
        return faker.text().text();
    }

    public String generateRandomSubject() {
        return faker.australia().locations();
    }


}
