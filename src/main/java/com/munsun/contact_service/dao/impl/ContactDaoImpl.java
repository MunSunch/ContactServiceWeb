package com.munsun.contact_service.dao.impl;

import com.munsun.contact_service.exceptions.ContactNotFoundException;
import com.munsun.contact_service.models.Contact;
import com.munsun.contact_service.dao.ContactDao;
import com.munsun.contact_service.dao.impl.mapping.ContactRowMapper;
import com.munsun.contact_service.dao.impl.utils.Queries;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
@RequiredArgsConstructor
public class ContactDaoImpl implements ContactDao {
    private final JdbcClient jdbcClient;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final ContactRowMapper contactRowMapper;

    @Override
    public Contact save(Contact contact) {
        if(Objects.nonNull(contact.getId())) {
            return update(contact);
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcClient.sql(Queries.SAVE_CONTACT.getQuery())
                    .param(Contact.Fields.firstname, contact.getFirstname())
                    .param(Contact.Fields.lastname, contact.getLastname())
                    .param(Contact.Fields.email, contact.getEmail())
                    .param(Contact.Fields.phone, contact.getPhone())
                    .update(keyHolder);
            contact.setId(getGeneratedId(keyHolder.getKeyList().get(0)));
            return contact;
        }
    }

    private Long getGeneratedId(Map<String,Object> map) {
        int id = (int) map.get("id");
        return (long)id;
    }

    private Contact update(Contact contact) {
        if(!existsById(contact.getId())) {
            throw new ContactNotFoundException("Contact with ID not found");
        }
        jdbcClient.sql(Queries.UPDATE_CONTACT.getQuery())
                .param(Contact.Fields.id, contact.getId())
                .param(Contact.Fields.firstname, contact.getFirstname())
                .param(Contact.Fields.lastname, contact.getLastname())
                .param(Contact.Fields.email, contact.getEmail())
                .param(Contact.Fields.phone, contact.getPhone())
                .update();
        return contact;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return jdbcClient.sql(Queries.FIND_BY_ID.getQuery())
                                    .param("id", id)
                                    .query(contactRowMapper)
                                    .optional();
    }

    @Override
    public boolean existsById(Long id) {
        Optional<Contact> contact = findById(id);
        return contact.isPresent();
    }

    @Override
    public Iterable<Contact> findAll() {
        return jdbcClient.sql(Queries.FIND_ALL.getQuery())
                .query(contactRowMapper)
                .list();
    }

    @Override
    public long count() {
        return jdbcClient.sql(Queries.COUNT_ROW.getQuery())
                .query(Long.class)
                .single();
    }

    @Override
    public void deleteById(Long id) {
        jdbcClient.sql(Queries.DELETE_BY_ID.getQuery())
                .param("id", id)
                .update();
    }

    @Override
    public List<Contact> save(List<Contact> contacts) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedJdbcTemplate.batchUpdate(Queries.SAVE_CONTACT.getQuery(),
                SqlParameterSourceUtils.createBatch(contacts),
                keyHolder);
        for (int i = 0; i < contacts.size(); i++) {
            long generatedId = getGeneratedId(keyHolder.getKeyList().get(i));
            contacts.get(i).setId(generatedId);
        }
        return contacts;
    }

    @Override
    public void deleteAll() {
        jdbcClient.sql(Queries.DELETE_ALL.getQuery())
                .update();
    }
}
