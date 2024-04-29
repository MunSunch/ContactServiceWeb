package com.munsun.contact_service.services;

import com.munsun.contact_service.models.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getContacts();
    Contact getEmptyContact();
    void addContact(Contact contact);
    Contact getContact(Long id);
    void removeContact(Long id);
}