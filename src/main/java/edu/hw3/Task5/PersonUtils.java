package edu.hw3.Task5;

import java.util.Arrays;
import java.util.Comparator;

public class PersonUtils {
    private PersonUtils() {
    }

    public static Person[] parseContacts(Person[] people, SortOrder order) {
        if (people == null || people.length == 0) {
            return new Person[0];
        }
        var sortedPeople = people.clone();
        if (order.equals(SortOrder.ASC)) {
            Arrays.sort(sortedPeople);
        } else if (order.equals(SortOrder.DESC)) {
            Arrays.sort(sortedPeople, Comparator.reverseOrder());
        }
        return sortedPeople;
    }
}
