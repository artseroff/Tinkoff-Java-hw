package edu.hw4;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class AnimalUtils {

    private static final String INVALID_MILLIMETERS = "k must be more than 0";

    private AnimalUtils() {
    }

    public static List<Animal> task1(@NotNull List<Animal> animals) {
        return animals
            .stream()
            .sorted(Comparator.comparingInt(Animal::height)).collect(Collectors.toList());
    }

    public static List<Animal> task2(@NotNull List<Animal> animals, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("count of animals must be >= 0");
        }
        return animals
            .stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    public static Map<Animal.Type, Integer> task3(@NotNull List<Animal> animals) {
        return animals
            .stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
            ));
    }

    public static Animal task4(@NotNull List<Animal> animals) {
        return animals
            .stream()
            .max(Comparator.comparingInt(o -> o.name().length()))
            .orElse(null);
    }

    public static Animal.Sex task5(@NotNull List<Animal> animals) {
        return animals
            .stream()
            .collect(Collectors.groupingBy(
                Animal::sex,
                Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
            ))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .orElse(new AbstractMap.SimpleEntry<>(null, null))
            .getKey();
    }

    public static Map<Animal.Type, Animal> task6(@NotNull List<Animal> animals) {
        return animals
            .stream()
            .collect(Collectors.toMap(Animal::type, Function.identity(), BinaryOperator
                    .maxBy(Comparator
                            .comparing(Animal::weight)))
            );
    }

    public static Animal task7(@NotNull List<Animal> animals, int k) {
        if (k < 1) {
            throw new IllegalArgumentException("Position of animal must be more than 0");
        }
        return animals
            .stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    /**
     * Get the heaviest animal among animals below k mm
     *
     * @param k height in mm
     * @return Optional object of Animal of the heaviest animal among animals below k mm
     */
    public static Optional<Animal> task8(@NotNull List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(INVALID_MILLIMETERS);
        }
        return animals
            .stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public static Integer task9(@NotNull List<Animal> animals) {

        return animals
            .stream()
            .reduce(0, (a, b) -> a + b.paws(), Integer::sum);
    }

    public static List<Animal> task10(@NotNull List<Animal> animals) {
        return animals
            .stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());
    }

    /**
     * Collect animals that bite and whose height > k
     *
     * @param k height in mm
     * @return list of animals that bite and whose height > k
     */
    public static List<Animal> task11(@NotNull List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(INVALID_MILLIMETERS);
        }
        return animals
            .stream()
            .filter(animal -> animal.bites() && (animal.height() > k))
            .collect(Collectors.toList());
    }

    public static Integer task12(@NotNull List<Animal> animals) {
        return Math.toIntExact(animals
            .stream()
            .filter(animal -> animal.weight() > animal.height())
            .count());
    }

    public static List<Animal> task13(@NotNull List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("Number of words in animal's name must be more than 0");
        }
        return animals
            .stream()
            .filter(animal -> animal.name().split("\\s").length > k)
            .collect(Collectors.toList());
    }

    /**
     * Is there an animal in the list with the type and the height > k mm
     *
     * @param k height in mm
     * @return true if animal with the animal type and the height > k mm exists, else false
     */
    public static boolean task14(@NotNull List<Animal> animals, @NotNull Animal.Type type, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(INVALID_MILLIMETERS);
        }
        return animals
            .stream()
            .anyMatch(animal -> animal.type().equals(type) && animal.height() > k);
    }

    public static Map<Animal.Type, Integer> task15(@NotNull List<Animal> animals, int l, int r) {
        if (l < 0 || l >= r) {
            throw new IllegalArgumentException("l and r must be >= 0 and l must be < r");
        }
        return animals.stream()
            .filter(a -> a.age() >= l && a.age() <= r)
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    public static List<Animal> task16(@NotNull List<Animal> animals) {
        return animals
            .stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .collect(Collectors.toList());
    }

    public static boolean task17(
        @NotNull List<Animal> animals,
        @NotNull Animal.Type type1,
        @NotNull Animal.Type type2
    ) {

        Map<Animal.Type, Integer> map = animals
            .stream()
            .filter(animal -> (animal.type().equals(type1) || animal.type().equals(type2)) && animal.bites())
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.collectingAndThen(
                    Collectors.counting(),
                    Math::toIntExact
                )
            ));
        return map.getOrDefault(type1, 0) > map.getOrDefault(type2, 0);
    }

    public static Animal task18(@NotNull List<List<Animal>> animals, @NotNull Animal.Type type) {

        return animals
            .stream()
            .flatMap(animalsList -> animalsList
                .stream()
                .filter(animal -> animal.type().equals(type)))
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }

    public static Map<String, Set<ValidationError>> task19(
        @NotNull List<Animal> animals
    ) {

        return animals
            .stream()
            .collect(Collectors.toMap(
                    Animal::name,
                    AnimalUtils::validate
                )
            );

    }

    public static Set<ValidationError> validate(@NotNull Animal animal) {
        final String isNullText = "is null";
        final String lessEqualText = "<= 0";

        Set<ValidationError> errors = new HashSet<>();
        if (animal.name() == null || animal.name().isBlank()) {
            errors.add(new ValidationError("name", "is null or blank"));
        }
        if (animal.type() == null) {
            errors.add(new ValidationError("type", isNullText));
        }
        if (animal.sex() == null) {
            errors.add(new ValidationError("sex", isNullText));
        }
        if (animal.age() < 0) {
            errors.add(new ValidationError("age", "< 0"));
        }
        if (animal.weight() <= 0) {
            errors.add(new ValidationError("weight", lessEqualText));
        }
        if (animal.height() <= 0) {
            errors.add(new ValidationError("height", lessEqualText));
        }
        return errors;
    }

    public static Map<String, String> task20(
        @NotNull List<Animal> animals
    ) {

        return task19(animals)
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                stringSetEntry ->
                    stringSetEntry
                        .getValue()
                        .stream()
                        .map(ValidationError::toString)
                        .collect(Collectors.joining("\n"))
            ));

    }

}
