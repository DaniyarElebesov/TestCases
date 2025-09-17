package com.gucci.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Country {
    INDIA("India"),
    US("United States"),
    CANADA("Canada"),
    AUSTRALIA("Australia"),
    ISRAEL("Israel"),
    NEW_ZEALAND("New Zealand"),
    SINGAPORE("Singapore");

    private final String country;
}
