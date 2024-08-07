package src.components;

import java.io.IOException;

//Child class of User
class Patient extends User {

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
}