import BruteForce.BruteForce;
import Cipher.Cipher;
import StatisticalAnalyzer.StatisticalAnalyzer;
import UserDialog.UserDialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class CriptoApp {
    private static Scanner scanner = new Scanner(System.in);
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    private static int maxCode = ALPHABET.length - 1;
    enum ConvertMethod {ENCRYPTION, DECRYPTION}

    public static void main(String[] args) {
        while (true) {
            dislayMenu();
            String choise = scanner.nextLine();
            if (choise.trim().equals("5")) break;
            handleUserChoice(choise);
            System.out.print("Для выхода введите 5. Для продолжения - введите любой другой символ: ");
            choise = scanner.nextLine();
            if (choise.equals("5")) {
                System.out.println("Программа завершена. Возвращайтесь, всегда рады поработать для Вас )");
                break;
            }
            System.out.println();
        }
    }

    // Обработка 4. Дешифрование методом "statistical analysis"
    private static void StatisticalAnalysisDecoding() {
        UserDialog userDialog = new UserDialog();
        userDialog.UserDialog(4, maxCode);
        Path pathInput = userDialog.getPathInput();
        Path pathOutput = userDialog.getPathOutput();

        int encryptionCode = StatisticalAnalyzer.DeterminationEncryptionCode(maxCode, ALPHABET, pathInput);
        if (encryptionCode == -1) {
            System.out.println("Не удалось расшифровать зашифрованный файл. Зови автора кода - пусть дорабатывает...");
            return;
        }

        // расшифруем файл
        try {
            ConvertFile(ConvertMethod.DECRYPTION, pathInput, pathOutput, encryptionCode);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом");
            e.printStackTrace();
        }
    }

    // Обработка 3. Дешифрование методом "brute force"
    private static void BruteForceDecoding() {
        UserDialog userDialog = new UserDialog();
        userDialog.UserDialog(3, maxCode);
        Path pathInput = userDialog.getPathInput();
        Path pathOutput = userDialog.getPathOutput();

        int encryptionCode = BruteForce.DeterminationEncryptionCode(maxCode, ALPHABET, pathInput);
        if (encryptionCode == -1) {
            System.out.println("Не удалось расшифровать зашифрованный файл. Зови автора кода - пусть дорабатывает...");
            return;
        }

        // расшифруем файл
        try {
            ConvertFile(ConvertMethod.DECRYPTION, pathInput, pathOutput, encryptionCode);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом");
            e.printStackTrace();
        }
    }

    // Обработка 2. Дешифрование указанного файла
    private static void DecipherFile() {
        UserDialog userDialog = new UserDialog();
        userDialog.UserDialog(2, maxCode);
        Path pathInput = userDialog.getPathInput();
        Path pathOutput = userDialog.getPathOutput();
        int encryptionCode = userDialog.getEncryptionCode();

        // расшифруем файл
        try {
            ConvertFile(ConvertMethod.DECRYPTION, pathInput, pathOutput, encryptionCode);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом");
            e.printStackTrace();
        }
    }

    // Обработка 1. Шифрование указанного файла
    private static void EncryptingFile() {
        UserDialog userDialog = new UserDialog();
        userDialog.UserDialog(1, maxCode);
        Path pathInput = userDialog.getPathInput();
        Path pathOutput = userDialog.getPathOutput();
        int encryptionCode = userDialog.getEncryptionCode();

        // зашифруем файл
        try {
            ConvertFile(ConvertMethod.ENCRYPTION, pathInput, pathOutput, encryptionCode);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом");
            e.printStackTrace();
        }
    }

    // Вывод меню
    private static void dislayMenu() {
        System.out.println("""
                Приложение шифрования / дешифрования
                Выберите пункт меню: 
                1. Шифрование указанного файла
                2. Дешифрование указанного файла
                3. Дешифрование методом \"brute force\"
                4. Дешифрование методом \"statistical analysis\"
                5. Выход из приложения
                """);
        System.out.print("Ваш выбор: ");
    }

    // Обработка выбора пользователя в основном меню
    private static void handleUserChoice(String choise) {
        switch (choise) {
            case "1" -> EncryptingFile();
            case "2" -> DecipherFile();
            case "3" -> BruteForceDecoding();
            case "4" -> StatisticalAnalysisDecoding();
            default -> System.out.println("Неверный выбор пункта меню...");
        }
    }

    // Преобразование файлов (шифрование или дешифрование)
    public static void ConvertFile(ConvertMethod metod, Path pathInput, Path pathOutput, int encryptionCode) throws IOException {
        // создадим класс для дешифрования
        Cipher cipher = new Cipher(ALPHABET);

        // читаем исходный файл построчно, каждую строку шифруем / дешифруем и записываем в результирующий файл
        try (BufferedReader reader = Files.newBufferedReader(pathInput);
             BufferedWriter writer = Files.newBufferedWriter(pathOutput, StandardCharsets.UTF_8)) {
            String lineIn, lineOut;
            while ((lineIn = reader.readLine()) != null) {
                if (metod == ConvertMethod.ENCRYPTION) {
                    lineOut = cipher.encrypt(lineIn, encryptionCode);
                } else {
                    lineOut = cipher.decrypt(lineIn, encryptionCode);
                }
                writer.write(lineOut);
                writer.newLine();
            }

            // финальное сообщение
            StringBuilder stringBuilder = new StringBuilder();
            if (metod == ConvertMethod.ENCRYPTION) {
                stringBuilder.append("Шифрование произведено, зашифрованный текст помещен в файл ");
            } else if (metod == ConvertMethod.DECRYPTION) {
                stringBuilder.append("Расшифрование произведено, расшифрованный текст помещен в файл ");
            }
            stringBuilder.append(pathOutput.toAbsolutePath().toString());
            System.out.println(stringBuilder.toString());
            System.out.println("//========================================================================");
        }
    }
}