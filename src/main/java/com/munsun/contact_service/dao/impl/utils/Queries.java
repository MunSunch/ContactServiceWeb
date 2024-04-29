package com.munsun.contact_service.dao.impl.utils;

public enum Queries {
    SAVE_CONTACT("""
        insert into contact_service.contacts(firstname, lastname, email, phone) values
        (:firstname, :lastname, :email, :phone);
        """),
    UPDATE_CONTACT("""
            update contact_service.contacts
            set firstname=:firstname, lastname=:lastname, email=:email, phone=:phone
            where id=:id;
            """),
    FIND_BY_ID("""
            select *
            from contact_service.contacts
            where id=:id
            """),
    FIND_ALL("""
            select *
            from contact_service.contacts;
            """),
    COUNT_ROW("""
            select count(*)
            from contact_service.contacts;
            """),
    DELETE_BY_ID("""
            delete from contact_service.contacts where id=:id;
            """),
    DELETE_ALL("""
            delete from contact_service.contacts;
            """);

    private String query;

    Queries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
