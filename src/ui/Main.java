package src.ui;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import src.components.Country;
import src.components.Extensions;
import src.components.Patient;
import src.components.RandomString;

public class Main {

    public static RandomString randomStr = new RandomString();
    Extensions animation = new Extensions();

    public static String registerAPI(String em, String uuid, String role) {
        StringBuilder result = new StringBuilder();
        String randomKey;
        int stringLength = 24;
        randomKey = randomStr.getAlphaNumericString(stringLength);
        try {

            // Path to Git Bash on windows os:
            // String bashPath = "C:\\Program Files\\Git\\bin\\bash.exe";

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/register.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + em + " " + uuid + " " + role + " " + randomKey };

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
            // int exitCode = process.waitFor();
            // System.out.println("Exit code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString(); // return "true" for success!
    }

    public static String loginAPI(String em, String pwd) {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/login.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + em + " " + pwd };

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

    public static String confirmOnboardedStatus(String uuid) {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script to check if a user had been onboarded or not:
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/confirmOnboarded.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + uuid };

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

    public static String completeUserRegistrationAPI(String uuid, String fname, String lname, String password,
            String dob, String hivStat, String country) {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/completeOnboarding.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + uuid + " " + fname.toUpperCase() + " "
                    + lname.toUpperCase() + " " + password + " " + dob + " " + hivStat + " " + country.toUpperCase() };

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

    // Saves Logged in User session:
    private static String sessionEmail = "", sessionPass = "";

    private static void loginUI() {
        int choice;
        // Scanner objects to get user inputs
        Scanner input = new Scanner(System.in);
        String email, password;

// Loop until a valid email is provided
while (true) {
    System.out.print("\u270F\uFE0F Enter Email: ");
    email = input.nextLine();
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    if (email.matches(emailRegex)) {
        break;
    } else {
        System.err.print("\n\u274C Please input a valid email! \n");
    }
}


        if (email.length() > 6) {
            Console console = System.console();
            char[] hiddenPass = console.readPassword("\n\u270F\uFE0F Enter Password: \t");
            password = new String(hiddenPass);
            if (password.length() > 0) {
                /*
                 * --- call User Login Class method: ---
                 * 
                 * Checks and validate login credentials then:
                 * notes user role type, to determine their type of Dashboard,
                 * either patientUI or adminUI
                 */

                String result = loginAPI(email, password); // Call Bash script API method

                if (result.toLowerCase().contains("true") && result.toLowerCase().contains("admin")) {
                    // User is admin
                    sessionEmail = email;
                    sessionPass = password;
                    // Run admin Menu UI:
                    System.out.println("\n\n\u2139\uFE0F Welcome Admin! " + (char) 3 + "\n");
                    adminMenu();

                } else if (result.toLowerCase().contains("true") && result.toLowerCase().contains("patient")) {
                    // User is Normal user - Patient
                    sessionEmail = email;
                    sessionPass = password;
                    // Run patient Menu UI:
                    patientMenu();

                } else {
                    System.out.println("\n\n\u274C Invalid Email or Password \n");
                    choice = 1;
                }
            } else {
                // No password provided:
                System.err.println("\n\n\u274C Password error! \n");
                choice = 1;
            }
        } else {
            System.err.println("\n\n\u274C Please input a valid email! \n");
        }

    }

