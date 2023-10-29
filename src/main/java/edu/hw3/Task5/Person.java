package edu.hw3.Task5;

import java.util.Objects;

public class Person implements Comparable<Person> {
    private final String lastname;
    private final String firstname;

    private Person(String firstname, String lastname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public static Person of(String fullname) {
        if (fullname == null) {
            throw new IllegalArgumentException("fullname must be not null");
        }

        String trimmedFullname = fullname.trim();
        if (trimmedFullname.isEmpty()) {
            throw new IllegalArgumentException("fullname must be not empty");
        }

        String[] names = trimmedFullname.split(" ");
        if (names.length > 1) {
            return new Person(names[0], names[1]);
        } else {
            return new Person(names[0], null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person person)) {
            return false;
        }
        if (lastname == null) {
            return Objects.equals(firstname, (person.lastname == null ? person.firstname : person.lastname));
        } else {
            return Objects.equals(lastname, (person.lastname == null ? person.firstname : person.lastname));
        }
    }

    @Override
    public int hashCode() {
        if (lastname == null) {
            Objects.hash(firstname);
        }
        return Objects.hash(lastname, firstname);
    }

    @Override
    public int compareTo(Person person) {
        if (lastname == null) {
            return firstname.compareTo(person.lastname == null ? person.firstname : person.lastname);
        } else {
            return lastname.compareTo(person.lastname == null ? person.firstname : person.lastname);
        }
    }

    @Override public String toString() {
        if (lastname == null) {
            return firstname;
        }
        return "%s %s".formatted(firstname, lastname);
    }
}
