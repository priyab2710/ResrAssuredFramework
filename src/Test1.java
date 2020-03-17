import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Utilities.Reusable;
import files.Payload;

public class Test1 {

	public static void main(String[] args) {
		
		//Given - all the inputs - header, param, URI, body
		//When - submit resource and method
		//then - validate the response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		System.out.println("Executing add place");
		String response=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).body("status", equalTo("OK")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		JsonPath jp = Reusable.rawToJson(response);
		String place_id =jp.getString("place_id");
		String newaddress="70 Summer walk, USA";
		System.out.println("Executing update place");
		given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+place_id+"\",\r\n" + 
				"\"address\":\""+newaddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
		.when().put("maps/api/place/update/json").then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		System.out.println("Executing get place");
		
		
		String response1= given().queryParam("key", "qaclick123").queryParam("place_id", place_id).header("Content-Type", "application/json")
		
		.when().get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();
		
		
		JsonPath js1=Reusable.rawToJson(response1);
		String actualaddress= js1.getString("address");
	    Assert.assertEquals(actualaddress, newaddress);
		System.out.println("Executing delete place");
		
		given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"    \"place_id\":\""+place_id+"\"\r\n" + 
				"}\r\n" + 
				"").when().delete("maps/api/place/delete/json")
		.then().assertThat().statusCode(200);
	}

}
