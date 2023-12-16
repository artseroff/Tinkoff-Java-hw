package edu.hw8.task3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentPasswordHacker extends AbstractPasswordHacker {
    private static final int TREADS_COUNT_DEFAULT = Runtime.getRuntime().availableProcessors();
    private static final int PASSWORD_GAP = 1_000;
    private final int threadsCount;

    public ConcurrentPasswordHacker(Map<String, String> encryptedPasswords) {
        this(encryptedPasswords, TREADS_COUNT_DEFAULT);
    }

    public ConcurrentPasswordHacker(Map<String, String> encryptedPasswords, int threadsCount) {
        this.encryptedPasswords = new ConcurrentHashMap<>(encryptedPasswords);
        hackedPasswords = new ConcurrentHashMap<>();
        this.threadsCount = threadsCount;
    }

    @Override
    public Map<String, String> hack() {
        AtomicLong startState = new AtomicLong(0);
        try (ExecutorService service = Executors.newFixedThreadPool(threadsCount)) {
            Future<?>[] futures = new Future[threadsCount];
            for (int i = 0; i < threadsCount; i++) {
                futures[i] = service.submit(() -> {
                    while (!encryptedPasswords.isEmpty()) {
                        long initState = startState.getAndAdd(PASSWORD_GAP);
                        PasswordGenerator generator = new PasswordGenerator(initState);
                        for (int j = 0; j < PASSWORD_GAP; j++) {
                            if (encryptedPasswords.isEmpty()) {
                                break;
                            }
                            tryFindEncryptedPassword(generator.next());
                        }
                    }
                });
            }
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Ошибка при подборе паролей");
        }
        return hackedPasswords;
    }
}
