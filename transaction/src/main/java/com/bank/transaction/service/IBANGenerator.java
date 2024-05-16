package com.bank.transaction.service;

import org.springframework.stereotype.Service;

/**
 * Utility class to generate an (IBAN).
 */
@Service
public class IBANGenerator {

    /**
     * Generates an IBAN using the specified country code, bank code, and account number.
     *
     * @param countryCode   The two-letter country code as per ISO 3166-1.
     * @param bankCode      The bank code assigned by the country's central bank.
     * @param accountNumber The account number within the bank.
     * @return A string representing the formatted IBAN.
     */
    public  String generateIBAN(String countryCode, String bankCode, String accountNumber) {
        String bban = bankCode + accountNumber;
        String preIBAN = countryCode + "00" + bban;
        int checkDigits = calculateCheckDigits(preIBAN);
        String checkDigitString = String.format("%02d", checkDigits);
        return countryCode + checkDigitString + bban;
    }

    private static int calculateCheckDigits(String iban) {
        String reformattedIban = iban.substring(4) + iban.substring(0, 4);
        reformattedIban = convertLettersToNumbers(reformattedIban);
        return 98 - mod97(reformattedIban);
    }

    private static String convertLettersToNumbers(String input) {
        StringBuilder numeric = new StringBuilder();
        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                // Convert letter to number based on its position in the alphabet
                int value = Character.toUpperCase(character) - 'A' + 10;
                numeric.append(value);
            } else {
                numeric.append(character);
            }
        }
        return numeric.toString();
    }

    private static int mod97(String input) {
        String temp = input;
        int mod = 0;
        for (int i = 0; i < temp.length(); i++) {
            int current = Integer.parseInt(temp.substring(i, i + 1));
            mod = (mod * 10 + current) % 97;
        }
        return mod;
    }

}
