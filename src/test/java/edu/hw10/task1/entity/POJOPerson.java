package edu.hw10.task1.entity;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;

public class POJOPerson {
    private int age;
    private String firstname;
    private String surname;
    private String patronymic;

    public POJOPerson(@Min(0) @Max(150) int age, @NotNull String firstname, @NotNull String surname, String patronymic) {
        this.age = age;
        this.firstname = firstname;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public static POJOPerson create(@Min(0) @Max(150) int age, @NotNull String firstname, @NotNull String surname, String patronymic) {
        return new POJOPerson(age,firstname,surname,patronymic);
    }

    private static POJOPerson privateStaticCreate(int age, String firstname, String surname, String patronymic) {
        return new POJOPerson(age,firstname,surname,patronymic);
    }

    public static int nonPersonReturnTypeCreate() {
        return 0;
    }

    public POJOPerson publicNonStaticCreate(int age, String firstname, String surname, String patronymic) {
        return new POJOPerson(age,firstname,surname,patronymic);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
