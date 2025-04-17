package Cipher;

import Validator.*;

import java.util.HashMap;
import java.util.Map;

public class Cipher {
    private char[] alphabet;
    private int alphabetSize;
    // заведем мапу для облегчения и ускорения процесса поиска позиции символа в массиве alphabet
    private Map<Character, Integer> mapAlphabet;

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
        this.alphabetSize = alphabet.length;
        this.mapAlphabet = new HashMap<Character, Integer>();
        for (int i = 0; i < alphabetSize; i++) {
            mapAlphabet.put(alphabet[i], i);
        }
    }

    // шифрование строки
    public String encrypt(String textIn, int encryptionCode) {

        // массивы символов - исходный и зашифрованный
        char[] textInArray = textIn.toCharArray();
        char[] textOutArray = new char[textIn.length()];

        // посимвольно обойдем исходный массив, проверим, что есть такой символ в alphabet, если нет -
        // оставим его незашифрованным, остальные - зашифруем и поместим в зашифрованный массив
        for (int i = 0; i < textInArray.length; i++) {
            char symbol = textInArray[i];
            char symbolCopy = Character.toLowerCase(symbol);
            boolean itIsUpperCase = symbol == symbolCopy ? false : true;
            if(Validator.isSymbolExist(symbolCopy, mapAlphabet)) {
                int position = mapAlphabet.get(symbolCopy);
                int newPosition = (position + encryptionCode) % alphabetSize;
                if (itIsUpperCase) {
                    textOutArray[i] = Character.toUpperCase(alphabet[newPosition]);
                } else {
                    textOutArray[i] = alphabet[newPosition];
                }
            } else {
                textOutArray[i] = symbol;
            }
        }

        // зашифрованный массив преобразуем в строку и вернем ее
        String textOut = new String(textOutArray);
        return textOut;
    }

    // расшифровка строки
    public String decrypt(String textIn, int encryptionCode) {

        // массивы символов - исходный и зашифрованный
        char[] textInArray = textIn.toCharArray();
        char[] textOutArray = new char[textIn.length()];

        // посимвольно обойдем исходный массив, проверим, что есть такой символ в alphabet, если нет -
        // оставим его незашифрованным, остальные - расшифруем и поместим в расшифрованный массив
        for (int i = 0; i < textInArray.length; i++) {
            char symbol = textInArray[i];
            char symbolCopy = Character.toLowerCase(symbol);
            boolean itIsUpperCase = symbol == symbolCopy ? false : true;
            if(Validator.isSymbolExist(symbolCopy, mapAlphabet)) {
                int position = mapAlphabet.get(symbolCopy);
                int newPosition = position - encryptionCode;
                while (newPosition < 0) {
                    newPosition += alphabetSize;
                }
                if (itIsUpperCase) {
                    textOutArray[i] = Character.toUpperCase(alphabet[newPosition]);
                } else {
                    textOutArray[i] = alphabet[newPosition];
                }
            } else {
                textOutArray[i] = symbol;
            }
        }

        // зашифрованный массив преобразуем в строку и вернем ее
        String textOut = new String(textOutArray);
        return textOut;
    }
}
