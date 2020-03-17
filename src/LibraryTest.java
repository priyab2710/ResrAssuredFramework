import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utilities.Reusable;
import files.LibraryPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class LibraryTest {
	public String ID;
	
	@Test(dataProvider="BookData")
	public void addbook(String AuthorName, String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
  //System.out.println("Post request for "+AuthorName+" , "+isbn+" , "+aisle);
		String addBookResponse=given().header("Content-Type","application/json")
		.body(LibraryPayload.getaddbookpayload(AuthorName,isbn,aisle)).when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=Reusable.rawToJson(addBookResponse);
		 ID= js.get("ID");
		given().queryParam("AuthorName", AuthorName).when().get("/Library/GetBook.php")
			.then().log().all().assertThat().statusCode(200);
		//	System.out.println("Get request for "+AuthorName);
				
			given().header("Content-Type","application/json").body(LibraryPayload.deleteaddbookpayload(ID))
			.when().post("Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));
		//	System.out.println("Delete request for "+AuthorName);
			
	}

	@DataProvider(name="BookData")
	public Object[][] getData()
	{
		//return new Object[][] {{"planit","planittest","222"},{"planit","planittest","333"},{"planit","planittest","444"}};
		//return new Object[][] {{"Priya123","test123","12222"}};
		return new Object[][] {{"Jiratest","input1","001"}};
	}
}
