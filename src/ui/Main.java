package src.ui;

import java.util.Scanner;
import src.components.RandomString;

public class Main{

    private static  void loginUI(){
        int choice;
        //Scanner objects to get user inputs
        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        String email, password;

        System.out.println("\n\n\t\t\t Enter your login details \n ");
        System.out.println("\t\t Email: \t");
        email = input1.nextLine();

        if(email.length() > 0){
            System.out.println("\t\t Password: \t");
            password = input2.nextLine();
            if(password.length() > 0){

                /* --- call User Login Class method: ---

                Checks and validate login credentials then:
                notes user role type, to determine their type of Dashboard,
                either patientUI or adminUI */

                if(new String("admin@user.com").equals(email) && new String("admin").equals(password)){
                     //User is admin
                    //Run admin Menu UI:
                    System.out.println("\n\n\t\t\t\t Welcome Admin! "+ (char)3+"\n");
                    adminMenu();

                }else if(new String("patient@user.com").equals(email) && new String("patient").equals(password)){
                    //User is Normal user - Patient
                    //Run patient Menu UI:
                    System.out.println("\n\n\t\t\t\t Welcome Back! "+ (char)3+"\n");
                    patientMenu();

                }else{
                    System.out.println("\n\n\t\t\t\t Invalid Email or Password \n");
                    choice = 1;
                }
            }else{
                //No password provided:
                System.err.println("\n\n\t Password error! \n");
                choice = 1;
            }
        }else{
            System.err.println("\n\n\tPlease input an email! \n");
        }

    }

    private  static void completeUserRegistrationUI(){
        //Scanner objects to get user inputs
        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        Scanner input3 = new Scanner(System.in);
        Scanner input4 = new Scanner(System.in);
        Scanner input5 = new Scanner(System.in);

        String inputUUID, inputFName, inputLName, inputEmail, inputPassword;

        System.out.println("\n\n\t\t\t Enter your UUID :--> \t ");
        inputUUID = input1.nextLine();

        System.out.println("\n\n\t\t\t Enter your Email :--> \t ");
        inputEmail = input2.nextLine();

        //Check that there were actual input values:
        if(inputUUID.length() > 0 && inputEmail.length() > 0){
            /* Check the user text file, to see if the user had been onboarded, then:
                Continue with the registration process if this user with email and UUID was onboarded: */

                //Then go on to collect their data for final registration:
                System.out.println("\n\n\t\t\t Enter your First Name :--> \t ");
                inputFName = input3.nextLine();
        
                System.out.println("\n\n\t\t\t Enter your Last Name :--> \t ");
                inputLName = input4.nextLine();
        
                System.out.println("\n\n\t\t\t Enter your Password :--> \t ");
                inputPassword = input5.nextLine();

                //--- Call The registerUser(inputFName, inputLName, password) Method of the User Class ---

                System.out.println("\n\n\t\t\t" + inputFName+ " " + inputLName + "\'s registration is successful!! \n");
                //return back to Menu
        }else{
            System.err.println("\n\n\t\t Please input valid email and UUID!! \n");
            //returns to Menu
        }
    }


