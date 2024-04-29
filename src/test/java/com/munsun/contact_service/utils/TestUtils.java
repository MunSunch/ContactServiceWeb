package com.munsun.contact_service.utils;

import com.munsun.contact_service.models.Contact;

import java.util.List;

public class TestUtils {
    public static Contact getTransientContactMunir() {
        return Contact.builder()
                .firstname("Munir")
                .lastname("Sunchalyaev")
                .email("msunchalyaev@gmail.com")
                .phone("79873022923")
                .build();
    }

    public static Contact getTransientContactAndrey() {
        return Contact.builder()
                .firstname("Andrey")
                .lastname("Sunchalyaev")
                .email("andrey1997@yandex.ru")
                .phone("79869963031")
                .build();
    }

    public static Contact getTransientContactNikolay() {
        return Contact.builder()
                .firstname("Kolya")
                .lastname("Sunchalyaev")
                .email("kaka20011@yandex.ru")
                .phone("-")
                .build();
    }

    public static List<Contact> getTransientContacts() {
        return List.of(getTransientContactMunir(),
                getTransientContactNikolay(),
                getTransientContactAndrey());
    }
}
