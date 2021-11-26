package zcc_package;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.*;

/**
 * 
 * @author Luke Frazer
 * 
 * Zendesk Coding Challenge class
 * <p>
 * Unit testing code for ZCC_Class code. 
 * <p>
 * Takes in a testing json file with a few fake tickets to show code functions
 * <p>
 * Does not connect to api for testing. 
 *
 */
public class ZCC_Unit_Tests
   {
      
      /**
       * @param args
       */
      public static void main(String[] args)
         {
            // set the scanner object to the user's input
            SCAN = new Scanner( System.in );
            
            // print line to show that we are connecting
            System.out.println( "Attempting to connect to zendesk.com for "
                              + "testing the pinghost comand: ");
            
            // attempt to ping zendesk.com
            pingHost( "www.zendesk.com", 443, 200 );
            System.out.println();
            System.out.println();
            
            // print line to let user know it is searching for ticket 2
            System.out.println( "SEARCHING FOR TICKET NUMBER 2 FOR TESTING: " );
            System.out.println();
            System.out.println();
            
            // search for ticket 2
            searchTicket( "test.json", 2 );
            System.out.println();
            System.out.println();
            
            // print line to let user know it is reading the tickets for testing
            System.out.println( "READING ALL AVAILABLE TICKETS FOR TESTING: " );
            System.out.println();
            System.out.println();
            
            // test the readJSON method
            readJSON( "test.json" );
            System.out.println();
            System.out.println();
            
            // print line to let user know it is calling the menu for testing
            System.out.println( "CALLING MAIN MENU FOR TESTING: " );
            System.out.println();
            System.out.println();
            
            // test the callMenu method
            callMenu( "test.json" );
            
            // print out the raw json file used for testing
            System.out.print( "Raw JSON File: \n" + 
                              convertJSONToArray( "test.json" ) );
            System.out.println();
            System.out.println();
            
         }
      
      /**
       * global scanner constant for taking in data from user
       */
      public static Scanner SCAN = null;
      
      // create the token string
      private static final String TOKEN = System.getenv("TOKEN");
      
      // get email string
      private static final String EMAIL = System.getenv( "EMAIL" );
      
      // get subdomain string
      private static final String SUBDOMAIN = System.getenv( "SUBDOMAIN" );
      
      // get subdomian url
      private static final String SUBDOMAIN_URL =System.getenv("SUBDOMAIN_URL");
      
      /**
       * Default constructor, does nothing in this case
       */
      public ZCC_Unit_Tests()
      {
      }
      
      /**
       * User interface portion
       * <p>
       * allows user to chose to print all tickets, search for ticket, or exit
       */
      public static void callMenu( String jsonFile)
      {
         // initialize variables
         String separator = "=-------------------------=";
         String inputVal;
         int index = 0;
         boolean exitLoop = false, inputIncorrect;
         
         // display the menu screen
         System.out.println( "Welcome to the Zendesk ticket viewer!" );
         
         while( !exitLoop )
            {
               // reset the flag
               inputIncorrect = true;
               
               // print menu options
               System.out.println( "Please select a menu option "
                                 + "using numbers 1-3" );
               System.out.println();
               
               // display the view options
               System.out.println( separator );
               System.out.println( "[1] View all tickets");
               System.out.println( separator );
               System.out.println( "[2] View a specific ticket");
               System.out.println( separator );
               System.out.println( "[3] Quit the ticket viewer");
               System.out.println( separator );
               System.out.println();
      
               // print the input line
               System.out.print( "YOUR INPUT: ");
               
               // take in the user input using the scanner utility
               inputVal = SCAN.next();
               
               // check for user's choice and print the appropriate response
               switch(inputVal)
               {
                  case "1":
                     // print out appropriate message and call to read out json
                     System.out.println("You have chosen to view all tickets:");
                     System.out.println();
                     
                     // call method to read the json file
                     readJSON( jsonFile );
                     break;
                  case "2":
                     // print appropriate message and call to search for ticket
                     System.out.println("You have chosen to view a "
                                      + "specific ticket:");
                     System.out.println();
                     System.out.print( "Enter a Ticket number: " );
                     
                     // accept user input while the input is not valid
                     while( inputIncorrect )
                        {
                           // attempt to take in a user integer
                           try
                              {
                                 // take the user's input for the next page
                                 index = SCAN.nextInt();
                                 System.out.println();
                                 
                                 // set incorrect input to false
                                 inputIncorrect = false;
                           }
                           // catch if the input is not an int
                           catch( InputMismatchException e)
                              {
                                 // tell user to enter an integer value
                                 System.out.println("Please enter an integer "
                                                   + "value");
                               
                                 // move the scanner to a new line
                                 SCAN.next();
                              }
                        }
                     System.out.println();
                     searchTicket( jsonFile, index );
                     break;
                  case "3":
                     // print out appropriate message and end the program
                     System.out.println( "You have chosen quit the "
                                       + "ticket viewer:");
                     System.out.println();
                     exitLoop = true;
                     break;
                  default:
                     // print out message that it was not a valid input and end
                     System.out.println("I'm sorry, that is not a valid input");
                     System.out.println();
                     break;
               }
            }
         
         // close the scanner
         SCAN.close();
      }
      
      /**
       * imports json and converts it to an array
       * 
       * @param inFile - name/file path of json file to be parsed and printed
       * 
       * @return array element of the json file
       */
      private static JSONArray convertJSONToArray( String inFile )
      {
         // initialize variables
         JSONArray jArray = null;
         JSONObject jsonFile;
         String json = null;
         
         // attempt to convert imported file to a string array
         try
            {
               // use Files library to read the bytes of the imported string
               // and convert to a string names "json"
               json = new String( Files.readAllBytes( Paths.get( inFile ) ) );
               
            }
         catch ( IOException e )
            {
               // call exit program to prevent error
               exitProgram();
            }
         
         // set the json file string to a jsonObject variable
         jsonFile = new JSONObject(json.toString());
         
         // attempt to create the json array
         try
            {
               // create ticket array by finding all values under name "tickets"
               jArray = jsonFile.getJSONArray( "tickets" );
            }
         // catch if there is an issue converting to array due to json issue
         catch ( JSONException e )
            {
               // exit the program to prevent error
               exitProgram();
            }
         
         // return the json array
         return jArray;
      }
      
      /**
       * exits the program with display message
       */
      private static void exitProgram()
      {
         // print message to try different credentials or subdomain
         System.out.println("There was an issue loading the data, \ncheck "
                          + "that your credentials and subdomain are correct.");
         
         // exit the program
         System.exit(0);
      }
      
      /**
       * gets the target API at zendesk.com, imports the tickets and saves
       * them to tickets.json in the current Zendesk_Coding_Challenge directory
       * before calling the main menu of the program
       */
      public static void getProcess( String jsonFile )
      {
         // initialize variables
         boolean isConnected;
         Process process;
         String command;
         String lineSeparator = "/------------------------------------------\\";
         String dataSeparator = "\\------------------------------------------/";
         String successMsg = "Data successfully imported";
         String spacing = "         ";
         
         // create the command string for 
         command = "curl -o tickets.json "
               + SUBDOMAIN + "/api/v2/tickets.json"
               + "-v -u " + EMAIL + "/token:" + TOKEN;
         
         // use the process builder constructor to build the command
         ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
         
         // check to make sure that the connection is established first
         isConnected = pingHost( SUBDOMAIN_URL, 443, 300 );
         
         // if there is a connection, attempt to retrieve the requests
         if( isConnected )
            {
               // try starting the process
               try
                  {
                     // start the process builder using the set command
                     process = processBuilder.start();
                     
                     // print line separator with spacing
                     System.out.println();
                     System.out.println( lineSeparator );
                     
                     // print that the data was successfully imported with 
                     // data separator
                     System.out.println( spacing + successMsg + spacing );
                     System.out.println( dataSeparator );
                     System.out.println();
                     
                     // exit the process
                     process.onExit();
                     
                     // call the menu
                     callMenu( jsonFile );
                     
                  } 
               
               // otherwise, throw the error message
               catch (IOException e)
                  {
                     // print line separator with spacing
                     System.out.println();
                     System.out.println( lineSeparator );
                     System.out.println();
                     
                     // throw error message
                     e.printStackTrace();
                     System.out.println( "There was an issue "
                                       + "importing the data" );
                  }
            }
         
         // otherwise, print that the user must retry at a different time
         else
            {
               // print line separator with spacing
               System.out.println();
               System.out.println( lineSeparator );
               System.out.println();
               
               // print the error message
               System.out.println( "Please try again another time");
            }
         
      }
      
      /**
       * Pings the selected URL to check if it is available
       * 
       * @param host - URL of the site to be reached
       * 
       * @param port - port number of site, 443 for HTTPS, 80 for HTTP
       * 
       * @param timeout - time to wait for the connection to establish
       * 
       * @return boolean result of if the URL is online
       */
      public static boolean pingHost(String host, int port, int timeout) 
         {
         // attempts to connect to the inputed host url
         try (Socket socket = new Socket()) 
            {
               // connect to the host
               socket.connect(new InetSocketAddress(host, port), timeout);
               
               // print that there was a successful connection
               System.out.println( "Connection to host: "
                + host + " has been established" ); 
               
               // return true
               return true;
            } 
         catch (IOException e) 
            {
               // print that there was NOT a successful connection
               System.out.println( "Failure to connect to host: "
                + host ); 
               
               // Either timeout or unreachable or failed DNS lookup.
               return false; 
            }
      }
      
      /**
       * read the JSON file and print it out to user, with page navigation
       * 
       * @param inFile - name/file path of json file to be parsed and printed
       */
      public static void readJSON( String inFile )
      {
         // initialize variables
         String separator = "#===============================================#";
         String sectioner = "+--------------------------+";
         String subject, description, status, lastUpdated;
         JSONObject ticket;
         JSONArray tickets;
         int id, pages, currentTicket, pageIndex, finalPage;
         int userChoice = 1, exit = 0, end = 0;
         boolean inputIncorrect = true;
         
         // convert the inputed file to an array and save to variable
         tickets = convertJSONToArray( inFile );
         
         // find the number of tickets in the database
         int length = tickets.length();
         
         // print the total number of tickets
         System.out.println( sectioner );
         System.out.println( "TOTAL NUMBER OF TICKETS: " + length );
         System.out.println( sectioner );
         System.out.println();
         
         // find the number of pages
         pages = ( length / 25 ) + 1;
         
         // find final page
         finalPage = length % 25;
         
         // check if there is a final page
         if( finalPage != 0 )
            {
               // increment the pages
               pages += 1;
            }
         
         // loop until the user chooses to exit
         while( userChoice != exit )
            {
               // reset incorrect flag
               inputIncorrect = true;
               
               // find the current page
               currentTicket = ( userChoice - 1 ) * 25;
            
               // find the ending index of the current page
               end = currentTicket + 25;
            
               // find final page count
               if( userChoice == pages - 1 )
                  {
                     // find the end to the final page
                     end = currentTicket + finalPage;
                  }
            
               // loop through all tickets
               while( currentTicket < end )
                  {
                     // set the current individual ticket at the index
                     ticket = tickets.getJSONObject( currentTicket );
                     
                     // save the subject of the current ticket
                     subject = ticket.optString( "subject" );
                           
                     // save the description of the current ticket
                     description = ticket.optString( "description" );
                     
                     // save the status of the current ticket
                     status = ticket.optString( "status" );
                           
                     // save the date and time of last updated
                     lastUpdated = ticket.optString( "updated_at");
                           
                     // save the id of the current ticket to identify where
                     //  we are in the overall ticket count
                     id = ticket.getInt( "id" );
                           
                     // print all of the data with spacing
                     System.out.println( separator );
                     System.out.println();
                     System.out.println( "Subject: " + subject );
                     System.out.println();
                     System.out.println( "Description: " + description );
                     System.out.println();
                     System.out.println( "Status: " + status );
                     System.out.println();
                     System.out.println("Ticket last updated: "+lastUpdated);
                     System.out.println();
                     System.out.println( "ID: " + id );
                     System.out.println();
                           
                     // increment the ticket
                     currentTicket++;
                  }
            
               // print the separator, marking the end of the current ticket
               System.out.println();
               System.out.println( separator );
               System.out.println();
               
               // print pages line
               System.out.println( "PAGES" );
               
               // print the page numbers
               for( pageIndex = 1; pageIndex < pages; pageIndex++ )
                  {
                     // check if currentPage
                     if( pageIndex == userChoice )
                        {
                           // print the page number with brackets
                           System.out.print( "[" + pageIndex + "]   " );
                        }
                     
                     // otherwise, assume not current page
                     else
                        {
                           // print the page normally
                           System.out.print( pageIndex + "   " );
                        }
                  }
               
               // print the line to accept the user's next page
               System.out.println();
               System.out.print("Please choose a page to navigate to or choose "
                              + "(0) to exit to main menu: " );
               
               // accept user input while the input is not valid
               while( inputIncorrect )
                  {
                     // attempt to take in a user integer
                     try
                        {
                           // take the user's input for the next page
                           userChoice = SCAN.nextInt();
                           System.out.println();
                           
                           // set incorrect input to false
                           inputIncorrect = false;
                     }
                     // catch if the input is not an int
                     catch( InputMismatchException e)
                        {
                           // tell user to enter an integer value
                           System.out.println("Please enter an integer value");
                         
                           // move the scanner to a new line
                           SCAN.next();
                        }
                  }
            
               // loop until the user enter's a valid page number to navigate to
               while( userChoice < 0 || userChoice >= pages )
                  {
                     // print a message to enter a valid page
                     System.out.println( "I'm sorry, the page number you "
                                       + "entered was not valid" );
                     System.out.println();
                     
                     // take the user's input for the next page
                     userChoice = SCAN.nextInt();
                  }
            }
      }
      
      /**
       * searches for a specific ticket and prints the contents of the ticket
       * 
       * @param inFile - name/file path of json file to be parsed and printed
       * 
       * @param index - integer value of the ticket number to be searched
       */
      public static void searchTicket( String inFile, int index )
      {
         // initialize variables
         String separator = "#===============================================#";
         String subject, description, status, lastUpdated;
         JSONObject ticket;
         JSONArray tickets;
         int id;
         
         // call separator
         System.out.println( separator );
         
         // convert the inputed file to an array and save to variable
         tickets = convertJSONToArray( inFile );
         
         // check if the index is within the range
         if( index > 0 && index <= tickets.length() )
            {
               // save the ticket array to an array variable
               ticket = tickets.getJSONObject( index - 1 );
         
               // save the subject of the current ticket
               subject = ticket.optString( "subject" );
         
               // save the description of the current ticket
               description = ticket.optString( "description" );
               
               // save the status of the current ticket
               status = ticket.optString( "status" );
         
               // save the date and time of last updated
               lastUpdated = ticket.optString( "updated_at");
         
               // save the id of the current ticket to identify 
               // where we are in the overall ticket count
               id = ticket.getInt( "id" );
         
               // print all of the data with spacing
               System.out.println( "Subject: " + subject );
               System.out.println();
               System.out.println( "Description: " + description );
               System.out.println();
               System.out.println( "Status: " + status );
               System.out.println();
               System.out.println( "Ticket last updated: " + lastUpdated);
               System.out.println();
               System.out.println( "ID: " + id );
               
               // call separator
               System.out.println( separator );
               System.out.println();
            }
         
         // otherwise, print a message that the number was not valid
         else
            {
               // print message
               System.out.println( "I'm sorry, that inputed value " 
                     + "was not valid, \nplease try a different number "
                     + "between 1 - " + tickets.length());
               System.out.println( separator );
               System.out.println();
            }
      }
      
   }
      