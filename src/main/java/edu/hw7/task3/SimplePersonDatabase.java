package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimplePersonDatabase implements PersonDatabase {

    private final Map<Integer, Person> idMap = new HashMap<>();
    private final Map<String, List<Person>> nameMap = new HashMap<>();
    private final Map<String, List<Person>> addressMap = new HashMap<>();
    private final Map<String, List<Person>> phoneMap = new HashMap<>();


    @Override
    public void add(Person person) throws NonuniqueIDException {
        if (idMap.containsKey(person.id())) {
            throw new NonuniqueIDException("%d is not unique id!".formatted(person.id()));
        }
        idMap.put(person.id(), person);
        addToAttributeMap(person.name(), person, nameMap);
        addToAttributeMap(person.address(), person, addressMap);
        addToAttributeMap(person.phoneNumber(), person, phoneMap);
    }

    @Override
    public void delete(int id) {
        var deletedPerson = idMap.remove(id);
        if (deletedPerson == null) {
            return;
        }
        removeFromAttributeMap(deletedPerson.name(), deletedPerson, nameMap);
        removeFromAttributeMap(deletedPerson.address(), deletedPerson, addressMap);
        removeFromAttributeMap(deletedPerson.phoneNumber(), deletedPerson, phoneMap);
    }

    @Override
    public List<Person> findByName(String name) {
        return nameMap.get(name);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return addressMap.get(address);
    }

    @Override
    public List<Person> findByPhone(String phone) {
        return phoneMap.get(phone);
    }

    private void addToAttributeMap(String key, Person person, Map<String, List<Person>> attributeMap) {
        List<Person> people = attributeMap.computeIfAbsent(key, k -> new ArrayList<>());
        people.add(person);
    }

    private void removeFromAttributeMap(String key, Person deletedPerson, Map<String, List<Person>> map) {
        List<Person> people = map.get(key);
        people.remove(deletedPerson);
        if (people.isEmpty()) {
            map.remove(key);
        }
    }

}
