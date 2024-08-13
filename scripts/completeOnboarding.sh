#!/bin/bash

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FILE="$SCRIPT_DIR/../Resources/user-store.txt"
# Function to update user information based on email and UUID
completeOnboarding() {
    local uuid="$1"
    local new_firstname="$2"
    local new_lastname="$3"
    local new_password="$4"
    local dob="$5"
    local hivStatus="$6"
    local country="$7"

    # Check if the file exists
    if [[ ! -f "$FILE" ]]; then
        echo "false"
        return
    fi

    # Complete Onboarding user information where email and uuid match
    if grep -q "$uuid" "$FILE"; then
        # Use awk to update the fields in the file
        awk -v uuid="$uuid" -v firstname="$new_firstname" -v lastname="$new_lastname" -v password="$new_password" -v dob="$dob" -v hivStatus="$hivStatus" -v country="$country" \
        'BEGIN {FS=OFS=" "} {
            if ($8 == uuid) {
                $1 = firstname; 
                $2 = lastname;
                $4 = password;
                $5 = dob;
                $6 = hivStatus;
                $9 = country
            } 
            print
        }' "$FILE" > tmpfile && mv tmpfile "$FILE"
        
        echo "true" # Return true if update was successful
    else
        echo "false" # Return false if user is not found
    fi
}

completeOnboarding "$1" "$2" "$3" "$4" "$5" "$6" "$7"