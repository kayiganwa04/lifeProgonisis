import openai
import pyttsx3
import sys
import os

# API Key:
openai.api_key = "" 

def query_gpt_and_speak(prompt):
    try:
        # Setting up the context for GPT-3.5-turbo
        system_message = {
            "role": "system", 
            "content": "Provide summarized answers. You are an AI model specialized in Health-related questions, especially HIV/AIDS. You were created by the Nyungwe4 group at Carnegie Mellon University Africa, also called CMU-Africa.",
        },
        # Query GPT
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "user", "content": prompt + ' (provide summarized answers).'}
            ]
        )

        # Extract GPT's response
        gpt_response = response['choices'][0]['message']['content'].strip()

        # Initialize text-to-speech engine
        engine = pyttsx3.init(driverName='espeak')  # 'espeak' for Linux, 'sapi5' for Windows, 'nsss' for macOS
        engine.setProperty('voice', 'english')
        engine.setProperty('rate', 150)  # Adjust speech rate

        # Speak out the response
        #engine.say(gpt_response)
        #engine.runAndWait()

        return gpt_response

    except Exception as e:
        print(f"An error occurred: {str(e)}")
        return None

if __name__ == "__main__":
    if len(sys.argv) > 1:
        prompt = " ".join(sys.argv[1:])
        result = query_gpt_and_speak(prompt)
        if result:
            print("\n\n🤖:", result)
        else:
            print("Please try again.!")
    else:
        print("Please provide an accurate question.")
