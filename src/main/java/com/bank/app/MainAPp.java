package com.bank.app;

import java.util.ArrayList;
import java.util.List;

public class MainAPp {

    public static void main(String[] args) {
        int x = 5;
        int y = 8;
        y += 2 + x++;
        System.out.println(x);
        System.out.println(y);
        //	       int i = 10;
        //	       List<Integer> firstBox = new LinkedList<>();
        //	       List<Integer> secondBox = new LinkedList<>();
        //	       List<Integer> thirdBox = new LinkedList<>();
        ////	       [1,9]
        ////
        ////	    		   [4,6,8,10]
        ////
        ////	    		   [2,3,5,7]
        //	       for (int r = 1; r <= 20 ; r++ ) {
        //	    	   if (r % 2 == 0) {
        //	    		   if (r == 2) {
        //	    			   thirdBox.add(r);
        //	    			   continue;
        //		    	   }
        //	    		   secondBox.add(r);
        //	    	   } else {
        //	    		   if (r != 1) {
        //	    			   if (Math.sqrt(r) % 1 != 0) {
        //	    				   boolean prime = false;
        //	    				   if (i > 3) {
        //	    					   for (int a : thirdBox) {
        //		    					   if (r % a == 0) {
        //		    						   prime = true;
        //		    					   }
        //		    				   }
        //	    				   } else {
        //	    					   prime = true;
        //	    				   }
        //	    				   if (prime) {
        //	    					   thirdBox.add(r);
        //	    				   }
        //	    			   }
        //	    		   }
        //	    		   firstBox.add(r);
        //	    	   }
        //	       }
        //
        ////	       for (int r = 1; r <= 20 ; r++ ) {
        ////
        ////	    	   if (r % 2 == 0 && r !=2) {
        ////	    		   secondBox.add(r);
        ////	    		   continue;
        ////	    	   }
        ////
        ////
        ////	    	   if (r / 2 == 0 || Math.sqrt(r) % 1 == 0) {
        ////	    		   firstBox.add(r);
        ////	    		   continue;
        ////	    	   }
        ////
        ////	    	   if (r % 2 == 1 || r == 2) {
        ////	    		   thirdBox.add(r);
        ////	    		   continue;
        ////	    	   }
        ////
        ////
        ////
        ////	    	   System.out.println(r / 2);
        ////
        ////
        ////	       }
        ////
        //	       System.out.println(firstBox);
        //	       System.out.println(secondBox);
        //	       System.out.println(thirdBox);
    }

    // Method to build the list of states
    public static List<States> buildStateList() {
        List<States> stateList = new ArrayList<>();

        // Add states to the list
        stateList.add(new States("AL", "Alabama"));
        stateList.add(new States("LK", "Llaska"));
        stateList.add(new States("AR", "Arkansas"));
        stateList.add(new States("AZ", "Arizona"));
        // Add more states...

        return stateList;
    }
}
