#!/bin/bash

file="$1"
email="$2"
password="$3"

# Check if the email and password combination exists
if grep -q "Email: $email" "$file" && grep -A 1 "Email: $email" "$file" | grep -q "Password: $password"; then
    echo "success"
    exit 0
else
    echo "failure"
    exit 1
fi
