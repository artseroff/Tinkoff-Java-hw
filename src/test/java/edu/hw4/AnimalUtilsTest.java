package edu.hw4;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AnimalUtilsTest {

    private static final Animal[] A = new Animal[] {
        /*0*/new Animal("Лаки", Animal.Type.DOG, Animal.Sex.F, 4, 400, 6000, false),
        /*1*/new Animal("Терьер", Animal.Type.DOG, Animal.Sex.M, 15, 700, 12000, true),
        /*2*/new Animal("Мурзик Рыжий Домашний", Animal.Type.CAT, Animal.Sex.M, 5, 310, 4000, false),
        /*3*/new Animal("Британец", Animal.Type.CAT, Animal.Sex.M, 10, 390, 7000, false),
        /*4*/new Animal("Спайди", Animal.Type.SPIDER, Animal.Sex.M, 4, 3, 16, true),
        /*5*/new Animal("Капитан Немо", Animal.Type.FISH, Animal.Sex.M, 1, 30, 40, false),
        /*6*/new Animal("Грач Обыкновенный Громкий", Animal.Type.BIRD, Animal.Sex.F, 2, 290, 4, false),
        /*7*/new Animal("Снегирь", Animal.Type.BIRD, Animal.Sex.F, 3, 250, 5, false)
    };

    @ParameterizedTest
    @MethodSource("task1Param")
    public void task1(List<Animal> input, List<Animal> expected) {
        List<Animal> actual = AnimalUtils.task1(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task1Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), List.of(A[4], A[5], A[7], A[6], A[2], A[3], A[0], A[1])),
            Arguments.of(List.of(), List.of())
        };
    }

    @ParameterizedTest
    @MethodSource("task2Param")
    public void task2(List<Animal> input, int k, List<Animal> expected) {
        List<Animal> actual = AnimalUtils.task2(input, k);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void task2InvalidPosition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task2(List.of(A[0]), -5));
    }
    private static Arguments[] task2Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), 100, List.of(A[1], A[3], A[0], A[2], A[5], A[4], A[7], A[6])),
            Arguments.of(List.of(A), 2, List.of(A[1], A[3])),
            Arguments.of(List.of(), 1, List.of())
        };
    }

    @ParameterizedTest
    @MethodSource("task3Param")
    public void task3(List<Animal> input, Map<Animal.Type, Integer> expected) {
        Map<Animal.Type, Integer> actual = AnimalUtils.task3(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task3Param() {
        return new Arguments[] {
            Arguments.of(
                List.of(A),
                Map.of(
                    Animal.Type.DOG,
                    2,
                    Animal.Type.CAT,
                    2,
                    Animal.Type.SPIDER,
                    1,
                    Animal.Type.FISH,
                    1,
                    Animal.Type.BIRD,
                    2
                )
            ),
            Arguments.of(List.of(), Map.of())
        };
    }

    @ParameterizedTest
    @MethodSource("task4Param")
    public void task4(List<Animal> input, Animal expected) {
        Animal actual = AnimalUtils.task4(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task4Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), A[6]),
            Arguments.of(List.of(), null)
        };
    }

    @ParameterizedTest
    @MethodSource("task5Param")
    public void task5(List<Animal> input, Animal.Sex expected) {
        Animal.Sex actual = AnimalUtils.task5(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task5Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), Animal.Sex.M),
            Arguments.of(List.of(), null)
        };
    }

    @ParameterizedTest
    @MethodSource("task6Param")
    public void task6(List<Animal> input, Map<Animal.Type, Animal> expected) {
        Map<Animal.Type, Animal> actual = AnimalUtils.task6(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task6Param() {
        return new Arguments[] {
            Arguments.of(
                List.of(A),
                Map.of(
                    Animal.Type.SPIDER,
                    A[4],
                    Animal.Type.CAT,
                    A[3],
                    Animal.Type.DOG,
                    A[1],
                    Animal.Type.BIRD,
                    A[7],
                    Animal.Type.FISH,
                    A[5]
                )
            ),
            Arguments.of(List.of(), Map.of())
        };
    }

    @ParameterizedTest
    @MethodSource("task7Param")
    public void task7(List<Animal> input, int k, Animal expected) {
        Animal actual = AnimalUtils.task7(input, k);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task7Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), 3, A[2]),
            Arguments.of(List.of(A), 9, null),
            Arguments.of(List.of(), 4, null)
        };
    }

    @Test
    public void task7InvalidPosition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task7(List.of(A[0]), -5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task7(List.of(A[0]), 0));
    }

    @ParameterizedTest
    @MethodSource("task8Param")
    public void task8(List<Animal> input, int k, Animal expected) {
        Optional<Animal> actual = AnimalUtils.task8(input, k);
        Assertions.assertEquals(Optional.ofNullable(expected), actual);
    }

    private static Arguments[] task8Param() {
        return new Arguments[] {
            Arguments.of(List.of(A[0], A[1], A[2]), 500, A[0]),
            Arguments.of(List.of(A[0], A[1], A[2]), 400, A[2]),
            Arguments.of(List.of(A[0], A[1], A[2]), 100, null),
            Arguments.of(List.of(), 9, null)
        };
    }

    @Test
    public void task8InvalidMillimeters() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task8(List.of(A[0]), -5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task8(List.of(A[0]), 0));
    }

    @ParameterizedTest
    @MethodSource("task9Param")
    public void task9(List<Animal> input, Integer expected) {
        Integer actual = AnimalUtils.task9(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task9Param() {
        return new Arguments[] {
            Arguments.of(List.of(A[0], A[4], A[5]), 12),
            Arguments.of(List.of(A[0], A[1], A[2]), 12),
            Arguments.of(List.of(), 0)
        };
    }

    @ParameterizedTest
    @MethodSource("task10Param")
    public void task10(List<Animal> input, List<Animal> expected) {
        List<Animal> actual = AnimalUtils.task10(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task10Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), List.of(A[1], A[2], A[3], A[4], A[5], A[7])),
            Arguments.of(List.of(), List.of())
        };
    }

    @ParameterizedTest
    @MethodSource("task11Param")
    public void task11(List<Animal> input, int k, List<Animal> expected) {
        List<Animal> actual = AnimalUtils.task11(input, k);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task11Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), 1000, List.of()),
            Arguments.of(List.of(A), 300, List.of(A[1])),
            Arguments.of(List.of(), 100, List.of())
        };
    }

    @Test
    public void task11InvalidMillimeters() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task11(List.of(A[0]), -5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task11(List.of(A[0]), 0));
    }

    @ParameterizedTest
    @MethodSource("task12Param")
    public void task12(List<Animal> input, int expected) {
        int actual = AnimalUtils.task12(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task12Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), 6),
            Arguments.of(List.of(), 0)
        };
    }

    @ParameterizedTest
    @MethodSource("task13Param")
    public void task13(List<Animal> input, int k, List<Animal> expected) {
        List<Animal> actual = AnimalUtils.task13(input, k);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task13Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), 2, List.of(A[2], A[6])),
            Arguments.of(List.of(A), 3, List.of()),
            Arguments.of(List.of(A), 1, List.of(A[2], A[5], A[6]))
        };
    }

    @Test
    public void task13InvalidInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task13(List.of(A[0]), -5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnimalUtils.task13(List.of(A[0]), 0));
    }

    @ParameterizedTest
    @MethodSource("task14Param")
    public void task14(List<Animal> input, Animal.Type type, int k, boolean expected) {
        boolean actual = AnimalUtils.task14(input, type, k);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task14Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), Animal.Type.DOG, 2, true),
            Arguments.of(List.of(A), Animal.Type.DOG, 500, true),
            Arguments.of(List.of(A), Animal.Type.DOG, 700, false),
            Arguments.of(List.of(), Animal.Type.DOG, 3, false)
        };
    }

    @Test
    public void task14InvalidMillimeters() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> AnimalUtils.task14(List.of(A[0]), Animal.Type.DOG, -5)
        );
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> AnimalUtils.task14(List.of(A[0]), Animal.Type.DOG, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("task15Param")
    public void task15(List<Animal> input, int l, int r, Map<Animal.Type, Integer> expected) {
        Map<Animal.Type, Integer> actual = AnimalUtils.task15(input, l, r);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task15Param() {
        return new Arguments[] {
            Arguments.of(
                List.of(A),
                2,
                5,
                Map.of(
                    Animal.Type.DOG,
                    6000,
                    Animal.Type.CAT,
                    4000,
                    Animal.Type.BIRD,
                    9,
                    Animal.Type.SPIDER,
                    16
                )
            ),
            Arguments.of(List.of(), 3, 5, Map.of())
        };
    }

    @ParameterizedTest
    @MethodSource("task15InvalidParam")
    public void task15InvalidParameters(List<Animal> input, int l, int r) {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> AnimalUtils.task15(input, l, r)
        );
    }

    private static Arguments[] task15InvalidParam() {
        return new Arguments[] {
            Arguments.of(List.of(), 5, -1),
            Arguments.of(List.of(), -3, 5),
            Arguments.of(List.of(), -3, -5),
            Arguments.of(List.of(), 5, 3),
            Arguments.of(List.of(), 3, 3),
        };
    }

    @ParameterizedTest
    @MethodSource("task16Param")
    public void task16(List<Animal> input, List<Animal> expected) {
        List<Animal> actual = AnimalUtils.task16(input);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task16Param() {
        return new Arguments[] {
            Arguments.of(List.of(A[0], A[1], A[2]), List.of(A[2], A[1], A[0])),
            Arguments.of(List.of(A), List.of(A[3], A[2], A[1], A[0], A[6], A[7], A[5], A[4])),
            Arguments.of(List.of(), List.of())
        };
    }

    @ParameterizedTest
    @MethodSource("task17Param")
    public void task17(List<Animal> input, Animal.Type type1, Animal.Type type2, boolean expected) {
        boolean actual = AnimalUtils.task17(input, type1, type2);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task17Param() {
        return new Arguments[] {
            Arguments.of(List.of(A), Animal.Type.SPIDER, Animal.Type.DOG, false),
            Arguments.of(List.of(A[4], A[0]), Animal.Type.SPIDER, Animal.Type.DOG, true),
            Arguments.of(List.of(A[4], A[1]), Animal.Type.SPIDER, Animal.Type.DOG, false),
            Arguments.of(List.of(A[0], A[2]), Animal.Type.SPIDER, Animal.Type.DOG, false),
            Arguments.of(List.of(), Animal.Type.SPIDER, Animal.Type.DOG, false),
        };
    }

    @ParameterizedTest
    @MethodSource("task18Param")
    public void task18(List<List<Animal>> input, Animal.Type type, Animal expected) {
        Animal actual = AnimalUtils.task18(input, type);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task18Param() {
        return new Arguments[] {
            Arguments.of(
                List.of(
                    List.of(
                        new Animal("Капитан", Animal.Type.FISH, Animal.Sex.M, 1, 30, 40, false),
                        new Animal("Сом", Animal.Type.FISH, Animal.Sex.M, 3, 20, 4000, false)
                    ),
                    List.of(
                        new Animal("Телескоп", Animal.Type.FISH, Animal.Sex.M, 1, 30, 100, false),
                        new Animal("Щука", Animal.Type.FISH, Animal.Sex.M, 2, 15, 3000, false)
                    )
                ),
                Animal.Type.FISH,
                new Animal("Сом", Animal.Type.FISH, Animal.Sex.M, 3, 20, 4000, false)
            ),
            Arguments.of(List.of(), Animal.Type.DOG, null),
            Arguments.of(
                List.of(
                    List.of(new Animal("Сом", Animal.Type.FISH, Animal.Sex.M, 3, 20, 4000, false))),
                Animal.Type.DOG,
                null
            )
        };
    }

    @ParameterizedTest
    @MethodSource("task19Param")
    public void task19(List<Animal> input, Map<String, Set<ValidationError>> expected) {

        Map<String, Set<ValidationError>> actual = AnimalUtils.task19(input);

        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task19Param() {
        return new Arguments[] {
            Arguments.of(
                List.of(
                    new Animal("Капитан", Animal.Type.FISH, Animal.Sex.M, -1, -30, -40, false),
                    new Animal("        ", null, null, 3, 20, 4000, false),
                    new Animal("Телескоп", Animal.Type.FISH, Animal.Sex.M, 1, 30, 100, false)
                ),

                Map.of(
                    "Капитан",
                    Set.of(
                        new ValidationError("age", "< 0"),
                        new ValidationError("height", "<= 0"),
                        new ValidationError("weight", "<= 0")
                    ),
                    "        ",
                    Set.of(
                        new ValidationError("name", "is null or blank"),
                        new ValidationError("type", "is null"),
                        new ValidationError("sex", "is null")
                    ),
                    "Телескоп",
                    Set.of()
                )
            )
        };
    }

    @ParameterizedTest
    @MethodSource("task20Param")
    public void task20(List<Animal> input, Map<String, String> expected) {

        Map<String, String> actual = AnimalUtils.task20(input);

        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] task20Param() {
        return new Arguments[] {
            Arguments.of(
                List.of(
                    new Animal("Капитан", Animal.Type.FISH, Animal.Sex.M, -1, -30, -40, false),
                    new Animal("        ", null, null, 3, 20, 4000, false),
                    new Animal("Телескоп", Animal.Type.FISH, Animal.Sex.M, 1, 30, 100, false)
                ),

                Map.of(
                    "Капитан",
                    "age < 0\nweight <= 0\nheight <= 0",
                    "        ",
                    "name is null or blank\ntype is null\nsex is null",
                    "Телескоп",
                    ""
                )
            )
        };
    }

}
