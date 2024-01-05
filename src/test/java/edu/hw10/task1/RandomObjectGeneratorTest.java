package edu.hw10.task1;

import edu.hw10.task1.entity.InterfaceExample;
import edu.hw10.task1.entity.POJOPerson;
import edu.hw10.task1.entity.RecordPerson;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RandomObjectGeneratorTest {

    @Test
    void nextObjectConstructorPojoTest()
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Arrange
        var rog = new RandomObjectGenerator();

        //Act
        POJOPerson person = (POJOPerson) rog.nextObject(POJOPerson.class);

        //Assert
        Assertions.assertTrue(person.getAge() >= 0 && person.getAge() <= 150);
        Assertions.assertNotNull(person.getFirstname());
        Assertions.assertNotNull(person.getSurname());
        Assertions.assertNotNull(person.getPatronymic());
    }

    @Test
    void nextObjectFactoryMethodTest()
        throws InvocationTargetException, IllegalAccessException {
        //Arrange
        var rog = new RandomObjectGenerator();

        //Act
        POJOPerson person = (POJOPerson) rog.nextObject(POJOPerson.class, "create");

        //Assert
        Assertions.assertTrue(person.getAge() >= 0 && person.getAge() <= 150);
        Assertions.assertNotNull(person.getFirstname());
        Assertions.assertNotNull(person.getSurname());
        Assertions.assertNotNull(person.getPatronymic());
    }

    @Test
    void nextObjectConstructorRecordTest()
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Arrange
        var rog = new RandomObjectGenerator();

        //Act
        RecordPerson person = (RecordPerson) rog.nextObject(RecordPerson.class);

        //Assert
        Assertions.assertTrue(person.age() >= 0 && person.age() <= 150);
        Assertions.assertNotNull(person.firstname());
        Assertions.assertNotNull(person.surname());
        Assertions.assertNotNull(person.patronymic());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "privateStaticCreate", "publicNonStaticCreate", "nonPersonReturnTypeCreate"})
    void nextObjectEmptyMethodTest(String factoryMethod) {
        //Arrange
        var rog = new RandomObjectGenerator();
        Throwable expected = new IllegalArgumentException();

        //Act
        Throwable t = org.assertj.core.api.Assertions
            .catchThrowable(() -> rog.nextObject(POJOPerson.class, factoryMethod));

        //Assert
        Assertions.assertEquals(expected.getClass(), t.getClass());
    }

    @Test
    void nextObjectNoConstructorTest() {
        //Arrange
        var rog = new RandomObjectGenerator();
        Throwable expected = new NoSuchMethodException();

        //Act
        Throwable t = org.assertj.core.api.Assertions
            .catchThrowable(() -> rog.nextObject(InterfaceExample.class));

        //Assert
        Assertions.assertEquals(expected.getClass(), t.getClass());
    }

}
