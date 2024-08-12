#!/bin/bash

# Function to log in a user
confirmOnboarded() {
    local email="$1"
    local uuid="$2"
    local file="Resources/user-store.txt"

    # Search for the email and UUID in the file
    if grep -q "$email .* $uuid" "$file"; then
        echo "true" # Return true if found
    else
        echo "false" # Return false if not found
    fi
}

# Calling the function with arguments passed to the script:
confirmOnboarded "$1" "$2"
