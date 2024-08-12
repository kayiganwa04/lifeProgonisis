#!/bin/bash

# Function to get user information based on email and password
getUserData() {
    local email="$1"
    local password="$2"
    local file="Resources/user-store.txt"
    local firstname lastname

    # Search for the email and password in the file
    user_data=$(awk -v email="$email" -v password="$password" 'BEGIN {FS=OFS=" "} {if ($3 == email && $4 == password) {print $1, $2}}' "$file")

    if [[ -n "$user_data" ]]; then
        # Read the firstname and lastname from the user_data
        read -r firstname lastname <<< "$user_data"
        echo "($firstname, $lastname)"
    else
        echo "User not found"
    fi
}

# Call the function with the provided arguments
getUserData "$1" "$2"