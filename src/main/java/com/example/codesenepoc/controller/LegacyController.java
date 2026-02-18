package com.example.codesenepoc.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/legacy")
public class LegacyController {

    // ISSUE 1: Deprecated collections
    private Vector<String> legacyList = new Vector<>();
    private Hashtable<String, Object> legacyMap = new Hashtable<>();

    // ISSUE 2: Mutable static field (thread-unsafe)
    public static List<String> sharedCache = new ArrayList<>();

    // ISSUE 3: Magic numbers without explanation
    private static final int BUFFER_SIZE = 8192;
    private int maxRetries = 5;
    private int timeout = 30000;

    // ISSUE 4: Synchronized method - performance bottleneck
    public synchronized String processSynchronized(String input) {
        legacyList.add(input);
        legacyMap.put(input, new Object());
        // ISSUE 5: Unnecessary object creation in loop
        for (int i = 0; i < 100; i++) {
            new String(input); // String objects created unnecessarily
        }
        return "Processed: " + input;
    }

    // ISSUE 6: Using Enumeration instead of Iterator
    @GetMapping("/items")
    public String getItems() {
        StringBuilder result = new StringBuilder();
        Enumeration<String> elements = legacyList.elements();
        while (elements.hasMoreElements()) {
            String item = elements.nextElement();
            // ISSUE 7: String concatenation in loop
            result.append(item).append(", ");
        }
        return result.toString();
    }

    // ISSUE 8: Empty catch block - silently ignoring exceptions
    @GetMapping("/risky")
    public String riskyOperation() {
        try {
            int result = 10 / 0;
            return String.valueOf(result);
        } catch (Exception e) {
            // Silently ignoring exception - BAD PRACTICE
        }
        return "Error handled";
    }

    // ISSUE 9: Multiple unresolved TODO/FIXME comments
    // TODO: Refactor this to use modern collections
    // TODO: Remove synchronized and use concurrent collections
    // FIXME: Handle exceptions properly
    // TODO: Improve performance of this method
    // FIXME: Fix memory leak in cache
    @PostMapping("/process")
    public String processData(String data) {
        // ISSUE 10: Null pointer risk - no null check
        String processed = data.toUpperCase();

        // ISSUE 11: Inefficient string operations
        String result = "";
        for (char c : processed.toCharArray()) {
            result += c + "|"; // String concatenation in loop
        }

        // ISSUE 12: Hardcoded values
        if (result.length() > 1000) {
            return "Too long";
        }

        return result;
    }

    // ISSUE 13: No input validation
    @PostMapping("/save")
    public String saveData(String key, String value) {
        legacyMap.put(key, value);
        sharedCache.add(key); // Race condition on mutable static
        return "Saved";
    }

    // ISSUE 14: Complex method doing too much (God Method)
    @GetMapping("/complex/{id}")
    public String complexOperation(String id) {
        try {
            // File I/O without proper resource management
            FileInputStream fis = new FileInputStream("data.txt");
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = fis.read(buffer);
            String fileContent = new String(buffer);

            // Database operation (simulated)
            legacyList.add(fileContent);

            // API call (simulated)
            String apiResult = callExternalAPI(id);

            // Complex business logic mixed with infrastructure
            if (apiResult != null && apiResult.length() > 0) {
                legacyMap.put(id, apiResult);
            }

            // ISSUE 15: Resource leak - fis never closed
            return apiResult;
        } catch (Exception e) {
            // ISSUE 16: Generic exception handling
            e.printStackTrace(); // Anti-pattern
            return null;
        }
    }

    // ISSUE 17: Method with too many parameters
    @PostMapping("/update")
    public String updateRecord(String id, String name, String email,
                               String phone, String address, String city,
                               String state, String zip, String country) {
        // ISSUE 18: No parameter validation
        legacyMap.put(id, name + email + phone + address);
        return "Updated";
    }

    // ISSUE 19: Unused method
    public void unusedMethod() {
        System.out.println("This method is never called");
    }

    // ISSUE 20: Duplicate code
    @GetMapping("/fetch1")
    public String fetch1() {
        StringBuilder result = new StringBuilder();
        Enumeration<String> elements = legacyList.elements();
        while (elements.hasMoreElements()) {
            result.append(elements.nextElement()).append(", ");
        }
        return result.toString();
    }

    @GetMapping("/fetch2")
    public String fetch2() {
        StringBuilder result = new StringBuilder();
        Enumeration<String> elements = legacyList.elements();
        while (elements.hasMoreElements()) {
            result.append(elements.nextElement()).append(", ");
        }
        return result.toString();
    }

    // ISSUE 21: Long method with low readability
    @PostMapping("/validate")
    public boolean validateInput(String input) {
        if (input == null) return false;
        if (input.length() == 0) return false;
        if (input.length() > 255) return false;
        if (!input.matches("^[a-zA-Z0-9]*$")) return false;
        if (input.contains("admin")) return false;
        if (input.contains("root")) return false;
        if (input.contains("test")) return false;
        if (input.contains("debug")) return false;
        if (legacyList.contains(input)) return false;
        if (legacyMap.containsKey(input)) return false;
        // More validation...
        return true;
    }

    // ISSUE 22: Missing dependency injection
    private DatabaseConnection dbConnection = new DatabaseConnection();

    // ISSUE 23: Hardcoded credentials (Security issue)
    private String dbUser = "admin";
    private String dbPassword = "password123";

    // ISSUE 24: Inconsistent error handling
    @GetMapping("/data/{id}")
    public String getData(String id) {
        try {
            return legacyMap.get(id).toString();
        } catch (NullPointerException e) {
            return "Not found";
        } catch (Exception e) {
            return null;
        }
    }

    // Helper method
    private String callExternalAPI(String id) {
        return "API_RESULT_" + id;
    }

    // ISSUE 25: Dummy class for demonstration
    private static class DatabaseConnection {
    }
}