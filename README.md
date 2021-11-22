# Zendesk-Coding-Challenge
Author: Luke Frazer

This is the code for the Zendesk Coding Challenge for possible upcoming interns. 
This is a ticket viewer that will import all tickets from my Zendesk account and allow the user to navigate through them using a command line. 

=-------------------------------------------------------------------------------------------------------------------------------=

This code was written and tested using the Eclipse IDE
The two code files for this challenge are located under Zendesk_Coding_Challenge\src\zcc_package\

There are two files, ZCC_Class.java and ZCC_Main.java

The ZCC_Class is the tool class that holds all methods and variables to be executed in ZCC_Main in order to run the Ticket Viewer

The main menu is very easy to use, and includes separators for different data segments. 

The method: getProcess() is the primary driver for the ticket viewer, as it calls all other methods for running the Ticket Viewer. 

Each method runs a small piece of the overall system, allowing for modularity and future-proofing of the program. 

The methods are able to account if the values that are too large or small, if the api is unavailable, as well as other possible issues along the way.

I hope my ticket viewer is satisfactory for what Zendesk is looking for. 
