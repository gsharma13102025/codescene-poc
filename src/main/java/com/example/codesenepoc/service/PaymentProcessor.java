package com.example.codesenepoc.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentProcessor {

    // Brain method - does too much
    public boolean processPayment(String type, double amount, String currency,
                                  String cardNumber, String expiry, String cvv,
                                  String billingAddress, String shippingAddress,
                                  String customerEmail, String customerPhone,
                                  String orderId, String merchantId, String deviceId,
                                  String ipAddress, String userAgent, String sessionId) {

        // Validation
        if (type == null || type.isEmpty()) return false;
        if (amount <= 0) return false;
        if (currency == null || currency.isEmpty()) return false;

        // Card validation
        if (type.equals("CREDIT_CARD") || type.equals("DEBIT_CARD")) {
            if (cardNumber == null || cardNumber.length() < 13) return false;
            if (expiry == null || !expiry.matches("\\d{2}/\\d{2}")) return false;
            if (cvv == null || cvv.length() < 3) return false;

            // Luhn algorithm check
            int sum = 0;
            boolean alternate = false;
            for (int i = cardNumber.length() - 1; i >= 0; i--) {
                int n = Integer.parseInt(cardNumber.substring(i, i + 1));
                if (alternate) {
                    n *= 2;
                    if (n > 9) n -= 9;
                }
                sum += n;
                alternate = !alternate;
            }
            if (sum % 10 != 0) return false;
        }

        // Currency conversion
        double usdAmount = amount;
        if (currency.equals("EUR")) {
            usdAmount = amount * 1.1;
        } else if (currency.equals("GBP")) {
            usdAmount = amount * 1.3;
        } else if (currency.equals("JPY")) {
            usdAmount = amount * 0.007;
        } else if (currency.equals("CAD")) {
            usdAmount = amount * 0.75;
        } else if (currency.equals("AUD")) {
            usdAmount = amount * 0.65;
        } else if (!currency.equals("USD")) {
            return false; // Unsupported currency
        }

        // Fraud detection
        if (usdAmount > 10000) {
            // High value transaction
            if (ipAddress != null) {
                String[] parts = ipAddress.split("\\.");
                if (parts.length == 4) {
                    int firstOctet = Integer.parseInt(parts[0]);
                    if (firstOctet == 10 || firstOctet == 172 || firstOctet == 192) {
                        // Private IP - potential fraud
                        return false;
                    }
                }
            }
        }

        // More processing logic...
        // This method continues with many more lines of code
        // demonstrating high complexity and multiple responsibilities

        return true;
    }
}