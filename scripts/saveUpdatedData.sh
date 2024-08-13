
# Function to update user information based on email and password
update_user_info() {
    local email="$1"
    local password="$2"
    local new_firstname="$3"
    local new_lastname="$4"
    local newDOB="$5"
    local newHIVStatus="$6"
    local newCountry="$7"
    local file="Resources/user-store.txt"

    # Check if the file exists
    if [[ ! -f "$file" ]]; then
        echo "false"
        return
    fi

    # Update user information where email and password match
    if grep -q "$email $password" "$file"; then
        # Use awk to update the fields in the file
        awk -v email="$email" -v password="$password" -v firstname="$new_firstname" -v lastname="$new_lastname" -v dob="$newDOB" -v hiv_status="$newHIVStatus" -v country="$newCountry" \
        'BEGIN {FS=OFS=" "} {
            if ($3 == email && $4 == password) {
                $1 = firstname; 
                $2 = lastname;
                $5 = dob;
                $6 = hiv_status;
                $9 = country;
            } 
            print
        }' "$file" > tmpfile && mv tmpfile "$file"
        
        echo "true" # Return true if update was successful
    else
        echo "false" # Return false if user is not found
    fi
}

# Call the function with the provided arguments
update_user_info "$1" "$2" "$3" "$4" "$5" "$6" "$7"