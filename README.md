# Zendesk-Coding-Challenge
Author: Luke Frazer

This is the code for the Zendesk Coding Challenge for possible upcoming interns. 
This is a ticket viewer that will import all tickets from my Zendesk account and allow the user to navigate through them using a command line. 

=---------------------------------------------------------------------------------------------------------------------------=

INSTRUCTIONS FOR USING THIS CODE WITH YOUR OWN CREDENTIALS:

I used environmental variables within Eclipse to keep my credentials secure. 

The constants TOKEN, EMAIL, SUBDOMAIN, AND SUBDOMAIN_URL get assigned to environmental variables under the exact same labels. 

When using your own environmental variables:

At Lines 35-44 in `ZCC_Class.java`, You can change either the environmental variable keys in `System.getenv( "{KEY}" )` to your appropriate key for testing or change the entire `System.getenv()` call for each constant to a String (ex: `private final String EMAIL = "john.smith@gmail.com";`

The TOKEN is the API token generated for authentication within Zendesk; it is a long string of random characters. 
    
The EMAIL is self explanatory (ex: `john.smith@zendesk.com` or `john.smith@gmail.com`)
    
The SUBDOMAIN is the full url of whatever subdomain you use in the format: `https://{subdomain}.zendesk.com`
    
The SUBDOMAIN_URL is similar to the subdomain string, however it is in the format: `www.{subdomain}.zendesk.com`
  
With these variables, the code will establish the connection and let the user know in the command line, then run the menu for navigation and viewing the tickets. 
  
=---------------------------------------------------------------------------------------------------------------------------=

This code was written and tested using the Eclipse IDE.

The two code files for this challenge are located under `Zendesk_Coding_Challenge\src\zcc_package\`.

There are two files, `ZCC_Class.java` and `ZCC_Main.java`.

The ZCC_Class is the tool class that holds all methods and variables to be executed in ZCC_Main in order to run the Ticket Viewer.

The method: getProcess() is the primary driver for the ticket viewer, as it calls all other methods for running the Ticket Viewer. 

Each method runs a small piece of the overall system, allowing for modularity and future-proofing of the program. 

The methods are able to account if the values that are too large or small, if the api is unavailable, as well as other possible issues along the way.

I hope that this code is what Zendesk is looking for and I look forward to any possible next steps in the process. 


  
