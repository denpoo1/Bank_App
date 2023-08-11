package com.onlinebank.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class MoonAlgorithm {

    public static BigDecimal generateCardNumber(int length) {
        StringBuilder cardNumber = new StringBuilder();

        // Generate random digits for the card number
        Random random = new Random();
        for (int i = 0; i < length - 1; i++) {
            cardNumber.append(random.nextInt(10));
        }

        // Calculate the Luhn check digit
        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return new BigDecimal(cardNumber.toString());
    }

    public static int calculateLuhnCheckDigit(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        int checkDigit = 10 - (sum % 10);
        return (checkDigit == 10) ? 0 : checkDigit;
    }
}
