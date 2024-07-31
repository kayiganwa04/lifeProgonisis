package src.ui;

//import Java Scanner class
import java.util.Scanner;

public class Main{

    //login function: inherits User.login Method
    private static void login() {}

    //register function: inherits User.login Method
    private static void register() { /*implements User registration*/  }

    private static void adminMenu(){

    }
    private static void patientMenu(){
        
    }

    public static void main(String[] args){
        System.out.println("\n\n\t\t\t\t Welcome To Life Prognosis! \n");

        //App option choice as int:
        int choice;
        //Scanner objects to get user inputs
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        Scanner input3 = new Scanner(System.in);
        //Choice menu:
        //do while loop if choice is not 0:
        do{
            System.out.println("\n\t\t\t\t\t Menu \n");
            System.out.println("\t\t\t 1. Login \n");
            System.out.println("\t\t\t 2. Registration \n");
            System.out.println("\t\t\t 0. Exit \n\n");

            System.out.print("\t\t\t Choose Option--> ");

            //Get User Input Choice:
            choice = input.nextInt();

            if(choice == 1){
                //Login Module
                String email, password;

                System.out.println("\n\n\t\t\t Enter your login details \n ");
                System.out.println("\t\t Email: \t");
                email = input2.nextLine();

                if(email.length() > 0){
                    System.out.println("\t\t Password: \t");
                    password = input3.nextLine();
                    if(password.length() > 3){
                        //call Login method:
                        //If successful, run the Appropriate User interface
                    }
                }else{
                    System.err.println("\n\n\tPlease input an email!\n");
                    choice = 1;
                }

                //Login Bash Api call:
                //userLogin (em, pwd);

            }else if(choice == 2){
                //Registration Module
            }
            //Choice 0 Exits the program!
        }while(choice != 0);

    }
}