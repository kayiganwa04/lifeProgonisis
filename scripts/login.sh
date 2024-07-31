#!/bin/bash

files="$@"

for i in $files; do
    # Append a sentence to the text file
    echo "Email: kayiganwa04@gmail.com" >> "$i"
    echo "Password: 123456" >> "$i"
    echo "" >> "$i"
    # Read and print the contents of the file
    echo "Contents of $i:"
    cat "$i"
    echo "-----------------------"
done
