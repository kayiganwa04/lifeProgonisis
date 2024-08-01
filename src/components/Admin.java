package src.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Admin {
    public static boolean login(String password, String email) {
        String[] command = {"/bin/bash", "scripts/login.sh", "scripts/file1.txt", email, password};
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = reader.readLine();
            process.waitFor();
            return "success".equals(result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        // Example usage
        boolean isLoggedIn = login("123456", "kayiganwa04@gmail.com");
        System.out.println("Login successful: " + isLoggedIn);
    }
}
