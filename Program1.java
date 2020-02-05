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
    	
    	//First type of instability
        for(int i = 0; i < n; i++) {
        	int location = finalMatching.get(i);
        	for(int j = i+1; j < n; j++) {
        		if(finalMatching.get(j).equals(-1)) {
        			ArrayList<Integer> locationPref = marriage.getLocationPreference().get(location);
        			if(locationPref.indexOf(j) < locationPref.indexOf(i)) {
        				return false;
        			}
        		}
        	}
        }
        
        //Second type of instability
        for(int i = 0; i < n; i++) {
        	int locationOne = finalMatching.get(i);
        	for(int j = i+1; j < n; j++) {
        		int locationTwo = finalMatching.get(j);
        		ArrayList<Integer> locationOnePref = marriage.getLocationPreference().get(locationOne);
        		ArrayList<Integer> employeeTwoPref = marriage.getEmployeePreference().get(j);
        		if((locationOnePref.indexOf(j) < locationOnePref.indexOf(i)) && (employeeTwoPref.indexOf(locationOne) < employeeTwoPref.indexOf(locationTwo))) {
    				return false;
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
        
        //Arrays of Men - Employees and Women - Locations
        int[] employees = new int[numLocations];
        int[] locations = new int[numEmployees];
        for(int i = 0; i < numLocations; i++) {
        	employees[i] = -1;
        }
        //Queue of free "men"
        Queue<Integer> freeEmployees = new LinkedList();
       
        
        //Number of proposals made by "men"
        int[] count = new int[numEmployees];
        for(int i = 0; i < numEmployees; i++) {
        	freeEmployees.add(-1);
        	count[i] = 0;
        	locations[i] = -1;
        }
        
        return null; /* TODO remove this line */
    }

    /**
     * Determines a location optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_locationoptimal(Matching marriage) {
        /* TODO implement this function */
        return null; /* TODO remove this line */
    }
}
