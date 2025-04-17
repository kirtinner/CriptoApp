package StatisticalAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class StatisticalAnalyzer {

    public static int DeterminationEncryptionCode(int maxCode, char[] ALPHABET, Path pathInput) {
        /*
        Определять наилучший вариант расшифровки будем исходя из следующих соображений:
        "попробуйте угадать пробел — это наверняка наиболее часто встречающийся символ в обычном тексте"
        */

        // мапа numberOfLetters будет содержать количество повторений букв, встречающихся в зашифрованном тексте
        HashMap<Character, Integer> numberOfLetters = new HashMap();
        for (char c : ALPHABET) {
            numberOfLetters.put(c, 0);
        }

        // читаем зашифрованный файл построчно (только теперь, для разнообразия, методом Files.lines)
        try (Stream<String> lines = Files.lines(pathInput)) {
            lines.forEach(lineIn -> {
                // каждую строку обойдем и заполним количество повторений в numberOfLetters
                for (char c : lineIn.toCharArray()) {
                    numberOfLetters.put(c, numberOfLetters.getOrDefault(c, 0) + 1);
                }
            });
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом");
            e.printStackTrace();
        }

        // определим символ с максимальным количеством повторений
        int maxNumber = -1;
        Character maxCharacter = null;
        for (Map.Entry<Character, Integer> entry : numberOfLetters.entrySet()) {
            Integer number = entry.getValue();
            if (number > 0 && number > maxNumber) {
                maxNumber = number;
                maxCharacter = entry.getKey();
            }
        }

        // определим "победителя" из encryptionCode
        int encryptionCode = -1;
        int spacePosition = -1;
        int maxCharacterPosition = -1;
        if (maxNumber > 0) {
            for (int i = 0; i <= maxCode; i++) {
                if (ALPHABET[i] == ' ') spacePosition = i;
                if (ALPHABET[i] == maxCharacter) maxCharacterPosition = i;
            }
            if (maxCharacterPosition >= 0 && spacePosition >= 0) {
                if (spacePosition < maxCharacterPosition) {
                    encryptionCode = maxCharacterPosition - spacePosition + 1;
                } else {
                    encryptionCode = maxCharacterPosition - spacePosition + maxCode + 1;
                }
            }
        }

        return encryptionCode;
    }

}
