package UserDialog;

import Exceptions.InvalidEncryptionCode;
import Validator.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class UserDialog {
    private static Scanner scanner = new Scanner(System.in);
    private Path pathInput;
    private Path pathOutput;
    private int encryptionCode;

    public Path getPathInput() {
        return pathInput;
    }

    public Path getPathOutput() {
        return pathOutput;
    }

    public int getEncryptionCode() {
        return encryptionCode;
    }

    // Диалог с пользователем
    public void UserDialog(int menuNumber, int maxCode) {

        String[] messages = new String[4];
        switch (menuNumber) {
            case 1 -> {
                messages[0] = "Ваш выбор - шифрование файла.";
                messages[1] = "Введите файл для шифрования: ";
                messages[2] = "Введите файл для помешения зашифрованного текста: ";
                messages[3] = "Теперь введите код шифрования (количество символов сдвига - число от 1 до " + maxCode + ")" +
                        " или введите \"exit\" для продолжения: ";
            }
            case 2 -> {
                messages[0] = "Ваш выбор - шифрование файла.";
                messages[1] = "Введите файл для шифрования: ";
                messages[2] = "Введите файл для размещения зашифрованного текста: ";
                messages[3] = "Теперь введите код шифрования (количество символов сдвига - число от 1 до " + maxCode + ")" +
                        " или введите \"exit\" для продолжения: ";
            }
            case 3 -> {
                messages[0] = "Ваш выбор - дешифрование методом \"brute force\".";
                messages[1] = "Введите путь к зашифрованному файлу: ";
                messages[2] = "Введите файл для размещения расшифрованного текста: ";
            }
            case 4 -> {
                messages[0] = "Ваш выбор - дешифрование методом статистического анализа.";
                messages[1] = "Введите путь к зашифрованному файлу: ";
                messages[2] = "Введите файл для размещения расшифрованного текста: ";
            }
        }

        // наведем "красоту"
        System.out.println("//========================================================================");
        System.out.println(messages[0]);

        // введем имя исходного и результирующего файла, проверим их существование
        System.out.print(messages[1]);
        String inFileName = scanner.nextLine();
        Path pathInput = Path.of(inFileName);
        if (Files.exists(pathInput) == false) {
            System.out.println("Не нахожу файл " + inFileName);
            return;
        }
        System.out.print(messages[2]);
        String outFileName = scanner.nextLine();
        Path pathOutput = Path.of(outFileName);
        if (Files.exists(pathOutput) == false) {
            try {
                Files.createFile(pathOutput);
            } catch (IOException e) {
                System.out.println("Не нахожу файл " + outFileName + ". И создать не могу. Не взыщи (");
                e.printStackTrace();
                return;
            }
        }
        this.pathInput = pathInput;
        this.pathOutput = pathOutput;

        if (menuNumber == 1 || menuNumber == 2) {
            // введем код шифрования (количество символов сдвига)
            System.out.print(messages[3]);
            while (true) {
                String str = scanner.nextLine();
                if (str.equals("exit")) {
                    return;
                }
                try {
                    encryptionCode = Integer.parseInt(str);
                    Validator.isValidEncryptionCode(encryptionCode, maxCode);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Введено не число. Введите число: ");
                } catch (InvalidEncryptionCode e) {
                    System.out.println(e.getMessage());
                }
            }
            this.encryptionCode = encryptionCode;
        }


    }
}
