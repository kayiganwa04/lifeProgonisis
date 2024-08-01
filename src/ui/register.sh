#!/bin/bash

# Function to store user information
store_user_info() {
    local email="$1"
    local uuid="$2"
    local role="$3"
    local file="user-store.txt"

    echo "Arguments received: $1 $2 $3"
    
    # Append the user information to the file
    echo "$email,$uuid,$role" >> "$file"
    
    # Check if the last command was successful
    if [[ $? -eq 0 ]]; then
        echo "true"
    else
        echo "false"
    fi
}

echo "Arguments received: $1 $2 $3"

# Calling the function with arguments passed to the script
store_user_info "$1" "$2" "$3"
