package zcc_package;

/**
 * 
 * @author Luke Frazer
 * 
 * Main class to call the coding challenge process
 *
 */
public class ZCC_Main
   {
      /**
       * @param args
       */
      public static void main(String[] args)
         {
            // create zendesk coding challenge class object
            ZCC_Class ZCC = new ZCC_Class();
            
            // start the process
            ZCC.getProcess();
            
            // pings the host and prints result to user
               // commented out as the getProcess() method includes this method
               // but this is included as a unit test for this method
            // ZCC.pingHost( "www.zccstudents9733.zendesk.com", 443, 50 );
            
            // calls main menu to navigate through json file
               // commented out as the getProcess() method includes this method
               // but this is included as a unit test for this method
            // ZCC.callMenu();
         }

   }
