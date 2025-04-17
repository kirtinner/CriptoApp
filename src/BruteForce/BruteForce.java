package BruteForce;

import Cipher.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class BruteForce {

    private static String[] shortWords = {"и","в","с","я","о","на","по","не","то","но","И","В","С","Я","О","На","По","Не","То","Но"};

    // функция возвращает количество совпадений "коротких" слов в строке
    public static int NumberOfMatches(String text) {
        int result = 0;
        String[] parts = text.split("[,\\s]+");
        for (String part : parts) {
            for (String shortWord : shortWords) {
                if (part.equals(shortWord)) result++;
            }
        }
        return result;
    }

    // функция определяет наилучший encryptionCode
    public static int DeterminationEncryptionCode(int maxCode, char[] ALPHABET, Path pathInput) {
        /*
        Определять наилучший вариант расшифровки будем исходя из следующих соображений:
        В русском языке чаще всего встречаются короткие служебные слова, такие как «и», «в», «на», «с» и т.п.
        Поэтому выберем за "правильно" расшифрованный тот вариант, у которого количество совпадений
        с этими короткими словами - наибольшее
        */

        // массив metricaValues будет содержать количество совпадений "коротких" слов для каждого сдвига (encryptionCode)
        int[] metricaValues = new int[maxCode];
        for (int i = 0; i <= maxCode; i++) {
            metricaValues[i] = 0;
        }

        // создадим класс для дешифрования
        Cipher cipher = new Cipher(ALPHABET);

        // читаем исходный файл построчно (только теперь, для разнообразия, методом Files.lines)
        try (Stream<String> lines = Files.lines(pathInput)) {
            lines.forEach(lineIn -> {
                // каждую строку дешифруем всеми возможными вариантами сдвига
                for (int encryptionCode = 1; encryptionCode < maxCode; encryptionCode++) {
                    String lineOut = cipher.decrypt(lineIn, encryptionCode);
                    metricaValues[encryptionCode - 1] += BruteForce.NumberOfMatches(lineOut);
                }
            });
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом");
            e.printStackTrace();
        }

        // определим "победителя" из encryptionCode
        int encryptionCode = -1;
        int maxMetrica = 0;
        for (int i = 0; i < maxCode; i++) {
            if (metricaValues[i] > maxMetrica) {
                maxMetrica = metricaValues[i];
                encryptionCode = i + 1;
            }
        }
        return encryptionCode;
    }

}
