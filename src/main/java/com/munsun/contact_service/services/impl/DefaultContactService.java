package com.munsun.contact_service.services.impl;

import com.munsun.contact_service.exceptions.ContactNotFoundException;
import com.munsun.contact_service.models.Contact;
import com.munsun.contact_service.dao.ContactDao;
import com.munsun.contact_service.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultContactService implements ContactService {
    private final ContactDao repository;

    @Override
    public List<Contact> getContacts() {
        return (List<Contact>) (repository.findAll());
    }

    @Override
    public Contact getEmptyContact() {
        return new Contact();
    }

    @Override
    public void addContact(Contact contact) {
        repository.save(contact);
    }

    @Override
    public Contact getContact(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new ContactNotFoundException("Contact not found"));
    }

    @Override
    public void removeContact(Long id) {
        repository.deleteById(id);
    }
}
