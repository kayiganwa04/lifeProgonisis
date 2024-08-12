package src.components;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;

//Text to speech library:
/* 
import javax.speech.Central; 
import javax.speech.synthesis.Synthesizer; 
import javax.speech.synthesis.SynthesizerModeDesc; 
*/

public class Extensions {
    public void createAnimation() {
        // ANSI escape code for yellow color (Doctor) and red color (Heart)
        final String YELLOW = "\u001B[33m";
        final String RED = "\u001B[31m";
        final String RESET = "\u001B[0m";

        // ASCII Art of a Doctor with a Hand Under a Heart
        String[] doctorWithHeart = {
            "                *****   *****          ",
            "              ***************          ",
            "             *****************         ",
            "              ****************         ",
            "               *************           ",
            "                 *********             ",
            "                   ****               ",
            "                     *   \uD83E\uDE78               ",
            "              _____     \uD83D\uDC89               ",
            "             /     \\   \\|/            ",
            "            | O   O |  \\|/            ",
            "             \\  ^  /   |             ",
            "              |||||   / \\            ",
            "             / |||||  \\ /            ",
            "            /  |   |  / \\            ",
            "           |   |   |   |              ",
            "           |   |   |   |              ",
            "           |   |   |   |              ",
            "          /    |   |    \\             ",
            "         /     |   |     \\            ",
            "        /      |   |      \\           ",
            "       /       |   |       \\          ",
            "       |_______|   |________|         "
        };

        // Print the ASCII art: Heart in red, Doctor in yellow
        for (String line : doctorWithHeart) {
            // Split the line into two parts: Heart and Doctor
            String[] parts = line.split("                    ");
            if (parts.length > 1) {
                System.out.println(RED + parts[0] + RESET + YELLOW + "                    " + parts[1] + RESET);
            } else {
                System.out.println(YELLOW + line + RESET);
            }
        }
    }

    /* 
    // Method to speak a given text
    public void speak() {
    try { 
            // Set property as Kevin Dictionary 
            System.setProperty( 
                "freetts.voices", 
                "com.sun.speech.freetts.en.us"
                    + ".cmu_us_kal.KevinVoiceDirectory"); 
  
            // Register Engine 
            Central.registerEngineCentral( 
                "com.sun.speech.freetts"
                + ".jsapi.FreeTTSEngineCentral"); 
  
            // Create a Synthesizer 
            Synthesizer synthesizer 
                = Central.createSynthesizer( 
                    new SynthesizerModeDesc(Locale.US)); 
  
            // Allocate synthesizer 
            synthesizer.allocate(); 
  
            // Resume Synthesizer 
            synthesizer.resume(); 
  
            // Speaks the given text 
            // until the queue is empty. 
            synthesizer.speakPlainText( 
                "Hello There", null); 
            synthesizer.waitEngineState( 
                Synthesizer.QUEUE_EMPTY); 
  
            // Deallocate the Synthesizer. 
            synthesizer.deallocate(); 
        } 
  
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
        
    }
    */

    public void speak(String text){
        try {
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/speak.py").toString();

            // Command to execute the Python script
            String[] command;
            command = new String[] {
                "python3", // Use "python" or "python3" depending on your environment
                scriptPath, // Path to your Python script
                text // The text you want the Python script to speak
            };
             
            // Execute the command
            Process process = Runtime.getRuntime().exec(command);

            // Get the output of the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void queryAI(String query){
        try {
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/ai_assistant.py").toString();
            // Example prompt to send to GPT
            String prompt = query;

            // Command to execute the Python script
            String[] command = new String[] {
                "python3",  // Use "python" or "python3" depending on your environment
                scriptPath,  // Path to your Python script
                prompt  // The prompt to send to GPT
            };

            // Execute the command
            Process process = Runtime.getRuntime().exec(command);

            // Get the output of the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

