package edu.hw7.task3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimplePersonDatabaseTest {

    private PersonDatabase db;

    @BeforeEach
    void init() {
        db = new SimplePersonDatabase();
    }

    @Test
    void add() throws NonuniqueIDException {
        var person = new Person(1, "name", "address", "phone");

        db.add(person);

        Assertions.assertTrue(db.findByName(person.name()).contains(person));
        Assertions.assertTrue(db.findByAddress(person.address()).contains(person));
        Assertions.assertTrue(db.findByPhone(person.phoneNumber()).contains(person));
    }

    @Test
    void delete() throws NonuniqueIDException {
        var person = new Person(1, "name", "address", "phone");

        db.add(person);

        db.delete(person.id());
        Assertions.assertNull(db.findByName(person.name()));
        Assertions.assertNull(db.findByAddress(person.address()));
        Assertions.assertNull(db.findByPhone(person.phoneNumber()));
    }

    @Test
    void addNotuniqueId(){
        var person = new Person(1, "name", "address", "phone");
        try {
            db.add(person);
        } catch (NonuniqueIDException ignored) {
        }
        Assertions.assertThrows(NonuniqueIDException.class, ()->db.add(person));

    }

    @Test
    void testFindByName() throws NonuniqueIDException {
        var person = new Person(1, "name", "address", "phone");
        db.add(person);

        Assertions.assertTrue(db.findByName("name").contains(person));
    }

    @Test
    void testFindByAddress() throws NonuniqueIDException {
        var person = new Person(1, "name", "address", "phone");
        db.add(person);

        Assertions.assertTrue(db.findByAddress("address").contains(person));
    }

    @Test
    void testFindByPhone() throws NonuniqueIDException {
        var person = new Person(1, "name", "address", "phone");
        db.add(person);

        Assertions.assertTrue(db.findByPhone("phone").contains(person));
    }
}
