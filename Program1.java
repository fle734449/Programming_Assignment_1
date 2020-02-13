/*
 * Name: Frank Le
 * EID: fpl227
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the Stable Marriage problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching marriage) {
    	ArrayList<Integer> finalMatching = marriage.getEmployeeMatching();
    	int n = finalMatching.size();
        
        for(int employeeOne = 0; employeeOne < n; employeeOne++) {
        	int locationOne = finalMatching.get(employeeOne);
        	if (locationOne != -1) {
        		for(int employeeTwo = 0; employeeTwo < n; employeeTwo++) {
        			if (employeeOne != employeeTwo) {
        			int locationTwo = finalMatching.get(employeeTwo);
        			
        				//First Type of Instability
        				if (locationTwo == -1) {
        					ArrayList<Integer> locationPref = marriage.getLocationPreference().get(locationOne);
        					if(locationPref.indexOf(employeeTwo) < locationPref.indexOf(employeeOne)) {
        						return false;
        					}
        				} 
        				//Second Type of Instability
        				else {
        					ArrayList<Integer> locationOnePref = marriage.getLocationPreference().get(locationOne);
        					ArrayList<Integer> employeeTwoPref = marriage.getEmployeePreference().get(employeeTwo);
        					if((locationOnePref.indexOf(employeeTwo) < locationOnePref.indexOf(employeeOne)) 
        							&& (employeeTwoPref.indexOf(locationOne) < employeeTwoPref.indexOf(locationTwo))) {
        						return false;
        					}
    					}
        			}
        		}
        	}
        }
        
        return true; 
    }


    /**
     * Determines a employee optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_employeeoptimal(Matching marriage) {
    	//Number of locations
    	int numLocations = marriage.getLocationCount();
    	
    	//Number of employees
        int numEmployees = marriage.getEmployeeCount();
        
        //Queue of free "men"
        Queue<Integer> freeEmployees = new LinkedList<Integer>();
        
        //Inverse preference list for "women"
        //int[] invLocPref = new int[numEmployees];
        ArrayList<ArrayList<Integer>> invLocPref = new ArrayList<ArrayList<Integer>>();
        
        //Number of proposals made by "men"
        int[] count = new int[numEmployees];
        
        //Arrays of Men = Employees and Women = Locations
        int[] employees = new int[numEmployees];
        //int[] locations = new int[numLocations];
        ArrayList<ArrayList<Integer>> locations = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> location_slots = new ArrayList<Integer>();
		location_slots = new ArrayList<Integer>(marriage.getLocationSlots()); 
        for(int i = 0; i < numEmployees; i++) {
        	employees[i] = -1;
        	freeEmployees.add(i);
        	count[i] = 0;
        	
        }

        for(int i = 0; i < numLocations; i++) {
        	locations.add(new ArrayList<Integer>(location_slots.get(i)));
        	for(int j = 0; j < location_slots.get(i); j++) {
        		locations.get(i).add(-1);
        	}
        }
        for(int i = 0; i < numLocations; i++) {
        	//locations[i] = -1;
        	invLocPref.add(new ArrayList<Integer>());
        	for(int j = 0; j < numEmployees; j++) {
        		invLocPref.get(i).add(0);
        	}
        }
        
        for(int i = 0; i <numLocations; i++) {
        	for(int j = 0; j < numEmployees; j++) {
        		int rankEmployee = marriage.getLocationPreference().get(i).get(j);
        		invLocPref.get(i).set(rankEmployee, j);
        	}
        }
        
        while(freeEmployees.size() > 0) {
        	int currentEmployee = freeEmployees.peek();
        	if (count[currentEmployee] == numLocations) {
        		freeEmployees.remove();
        		if (freeEmployees.size() <= 0) {
        			break;
        		}
        		currentEmployee = freeEmployees.peek();
        	}
        	
        	int currentLocation = marriage.getEmployeePreference().get(currentEmployee).get(count[currentEmployee]);
        	
        	//if location is free
        	//if(locations[currentLocation] == -1) {
        	if(locations.get(currentLocation).contains(-1)) {
        		employees[currentEmployee] = currentLocation;
        		for(int i = 0; i < locations.get(currentLocation).size(); i++) {
        			if(locations.get(currentLocation).get(i) == -1) {
        				locations.get(currentLocation).set(i, currentEmployee);
        				break;
        			}
        		}
        		/*
        		location_slots.set(currentLocation, location_slots.get(currentLocation)-1);
        		if(location_slots.get(currentLocation) == 0) {
        			locations[currentLocation] = currentEmployee;
        			
            	}
            	*/
        		freeEmployees.remove();
        	}
        	//if locations is not free
        	else {
        		/*if(invLocPref[currentEmployee] < invLocPref[locations[currentLocation]]) { */
        		for(int i = 0; i <locations.get(currentLocation).size(); i++) {
        			
        		  if(invLocPref.get(currentLocation).get(currentEmployee) 
        				  < invLocPref.get(currentLocation).get(locations.get(currentLocation).get(i))) {
        			int prevEmployee = locations.get(currentLocation).get(i);
        			employees[prevEmployee] = -1;
        			count[prevEmployee] -= 1;
        			employees[currentEmployee] = currentLocation;
        			locations.get(currentLocation).set(i, currentEmployee);
        			freeEmployees.remove();
        			freeEmployees.add(prevEmployee);
        			break;
        		  }
        		}
        	}
        	count[currentEmployee] += 1;
        }

        ArrayList<Integer> matching = new ArrayList<Integer>();
    	for (int i = 0; i < employees.length; i++) {
    		matching.add(employees[i]);
    	}
    	marriage.setEmployeeMatching(matching);
    	return marriage;
    }

    /**
     * Determines a location optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_locationoptimal(Matching marriage) {
    	//Number of locations
    	int numLocations = marriage.getLocationCount();
    	
    	//Number of employees
        int numEmployees = marriage.getEmployeeCount();
        
        //Queue of free "men"
        Queue<Integer> freeLocations = new LinkedList<Integer>();
        
        //Inverse preference list for "women"
        //int[] invLocPref = new int[numLocations];
        ArrayList<ArrayList<Integer>> invLocPref = new ArrayList<ArrayList<Integer>>();
        //Number of proposals made by "men"
        int[] count = new int[numLocations];
        
        //Arrays of Men = Location and Women = Employees
        int[] employees = new int[numEmployees];
        int[] locations = new int[numLocations];
        ArrayList<Integer> location_slots = new ArrayList<Integer>();
		location_slots = new ArrayList<Integer>(marriage.getLocationSlots()); 
        for(int i = 0; i < numLocations; i++) {
        	locations[i] = -1;
        	freeLocations.add(i);
        	count[i] = 0;
        	
        }

        for(int i = 0; i < numEmployees; i++) {
        	employees[i] = -1;  
        	invLocPref.add(new ArrayList<Integer>());
        	for(int j = 0; j < numLocations; j++) {
        		invLocPref.get(i).add(0);
        	}
        }
        for(int i = 0; i < numEmployees; i++) {
        	for(int j = 0; j < numLocations; j++) {
        		int rankLocation = marriage.getEmployeePreference().get(i).get(j);
        		invLocPref.get(i).set(rankLocation, j);
        	}
        }
        
        while(freeLocations.size() > 0) {
        	int currentLocation = freeLocations.peek();
        	if(location_slots.get(currentLocation) == 0) {
        		freeLocations.remove();
        		if (freeLocations.size() <= 0) {
        			break;
        		}
        		currentLocation = freeLocations.peek();
        	}
        	int currentEmployee = marriage.getLocationPreference().get(currentLocation).get(count[currentLocation]);

        	//if employee is free
        	if(employees[currentEmployee] == -1) {
        		employees[currentEmployee] = currentLocation;
        		locations[currentLocation] = currentEmployee;
        		location_slots.set(currentLocation, location_slots.get(currentLocation)-1);
        	} 
        	//employee is not free
        	else {
        		if(invLocPref.get(currentEmployee).get(currentLocation) < 
        				invLocPref.get(currentEmployee).get(employees[currentEmployee])) {
        			int prevLocation = employees[currentEmployee];
        			location_slots.set(prevLocation, location_slots.get(prevLocation)+1);
        			employees[currentEmployee] = currentLocation;
        			locations[currentLocation] = currentEmployee;
        			freeLocations.remove();
        			freeLocations.add(prevLocation);
        		}
        			
        	}
        	count[currentLocation] += 1;
        }
        ArrayList<Integer> matching = new ArrayList<Integer>();
    	for (int i = 0; i < employees.length; i++) {
    		matching.add(employees[i]);
    	}
    	marriage.setEmployeeMatching(matching);
    	return marriage;
    }
}
