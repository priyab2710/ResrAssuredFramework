package Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.ITestResult;

import files.JiraPayload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;

public class Reusable {

	
	public static JsonPath rawToJson(String response)
	{
		JsonPath js= new JsonPath(response);
		return js;
	}
	
	public static String GenerateStringFromJsonFile(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	public static void CreateJiraIssue(ITestResult result)
	{
		
		System.out.println("In createJiraIssue method " + result);
		RestAssured.baseURI="http://localhost:8080";
		String getsession=given().header("Content-Type", "application/json").body("{ \"username\": \"priyab2710\", \"password\": \"Saurabh#7\" }")
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2= Reusable.rawToJson(getsession);
		String name =js2.get("session.name");
		String value=js2.get("session.value");
		String cookie = name+"="+value;
		System.out.println("cookie = "+cookie);
	
		String createissueresponse= given().log().all().header("Cookie",cookie).body(JiraPayload.createissuepayload())
		.when().post("rest/api/2/issue/")
		.then().log().all().extract().response().asString();
		
	//	System.out.println(createissueresponse);
		JsonPath js=Reusable.rawToJson(createissueresponse);
		String issueid=js.get("id");
			
		given().log().all().pathParam("issuekey", issueid).body("{\r\n" + 
				"    \"body\": \"This is a comment regarding the quality of the response.\"\r\n" + 
				"}")
		.when().post("rest/api/2/issue/{issuekey}/comment")
		.then().assertThat().statusCode(200);
		
	}
}