    private static void completeUserRegistrationUI() {
        // Scanner objects to get user inputs
        Scanner input = new Scanner(System.in);

        String inputUUID, inputFName, inputLName, country, dob, hivStatus;
        dob = ""; // Control initialization
        String inputPassword = "";
        boolean cnt = true;
        boolean dateCnt = false;

        System.out.print("\n\n\u270F\uFE0F Enter your UUID : \t ");
        inputUUID = input.nextLine();

        // Check that there were actual input values:
        if (inputUUID.length() == 24) {

            /*
             * Check the user text file, to see if the user had been onboarded, then:
             * Continue with the registration process if this user with email and UUID was
             * onboarded:
             */
            String result = confirmOnboardedStatus(inputUUID);

            if (new String(result).equals("true")) {
                System.out.println("\n\n\u2139\uFE0F Complete your registration!");

                // Then go on to collect their data for final registration:
                System.out.print("\n\n\u270F\uFE0F Enter your First Name : \t ");
                inputFName = input.nextLine();

                System.out.print("\n\n\u270F\uFE0F Enter your Last Name : \t ");
                inputLName = input.nextLine();

                // Country Control:
                boolean Countrycnt = false;
                do {
                    System.out.print("\n\n\u270F\uFE0F Enter your country (ISO 2 Code) : \t ");
                    country = input.nextLine();

                    if (country.length() > 0 && country.length() < 3) {
                        Countrycnt = true;
                    } else {
                        System.out.println("\n\n\u274C Please enter valid country code! \n"); // Case of invalid country code:
                        Countrycnt = false;
                    }
                } while (Countrycnt == false);

                // Date Control:
                String dateStr;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                do {

                    // Prompt the user to enter a date
                    System.out.print("\n\n\u270F\uFE0F Enter your date of birth (DD/MM/YYYY e.g: 17/05/2024) : \t ");
                    // Read the input using the next() method
                    dateStr = input.nextLine();
                    sdf.setLenient(false); // Enforce strict date parsing

                    Date date = null;
                    boolean isValid = true;

                    // Attempt to parse the date
                    date = sdf.parse(dateStr, new java.text.ParsePosition(0));

                    if (date != null) {
                        dob = (String) sdf.format(date);
                        dateCnt = true;
                    } else {
                        System.out.println("\n\u274C Invalid date format");
                        dateCnt = false;
                    }

                } while (dateCnt == false);

                // HIV Status selction control:

                do {
                    System.out.println("\n\n\u270F\uFE0F Select your HIV Status : \t ");
                    System.out.println(" 1. Positive");
                    System.out.println(" 2. Negative");
                    System.out.print("\n Choose option: ");
                    hivStatus = input.nextLine();

                    if ("1".equals(hivStatus) || "2".equals(hivStatus)) {
                        cnt = true;
                        if ("1".equals(hivStatus)) {
                            hivStatus = "Positive";
                        } else if ("2".equals(hivStatus)) {
                            hivStatus = "Negative"; // case of option 2
                        } else {
                            System.out.println("\n\u274C Invalid option");
                        }
                    } else {
                        System.out.println("\n\n\u274C Please select a valid HIV Status! \n"); // Case of invalid option neither 1 or 2:
                        cnt = false;
                    }
                } while (cnt == false);

                // Password Control:
                boolean repeat = false;
                Console console = System.console();
                char[] hiddenPass1;
                char[] hiddenPass2;

                do {
                    //
                    System.out.print("\n\n\u270F\uFE0F Enter your Password : \t ");
                    hiddenPass1 = console.readPassword(" Password: ");
                    System.out.print("\n\n\u270F\uFE0F Enter your Password : \t ");
                    hiddenPass2 = console.readPassword(" Confirm Password: ");
                    if (!new String(hiddenPass1).equals(new String(hiddenPass2))) {
                        // Passwords do not match:
                        System.out.println("\n\n\u274C Passwords do not match! \n");
                        repeat = true;
                    } else if (new String(hiddenPass1).length() < 6) {
                        System.out.println("\n\n\u274C Password must be at least 6 characters long!\n");
                        repeat = true;
                    } else {
                        // Passwords match:
                        inputPassword = new String(hiddenPass1);
                        repeat = false;
                    }
                } while (repeat != false);

                // --- Call The registerUser(inputFName, inputLName, password) Method of the
                // User Class ---
                String result2 = completeUserRegistrationAPI(inputUUID, inputFName, inputLName, inputPassword, dob,
                        hivStatus, country);
                if ("true".equals(result2.toLowerCase())) {
                    System.out.println("\n\n\u2705" + inputFName + " " + inputLName
                            + ", your Registration has been completed! \n");
                } else {
                    System.out.println("\n\n\u274C Failed to register your account! \n");
                    System.err.println(result2);
                }
                // return back to Menu
            } else {
                System.out.println("\n\n\u274C Sorry, you can't perform this activity. Try again later!\n");
                // go back
            }
        } else {
            System.err.println("\n\n\u274C Please input valid email and UUID!! \n");
            // returns to Menu
        }
    }

