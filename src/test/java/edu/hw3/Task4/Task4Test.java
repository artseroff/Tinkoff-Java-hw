package edu.hw3.Task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task4Test {

    @Test
    public void parseContacts() {

    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void parseContacts(Person[] inputPeople, SortOrder order, Person[] expected) {
        var actual = PersonUtils.parseContacts(inputPeople, order);
        Assertions.assertArrayEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] provideParameters() {
        return new Arguments[] {
            Arguments.of(
                new Person[] {Person.of("John Locke"), Person.of("Thomas Aquinas"),
                    Person.of("David Hume"), Person.of("Rene Descartes")},
                SortOrder.ASC,
                new Person[] {Person.of("Thomas Aquinas"), Person.of("Rene Descartes"),
                    Person.of("David Hume"), Person.of("John Locke")}
            ),
            Arguments.of(
                new Person[] {Person.of("John Locke"), Person.of("Albert"),
                    Person.of("David Hume"), Person.of("Donald")},
                SortOrder.ASC,
                new Person[] {Person.of("Albert"), Person.of("Donald"),
                    Person.of("David Hume"), Person.of("John Locke")}
            ),
            Arguments.of(
                new Person[] {Person.of("Donald"), Person.of("Alex"),
                    Person.of("Bob"), Person.of("Sergey")},
                SortOrder.ASC,
                new Person[] {Person.of("Alex"), Person.of("Bob"),
                    Person.of("Donald"), Person.of("Sergey")}
            ),
            Arguments.of(
                new Person[] {Person.of("Donald Grey"), Person.of("Alex White"),
                    Person.of("Bob")},
                SortOrder.ASC,
                new Person[] {Person.of("Bob"), Person.of("Donald Grey"),
                    Person.of("Alex White")}
            ),
            Arguments.of(
                new Person[] {Person.of("Donald"), Person.of("Alex"),
                    Person.of("Bob"), Person.of("Sergey")},
                SortOrder.DESC,
                new Person[] { Person.of("Sergey"),  Person.of("Donald"), Person.of("Bob"), Person.of("Alex")}
            ),
            Arguments.of(
                new Person[] {Person.of("Donald Grey"), Person.of("Alex White"),
                    Person.of("Bob")},
                SortOrder.DESC,
                new Person[] {Person.of("Alex White"), Person.of("Donald Grey"),
                    Person.of("Bob")}
            )
        };
    }
}
