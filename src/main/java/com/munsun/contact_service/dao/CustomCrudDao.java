package com.munsun.contact_service.dao;

import com.munsun.contact_service.models.Contact;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomCrudDao extends Repository<Contact, Long> {
    Contact save(Contact contact);
    Optional<Contact> findById(Long id);
    boolean existsById(Long id);
    Iterable<Contact> findAll();
    long count();
    void deleteById(Long aLong);
    void deleteAll();
    List<Contact> save(List<Contact> contacts);
}