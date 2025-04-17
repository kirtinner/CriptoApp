package Exceptions;

public class InvalidEncryptionCode extends Exception {
    public InvalidEncryptionCode(int maxCode) {
        super("Код должен быть в диапазоне от 1 до " + maxCode + ". Введите еще раз: ");
    }
}
