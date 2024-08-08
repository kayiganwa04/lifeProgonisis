package src.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Scanner;
import src.components.Patient;
import src.components.RandomString;

public class Main{

    
    public static String registerAPI(String em, String uuid, String role) {
        StringBuilder result = new StringBuilder();
        try {
            
            // Path to Git Bash on windows os:
            //String bashPath = "C:\\Program Files\\Git\\bin\\bash.exe";

            /*
             * Build command on windows os would be: 
             * String[] cmd = {"bashPath", "-c", scriptPath + " " + em + " " + uuid + " " + role};
             * 
             * */
             

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/register.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + em + " " + uuid + " " + role};

            // Debug information
            //System.out.println("Executing command: " + String.join(" ", cmd));

            // Start the process
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true); // Combine error and output streams
            Process process = pb.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            // Wait for the process to complete
            //int exitCode = process.waitFor();
            //System.out.println("Exit code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString(); //return "true" for success!
    }
    
    public static String loginAPI(String em, String pwd) {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/login.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + em + " " + pwd};

            // Start the process
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true); // Combine error and output streams
            Process process = pb.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String confirmOnboardedStatus(String em, String uuid) {
        StringBuilder result = new StringBuilder();
        try {
            System.out.println("got here" + em + uuid);

            // Full path to the script to check if a user had been onboarded or not:
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/confirmOnboarded.sh").toString();
            System.out.println("path" + scriptPath);

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + em + " " + uuid};

            // Start the process
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true); // Combine error and output streams
            Process process = pb.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    

    
    public static String completeUserRegistrationAPI(String em, String uuid, String fname, String lname, String password) {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/completeOnboarding.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + em + " " + uuid + " " + fname + " " + lname + " " + password};

            // Start the process
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true); // Combine error and output streams
            Process process = pb.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    
    //Saves Logged in User session:
    private static String sessionEmail = "", sessionPass = "";

    private static void loginUI(){
        int choice;
        //Scanner objects to get user inputs
        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        String email, password;

        System.out.println("\n\n\t\t\t Enter your login details \n ");
        System.out.println("\t\t Email: \t");
        email = input1.nextLine();

        if(email.length() > 6){
            System.out.println("\t\t Password: \t");
            password = input2.nextLine();
            if(password.length() > 0){
                /* --- call User Login Class method: ---

                Checks and validate login credentials then:
                notes user role type, to determine their type of Dashboard,
                either patientUI or adminUI */

                String result = loginAPI(email, password); //Call Bash script API method

                if(result.toLowerCase().contains("true") && result.toLowerCase().contains("admin")){
                     //User is admin
                     sessionEmail = email;
                     sessionPass = password;
                    //Run admin Menu UI:
                    System.out.println("\n\n\t\t\t\t Welcome Admin! "+ (char)3+"\n");
                    adminMenu();

                }else if(result.toLowerCase().contains("true") && result.toLowerCase().contains("patient")){
                    //User is Normal user - Patient
                    sessionEmail = email;
                    sessionPass = password;
                    //Run patient Menu UI:
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
            System.err.println("\n\n\tPlease input a valid email! \n");
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
        if(inputUUID.length() == 12 && inputEmail.length() > 5){

            /* Check the user text file, to see if the user had been onboarded, then:
                Continue with the registration process if this user with email and UUID was onboarded: */
                String result = confirmOnboardedStatus(inputEmail, inputUUID);

                if(new String(result).equals("true")){
                    System.out.println("\n\n\t\t\t\t Complete your registration, " + inputEmail + " !");

                    //Then go on to collect their data for final registration:
                    System.out.println("\n\n\t\t\t Enter your First Name :--> \t ");
                    inputFName = input3.nextLine();
        
                    System.out.println("\n\n\t\t\t Enter your Last Name :--> \t ");
                    inputLName = input4.nextLine();
        
                    System.out.println("\n\n\t\t\t Enter your Password :--> \t ");
                    inputPassword = input5.nextLine();

                    //--- Call The registerUser(inputFName, inputLName, password) Method of the User Class ---
                    String result2 = completeUserRegistrationAPI(inputEmail, inputUUID, inputFName, inputLName, inputPassword);
                    if(new String(result2).equals("true")){
                        System.out.println("\n\n\t\t\t\t"+ inputFName+ " " + inputLName + ", your Registration has been completed! \n");
                    } else{
                        System.out.println("\n\n\t\t\t\t Failed to register your account! \n");
                    }
                    //return back to Menu
                } else{
                    System.out.println("\n\n\t\t\t\t Sorry, you can't perform this activity. Try again later!\n");
                    //go back
                }        
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

         //Check if UUID generated and email are valid:
         if(UUID.length() > 0 && UUID.length() == 12 && userEmail.length() > 6){
            System.out.println("\t\t\tNew user Email: " + userEmail + "\n");

            System.out.println("\n\t\t\tNew User\'s Role:  "+ role+"\n");
            System.out.println("\n\t\t\tNew User\'s UUID:  "+ UUID+"\n");
   
            //--- inititiate OnboardUser(email, Role, UUID) Admin method: ---
            String result = registerAPI(userEmail, UUID, role);

            //Success:
            if(new String(result).equals("true")){
               System.out.println("\n\n\t\t\t"+ role +" User Onboarded Successfully!\n");
            }else{
                //Failure
               System.out.println("\n\n\t\t\tUser Onboarding Failed!\n");
            }
   
            System.out.println("\n\t\t\t\t\t Please mark down User Email and UUID for registration continuation!!\n ");
         }else{
            System.out.println("\n\n\t\t\tInvalid UUID or Email!\n");
         }

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

            } else{
                if(choice !=0){
                    System.out.println("\n\t\t\t\t Please enter a valid option!! \n");
                }
               }
            //Choice 0 will Logout Admin from his Dashboard!
        }while(choice != 0);


    }

    private static String[] getLoggedInUserDataAPI(String em, String pwd){
        //returns the loggedin user data in an array:
        String[] result = new String[2];
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/retrieveUserData.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + em + " " + pwd};


                        // Execute the command
                        Process process = Runtime.getRuntime().exec(cmd);

                        // Get the output of the bash script
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains("User not found")) {
                                return null; // Return null if user is not found
                            } else {
                                // Assuming output format is "(firstname, lastname)"
                                line = line.replaceAll("[()]", "").trim(); // Remove parentheses
                                result = line.split(", "); // Split by comma and space
                            }
                        }
            
                        // Wait for the process to complete
                        process.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return result;
        
    }

    private static String SaveUpdatedDataAPI(String email, String password, String fname, String lname){
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/saveUpdatedData.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + email + " " + password + " " + fname + " " + lname};

            // Start the process
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true); // Combine error and output streams
            Process process = pb.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static String getCountryLifeExpectancyAPI(String country){
        //Call bash to get info from life-expectancy.csv:
        String result = "";
        try {
            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/getLifeExpectancy.sh").toString();
            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + country};

            // Execute the command
            Process process = Runtime.getRuntime().exec(cmd);

            // Get the output of the bash script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Script execution failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static void evaluteLifeExpectancyUI(){
        //Needed variables:
        String country, takingART, lifeExpecResultFromAPI;
        int currentAge, ageContracted, ageStartedTakingART;
        double countryLifeExpec = 0;
        boolean onART = false;

        Scanner input = new Scanner(System.in);

        System.out.println("\n\n\t\t\t Which country are you from: ");
        country = input.nextLine();

        System.out.println("\n\t\t\t Are you on ART Treatment ?(Yes or No): ");
        takingART = input.nextLine();

        System.out.println("\n\t\t\t What\'s your current age(in Years): ");
        currentAge = input.nextInt();

        System.out.println("\n\t\t\t How old where you when you first contracted HIV? (in Years) : ");
        ageContracted = input.nextInt();

        System.out.println("\n\t\t\t At What age did you start your treatment? (in Years): ");
        ageStartedTakingART = input.nextInt();

        //Patient is on ART ? True or False:
        onART = "yes".equals(takingART.toLowerCase());
        int estimate = 0;

        lifeExpecResultFromAPI = getCountryLifeExpectancyAPI(country);
        if(!"false".equals(lifeExpecResultFromAPI)){
            countryLifeExpec = Double.valueOf(lifeExpecResultFromAPI);
            System.out.println("\n\t\t General Life Expectancy in  "+ country + " is: " + countryLifeExpec);

            //Write Logic to calculate Patients's survivalRate:
            double survivalRate = 0;
            if(onART == true){
                int x = ageStartedTakingART - ageContracted;
                int y = (int)countryLifeExpec - ageContracted;
                for(int i=0; i< x+1; i++){
                    y *= 0.9;
                }
                estimate = y;
            }else{
                //Not on ART: Supposed to live max 5years to live:
                int y = currentAge - ageContracted;
                if(y < 6){
                    estimate = 5-y;
                }
            }
        }

        System.out.println("\n\n\t\t\t\t Your HIV Survival rate is: " + estimate + " Year(s) from now!!\n");
    }
    private static void downloadSchedule(){
        //Download calendar Schedule from API


    }

    private static void patientMenu(){
        Patient patient = new Patient();
        patient.setEmail(sessionEmail);
        patient.setPassword(sessionPass);


        //Get unique User data:
        //Check and make sure patient email and password is set:
        if(!patient.getEmail().isEmpty() && !patient.getPassword().isEmpty()){

        //Retrieve Users firstname and lastname:
        String[] userData = getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword());

        //set fname and lastname:
        patient.setFirstName(userData[0]);
        patient.setLastName(userData[1]);
        //Render Patient Menu:
                      //Patient ption choice as int: local int
       
                      int choice;
                      //Scanner objects to get user inputs
                      Scanner input = new Scanner(System.in);
       
                      //Normal user - Patient Dashboard Menu:
                      System.out.println("\n\t\t\t\t Welcome back, " + patient.getFirstName() + " " + patient.getLastName() + (char)3+"\n");
               do{

                   System.out.println("\n\t\t\t\t Dashboard: \n");
                   System.out.println("\t\t\t 1. Evaluate life expectancy \n");
                   System.out.println("\t\t\t 2. View your information \n");
                   System.out.println("\t\t\t 3. Update your information \n");
                   System.out.println("\t\t\t 4. Download Schedule of demise \n");
                   System.out.println("\t\t\t 0. Logout \n\n");
       
                   System.out.print("\t\t\t Enter Option--> ");
       
                   //Get User Input Choice:
                   choice = (int)input.nextInt();
       
                   if(choice == 1){
                        System.out.println("\n\t\t\t Estimate Life Expectancy: \n");
                       //Call Up Calculate Life Expectancy interface;
                       evaluteLifeExpectancyUI();
       
                   }else if(choice == 2){
                        //Retrieve Users firstname and lastname:

                        //Get Reload fname and lastname:
                        patient.setFirstName(getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword())[0]);
                        patient.setLastName(getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword())[1]);
                       System.out.println("\n\n\t\t\t First name: " + patient.getFirstName());
                       System.out.println("\n\t\t\t Last name: " + patient.getLastName());
                       System.out.println("\n\t\t\t Email address: " + patient.getEmail() +"\n\n");                       

                   }else if(choice == 3){
                    System.out.println("\n\t\t\t\t Update Your information: \n");
                    System.out.println("\n\t\t\t 1. Are you sure you want to update your information: (Yes or No) --> ");
                    
                    String updateCntrl, newFName, newLName;

                    Scanner updateInput = new Scanner(System.in);
                    updateCntrl = updateInput.nextLine();
                    if("yes".equals((String)updateCntrl.toLowerCase())){
                        System.out.println("\n\t\t\t\t 1. Enter new First name: ");
                        newFName = updateInput.nextLine();
                        System.out.println("\n\t\t\t\t 1. Enter new Last name: ");
                        newLName = updateInput.nextLine();

                        //-- SaveUpdatedDataAPI() --:
                        String res = SaveUpdatedDataAPI(patient.getEmail(), patient.getPassword(), newFName, newLName);
                        System.out.println(res+"\n");
                        if("true".equals(res.toLowerCase())){
                            //Save successful:
                            System.out.println("\n\t\t\t Data Updated Successfully!! \n");
                        }else{
                            System.err.print("\n\t\t\t Update Failed!! \n");
                        }
                    }
       
                   }else if(choice == 4){
                    System.out.println("\n\t\t Coming soon!! \n");
                    
                    //Call Up Download Schedule of demise interface;
                    downloadSchedule();
                    
                   } else{
                    if(choice !=0){
                        System.out.println("\n\t\t\t\t Please enter a valid option!! \n");
                    }
                   }
               }while(choice != 0);
            }else{
                //Cannot access:
                System.out.println("\n\t\t\t\t Sorry, try again!! \n");
            }
    }


    public static void main(String[] args) {
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

            }else{
                if(choice !=0){
                System.out.println("\n\t\t\t\t Please enter a valid option!! \n");
                }
               }
            //Choice 0 Exits the program!
        }while(choice != 0);

    }

}