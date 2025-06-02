package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger count3 = new AtomicInteger(0);
    static AtomicInteger count4 = new AtomicInteger(0);
    static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));

        }
        List<Thread> threads = new ArrayList<>();

        Thread symbolOrder = new Thread(() ->
        {
            for (String text : texts) {
                boolean isBeauty = true;
                char[] chars = text.toCharArray();
                for (int i = 1; i < chars.length; i++) {
                    if (chars[i] != chars[i - 1] || chars[i] > chars[i - 1]) {
                        isBeauty = false;
                        break;
                    }
                }
                if (isBeauty) {
                    increment(text);
                }
            }
        });
        symbolOrder.start();
        threads.add(symbolOrder);

        Thread palindrome = new Thread(() ->
        {
            for (String text : texts) {
                boolean isBeauty = true;
                char[] chars = text.toCharArray();
                for (int i = 0; i < (chars.length / 2); i++) {
                    if (chars[chars.length - (i + 1)] != chars[i]) {
                        isBeauty = false;
                        break;
                    }
                }
                if (isBeauty) {
                    increment(text);
                }
            }
        });
        palindrome.start();
        threads.add(palindrome);

        Thread oneSymbol = new Thread(() ->
        {
            for (String text : texts) {
                boolean isBeauty = true;
                char[] chars = text.toCharArray();
                for (int i = 1; i < chars.length; i++) {
                    if (chars[i] != chars[i - 1]) {
                        isBeauty = false;
                        break;
                    }
                }
                if (isBeauty) {
                    increment(text);
                }
            }
        });
        oneSymbol.start();
        threads.add(oneSymbol);
        for (Thread th : threads) {
            th.join();
        }

        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void increment(String text) {
        if (text.length() == 3) {
            count3.getAndIncrement();
        }
        if (text.length() == 4) {
            count4.getAndIncrement();
        }
        if (text.length() == 5) {
            count5.getAndIncrement();
        }
    }
}