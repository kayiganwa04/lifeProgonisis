package src.ui;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import src.components.Extensions;
import src.components.Patient;
import src.components.RandomString;

public class Main{

    
    public static RandomString randomStr = new RandomString();
    Extensions animation = new Extensions();
    
    public static String registerAPI(String em, String uuid, String role) {
        StringBuilder result = new StringBuilder();
        String randomKey;
        int stringLength = 24;
        randomKey = randomStr.getAlphaNumericString(stringLength);
        try {
            
            // Path to Git Bash on windows os:
            //String bashPath = "C:\\Program Files\\Git\\bin\\bash.exe";
             

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/register.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + em + " " + uuid + " " + role + " " + randomKey};

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

            // Full path to the script to check if a user had been onboarded or not:
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/confirmOnboarded.sh").toString();

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

    private static void loginUI() {
        int choice;
        Scanner input = new Scanner(System.in);
        String email, password;
    
        System.out.println("\n\n\u2139\uFE0F Enter your login details \n ");
    
        // Loop until a valid email is provided
        while (true) {
            System.out.print("\u270F\uFE0F Enter Email: ");
            email = input.nextLine();
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (email.matches(emailRegex)) {
                break;
            } else {
                System.err.println("\n\u274C Please input a valid email! \n");
            }
        }
    
        Console console = System.console();
        char[] hiddenPass = console.readPassword("\n\u270F\uFE0F Enter Password: ");
        password = new String(hiddenPass);
    
        if (password.length() > 0) {
            // Call User Login Class method
            String result = loginAPI(email, password); // Call Bash script API method
    
            if (result.toLowerCase().contains("true") && result.toLowerCase().contains("admin")) {
                // User is admin
                sessionEmail = email;
                sessionPass = password;
                // Run admin Menu UI:
                System.out.println("\n\n\u2139\uFE0F Welcome Admin! " + (char)3 + "\n");
                adminMenu();
    
            } else if (result.toLowerCase().contains("true") && result.toLowerCase().contains("patient")) {
                // User is Normal user - Patient
                sessionEmail = email;
                sessionPass = password;
                // Run patient Menu UI:
                patientMenu();
    
            } else {
                System.out.println("\n\u274C Invalid Email or Password \n");
                choice = 1;
            }
        } else {
            // No password provided:
            System.err.println("\n\n\u274C Password error! \n");
            choice = 1;
        }
    }
    

    private  static void completeUserRegistrationUI(){
        //Scanner objects to get user inputs
        Scanner input = new Scanner(System.in);

        String inputUUID, inputFName, inputLName, inputEmail;
        String  inputPassword = "";

        System.out.print("\n\u270F\uFE0F Enter your UUID : ");
        inputUUID = input.nextLine();

        System.out.print("\n\u270F\uFE0F Enter your Email : ");
        inputEmail = input.nextLine();

        //Check that there were actual input values:
        if(inputUUID.length() == 24 && inputEmail.length() > 5){

            /* Check the user text file, to see if the user had been onboarded, then:
                Continue with the registration process if this user with email and UUID was onboarded: */
                String result = confirmOnboardedStatus(inputEmail, inputUUID);

                if(new String(result).equals("true")){
                    System.out.println("\n\n\u2139\uFE0F Complete your registration, " + inputEmail + " !");

                    //Then go on to collect their data for final registration:
                    System.out.print("\n\n\u270F\uFE0F Enter your First Name : ");
                    inputFName = input.nextLine();
        
                    System.out.print("\n\u270F\uFE0F Enter your Last Name : ");
                    inputLName = input.nextLine();
        
                    String conPass;
                    boolean repeat = false;
                    Console console = System.console();
                    char[] hiddenPass1;
                    char[] hiddenPass2;

                    do { 
                        //
                        hiddenPass1 = console.readPassword("\n\u270F\uFE0F Enter your Password: ");
                        hiddenPass2 = console.readPassword("\n\u270F\uFE0F Confirm your Password: ");
                        if (!new String(hiddenPass1).equals(new String(hiddenPass2))) {
                            //Passwords do not match:
                            System.out.println("\n\u274C Passwords do not match! \n");
                            repeat = true;
                        }else{
                            //Passwords match:
                            inputPassword = new String(hiddenPass1);
                            repeat = false;
                        }
                    } while (repeat != false);
                    
                    //--- Call The registerUser(inputFName, inputLName, password) Method of the User Class ---
                    String result2 = completeUserRegistrationAPI(inputEmail, inputUUID, inputFName, inputLName, inputPassword);
                    if("true".equals(result2.toLowerCase())){
                        System.out.println("\n\n\u2705"+ inputFName+ " " + inputLName + ", your Registration has been completed! \n");
                    } else{
                        System.out.println("\n\n\u274C Failed to register your account! \n");
                    }
                    //return back to Menu
                } else{
                    System.out.println("\n\n\u274C Sorry, you can't perform this activity. Try again later!\n");
                    //go back
                }        
        }else{
            System.err.println("\n\n\u274C Please input valid email and UUID!! \n");
            //returns to Menu
        }
    }


    private static void onboardUserUI() {
    
        boolean isAdmin = false;  // Initialize isAdmin to a default value
        String userEmail, role, UUID;
        
        // Choice 1 input objects:
        Scanner input = new Scanner(System.in);
    
        // Get New User's email to onboard:
        System.out.print("\n\uD83D\uDC49 Enter user email: ");
        userEmail = input.nextLine();
    
        // Admin or Patient Role?:
        while (true) {
            System.out.println("\n\uD83E\uDD14 Should the user be an Admin? \n");
            System.out.println("   1. Yes\n");
            System.out.println("   2. No \n");
            System.out.print("\u270F\uFE0F Choose option--> ");
            
            try {
                int choice = input.nextInt();
                if (choice == 1 || choice == 2) {
                    isAdmin = (choice == 1);
                    break;
                } else {
                    System.out.println("\n\u274C Please enter a valid option!! \n");
                }
            } catch (Exception e) {
                System.out.println("\n\u274C Invalid input! Please enter a number.");
                input.next();  // Clear the invalid input
            }
        }
    
        // Assign User roles:
        role = isAdmin ? "Admin" : "Patient";
    
        // Generate new unique random user ID and save initial User data for new user onboarding to text file:
        UUID = randomStr.getAlphaNumericString(24);
    
        // Check if UUID generated and email are valid:
        if (UUID.length() == 24 && userEmail.length() > 6) {
            System.out.println("\n\u2139\uFE0F New user Email: " + userEmail);
            System.out.println("\n\u2139 New User's Role:  " + role);
            System.out.println("\n\u2139 New User's UUID:  " + UUID);
    
            // --- initiate OnboardUser(email, Role, UUID) Admin method: ---
            String result = registerAPI(userEmail, UUID, role);
            System.out.println(">>>>>>>>log" + result);
            // Success:
            if ("true".equals(result)) {
                System.out.println("\n\n\u2705 " + role + " User Onboarded Successfully!\n");
            } else {
                System.out.println("\n\n\u274C User Onboarding Failed!\n");
            }
    
            System.out.println("\n\n\u2705 Please mark down User Email and UUID for registration continuation!!\n ");
        } else {
            System.out.println("\n\n\u274C Invalid UUID or Email!\n");
        }
    }
    
    private static String exportUsersAPI(){
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/exportUserData.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath};

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
    private static void exportUsers(){
        //Export User data:
        //Call Bash export data script:
        String result;
        result = exportUsersAPI();
        if("false".equals(result)){
            System.err.println("\n\n\u274C Sorry, could not export User data!!");
        }else{
            System.out.println(result);
        }
    }


    
    private static String exportStatisticsAPI(){
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/exportStatistics.sh").toString();

            // Build the command
            String[] cmd = {"bash", "-c", scriptPath};

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
    private static void exportStatistics(){
        //Export Statistics data:
        //Call Bash export Statistics script:
        String result;
        result = exportStatisticsAPI();
        if("false".equals(result)){
            System.err.println("\n\n\u274C Sorry, could not export Statistics data!!");
        }else{
            System.out.println(result);
        }
    }

    private static void adminMenu(){
            Extensions animation = new Extensions();

               //App option choice as int: local int
               int choice = -1;

               //Scanner objects to get user inputs
               Scanner input = new Scanner(System.in);

               //Admin Menu:
        do{
            System.out.println("\n\uD83D\uDEE0  Admin Menu \n");
            System.out.println("\u2795 1. Onboard new user \n");
            System.out.println("\uD83D\uDCE4 2. Export user data \n");
            System.out.println("\uD83D\uDCCA 3. Export statistical data \n");
            System.out.println("\uD83D\uDD13 0. Logout \n\n");

            System.out.print("\u270F\uFE0F Choose option--> ");
            try {
                
            //Get User Input Choice:
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    onboardUserUI();
                    break;
                case 2:
                    exportUsers();
                    break;
                case 3:
                    exportStatistics();
                    break;
                case 0:
                    break;
                default:
                    if (choice != 0) {
                        System.out.println("\n\u274C Please enter a valid option!! \n");
                    }
                    break;

            }
        } catch (Exception e) {
            System.out.println("\n\u274C Invalid input! Please enter a number.");
            input.next(); 
        }

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
                            if (line.contains("\u274CUser not found")) {
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
                throw new RuntimeException("\u274C Script execution failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    private static String saveEstimatetoCSVAPI(String fn, String ln, String email, int age, String country, String onARTStatus, int estimateVal){
        String result = "";
        try {
            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/saveStatistic.sh").toString();
            // Build the command
            String[] cmd = {"bash", "-c", scriptPath + " " + fn + " " + ln + " " + email + " " + age + " " + country + " " + onARTStatus + " " + estimateVal};

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
                throw new RuntimeException("\u274C Script execution failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;  
    }
    public static String sessionFName = " ";
    public static String sessionLName = " ";

    private static void evaluteLifeExpectancyUI(){
        //Needed variables:
        String country, takingART, lifeExpecResultFromAPI;
        int currentAge, ageContracted, ageStartedTakingART;
        double countryLifeExpec = 0;
        boolean onART = false;

        Scanner input = new Scanner(System.in);

        System.out.print("\n\u270F\uFE0F Which country are you from (Enter country code e.g: RW) ");
        country = input.nextLine();

        while (true) {
            System.out.println("\n\u270F\uFE0F Are you on ART Treatment?\n");
            System.out.println("   1. Yes\n");
            System.out.println("   2. No \n");
            System.out.print("\u270F\uFE0F Choose option--> ");
            try {
                int choice = input.nextInt();
                if (choice == 1) {
                    takingART = "Yes";
                    break;
                } else if (choice == 2) {
                    takingART = "No";
                    break;
                }
                 else {
                    System.out.println("\n\u274C Please enter a valid option!! \n");
                }
            } catch (Exception e) {
                System.out.println("\n\u274C Invalid input! Please enter a number.");
                input.next();  // Clear the invalid input
            }
        }

        
        takingART = input.nextLine();

        System.out.print("\n\u270F\uFE0F What\'s your current age(in Years e.g: 30): ");
        currentAge = input.nextInt();

        System.out.print("\n\u270F\uFE0F How old where you when you first contracted HIV? (in Years) : ");
        ageContracted = input.nextInt();

        System.out.print("\n\u270F\uFE0F At What age did you start your treatment? (in Years e.g: 31): ");
        ageStartedTakingART = input.nextInt();

        //Patient is on ART ? True or False:
        onART = "yes".equals(takingART.toLowerCase());
        int estimate = 0;

        lifeExpecResultFromAPI = getCountryLifeExpectancyAPI(country);
        countryLifeExpec = Double.valueOf(lifeExpecResultFromAPI);
        if(!"false".equals(lifeExpecResultFromAPI) && countryLifeExpec > 0){
            System.out.println("\n\n\u2705 General Life Expectancy in  "+ country + " is: " + countryLifeExpec);

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
        }else{
            System.out.println("\n\u274C Sorry check your country code and try again!\n");
        }
        Extensions animation = new Extensions();
        animation.speak("Your HIV Survival rate is: " + estimate + " Year(s) from now");
        System.out.println("\n\n ***************************************************************************************************** \n");
        System.out.println("\n\u2705 Your HIV Survival rate is: " + estimate + " Year(s) from now!!\n");
        System.out.println("\n\n ***************************************************************************************************** \n\n");
        
        //Save to csv:
        String result;
        result = saveEstimatetoCSVAPI(sessionFName, sessionLName, sessionEmail, currentAge, country, takingART, estimate);
        if(!"success".equals(result)){
            //error:
            System.err.print("\n\n\u274C Could not save your statistics!!");
        }else{
            System.out.println("\n\n\u2705 Statistic saved successfuly!");
        }
    }
    private static void downloadSchedule(){
        //Download calendar Schedule from API - for Patients

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

        //Assign Fname to be used anywhere in the Patient Menu:
        sessionFName = patient.getFirstName();
        sessionLName = patient.getLastName();
        //Render Patient Menu:
                      //Patient ption choice as int: local int
       
                      int choice;
                      //Scanner objects to get user inputs
                      Scanner input = new Scanner(System.in);
       
                      //Normal user - Patient Dashboard Menu:
                      Extensions animation = new Extensions();
                      animation.speak(" Welcome back, " + patient.getFirstName() + " " + patient.getLastName());
                      System.out.println("\n\n Welcome back, " + patient.getFirstName() + " " + patient.getLastName() + (char)3);
               do{

                   System.out.println("\n\n\uD83C\uDFE0 Dashboard: \n\n");
                   System.out.println("\uD83E\uDDEE 1. Evaluate life expectancy \n");
                   System.out.println("\uD83D\uDCC4 2. View your information \n");
                   System.out.println("\u270F\uFE0F 3. Update your information \n");
                   System.out.println("\uD83D\uDCE5 4. Download Schedule of demise \n");
                   System.out.println("\uD83E\uDD16 5. AI therapy assistant \n");
                   System.out.println("\uD83D\uDEAA 0. Logout \n\n");
       
                   System.out.print("\u270F\uFE0F Enter Option--> ");
       
                   //Get User Input Choice:
                   choice = (int)input.nextInt();
       
                   if(choice == 1){
                        System.out.println("\n\uD83E\uDDEE Estimate Life Expectancy: \n");
                       //Call Up Calculate Life Expectancy interface;
                       evaluteLifeExpectancyUI();
       
                   }else if(choice == 2){
                        //Retrieve Users firstname and lastname:

                        //Get Reload fname and lastname:
                        patient.setFirstName(getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword())[0]);
                        patient.setLastName(getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword())[1]);
                       System.out.println("\n\n First name: " + patient.getFirstName());
                       System.out.println("\n Last name: " + patient.getLastName());
                       System.out.println("\n Email address: " + patient.getEmail() +"\n\n");                       

                   }else if(choice == 3){
                    System.out.println("\n\u2139\uFE0F Update Your information: \n");
                    
                    while(true){
                        System.out.println("\n\u270F\uFE0F Are you sure you want to update your information?\n");
                        System.out.println("   1. Yes\n");
                        System.out.println("   2. No \n");
                        System.out.print("   \u270F\uFE0F Choose option--> ");
                        
                        String newFName, newLName;
    
                        Scanner choiceOption = new Scanner(System.in);
                        Scanner updateInput = new Scanner(System.in);
                        try {
                            int option = choiceOption.nextInt();
                            if (option == 1) {
                                System.out.print("\n\u270F\uFE0F Enter new First name: ");
                                newFName = updateInput.nextLine();
                                System.out.print("\n\u270F\uFE0F Enter new Last name: ");
                                newLName = updateInput.nextLine();

                                //-- SaveUpdatedDataAPI() --:
                                String res = SaveUpdatedDataAPI(patient.getEmail(), patient.getPassword(), newFName, newLName);
                                if("true".equals(res.toLowerCase())){
                                    //Save successful:
                                    System.out.println("\n\u2705 Data Updated Successfully!! \n");
                                }else{
                                    System.err.print("\n\u274C Update Failed!! \n");
                                }
                                    break;
                            } else if (option == 2) {
                                break;
                            }
                            else {
                                System.out.println("\n\u274C Please enter a valid option!! \n");
                            }
                        } catch (Exception e) {
                            System.out.println("\n\u274C Invalid input! Please enter a number.");
                            updateInput.next(); // Clear the invalid input
                        }
                    }
                   }else if(choice == 4){
                    System.out.println("\n Coming soon!! \n");
                    
                    //Call Up Download Schedule of demise interface;
                    downloadSchedule();
                    
                   }else if(choice == 5){
                    System.out.println("\n\n\uD83C\uDFE5 \uD83E\uDD16 --- Virtual AI Therapy Assistant --- \n\n");
                    boolean cont = true;
                    //AI Chat interface;
                    String query;
                    
                    do{
                        System.out.println("\n\n\uD83D\uDC64 Enter your query (Enter 0 to quit chat!): ");
                        query = input.nextLine();
                        if("0".equals(query)){
                            cont = false;
                        }else{
                            animation.queryAI(query);
                        }
                        
                    }while(cont == true);
                
                   } else{
                    if(choice !=0){
                        System.out.println("\n\u274C Please enter a valid option!! \n");
                    }
                   }
               }while(choice != 0);
            }else{
                //Cannot access:
                System.out.println("\n\u274C Sorry, try again!! \n");
            }
    }

    public static void main(String[] args) {

        System.out.println("\n");
        // ----------------------- welcome Animation --------------------
        final String YELLOW = "\u001B[33m";
        Extensions animation = new Extensions();
        animation.createAnimation();

        //--------------------------------------------------------------
        System.out.println("\n\n\uD83C\uDFE5 Welcome To Life Prognosis! \n");
        //App option choice as int:
        int choice = -1;
        //Scanner objects to get user inputs
        Scanner input = new Scanner(System.in);
        //Choice menu:
        //do while loop if choice is not 0:
        do {
            System.out.println(YELLOW + "\n\uD83C\uDFE0 Menu \n\n");
            System.out.println("1. \uD83D\uDD10 Login \n");
            System.out.println("2. \uD83D\uDCDD Registration \n");
            System.out.println("0. \uD83D\uDEAA Exit \n\n");

            System.out.print("\u270F\uFE0F Choose Option--> ");

            try {
                // Get User Input Choice:
                choice = input.nextInt();

                switch (choice) {
                    case 1:
                        // Login Interface
                        loginUI();
                        break;
                    case 2:
                        // Registration Module using UUID:
                        // Call up the complete registration screen/UI:
                        completeUserRegistrationUI();
                        break;
                    case 0:
                        break;
                    default:
                        if (choice != 0) {
                            System.out.println("\n\u274C Please enter a valid option!! \n");
                        }
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\u274C Invalid input! Please enter a number.");
                input.next(); // Clear the invalid input from the scanner buffer
            }

        } while (choice != 0);
    }
}