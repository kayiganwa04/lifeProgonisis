#!/bin/bash

files="$@"

for i in $files; do
    # Append a sentence to the text file
    echo "Email: kayiganwa04@gmail.com" >> "$i"
    echo "Password: 123456" >> "$i"
    echo "" >> "$i"
done
