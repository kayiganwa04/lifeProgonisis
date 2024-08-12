#!/bin/bash

# Function to copy data from user-store.txt to patient-records.csv
export_data_to_csv() {
    local input_file="Resources/user-store.txt"
    local output_file="Resources/patient-records.csv"

    # Check if the input file exists
    if [[ ! -f "$input_file" ]]; then
        echo "false"
        return 1
    fi

    # Write header to the CSV file
    echo -e "firstname,lastname,email,role" > "$output_file"

    # Append the content of user-store.txt to the CSV file excluding uuid and password
    awk 'BEGIN {OFS=","} {print $1, $2, $3, $5}' "$input_file" >> "$output_file"

    echo -e "Data successfully exported to $output_file"
    echo -e "   "

    # Open the file: -----------------------------
    local file="$output_file"

    # Check if the file exists
    if [[ -f "$file" ]]; then
        # Determine the operating system and open the file accordingly
        case "$OSTYPE" in
            linux-gnu*)      # Linux
                xdg-open "$file" &>/dev/null || echo " Please open the file to confirm!"
                ;;
            darwin*)         # macOS
                open "$file" &>/dev/null || echo " Please open the file to confirm!"
                ;;
            msys*|cygwin*|win32*) # Windows (Git Bash or Cygwin)
                start "" "$file"
                ;;
            *)
                echo "Unsupported OS: $OSTYPE"
                ;;
        esac
    else
        echo -e "File not found: $file"
    fi
    # Ends here ---------------------------------
}

# Call the function
export_data_to_csv