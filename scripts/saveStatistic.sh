#!/bin/bash


# Function to store user information
saveStatistic() {
 local firstname="$1"
    local lastname="$2"
    local email="$3"
    local age="$4"
    local country="$5"
    local onARTStatus="$6"
    local estimateVal="$7"
    local output_file="Resources/statistics.csv"

    # Check if the CSV file exists; if not, create it and write the header
    if [[ ! -f "$output_file" ]]; then
        echo "firstname,lastname,email,age,country,onARTStatus,estimateVal" > "$output_file"
    fi

    # Append the data to the CSV file
    echo "$firstname,$lastname,$email,$age,$country,$onARTStatus,$estimateVal" >> "$output_file"
    echo "success"
}


# Calling the function with arguments passed to the script
saveStatistic "$1" "$2" "$3" "$4" "$5" "$6" "$7"