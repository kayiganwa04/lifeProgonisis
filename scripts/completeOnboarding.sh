
# Function to update user information based on email and UUID
completeOnboarding() {
    local email="$1"
    local uuid="$2"
    local new_firstname="$3"
    local new_lastname="$4"
    local new_password="$5"
    local file="Resources/user-store.txt"

    # Check if the file exists
    if [[ ! -f "$file" ]]; then
        echo "false"
        return
    fi

    # Complete Onboarding user information where email and uuid match
    if grep -q "$email $uuid" "$file"; then
        # Use awk to update the fields in the file
        awk -v email="$email" -v uuid="$uuid" -v firstname="$new_firstname" -v lastname="$new_lastname" -v password="$new_password" \
        'BEGIN {FS=OFS=" "} {
            if ($3 == email && $6 == uuid) {
                $1 = firstname; 
                $2 = lastname;
            } 
            print
        }' "$file" > tmpfile && mv tmpfile "$file"
        
        echo "true" # Return true if update was successful
    else
        echo "false" # Return false if user is not found
    fi
}

completeOnboarding "$1" "$2" "$3" "$4" "$5"