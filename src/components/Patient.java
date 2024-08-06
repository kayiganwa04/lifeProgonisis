package src.components;

//Class Patient inherit from absstract user class:
public class Patient extends  User {
    
    public void viewData(String fname, String lname, String email, String userRole){
        System.out.println("\t\t First name: "+fname.toUpperCase());
        System.out.println("\t\t Last name: "+lname.toUpperCase());
        System.out.println("\t\t Email address: "+email.toUpperCase());
        System.out.println("\t\t Account type: "+userRole.toUpperCase());
    }
    public boolean updateData(String fname, String lname, String Password){
        return true;
    }
}
