package Utilities;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class GetResponse {

	@Test
	public void test1()
	{
		// TODO Auto-generated method stub

		
		   JsonPath js=new JsonPath(Payload.CourseResponseJson());
           int count= js.getInt("courses.size()");
           int purchseamount=js.getInt("dashboard.purchaseAmount");
           
          System.out.println(count);
           int purchseamountindividual=0;
           for(int i=0;i<count;i++)
           {
        	   
        	   String title= js.get("courses["+i+"].title");
        	   int price=js.getInt("courses["+i+"].price");
        	   int copies=js.getInt("courses["+i+"].copies");
        	   purchseamountindividual=purchseamountindividual+(price*copies);
        	 //  System.out.println(purchseamountindividual);
           /* if(title.equals("RPA"))
        	   {
        		   
               System.out.println(title);
        	   int price1= js.getInt("courses["+i+"].price");
        	   System.out.println(price);
        	   int copies1= js.getInt("courses["+i+"].copies");
        	   System.out.println(copies);*/
        	  
        	  
           } 
           System.out.println(purchseamountindividual);
           
           Assert.assertTrue(purchseamountindividual==purchseamount, "Pass");
           }
} 
		
	


