package src.components;

import java.io.IOException;

public abstract class User {
    private  String firstName;
    private  String lastName;
    private String email;
    private  String password;
    private  String role;
    private String uuid;

    //Getters:
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    public String getUUID(){
        return uuid;
    }

    //Setters:
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setRole(String role){
        this.role = role;
    }

    public static boolean login(String password, String email) {
        String[] command = {"bash", "scripts/login.sh", "scripts/Resources/user-store.txt", email, password};
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
    public void updateUserData(){
        
    }
}
