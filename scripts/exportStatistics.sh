#!/bin/bash

# returns false in case of failure:
# Function to open statistics.csv
open_statistics() {
    local file="Resources/statistics.csv"

    # Check if the file exists
    if [[ -f "$file" ]]; then
        # Open the file using less (for viewing)
        echo -e " Data Successfully Exported:   "
        less "$file"

        # Open the file: -----------------------------
        echo -e "  "

        # Check if the file exists
        if [[ -f "$file" ]]; then
            # Determine the operating system and open the file accordingly
            case "$OSTYPE" in
                linux-gnu*)      # Linux
                    xdg-open "$file" &>/dev/null || echo " Please open the file to confirm!"
                    ;;
                darwin*)         # macOS
                    open "$file" &>/dev/null || echo " Please open the file to confirm!"
                    ;;
                msys*|cygwin*|win32*) # Windows (Git Bash or Cygwin)
                    start "" "$file"
                    ;;
                *)
                    echo "Unsupported OS: $OSTYPE"
                    ;;
            esac
        else
            echo -e "File not found: $file"
        fi
        # Ends here ---------------------------------

    else
        echo -e "File not found: $file"
    fi

}

# Function call:
open_statistics