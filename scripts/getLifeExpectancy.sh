#!/bin/bash

# Function to get life expectancy for a given country
get_life_expectancy() {
    local country="$1"
    local file="Resources/life-expectancy.csv"

    # Check if the file exists
    if [[ ! -f "$file" ]]; then
        echo 0
        #return 1
    fi

    # Search for the country and get the corresponding life expectancy in 2021 (column G)
    # We assume the file has a header row, so we skip it using tail -n +2
    # Use awk to match the country in column A and print the value in column G
    local life_expectancy=$(awk -F, -v country="$country" '
    BEGIN {IGNORECASE=1} 
    NR>1 && $4 == country {print $7}' "$file")

    if [[ -n "$life_expectancy" ]]; then
        echo "$life_expectancy"
    else
        echo 0
    fi
}

# Call the function with the provided argument
get_life_expectancy "$1"
