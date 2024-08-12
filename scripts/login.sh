#!/bin/bash

# Function to log in a user
login_user() {
    local email="$1"
    local password="$2"
    local file="Resources/user-store.txt"

    # Check if the email and password match any entry
    # Search for the email and password and get the role if found
    local user_data=$(grep "$email $password" "$file")

    if [[ -n "$user_data" ]]; then
        # Extract the role from the user data
        local role=$(echo "$user_data" | awk '{print $5}')
        echo "true $role" # Return true and the user's role
    else
        echo "false" # Return false if not found
    fi
}

# Calling the function with arguments passed to the script
login_user "$1" "$2"
