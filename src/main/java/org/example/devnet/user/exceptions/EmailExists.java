package org.example.devnet.user.exceptions;

public class EmailExists extends RuntimeException {
    public EmailExists(String message) {
        super(
                "Email already exists: " + message
        );
    }

    public EmailExists() {
        super("Email already exists");
    }
}
