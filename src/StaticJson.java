import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.Reusable;
import files.LibraryPayload;


public class StaticJson {

	@Test
	public void addBook() throws IOException
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response= given().log().all().header("Content-Type", "application/json")
		.body(Reusable.GenerateStringFromJsonFile("C:\\Users\\pbhamare\\Desktop\\addbook.json"))
		.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = Reusable.rawToJson(response);
		 String Message = js1.getString("Msg");
		String ID= js1.get("ID");
		String expected="successfully added";
		
				Assert.assertEquals(Message, expected);
		//Assert.assertEquals(Message, equalTo("successfully added"));
		
				
		given().queryParam("ID", ID).
		when().get("Library/GetBook.php").
		then().assertThat().statusCode(200);
				
		
		given().header("Content-Type", "application/json").body(LibraryPayload.deleteaddbookpayload(ID)).
        when().post("Library/DeleteBook.php").
        then().assertThat().statusCode(200);
				
		
	}
	
	
	
}
