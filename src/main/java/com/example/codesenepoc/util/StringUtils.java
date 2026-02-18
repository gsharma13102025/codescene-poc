package com.example.codesenepoc.util;

public class StringUtils {

    // Duplicated code - same logic in multiple methods
    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    // Complex method with high cyclomatic complexity
    public static String formatPhoneNumber(String phone, String country) {
        if (phone == null) return null;

        String cleaned = phone.replaceAll("[^0-9]", "");

        if (country.equals("US")) {
            if (cleaned.length() == 10) {
                return "(" + cleaned.substring(0, 3) + ") " +
                        cleaned.substring(3, 6) + "-" +
                        cleaned.substring(6);
            } else if (cleaned.length() == 11 && cleaned.startsWith("1")) {
                return "+" + cleaned.substring(0, 1) + " (" +
                        cleaned.substring(1, 4) + ") " +
                        cleaned.substring(4, 7) + "-" +
                        cleaned.substring(7);
            } else {
                return phone;
            }
        } else if (country.equals("UK")) {
            if (cleaned.length() == 10) {
                return "+44 " + cleaned.substring(0, 4) + " " +
                        cleaned.substring(4, 7) + " " +
                        cleaned.substring(7);
            } else if (cleaned.length() == 11 && cleaned.startsWith("0")) {
                return "+44 " + cleaned.substring(1, 5) + " " +
                        cleaned.substring(5, 8) + " " +
                        cleaned.substring(8);
            } else {
                return phone;
            }
        } else if (country.equals("DE")) {
            if (cleaned.length() == 11) {
                return "+49 " + cleaned.substring(0, 3) + " " +
                        cleaned.substring(3, 7) + " " +
                        cleaned.substring(7);
            } else {
                return phone;
            }
        } else if (country.equals("FR")) {
            if (cleaned.length() == 10) {
                return "+33 " + cleaned.substring(0, 1) + " " +
                        cleaned.substring(1, 3) + " " +
                        cleaned.substring(3, 5) + " " +
                        cleaned.substring(5, 7) + " " +
                        cleaned.substring(7, 9) + " " +
                        cleaned.substring(9);
            } else {
                return phone;
            }
        } else {
            return phone;
        }
    }

    // Method with commented-out code
    public static String truncate(String str, int maxLength) {
        // TODO: Implement better truncation
        // if (str == null) {
        //     return "";
        // }
        // String result = str;
        // if (str.length() > maxLength) {
        //     result = str.substring(0, maxLength);
        // }
        // return result;

        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }
}