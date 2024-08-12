import pyttsx3

def speak_text(text):
    try:
        engine = pyttsx3.init(driverName='espeak')  # Use 'sapi5' on Windows, 'nsss' on macOS, 'espeak' on Linux
        engine.setProperty('rate', 140)
        engine.setProperty('voice', 'english')
        engine.say(text)
        engine.runAndWait()
    except Exception as e:
        print("An error occurred:", str(e))

if __name__ == "__main__":
    import sys
    if len(sys.argv) > 1:
        speak_text(sys.argv[1])
    else:
        print("Sorry, an error occured!")
