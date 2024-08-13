#!/bin/bash

# Function to log in a user
confirmOnboarded() {
    local uuid="$1"
    local file="Resources/user-store.txt"

    # Search for the email and UUID in the file
    if grep -q "$uuid" "$file"; then
        echo "true" # Return true if found
    else
        echo "false" # Return false if not found
    fi
}

# Calling the function with arguments passed to the script:
confirmOnboarded "$1"
