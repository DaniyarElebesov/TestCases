package com.gucci.data.MockDataService;

import com.gucci.entities.User;


public class UserFactory {
    static MockDataService mockDataService = new MockDataService();

    private UserFactory() {

    }


    public static User createDefaultUser() {
        return User.builder()
                .title("Mr")
                .name(mockDataService.generateRandomName())
                .email(mockDataService.generateRandomEmail())
                .password(mockDataService.generateRandomPassword())
                .dateOfBirth("17/July/2020")
                .firstName(mockDataService.generateRandomName())
                .lastName(mockDataService.generateRandomLastName())
                .company(mockDataService.generateRandomCompany())
                .address1(mockDataService.generateRandomAddress())
                .address2(mockDataService.generateRandomAddress())
                .country(mockDataService.generateRandomCountry())
                .state(mockDataService.generateRandomState())
                .city(mockDataService.generateRandomCity())
                .zipcode(mockDataService.generateRandomZipcode())
                .mobileNumber(mockDataService.generateRandomNumber())
                .text(mockDataService.generateRandomText())
                .subject(mockDataService.generateRandomSubject())
                .build();
    }
}
