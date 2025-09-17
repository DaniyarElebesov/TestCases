package com.gucci.data;

public record User(String name, int age) {
    public void sayHello(String text) {
        System.out.println(name + " says: " + text);
    }
}
