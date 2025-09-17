package com.gucci.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    String title;
    String name;
    String email;
    String password;
    String dateOfBirth;
    String firstName;
    String lastName;
    String company;
    String address1;
    String address2;
    String country;
    String city;
    String state;
    String zipcode;
    String mobileNumber;
    String text;
    String subject;


}
