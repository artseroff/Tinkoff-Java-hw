package edu.hw3.Task6;

import java.util.PriorityQueue;
import org.jetbrains.annotations.NotNull;

public class ConcreteStockMarket implements StockMarket {

    private final PriorityQueue<Stock> queue = new PriorityQueue<>((o1, o2) -> -o1.price().compareTo(o2.price()));

    @Override
    public void add(@NotNull Stock stock) {
        queue.add(stock);
    }

    @Override
    public void remove(@NotNull Stock stock) {
        queue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return queue.peek();
    }
}
