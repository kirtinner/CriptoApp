package Validator;

import Exceptions.InvalidEncryptionCode;
import java.util.Arrays;
import java.util.Map;

public class Validator {

    // валидация корректности кода шифрования (количества символов сдвига)
    public static void isValidEncryptionCode(int EncryptionCode, int maxCode) throws InvalidEncryptionCode {
        if ((EncryptionCode >= 1 && EncryptionCode <= maxCode) == false ) {
            throw new InvalidEncryptionCode(maxCode);
        }
    }

    // валидация присутствия символа из исходного файла в массиве alphabet
    public static boolean isSymbolExist(char symbol, Map mapAlphabet) {
        char ch = Character.toLowerCase(symbol); // это копия symbol
        return mapAlphabet.containsKey((char) ch);
    }
}
