package PJ3;

import java.util.*;
import java.io.*;

// You may add new functions or data fields in this class 
// You may modify any functions or data members here
// You must use Customer, Cashier and CheckoutArea classes
// to implement SuperMart simulator

class SuperMart {

  // input parameters
  private int numCashiers, customerQLimit;
  private int chancesOfArrival, maxServiceTime;
  private int simulationTime, dataSource;

  // statistical data
  private int numGoaway, numServed, totalWaitingTime;
  private double avgWaitingTime;

  // internal data
  private int counter;	             // customer ID counter
  private CheckoutArea checkoutarea; // checkout area object
  private Scanner dataFile;	     // get customer data from file
  private Random dataRandom;	     // get customer data using random function

  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;  
  private int serviceTime;
  private int dataInput;

  // initialize data fields
  private SuperMart()
  {
	  numCashiers = 0;
	  customerQLimit = 0;
	  chancesOfArrival = 0;
	  serviceTime = 0;
	  maxServiceTime = 0;
	  simulationTime = 0;
	  dataSource = 0;
	  numGoaway = 0;
	  numServed = 0;
	  totalWaitingTime = 0;
	  counter = 0;
	  
	  
	  // add statements
  }

  private void setupParameters()
  {
	// read input parameters from user
	// setup dataFile or dataRandom
	// add statements
	dataFile = new Scanner(System.in);
	System.out.print("Enter simulation time (positive integer): ");
	simulationTime = dataFile.nextInt();
	System.out.print("Enter the number of cashiers: ");
	numCashiers = dataFile.nextInt();
	System.out.print("Enter chances (0% < & <= 100%) of new customer: ");
	chancesOfArrival = dataFile.nextInt();
	while(chancesOfArrival <0 || chancesOfArrival >100){
		System.out.println("That is not a valid number! Please try again!");
		System.out.print(" Enter chances (0% < & <= 100%) of new customer: ");
		chancesOfArrival = dataFile.nextInt();	
	}
	System.out.print("Enter maximum service time of customers: ");
	maxServiceTime = dataFile.nextInt();
	System.out.print("Enter customer queue limit: ");
	customerQLimit = dataFile.nextInt();
	System.out.print("Enter 0/1 to get data from random/file: ");
	dataInput = dataFile.nextInt();
	if (dataInput == 0){
        dataRandom = new Random();      
	}
	else if(dataInput == 1){
		// ask user to input a filename
		System.out.print("Enter filename: ");
		String fileName = dataFile.next();// read a filename
		try{
			dataFile = new Scanner(new File(fileName));
		}
		catch(FileNotFoundException e){
			System.out.println("Error opening the file " + fileName);
			System.exit(0); 
		}
	}
  }
  
  // Use by step 1 in doSimulation()
  private void getCustomerData()
  {
	// get next customer data : from file or random number generator
	// set anyNewArrival and serviceTime
	// add statements
	  
	  if (dataInput == 0){
		  anyNewArrival = ((dataRandom.nextInt(100)+1) <= chancesOfArrival);
	      serviceTime = dataRandom.nextInt(maxServiceTime)+1;	
	  }
	  else{
	       int data1, data2;
	       data1 = dataFile.nextInt();
	       data2 = dataFile.nextInt();
	       anyNewArrival = (((data1%100)+1)<= chancesOfArrival);
	       serviceTime = (data2%maxServiceTime)+1;
	  }
  	}

