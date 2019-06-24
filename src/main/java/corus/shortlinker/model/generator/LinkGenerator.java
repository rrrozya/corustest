package corus.shortlinker.model.generator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class LinkGenerator {

    private static final int ALPHABET_POWER = 62;
    private static final int NUMBER_OF_PERMITS = 100;

    private static final Semaphore semaphore = new Semaphore(NUMBER_OF_PERMITS);
    private static final Map<Integer, Character> chars = new ConcurrentHashMap<>();

    static {
        initializeCharsMap();
    }

    /**
     * Алгоритм генерации короткой ссылки на основе длинной
     *
     * @param link Изначальная ссылка
     * @return возвращает сгенерированную короткую ссылку
     * @throws NullPointerException
     * @throws InterruptedException
     * @author не я
     * Используем синхронизатор Семафор для генерации не более 100 ссылок
     */
    public String generateShortLink(String link) throws NullPointerException, InterruptedException {
        semaphore.acquire();
        try {
            StringBuilder shortLink = new StringBuilder();
            int seed = link.hashCode();

            while (seed != 0) {
                int index = seed % ALPHABET_POWER;
                char literal = chars.get(index >= 0 ? index : ALPHABET_POWER + index);
                shortLink.append(literal);
                seed /= ALPHABET_POWER;
            }

            return shortLink.toString();
        } finally {
            semaphore.release();
        }
    }

    private static void initializeCharsMap() {
        final int asciiNumbersPos = 48;
        final int asciiLowerCasePos = 61;
        final int asciiUpperCasePos = 55;

        for (int i = 0; i < 10; i++) {
            chars.put(i, (char) (asciiNumbersPos + i));
        }

        for (int i = 10; i < 36; i++) {
            chars.put(i, (char) (asciiUpperCasePos + i));
        }

        for (int i = 36; i < 62; i++) {
            chars.put(i, (char) (asciiLowerCasePos + i));
        }
    }
}