package src.components;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Country {
    private String name;
    private String ISO2;
    private String ISO3;

    public String getName(){
        return name;
    }
    public String getISO2(){
        return ISO2;
    }

    public String getISO3(){
        return ISO3;
    }

    public void setName( String Name){
        name = Name;
    }
    public void setISO2(String iso2){
        ISO2 = iso2;
    }
    public void setISO3(String iso3){
        ISO3 = iso3;
    }

    public String getCountryAvgLifeExpec(String iso2){
        // code to get average life expectancy for a country using their:
        // call bash script to get average life of that country:
        // Call bash to get info from life-expectancy.csv:
        String result = "";
        try {
            // Full path to the script
            String scriptPath = Paths.get(System.getProperty("user.dir"), "scripts/getLifeExpectancy.sh").toString();
            // Build the command
            String[] cmd = { "bash", "-c", scriptPath + " " + iso2 };

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
}
