#!/bin/bash

file="$1"
email="$2"
password="$3"

# Check if email already exists
if grep -q "Email: $email" "$file"; then
    echo "failure"
    exit 1
fi

# Append the email and password to the file
echo "Email: $email" >> "$file"
echo "Password: $password" >> "$file"

echo "success"
exit 0
