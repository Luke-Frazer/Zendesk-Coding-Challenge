package zcc_package;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.*;


/**
 * 
 * @author Luke Frazer
 * 
 * Zendesk Coding Challenge class
 * <p>
 * Takes in all tickets from my Zendesk account
 * <p>
 * Includes user interface to select to view all tickets, view specific ticket, 
 * or quit the ticket viewer
 * <p>
 * When viewing all tickets, allows user to navigate through pages
 *
 */
public class ZCC_Class
   {
      
      /**
       * global scanner constant for taking in data from user
       */
      public Scanner SCAN = null;
      
      /**
       * Default constructor, does nothing in this case
       */
      public ZCC_Class()
      {
      }
      
      /**
       * User interface portion
       * <p>
       * allows user to chose to print all tickets, search for ticket, or exit
       */
      public void callMenu()
      {
         // initialize variables
         String separator = "=-------------------------=";
         String inputVal, jsonFile = "tickets.json";
         int index;
         boolean exitLoop = false;
         
         // display the menu screen
         System.out.println( "Welcome to the Zendesk ticket viewer!" );
         
         while( !exitLoop )
            {
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
               SCAN = new Scanner( System.in );
               inputVal = SCAN.nextLine();
               
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
                     
                     // take the user's input for the ticket number to search
                     index = SCAN.nextInt();
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
      private JSONArray convertJSONToArray( String inFile )
      {
         // initialize variables
         JSONArray jArray = null;
         JSONObject jsonFile;
         String json;
         
         // attempt to convert imported file to a string array
         try
            {
               // use Files library to read the bytes of the imported string
               // and convert to a string names "json"
               json = new String( Files.readAllBytes( Paths.get( inFile ) ) );
               
               // set the json file string to a jsonObject variable
               jsonFile = new JSONObject(json.toString());
               
               // create ticket array by finding all values under name "tickets"
               jArray = jsonFile.getJSONArray( "tickets" );
            }
         catch (IOException e)
            {
               e.printStackTrace();
               // print friendly error message
               System.out.println("The file could not be converted to a String,"
                                + "please try a different file");
            }
         
         // return the json array
         return jArray;
      }
      
      /**
       * gets the target API at zendesk.com, imports the tickets and saves
       * them to tickets.json in the current Zendesk_Coding_Challenge directory
       * before calling the main menu of the program
       */
      public void getProcess()
      {
         // initialize variables
         boolean isConnected;
         Process process;
         String token, command;
         String lineSeparator = "/------------------------------------------\\";
         String dataSeparator = "\\------------------------------------------/";
         String successMsg = "Data successfully imported";
         String spacing = "         ";
         
         // create the token string
         token = "4o7ou4l8wbcKhGRhI1UHHBGEJVxjB7a41JHJpOTd";
         
         // create the command string for 
         command = "curl -o tickets.json "
               + "https://zccstudents9733.zendesk.com/api/v2/tickets.json"
               + "-v -u luke.e.frazer@gmail.com/token:" + token;
         
         // use the process builder constructor to build the command
         ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
         
         // check to make sure that the connection is established first
         isConnected = pingHost( "www.zccstudents9733.zendesk.com", 443, 50 );
         
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
                     callMenu();
                     
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
      public boolean pingHost(String host, int port, int timeout) 
         {
         // attempts to connect to the inputed host url
         try (Socket socket = new Socket()) 
            {
               // connect to the host
               socket.connect(new InetSocketAddress(host, port), timeout);
               
               // print that there was a successful connection
               System.out.println( "Connection to host: "
                + "https://zccstudents9733.zendesk.com has been established" ); 
               
               // return true
               return true;
            } 
         catch (IOException e) 
            {
               // print that there was NOT a successful connection
               System.out.println( "Failure to connect to host: "
                + "https://zccstudents9733.zendesk.com" ); 
               
               // Either timeout or unreachable or failed DNS lookup.
               return false; 
            }
      }
      
      /**
       * read the JSON file and print it out to user, with page navigation
       * 
       * @param inFile - name/file path of json file to be parsed and printed
       */
      public void readJSON( String inFile )
      {
         // initialize variables
         String separator = "#===============================================#";
         String sectioner = "+--------------------------+";
         String subject, description, status, lastUpdated;
         JSONObject ticket;
         JSONArray tickets;
         int id, pages, currentTicket, pageIndex, finalPage;
         int userChoice = 1, exit = 0, end = 0;
         
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
            System.out.print( "Please choose a page to navigate to or choose "
                            + "(0) to exit to main menu: " );
      
            // take the user's input for the next page
            userChoice = SCAN.nextInt();
            System.out.println();
            
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
      public void searchTicket( String inFile, int index )
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
      