    private static void onboardUserUI(){
        
        boolean isAdmin;
        String userEmail, checkAdminRole, role, UUID;

        //Choise 1 input objects:
        Scanner input2 = new Scanner(System.in);
        Scanner input3 = new Scanner(System.in);

        //Get New User's email to onboard:
        System.out.println("\t\t Enter user email: \t");
        userEmail = input2.nextLine();

        //Admin or Patient Role?:
        System.out.println("\t\t Should the user be an Admin? Yes or No :-->  \t");
        checkAdminRole = input3.nextLine();

        if(checkAdminRole.toLowerCase() == "yes"){
            isAdmin = true;
        }else{
            isAdmin = false;
        }

        //Assign User roles:
        if(isAdmin){
            role = "Admin";
        }else{
            role = "Patient";
        }

        //Generate new unique random user id and Save initial User data for new user Onboarding to text file:
        RandomString randomStr = new RandomString();
        int stringLength = 12;
        UUID = randomStr.getAlphaNumericString(stringLength);

         //Check if UUID generated is valid:
         if(UUID.length() > 0 && UUID.length() == 12){
            System.out.println("\t\t\tNew user Email: " + userEmail + "\n");
         }
         System.out.println("\n\t\t\tNew User\'s Role:  "+ role+"\n");
         System.out.println("\n\t\t\tNew User\'s UUID:  "+ UUID+"\n");

         //--- inititiate OnboardUser(email, Role, UUID) Admin method; from the Admin.onboardUser() method. ---

         System.out.println("\n\t\t\t\t\t Please mark down User Email and UUID for registration continuation!!\n ");
    }

    private static void adminMenu(){

               //App option choice as int: local int
               int choice;

               //Scanner objects to get user inputs
               Scanner input = new Scanner(System.in);

               //Admin Menu:
        do{
            System.out.println("\n\t\t\t\t\t Admin Menu \n");
            System.out.println("\t\t\t 1. Onboard new user \n");
            System.out.println("\t\t\t 2. Export user data \n");
            System.out.println("\t\t\t 3. Download statistical data \n");
            System.out.println("\t\t\t 0. Logout \n\n");

            System.out.print("\t\t\t Admin Option--> ");

            //Get User Input Choice:
            choice = input.nextInt();

            if(choice == 1){
                //onboard interface;
                onboardUserUI();

            }else if(choice == 2){
                //Export user data method:
                System.out.println("\n\t\t Coming soon!! \n");
               
            }else if(choice == 3){
                //Download Statistics:
                System.out.println("\n\t\t Coming soon!! \n");

            }
            //Choice 0 will Logout Admin from his Dashboard!
        }while(choice != 0);


    }


    private static void patientMenu(){
        //Render Patient Menu:
                      //Patient ption choice as int: local int
       
                      int choice;
                      //Scanner objects to get user inputs
                      Scanner input = new Scanner(System.in);
       
                      //Normal user - Patient Dashboard Menu:
               do{
                   System.out.println("\n\t\t\t\t Dashboard: \n");
                   System.out.println("\t\t\t 1. Evaluate life expectancy \n");
                   System.out.println("\t\t\t 2. Update your information \n");
                   System.out.println("\t\t\t 3. Download Schedule of demise \n");
                   System.out.println("\t\t\t 0. Logout \n\n");
       
                   System.out.print("\t\t\t Enter Option--> ");
       
                   //Get User Input Choice:
                   choice = (int)input.nextInt();
       
                   if(choice == 1){
                        System.out.println("\n\t\t Coming soon!! \n");
                       //Call Up Calculate Life Expectancy interface;
                       //evaluteLifeExpectancyUI();
       
                   }else if(choice == 2){
                       System.out.println("\n\t\t Coming soon!! \n");
                        /* Call Up Update User data interface: Displays then:
                             ask if user wants to update their info (Yes or No) :
                             if yes go ahead and take new firstName, lastName and Password then:
                             Update that user's data! */
                             //updateUserDataUI();

                   }else if(choice == 3){
                    System.out.println("\n\t\t Coming soon!! \n");
                       /* Download Schedule of demise: calls up the UI,
                        which impliments the Patient downloadSchedule method then:
                         displays (success or failure) as to whether is was downloaded! */
                       //downloadScheduleOfDemiseUI();
       
                   }
                   //Choice 0 will Logout Admin from his Dashboard!
               }while(choice != 0);
       
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
                //Login Interface 
                loginUI();

            }else if(choice == 2){
                //Registration Module using uuid:
                //call up the complete registration screen/UI:
                completeUserRegistrationUI();

            }
            //Choice 0 Exits the program!
        }while(choice != 0);

    }

}