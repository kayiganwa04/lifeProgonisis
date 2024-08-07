#!/bin/bash


# Function to update user information based on email and UUID
completeOnboarding() {
    local email="$1"
    local uuid="$2"
    local new_firstname="$3"
    local new_lastname="$4"
    local new_password="$5"
    local file="C:\\Users\\STUDENT\\Documents\\CMU_FILES\\Prognosis_Project\\Resources\\user-store.txt"

    echo "Updating user with email: $email and UUID: $uuid"
    echo "New details: $new_firstname $new_lastname $new_password"

    # Complete user registration infos, where email and UUID match
   # if grep -q "$email .* $uuid" "$file"; then
    # Remove empty lines at the end of the file
       sed -i '/^$/d' "$file"
        # Use awk to update the fields in the file
        awk -v email="$email" -v uuid="$uuid" -v firstname="$new_firstname" -v lastname="$new_lastname" -v password="$new_password" \
        'BEGIN {FS=OFS=" "} {
            if ($3 == email && $6 == uuid) {
                $1 = firstname; 
                $2 = lastname; 
                $4 = password
            } 
            print
        }' "$file" > tmpfile && mv tmpfile "$file"

        echo "Update complete."
        echo "true" # Return true if update was successful
#    else
#        echo "false" # Return false if user is not found
#    fi
}

completeOnboarding "$1" "$2" "$3" "$4" "$5"