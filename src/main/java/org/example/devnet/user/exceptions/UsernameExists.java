package org.example.devnet.user.exceptions;

public class UsernameExists extends RuntimeException {
    public UsernameExists(String message) {
        super("Username already exists: " + message);
    }

    public UsernameExists() {
        super("Username already exists!");
    }
}
