package src.components;

import java.io.IOException;

public class Admin {

    public static boolean register(String password, String email) {
        String[] command = {"/bin/bash", "scripts/register.sh", "scripts/file1.txt", email, password};
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean login(String password, String email) {
        String[] command = {"/bin/bash", "scripts/login.sh", "scripts/file1.txt", email, password};
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        // Example usage
        boolean isRegistered = register("123456", "newuser8@example.com");
        System.out.println("Registration successful: " + isRegistered);

        // boolean isLoggedIn = login("123456", "kayiganwa04@gmail.com");
        // System.out.println("Login successful: " + isLoggedIn);
    }
}
