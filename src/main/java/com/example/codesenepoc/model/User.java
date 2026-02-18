package com.example.codesenepoc.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    
    // Intentionally complex method for CodeScene to detect
    public String getFullDetails() {
        String details = "";
        if (username != null && !username.isEmpty()) {
            details += "Username: " + username;
        }
        if (email != null && !email.isEmpty()) {
            if (!details.isEmpty()) details += ", ";
            details += "Email: " + email;
        }
        if (firstName != null && !firstName.isEmpty()) {
            if (!details.isEmpty()) details += ", ";
            details += "First Name: " + firstName;
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (!details.isEmpty()) details += ", ";
            details += "Last Name: " + lastName;
        }
        return details;
    }
}