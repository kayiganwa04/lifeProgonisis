#!/bin/bash

file="$1"
email="$2"
uuid="$3"
role="$4"

# Check if email already exists
echo "Register Bash shell Active!"
if grep -q "email: $email" && "uuid: $uuid" "$file"; then
    echo "failure"
    exit 1
fi

# Append the email and password to the file
echo " " >> "$file"
echo "firstname: " >> "$file"
echo "lastname: " >> "$file"
echo "email: $email" >> "$file"
echo "Password: " >> "$file"
echo "role: $role" >> "$file"
echo "uuid: $uuid" >> "$file"

echo "success"
exit 0