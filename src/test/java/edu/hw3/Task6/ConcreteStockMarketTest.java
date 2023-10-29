package edu.hw3.Task6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ConcreteStockMarketTest {

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void buildStockWithIllegalArguments(UUID uuid, String name, Double price) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Stock(uuid, name, price));
    }

    private static Arguments[] provideParameters() {
        return new Arguments[] {
            Arguments.of(UUID.randomUUID(), "st1", -2.5),
            Arguments.of(UUID.randomUUID(), "", 5.5)
        };
    }

    @Test
    public void mostValuableStock() {
        StockMarket market = new ConcreteStockMarket();

        Assertions.assertNull(market.mostValuableStock());
    }

    @Test
    public void remove() {
        StockMarket market = new ConcreteStockMarket();
        Stock stock = new Stock(UUID.randomUUID(), 1.0);

        market.remove(stock);
        Assertions.assertNull(market.mostValuableStock());

    }

    @Test
    public void add() {
        StockMarket market = new ConcreteStockMarket();
        Stock stock = new Stock(UUID.randomUUID(), 1.0);

        market.add(stock);
        Assertions.assertEquals(stock, market.mostValuableStock());

        market.remove(stock);
        Assertions.assertNull(market.mostValuableStock());
    }

    @Test
    public void mostValuableStockSortedComplexTest() {
        StockMarket market = new ConcreteStockMarket();

        for (int i = 10; i > 0; i--) {
            market.add(new Stock(UUID.randomUUID(), (double) i));
        }

        for (int i = 10; i > 0; i--) {
            var mostValuableStock = market.mostValuableStock();
            Assertions.assertEquals(i, market.mostValuableStock().price());
            market.remove(mostValuableStock);
        }

        Assertions.assertNull(market.mostValuableStock());

    }

    @Test
    public void mostValuableStockUnsortedComplexTest() {
        StockMarket market = new ConcreteStockMarket();
        final int bound = 20;
        var rand = new Random();

        Double[] values = new Double[bound];
        for (int i = 0; i < bound; i++) {
            values[i] = rand.nextDouble();
        }

        Double[] collectedRandomDoubles = new Double[bound];
        for (int i = 0; i < bound; i++) {
            var collectedValue = values[rand.nextInt(bound)];
            market.add(new Stock(UUID.randomUUID(), collectedValue));
            collectedRandomDoubles[i] = collectedValue;
        }

        Arrays.sort(collectedRandomDoubles, Comparator.reverseOrder());
        for (int i = 0; i < bound; i++) {
            var mostValuableStock = market.mostValuableStock();
            Assertions.assertEquals(collectedRandomDoubles[i], market.mostValuableStock().price());
            market.remove(mostValuableStock);
        }
        Assertions.assertNull(market.mostValuableStock());

    }
}
