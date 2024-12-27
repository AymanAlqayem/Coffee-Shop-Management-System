package org.example.dbp.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
    /**
     * hashPassword method that will hash the password to store it in the DB.
     * Using SHA-256 technique.
     * */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

}
