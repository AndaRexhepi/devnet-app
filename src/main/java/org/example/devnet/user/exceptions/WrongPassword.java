package org.example.devnet.user.exceptions;

public class WrongPassword extends RuntimeException {

    public WrongPassword(String message) {
        super("Wrong password: " + message);
    }

}