    private static void onboardUserUI() {

        boolean isAdmin;
        String userEmail, checkAdminRole, role, UUID;

        // Choise 1 input objects:
        Scanner input = new Scanner(System.in);

        // Get New User's email to onboard:
        System.out.print("\uD83D\uDC49 Enter user email: ");
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

        // Generate new unique random user id and Save initial User data for new user
        // Onboarding to text file:
        int stringLength = 24;
        UUID = randomStr.getAlphaNumericString(stringLength);

        // Check if UUID generated and email are valid:
        if (UUID.length() > 0 && UUID.length() == 24 && userEmail.length() > 6) {
            System.out.println("\u2139\uFE0F New user Email: " + userEmail + "\n");

            System.out.println("\n New User\'s Role:  " + role + "\n");
            System.out.println("\n New User\'s UUID:  " + UUID + "\n");

            // --- inititiate OnboardUser(email, Role, UUID) Admin method: ---
            String result = registerAPI(userEmail, UUID, role);

            // Success:
            if (new String(result).equals("true")) {
                System.out.println("\n\n\u2705 " + role + " User Onboarded Successfully!\n");
            } else {
                // Failure
                System.out.println("\n\n\u274C User Onboarding Failed!\n");
            }

            System.out.println("\n\n \u2705 Please mark down User Email and UUID for registration continuation!!\n ");
        } else {
            System.out.println("\n\n\u274C Invalid UUID or Email!\n");
        }

    }

    private static String exportUsersAPI() {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/exportUserData.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath };

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

    private static void exportUsers() {
        // Export User data:
        // Call Bash export data script:
        String result;
        result = exportUsersAPI();
        if ("false".equals(result)) {
            System.err.println("\n\n\u274C Sorry, could not export User data!!");
        } else {
            System.out.println(result);
        }
    }

    private static String exportStatisticsAPI() {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/exportStatistics.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath };

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

    private static void exportStatistics() {
        // Export Statistics data:
        // Call Bash export Statistics script:
        String result;
        result = exportStatisticsAPI();
        if ("false".equals(result)) {
            System.err.println("\n\n\u274C Sorry, could not export Statistics data!!");
        } else {
            System.out.println(result);
        }
    }

    private static void adminMenu() {

        // App option choice as int: local int
        int choice = -1;

        // Scanner objects to get user inputs
        Scanner input = new Scanner(System.in);

        // Admin Menu:
        do {
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
    
            // Choice 0 will Logout Admin from his Dashboard!
        } while (choice != 0);

    }

    private static String[] getLoggedInUserDataAPI(String em, String pwd) {
        // returns the loggedin user data in an array:
        String[] result = new String[2];
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/retrieveUserData.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + em + " " + pwd };

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

