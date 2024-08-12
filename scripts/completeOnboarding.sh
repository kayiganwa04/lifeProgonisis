#!/bin/bash

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FILE="$SCRIPT_DIR/../Resources/user-store.txt"
# Function to update user information based on email and UUID
completeOnboarding() {
    local email="$1"
    local uuid="$2"
    local new_firstname="$3"
    local new_lastname="$4"
    local new_password="$5"

    # Check if the file exists
    if [[ ! -f "$FILE" ]]; then
        echo "false"
        return
    fi

    # Complete Onboarding user information where email and uuid match
    if grep -q "$email .* $uuid" "$FILE"; then
        # Use awk to update the fields in the file
        awk -v email="$email" -v uuid="$uuid" -v firstname="$new_firstname" -v lastname="$new_lastname" -v password="$new_password" \
        'BEGIN {FS=OFS=" "} {
            if ($3 == email && $6 == uuid) {
                $1 = firstname; 
                $2 = lastname;
                $4 = password;
            } 
            print
        }' "$FILE" > tmpfile && mv tmpfile "$FILE"
        
        echo "true" # Return true if update was successful
    else
        echo "false" # Return false if user is not found
    fi
}

completeOnboarding "$1" "$2" "$3" "$4" "$5"