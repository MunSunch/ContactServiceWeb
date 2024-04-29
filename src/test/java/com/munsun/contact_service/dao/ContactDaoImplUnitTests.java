package com.munsun.contact_service.dao;

import com.munsun.contact_service.models.Contact;
import com.munsun.contact_service.dao.impl.mapping.ContactRowMapper;
import com.munsun.contact_service.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(ContactRowMapper.class)
public class ContactDaoImplUnitTests {
    @Autowired
    @Qualifier("contactDaoImpl")
    private ContactDao contactRepository;

    @DisplayName("Positive test save contact")
    @Test
    public void givenTransientContact_whenSaveContact_thenGetContactByIdNotNull() {
        Contact transientContact = TestUtils.getTransientContactMunir();

        Contact persistContact = contactRepository.save(transientContact);

        assertThat(persistContact)
                .isNotNull()
                .extracting(Contact::getId).isNotNull();
    }

    @DisplayName("Repeated test get count contacts")
    @ParameterizedTest
    @ValueSource(longs = {0,1,2,3})
    public void givenSomeCountSavedContacts_whenGetCountContact_thenReturnCountContacts(long expectedCount) {
        for (int i = 0; i < expectedCount; i++) {
            contactRepository.save(TestUtils.getTransientContactMunir());
        }

        long actualCount = contactRepository.count();

        assertThat(actualCount)
                .isEqualTo(expectedCount);
    }

    @DisplayName("Test get contact by id")
    @Test
    public void givenThreeSavedContacts_whenContactGetById_thenReturnTrueContact() {
        int countContacts = 3;
        long expectedIdContact = 0;
        for (int i = 0; i < countContacts; i++) {
            expectedIdContact = contactRepository.save(TestUtils.getTransientContactMunir()).getId();
        }

        Optional<Contact> actualContact = contactRepository.findById(expectedIdContact);

        assertThat(actualContact)
                .isPresent()
                .get().extracting(Contact::getId)
                    .isNotNull()
                    .isEqualTo(expectedIdContact);
    }

    @DisplayName("Test get not exists contact by id")
    @Test
    public void givenEmptyRepositoryAndNotExistsId_whenContactGetById_thenReturnNull() {
        long notExistsIdContact = 1000L;

        Optional<Contact> actualContact = contactRepository.findById(notExistsIdContact);

        assertThat(actualContact).isEmpty();
    }

    @DisplayName("Test exists contact by id")
    @Test
    public void givenThreeSavedContacts_whenContactExistsById_thenReturnTrue() {
        contactRepository.save(TestUtils.getTransientContactMunir());
        contactRepository.save(TestUtils.getTransientContactAndrey());
        long expectedIdContact = contactRepository.save(TestUtils.getTransientContactMunir()).getId();

        boolean isExists = contactRepository.existsById(expectedIdContact);

        assertThat(isExists).isTrue();
    }

    @DisplayName("Test exists not exists contact by id")
    @Test
    public void givenEmptyRepository_whenContactGetById_thenReturnFalse() {
        long notExistsIdContact = 3333L;

        boolean isExists = contactRepository.existsById(notExistsIdContact);

        assertThat(isExists).isFalse();
    }

    @DisplayName("Test get all contacts")
    @Test
    public void givenThreeSavedContacts_whenCallFindAll_thenReturnListWithThreeContacts() {
        int expectedSizeList = 3;
        contactRepository.save(TestUtils.getTransientContactMunir());
        contactRepository.save(TestUtils.getTransientContactAndrey());
        contactRepository.save(TestUtils.getTransientContactNikolay());

        List<Contact> contacts = (List<Contact>) contactRepository.findAll();

        assertThat(contacts)
                .hasSize(expectedSizeList)
                .allMatch(Objects::nonNull);
    }

    @DisplayName("Test delete exists contact by id")
    @Test
    public void givenSavedContact_whenDeleteContactByID_thenFindByIdReturnNull() {
        long id = contactRepository.save(TestUtils.getTransientContactMunir()).getId();

        contactRepository.deleteById(id);
        Optional<Contact> removedContact = contactRepository.findById(id);

        assertThat(removedContact).isEmpty();
    }

    @DisplayName("Test delete all contacts")
    @Test
    public void givenThreeSavedContacts_whenDeleteAll_thenFindAllReturnNull() {
        contactRepository.save(TestUtils.getTransientContactMunir());
        contactRepository.save(TestUtils.getTransientContactAndrey());
        contactRepository.save(TestUtils.getTransientContactNikolay());

        contactRepository.deleteAll();
        List<Contact> contacts = (List<Contact>) contactRepository.findAll();

        assertThat(contacts).isEmpty();
    }

    @DisplayName("Test batch save contacts")
    @Test
    public void givenListContacts_whenSaveAll_thenFindAllReturnContacts() {
        List<Contact> contacts = TestUtils.getTransientContacts();

        List<Contact> savedContacts = contactRepository.save(contacts);
        List<Contact> resultFindAll = (List<Contact>)contactRepository.findAll();

        assertThat(resultFindAll)
                .allMatch(contact -> contact.getId() != null);
        assertThat(savedContacts)
                .allMatch(contact -> contact.getId() != null);
    }

    @DisplayName("Test update contact")
    @Test
    public void givenSavedContactAndOtherContact_whenUpdateContact_thenFindByIdReturnUpdatedContact() {
        long id = contactRepository.save(TestUtils.getTransientContactMunir()).getId();
        Contact anotherContact = TestUtils.getTransientContactNikolay();
            anotherContact.setId(id);

        contactRepository.save(anotherContact);
        Optional<Contact> updatedContact = contactRepository.findById(id);

        assertThat(updatedContact)
                .isPresent()
                .get().extracting(Contact::getFirstname,
                                  Contact::getLastname,
                                  Contact::getEmail,
                                  Contact::getPhone)
                .isEqualTo(List.of(anotherContact.getFirstname(),
                                    anotherContact.getLastname(),
                                    anotherContact.getEmail(),
                                    anotherContact.getPhone()));

    }
}