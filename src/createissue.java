import static io.restassured.RestAssured.given;

import org.testng.ITestResult;
import org.testng.annotations.Test;

import Utilities.Reusable;
import files.JiraPayload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class createissue {

	@Test
	public static void CreateJiraIssue()
	{
		
		System.out.println("In createJiraIssue method ");
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session=new SessionFilter();
		String getsession=given().header("Content-Type", "application/json").body("{ \"username\": \"priyab2710\", \"password\": \"Saurabh#7\" }")
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2= Reusable.rawToJson(getsession);
		String name =js2.get("session.name");
		String value=js2.get("session.value");
		String cookie = name+"="+value;
		//System.out.println("cookie = "+cookie);
	
		String createissueresponse= given().log().all()
			//.header("Cookie",cookie)
				.filter(session)
			.header("Content-Type", "application/json").body(JiraPayload.createissuepayload())
		.when().post("rest/api/2/issue/")
		.then().log().all().extract().response().asString();
		
		JsonPath js3= Reusable.rawToJson(createissueresponse);
		//System.out.println("self is "+js3.get("self"));
		
		//JsonPath js=Reusable.rawToJson(createissueresponse);
		String issueid=js3.get("id");
	//	System.out.println("this is issueid = "+issueid);
			
		given().log().all().pathParam("issuekey", issueid)
		//.header("Cookie",cookie)
		.filter(session)
		.header("Content-Type", "application/json").body("{\r\n" + 
				"    \"body\": \"This is a comment that only administrators can see.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}")
		.when().post("rest/api/2/issue/{issuekey}/comment")
		.then().log().all().assertThat().statusCode(201);
	}
}
