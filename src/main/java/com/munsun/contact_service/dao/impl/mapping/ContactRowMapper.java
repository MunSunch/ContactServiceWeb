package com.munsun.contact_service.dao.impl.mapping;

import com.munsun.contact_service.models.Contact;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ContactRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Contact.builder()
                .id(rs.getLong(1))
                .firstname(rs.getString(2))
                .lastname(rs.getString(3))
                .email(rs.getString(4))
                .phone(rs.getString(5))
                .build();
    }
}
