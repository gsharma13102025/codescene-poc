package com.example.codesenepoc.controller;

import com.example.codesenepoc.model.User;
import com.example.codesenepoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Vector;
import java.util.Hashtable;
import java.io.File;
import java.io.FileWriter;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // ISSUE 1: Field injection instead of constructor injection
    @Autowired
    private UserService userService;

    // ISSUE 2: Deprecated collection
    private Vector<User> userCache = new Vector<>();

    // ISSUE 3: Mutable static field (thread-unsafe)
    public static Hashtable<Long, User> globalUserMap = new Hashtable<>();

    // ISSUE 4: Hardcoded values
    private static final String LOG_FILE = "/tmp/users.log";
    private static final int MAX_USERS = 1000;
    private static final String ADMIN_PASSWORD = "admin123";

    // ISSUE 5: No validation, accepts null
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // ISSUE 6: No null check on user object
        User createdUser = userService.createUser(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );

        // ISSUE 7: Unnecessary object creation
        for (int i = 0; i < 50; i++) {
            new User(); // Wasteful instantiation
        }

        // ISSUE 8: String concatenation in loop
        String log = "";
        for (User u : userCache) {
            log += u.getUsername() + " | " + u.getEmail() + "\n";
        }

        // ISSUE 9: Resource leak - FileWriter never closed
        try {
            FileWriter fw = new FileWriter(LOG_FILE, true);
            fw.write("User created: " + createdUser.getUsername());
            // Missing fw.close()
        } catch (Exception e) {
            // ISSUE 10: Empty catch block
        }

        // ISSUE 11: Race condition on mutable static
        globalUserMap.put(createdUser.getId(), createdUser);
        userCache.add(createdUser);

        return ResponseEntity.ok(createdUser);
    }

    // ISSUE 12: Synchronized method - performance bottleneck
    @GetMapping
    public synchronized ResponseEntity<List<User>> getAllUsers() {
        // ISSUE 13: No pagination for potentially large dataset
        List<User> users = userService.getAllUsers();

        // ISSUE 14: Inefficient filtering with nested loops
        List<User> filtered = new java.util.ArrayList<>();
        for (User u : users) {
            for (User cached : userCache) {
                if (u.getId().equals(cached.getId())) {
                    filtered.add(u);
                }
            }
        }

        // ISSUE 15: Duplicate code - same logic as createUser
        String log = "";
        for (User u : filtered) {
            log += u.getUsername() + " | " + u.getEmail() + "\n";
        }

        return ResponseEntity.ok(filtered);
    }

    // ISSUE 16: Multiple unresolved TODO comments
    // TODO: Add proper validation
    // TODO: Implement caching strategy
    // FIXME: Fix performance issue with getAllUsers
    // TODO: Add authentication checks
    // FIXME: Remove hardcoded values
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // ISSUE 17: No null check on id parameter
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // ISSUE 18: God method - doing too much
    @PostMapping("/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // ISSUE 19: No validation on input parameters
        User existingUser = userService.getUserById(id);

        // ISSUE 20: Null pointer risk
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        // ISSUE 21: Direct field manipulation instead of service method
        globalUserMap.put(id, existingUser);
        userCache.add(existingUser);

        // ISSUE 22: Duplicate logging code
        try {
            FileWriter fw = new FileWriter(LOG_FILE, true);
            fw.write("User updated: " + existingUser.getUsername());
            // Resource leak
        } catch (Exception e) {
            // Empty catch block
        }

        // ISSUE 23: No proper service call
        return ResponseEntity.ok(existingUser);
    }

    // ISSUE 24: Unused method
    public void unusedHelperMethod() {
        System.out.println("This is never called");
    }

    // ISSUE 25: Duplicate code from getAllUsers
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers() {
        List<User> users = userService.getAllUsers();

        List<User> filtered = new java.util.ArrayList<>();
        for (User u : users) {
            for (User cached : userCache) {
                if (u.getId().equals(cached.getId())) {
                    filtered.add(u);
                }
            }
        }

        String log = "";
        for (User u : filtered) {
            log += u.getUsername() + " | " + u.getEmail() + "\n";
        }

        return ResponseEntity.ok(filtered);
    }

    // ISSUE 26: Method with too many parameters
    @PostMapping("/bulk")
    public ResponseEntity<List<User>> bulkCreateUsers(
            String username1, String email1, String firstName1, String lastName1,
            String username2, String email2, String firstName2, String lastName2,
            String username3, String email3, String firstName3, String lastName3) {

        // ISSUE 27: No parameter validation
        List<User> createdUsers = new java.util.ArrayList<>();

        // ISSUE 28: Repetitive code
        User user1 = userService.createUser(username1, email1, firstName1, lastName1);
        User user2 = userService.createUser(username2, email2, firstName2, lastName2);
        User user3 = userService.createUser(username3, email3, firstName3, lastName3);

        createdUsers.add(user1);
        createdUsers.add(user2);
        createdUsers.add(user3);

        return ResponseEntity.ok(createdUsers);
    }

    // ISSUE 31: Synchronization on wrong object
    public void synchronizedOperation() {
        synchronized (userCache) {
            // ISSUE 32: Complex logic inside synchronized block
            for (User u : userCache) {
                globalUserMap.put(u.getId(), u);
                try {
                    Thread.sleep(100); // Blocking operation in synchronized block
                } catch (InterruptedException e) {
                    // Empty catch
                }
            }
        }
    }

    // ISSUE 33: Magic numbers without explanation
    @GetMapping("/paginated")
    public ResponseEntity<List<User>> getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        // ISSUE 34: No validation on page/size
        if (page < 0 || size > 1000) {
            // Inconsistent validation
        }

        List<User> users = userService.getAllUsers();

        // ISSUE 35: Manual pagination instead of using service
        int start = page * size;
        int end = Math.min(start + size, users.size());

        return ResponseEntity.ok(users.subList(start, end));
    }

    // ISSUE 36: Hardcoded security credentials
    @PostMapping("/admin")
    public ResponseEntity<String> adminOperation(@RequestParam String password) {
        // ISSUE 37: Hardcoded password comparison
        if (password.equals(ADMIN_PASSWORD)) {
            return ResponseEntity.ok("Admin access granted");
        }
        return ResponseEntity.status(403).body("Forbidden");
    }

    // ISSUE 38: No input sanitization
    @PostMapping("/export")
    public ResponseEntity<String> exportUserData(@RequestParam String format) {
        // ISSUE 39: No validation on format parameter
        String result = "Exporting in " + format + " format";

        // ISSUE 40: Potential SQL injection or command injection
        return ResponseEntity.ok(result);
    }
}