  private void doSimulation()
  {
	// add statements
	// Initialize CheckoutArea
	checkoutarea = new CheckoutArea(numCashiers, customerQLimit);
	
	// Time driver simulation loop
  	for (int currentTime = 0; currentTime < simulationTime; currentTime++) {
    		// Step 1: any new customer enters the checkout area?
    		getCustomerData();
    		System.out.println("---------------------------------------------");
    		System.out.println("Time: " + currentTime);
    		if (anyNewArrival) {
    			counter++;
    			Customer newCustomer = new Customer(counter, serviceTime, currentTime);     			
    			System.out.println("        Customer #" + counter + " arrives with checkout time " + serviceTime + " units.");
    			if(checkoutarea.isCustomerQTooLong()){
    				numGoaway++;
    				System.out.println("        The waiting queue is currently too long. Customer #" + counter + " has left.");
    			}
    			else{
    				//System.out.println(newCustomer);
    				checkoutarea.insertCustomerQ(newCustomer);
    				System.out.println("        Customer #" + counter + " wait in the customer queue.");
    				
    			}
      		    // Step 1.1: setup customer data
      		    // Step 1.2: check customer waiting queue too long?    			
    		} else {
      		    System.out.println("\tNo new customer!");
    		}    	
    		//System.out.println("hello");
    		while(!checkoutarea.emptyBusyCashierQ()){ 
    			
    		    Cashier currentCashier = checkoutarea.peekBusyCashierQ();  // retrieve  a cashier from PQ
    		     //System.out.print(currentCashier);
    		     if (currentCashier.getEndBusyClockTime() == currentTime) {  // busy cashier becomes free
    		    	 Customer currentCustomer = currentCashier.getCurrentCustomer();
    		         System.out.println("        Customer #" + currentCustomer.getCustomerID() + " is done.");
    		         checkoutarea.removeBusyCashierQ();
    		         currentCashier.busyToFree(); 
    		         checkoutarea.insertFreeCashierQ(currentCashier);
    		         System.out.println("        Cashier  #" + currentCashier.getCashierID() + " is free.");
    		     } else { // busy cashier is not free yet
    		           break; // end while loop
    		     }
    		}  		
    		while((!checkoutarea.emptyFreeCashierQ())&&(!checkoutarea.emptyCustomerQ())){
				Cashier cashier = checkoutarea.removeFreeCashierQ();
				Customer customerToBeServed = checkoutarea.removeCustomerQ();				
				cashier.freeToBusy(customerToBeServed, currentTime);				
				checkoutarea.insertBusyCashierQ(cashier);
				System.out.println("        Customer #" + counter + " gets a cashier.");
				System.out.println("        Cashier  #" + cashier.getCashierID() + " starts serving customer #" + customerToBeServed.getCustomerID() + " for " + customerToBeServed.getServiceTime() + " units.");
				numServed++;
			}
    		// Step 2: free busy cashiers, add to free cashierQ
    		// Step 3: get free cashiers to serve waiting customers 
  	} // end simulation loop
  	avgWaitingTime = ((double)totalWaitingTime)/counter;
  	System.out.println("End of simulation report");
  	// clean-up
  }

  private void printStatistics()
  {
	// add statements into this method!
	// print out simulation results
	// see the given example in README file
        // you need to display all free and busy gas pumps
	System.out.println(" # total arrival customers  : " + counter);
	System.out.println(" # customers gone-away      : " + numGoaway);
	System.out.println(" # customers served         : " + numServed);
	System.out.println();
	System.out.println("*** Current Cashiers Info. ***");
	System.out.println();
	checkoutarea.printStatistics();
	System.out.println();
	System.out.println("Total waiting time         : " + totalWaitingTime);
	System.out.println("Average waiting time       : " + avgWaitingTime);
	System.out.println("Busy Cashiers Info. :");
	while(!checkoutarea.emptyBusyCashierQ()){
		Cashier currentCashier = checkoutarea.peekBusyCashierQ();
		currentCashier.setEndBusyClockTime(simulationTime);
		currentCashier.printStatistics();
		checkoutarea.removeBusyCashierQ();
	}	
	System.out.println("Free Cashiers Info. :");
	while(!checkoutarea.emptyFreeCashierQ()){
		Cashier currentCashier = checkoutarea.removeFreeCashierQ();
		currentCashier.setEndFreeClockTime(simulationTime);
		currentCashier.printStatistics();
	}
  }


  // *** main method to run simulation ****
  public static void main(String[] args) {
   	SuperMart runSuperMart=new SuperMart();
   	runSuperMart.setupParameters();
   	runSuperMart.doSimulation();
   	runSuperMart.printStatistics();
  }

}
