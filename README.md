# Zendesk-Coding-Challenge
Author: Luke Frazer

This is the code for the Zendesk Coding Challenge for possible upcoming interns. 
This is a ticket viewer that will import all tickets from my Zendesk account and allow the user to navigate through them using a command line. 

=---------------------------------------------------------------------------------------------------------------------------=

INSTRUCTIONS FOR USING THIS CODE WITH YOUR OWN CREDENTIALS:

I used environmental variables to keep my credentials secure. 

The constants TOKEN, EMAIL, SUBDOMAIN, AND SUBDOMAIN_URL get assigned to environmental variables under the exact same labels. 

When using your own environmental variables:

You can set the environmental variables in the command prompt with the command `set EMAIL=john.smith@gmail.com` for every environmental variable. The code will be able to read your system env variables this way and run the code based on the inputted credentials.

The TOKEN is the API token generated for authentication within Zendesk; it is a long string of random characters. 
    
The EMAIL is self explanatory (ex: `john.smith@zendesk.com` or `john.smith@gmail.com`)
    
The SUBDOMAIN is the full url of whatever subdomain you use in the format: `https://{subdomain}.zendesk.com`
    
The SUBDOMAIN_URL is similar to the subdomain string, however it is in the format: `www.{subdomain}.zendesk.com`
  
With these variables, the code will establish the connection and let the user know in the command line, then run the menu for navigation and viewing the tickets. 

=---------------------------------------------------------------------------------------------------------------------------=

FOR RUNNING IN THE WINDOWS COMMAND PROMPT (CMD) AFTER INPUTING ENVIRONMENTAL VARIABLES:

The code can be run using the javac and java command. For this reason, ensure the system has the Java jdk: `https://www.oracle.com/java/technologies/downloads/`.

Once the JDK is installed, navigate to the folder Zendesk_Coding_Challenge where the folders bin, src, and doc are located.

Now, to compile the code, run the command: `javac -cp ./json-20210307.jar src/zcc_package/ZCC_Class.java`

Once this is compiled, there should be no errors and it should just open a new line for a command. 

Now, enter the command: `java -cp ./json-20210307.jar src/zcc_package/ZCC_Class.java`.

This will run the program, show if the connection was successful, and then allow navigation through the tickets. 

NOTE: If for some reason it fails to connect and you are sure you inputted the correct url, give it another few tries and it should connect. 
  
=---------------------------------------------------------------------------------------------------------------------------=

This code was written and tested using the Eclipse IDE.

The code file for this challenge is located under the folder: `Zendesk_Coding_Challenge\src\zcc_package\`.

The code file is: `ZCC_Class.java`. I originally split it up into a class file and a main file, but it is significanly easier to run through the cmd when it is all in one file. Thus, I updated the code to run in the class file with the main(). 

The method: getProcess() is the primary driver for the ticket viewer, as it calls all other methods for running the Ticket Viewer. 

Each method runs a small piece of the overall system, allowing for modularity and future-proofing of the program. 

The methods are able to account if the values that are too large or small, if the api is unavailable, as well as other possible issues along the way.

I hope that this code is what Zendesk is looking for and I look forward to any possible next steps in the process. 

=---------------------------------------------------------------------------------------------------------------------------=

UNIT TESTING:

To run the unit testing code, enter the command: `javac -cp ./json-20210307.jar src/zcc_package/ZCC_Unit_tests.java` followed by `java -cp ./json-20210307.jar src/zcc_package/ZCC_Unit_tests.java`

The code runs each individual method with messages to show which function is being called. It does not connect to the API, but rather uses a test.json file included in the folder for the ticket functionality. 

Because it does not connect to the API, it can be ran without entering any credentials.

Additionally, from my testing, I was able to enter any given values without errors due to my many checks for any given values in addition to many exception catches throughout the code. 

The code accounts for the API/website being unavailable, invalid menu options, invalid search options, invalid page options, the tickets.json not being available, invalid credentials being entered, issues importing data, etc. 
