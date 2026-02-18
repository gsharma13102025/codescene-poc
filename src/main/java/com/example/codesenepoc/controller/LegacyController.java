package com.example.codesenepoc.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

@RestController
@RequestMapping("/api/legacy")
public class LegacyController {

    //Comment for testing - new branch
    // Using deprecated collections
    private Vector<String> legacyList = new Vector<>();
    private Hashtable<String, Object> legacyMap = new Hashtable<>();

    // Synchronized method - potential performance issue
    public synchronized String processSynchronized(String input) {
        legacyList.add(input);
        legacyMap.put(input, new Object());
        return "Processed: " + input;
    }

    // Using Enumeration instead of Iterator
    @GetMapping("/items")
    public String getItems() {
        StringBuilder result = new StringBuilder();
        Enumeration<String> elements = legacyList.elements();
        while (elements.hasMoreElements()) {
            String item = elements.nextElement();
            result.append(item).append(", ");
        }
        return result.toString();
    }

    // Empty catch block - BAD PRACTICE
    @GetMapping("/risky")
    public String riskyOperation() {
        try {
            int result = 10 / 0;
            return String.valueOf(result);
        } catch (Exception e) {
            // Silently ignoring exception
        }
        return "Error handled";
    }

    // TODO comments indicating technical debt
    // TODO: Refactor this to use modern collections
    // TODO: Remove synchronized and use concurrent collections
    // FIXME: Handle exceptions properly
}