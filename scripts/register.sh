#!/bin/bash


# Function to store user information
onboardUserStore() {
    local email="$1"
    local uuid="$2"
    local role="$3"
    local randomKey="$4"
    local file="Resources/user-store.txt"

    # Remove empty lines at the end of the file
    sed -i '/^$/d' "$file"
    
    # Append the user information to the file:
    # firstname lastname email password role uuid:
    echo -e "\nUNKNOWN" "UNKNOWN" "$email" "$randomKey" "$role" "$uuid" >> "$file"
    
    # Check if the last command was successful
    if [[ $? -eq 0 ]]; then
        #Successfully onboarded!!
        echo "true"
    else
        echo "false"
    fi
}

# Calling the function with arguments passed to the script
onboardUserStore "$1" "$2" "$3"