    private static String SaveUpdatedDataAPI(String email, String password, String fname, String lname, String newDOB,
            String newHIVStatus, String newCountry) {
        StringBuilder result = new StringBuilder();
        try {

            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/saveUpdatedData.sh").toString();

            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + email + " " + password + " " + fname + " " + lname + " "
                    + newDOB + " " + newHIVStatus + " " + newCountry };

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

    private static String getCountryLifeExpectancyAPI(String country) {
        // Call bash to get info from life-expectancy.csv:
        String result;
        //Get countries average life expectancy:
        
        Country myCountry = new Country();
        //Set your country's ISO2 value:
        myCountry.setISO2(country);
        //Get country's average life expectancy:
        result = myCountry.getCountryAvgLifeExpec(country);
        return result;
    }

    private static String saveEstimatetoCSVAPI(String fn, String ln, String email, int age, String country,
            String onARTStatus, int estimateVal) {
        String result = "";
        try {
            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/saveStatistic.sh").toString();
            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + fn + " " + ln + " " + email + " " + age + " " + country
                    + " " + onARTStatus + " " + estimateVal };

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
    public static String sessionDOB = " ";
    public static String sessionHIVStatus = " ";
    public static String sessionCountry = " ";

    private static void evaluteLifeExpectancyUI() {
        // Needed variables:
        String country, takingART, lifeExpecResultFromAPI;
        int currentAge, ageContracted, ageStartedTakingART;
        double countryLifeExpec = 0;
        boolean onART = false;

        // If patient is Sick of HIV
        if (!sessionHIVStatus.equals("Negative")) {

            // Get from User file:

            // Country
            country = sessionCountry;
            // current age:
            // Define birthdate
            String birthDateString = sessionDOB;

            // Define date format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Parse birthdate string into a LocalDate
            LocalDate birthDate = LocalDate.parse(birthDateString, formatter);

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Calculate the age
            int current_age = Period.between(birthDate, currentDate).getYears();
            int choice;

            currentAge = current_age;

            Scanner input = new Scanner(System.in);
            OUTER:
            while (true) {
                System.out.println("\n\u270F\uFE0F Are you on ART Treatment?\n");
                System.out.println("   1. Yes\n");
                System.out.println("   2. No \n");
                System.out.print("\u270F\uFE0F Choose option--> ");
                try {
                    choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            takingART = "Yes";
                            break OUTER;
                        case 2:
                            takingART = "No";
                            break OUTER;
                        default:
                            System.out.println("\n\u274C Please enter a valid option!! \n");
                            break;
                    }
                }catch (Exception e) {
                    System.out.println("\n\u274C Invalid input! Please enter a number.");
                    input.next();  // Clear the invalid input
                }
            }

            String dateContracted = null;
            // Date contracted illness:
            // Date Control:
            String dateStr;
            boolean dateCnt = false;
            boolean dateCnt2 = false;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            do {
                // Prompt the user to enter a date
                System.out.print("\n\n\u270F\uFE0F Date you became Infected (DD/MM/YYYY e.g: 17/05/2020) : ");
                // Read the input using the next() method
                dateStr = input.nextLine();
                sdf.setLenient(false); // Enforce strict date parsing

                Date date = null;
                boolean isValid = true;

                // Attempt to parse the date
                date = sdf.parse(dateStr, new java.text.ParsePosition(0));

                if (date != null) {
                    dateContracted = (String) sdf.format(date);
                    dateCnt = true;
                } else {
                    System.out.println("\n\u274C Invalid date format");
                    dateCnt = false;
                }

            } while (dateCnt == false);

            ageContracted = Period.between(LocalDate.parse(dateContracted, formatter), currentDate).getYears();

            String dateTreatmentStarted = null;

            //In case not on ART
            int years = 0;
            // Date contracted illness:
            if(choice == 1){

            do {
                // Prompt the user to enter a date
                System.out.print("\n\n\u270F\uFE0F Date you started treatment (DD/MM/YYYY e.g: 17/05/2022) : ");
                // Read the input using the next() method
                dateStr = input.nextLine();
                sdf.setLenient(false); // Enforce strict date parsing

                Date date2 = null;
                boolean isValid = true;

                // Attempt to parse the date
                date2 = sdf.parse(dateStr, new java.text.ParsePosition(0));

                if (date2 != null) {
                    dateTreatmentStarted = (String) sdf.format(date2);
                    dateCnt2 = true;
                } else {
                    System.out.println("\n\u274C Invalid date format");
                    dateCnt2 = false;
                }

            } while (dateCnt2 == false);

            ageStartedTakingART = Period.between(LocalDate.parse(dateTreatmentStarted, formatter), currentDate)
                    .getYears();
            }else{
                years = currentAge - ageContracted;
                ageStartedTakingART = currentAge;
            }

            // Patient is on ART ? True or False:
            onART = "yes".equals(takingART.toLowerCase());
            int estimate = 0;

            lifeExpecResultFromAPI = getCountryLifeExpectancyAPI(country);
            countryLifeExpec = Double.valueOf(lifeExpecResultFromAPI);
            if (!"false".equals(lifeExpecResultFromAPI) && countryLifeExpec > 0) {
                System.out.println("\n\n\u2705 General Life Expectancy in  " + country + " is: " + countryLifeExpec);

                // Write Logic to calculate Patients's survivalRate:
                int y = 0;
                double survivalRate = 0;
                if (onART == true) {
                    int x = ageStartedTakingART - ageContracted;
                    y = (int) countryLifeExpec - ageContracted;
                    for (int i = 0; i < x + 1; i++) {
                        y *= 0.9;
                    }
                    estimate = y;
                } else {
                    // Not on ART: Supposed to live max 5years to live:
                    y = currentAge - ageContracted;
                    if (y < 6) {
                        estimate = 5 - y;
                    }else{
                        estimate = 0;
                    }
                }
            } else {
                System.out.println("\n\u274C Sorry check your country code and try again!\n");
            }

            Extensions animation = new Extensions();
            // animation.speak("Your HIV Survival rate is: " + estimate + " Year(s) from
            // now");
            System.out.println(
                    "\n\n ***************************************************************************************************** \n");
            System.out.println("\n\t \u2705 Your HIV Survival rate is: " + estimate + " Year(s) from now!!\n");
            System.out.println(
                    "\n\n ***************************************************************************************************** \n\n");

            // Save to csv:
            String result;
            result = saveEstimatetoCSVAPI(sessionFName, sessionLName, sessionEmail, currentAge, country, takingART,
                    estimate);
            if (!"success".equals(result)) {
                // error:
                System.err.print("\n\n\u274C Could not save your statistics!!");
            } else {
                System.out.println("\n\n\u2705 Statistic saved successfuly!");
            }

        } else {
            System.out.println("\n\nYou\'re HIV Negative, you can't calculate your live expectancy!\n");
        }

    }

    private static void downloadSchedule() {
        // Download calendar Schedule from API - for Patients

    }

    private static void patientMenu() {
        Patient patient = new Patient();
        patient.setEmail(sessionEmail);
        patient.setPassword(sessionPass);

        // Get unique User data:
        // Check and make sure patient email and password is set:
        if (!patient.getEmail().isEmpty() && !patient.getPassword().isEmpty()) {

            // Retrieve Users firstname and lastname:
            String[] userData = getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword());

            // set fname and lastname:
            patient.setFirstName(userData[0]);
            patient.setLastName(userData[1]);
            patient.setDOB(userData[2]);
            patient.setHIVStatus(userData[3]);
            patient.setCountry(userData[4]);

            // Assign Fname to be used anywhere in the Patient Menu:
            sessionFName = patient.getFirstName();
            sessionLName = patient.getLastName();
            sessionDOB = patient.getDOB();
            sessionHIVStatus = patient.getHIVStatus();
            sessionCountry = patient.getCountry();

            // Render Patient Menu:
            // Patient ption choice as int: local int

            int choice;
            // Scanner objects to get user inputs
            Scanner input = new Scanner(System.in);

            // Normal user - Patient Dashboard Menu:
            Extensions animation = new Extensions();
            animation.speak(" Welcome back, " + patient.getFirstName() + " " + patient.getLastName());
            System.out.println(
                    "\n Welcome back, " + patient.getFirstName() + " " + patient.getLastName() + (char) 3 + "\n");
            do {

                System.out.println("\n\n\uD83C\uDFE0 Dashboard: \n\n");
                System.out.println("\uD83E\uDDEE 1. Evaluate life expectancy \n");
                System.out.println("\uD83D\uDCC4 2. View your information \n");
                System.out.println("\u270F\uFE0F 3. Update your information \n");
                System.out.println("\uD83D\uDCE5 4. Download Schedule of demise \n");
                System.out.println("\uD83E\uDD16 5. AI therapy assistant \n");
                System.out.println("\uD83D\uDEAA 0. Logout \n\n");

                System.out.print("\u270F\uFE0F Enter Option--> ");

                // Get User Input Choice:
                choice = (int) input.nextInt();

                if (choice == 1) {
                    System.out.println("\n\uD83E\uDDEE Estimate Life Expectancy: \n");
                    // Call Up Calculate Life Expectancy interface;
                    evaluteLifeExpectancyUI();

                } else if (choice == 2) {
                    // Retrieve Users firstname and lastname:

                    // Get Reload fname and lastname:
                    patient.setFirstName(getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword())[0]);
                    patient.setLastName(getLoggedInUserDataAPI(patient.getEmail(), patient.getPassword())[1]);
                    System.out.println("\n\n First name: " + patient.getFirstName());
                    System.out.println("\n Last name: " + patient.getLastName());
                    System.out.println("\n Email address: " + patient.getEmail());
                    System.out.println("\n Date of birth: " + patient.getDOB());
                    System.out.println("\n HIV Status: " + patient.getHIVStatus());
                    System.out.println("\n Country: " + patient.getCountry());

                } else if (choice == 3) {
                    System.out.println("\n\u2139\uFE0F Update Your information: \n");
                    System.out.println(
                            "\n\u270F\uFE0F 1. Are you sure you want to update your information: (Yes or No) --> ");

                    String updateCntrl, newFName, newLName, newDOB, newHIVStatus, newCountry;
                    newDOB = "";

                    Scanner updateInput = new Scanner(System.in);
                    updateCntrl = updateInput.nextLine();
                    if ("yes".equals((String) updateCntrl.toLowerCase())) {
                        System.out.print("\n\n\u270F\uFE0F 1. Enter new First name: ");
                        newFName = updateInput.nextLine();
                        System.out.print("\n\n\u270F\uFE0F 1. Enter new Last name: ");
                        newLName = updateInput.nextLine();

                        // --------------

                        // Country Control:
                        boolean Countrycnt = false;
                        boolean dateCnt = false;

                        do {
                            System.out.print("\n\n\u270F\uFE0F Enter your country (ISO 2 Code) : \t ");
                            newCountry = input.nextLine();

                            if (newCountry.length() > 0 && newCountry.length() < 3) {
                                Countrycnt = true;
                                //Update new country patient data:
                                sessionCountry = newCountry;
                                patient.setCountry(newCountry);
                            } else {
                                System.out.println("\n\n\u274C Please enter valid country code! \n"); // Case of invalid country code:
                                Countrycnt = false;
                            }
                        } while (Countrycnt == false);

                        // Date Control:
                        String dateStr;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                        do {

                            // Prompt the user to enter a date
                            System.out.print(
                                    "\n\n\u270F\uFE0F Enter new date of birth (DD/MM/YYYY e.g: 17/05/2000) : \t ");
                            // Read the input using the next() method
                            dateStr = input.nextLine();
                            sdf.setLenient(false); // Enforce strict date parsing

                            Date date = null;
                            boolean isValid = true;

                            // Attempt to parse the date
                            date = sdf.parse(dateStr, new java.text.ParsePosition(0));

                            if (date != null) {
                                newDOB = (String) sdf.format(date);
                                dateCnt = true;
                            } else {
                                System.out.println("\n\u274C Invalid date format");
                                dateCnt = false;
                            }

                        } while (dateCnt == false);

                        // HIV Status selction control:
                        boolean cnt = false;
                        do {
                            System.out.print("\n\n\u270F\uFE0F Select your new HIV Status : \t ");
                            System.out.println(" 1. Positive");
                            System.out.println(" 2. Negative");
                            System.out.print("\n Choose option: ");
                            newHIVStatus = input.nextLine();

                            if ("1".equals(newHIVStatus) || "2".equals(newHIVStatus)) {
                                cnt = true;
                                if ("1".equals(newHIVStatus)) {
                                    newHIVStatus = "Positive";
                                } else if ("2".equals(newHIVStatus)) {
                                    newHIVStatus = "Negative"; // case of option 2
                                } else {
                                    System.out.println("\n\u274C Invalid option");
                                }
                            } else {
                                System.out.println("\n\n\u274C Please select a valid HIV Status! \n"); // Case of
                                                                                                       // invalid option
                                                                                                       // - neither 1 or
                                                                                                       // 2:
                                cnt = false;
                            }
                        } while (cnt == false);

                        // ------------------------------

                        // -- SaveUpdatedDataAPI() --:
                        String res = SaveUpdatedDataAPI(patient.getEmail(), patient.getPassword(), newFName, newLName,
                                newDOB, newHIVStatus, newCountry);
                        System.out.println(res + "\n");
                        if ("true".equals(res.toLowerCase())) {
                            // Save successful:
                            System.out.println("\n\u2705 Data Updated Successfully!! \n");
                        } else {
                            System.err.print("\n\u274C Update Failed!! \n");
                        }
                    }

                } else if (choice == 4) {
                    System.out.println("\n Coming soon!! \n");

                    // Call Up Download Schedule of demise interface;
                    downloadSchedule();

                } else if (choice == 5) {
                    System.out.println("\n\n\uD83C\uDFE5\uD83E\uDD16 --- Virtual AI Therapy Assistant --- \n\n");
                    boolean cont = true;
                    // AI Chat interface;
                    String query;

                    do {
                        System.out.print("\n\n\uD83D\uDC64 You: (Enter 0 to quit chat!) ");
                        query = input.nextLine();
                        if ("0".equals(query)) {
                            cont = false;
                        } else {
                            animation.queryAI(query);
                        }

                    } while (cont == true);

                    // Other patient choice:
                } else {
                    if (choice != 0) {
                        System.out.println("\n\u274C Please enter a valid option!! \n");
                    }
                }
            } while (choice != 0);
        } else {
            // Cannot access:
            System.out.println("\n\u274C Sorry, try again!! \n");
        }
    }

    public static void main(String[] args) {

        System.out.println("\n");
        // ----------------------- welcome Animation --------------------
        final String YELLOW = "\u001B[33m";
        Extensions animation = new Extensions();
        animation.createAnimation();

        // --------------------------------------------------------------
        System.out.println("\n\n\uD83C\uDFE5 Welcome To Life Prognosis! \n");
        // App option choice as int:
        int choice;
        // Scanner objects to get user inputs
        Scanner input = new Scanner(System.in);
        // Choice menu:
        // do while loop if choice is not 0:
        do {
            System.out.println(YELLOW + "\n \uD83C\uDFE0 Menu \n\n");
            System.out.println(" 1. \uD83D\uDD10 Login \n");
            System.out.println(" 2. \uD83D\uDCDD Registration \n");
            System.out.println(" 0. \uD83D\uDEAA Exit \n\n");

            System.out.print("\u270F\uFE0F Choose Option--> ");

            // Get User Input Choice:
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Login Interface
                    loginUI();
                    break;
                // Choice 0 Exits the program!
                case 2:
                    // Registration Module using uuid:
                    // call up the complete registration screen/UI:
                    completeUserRegistrationUI();
                    break;
                case 0:
                    animation.speak("Good bye!");
                    System.out.println(
                            "\n\n ***************************************************************************************************** \n");
                    System.out.println("\n\t\t\t\uD83D\uDC4B Good bye for now \uD83D\uDC4B \n");
                    System.out.println(
                            "\n\n ***************************************************************************************************** \n\n");

                    break;
                default:
                    if (choice != 0) {
                        System.out.println("\n\u274C Please enter a valid option!! \n");
                    }
                    break;
            }
        } while (choice != 0);

    }

}