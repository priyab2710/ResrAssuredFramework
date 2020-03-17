package files;

public class JiraPayload {

	
	public static String createissuepayload()
	{
		String createissue = "{\r\n" + 
				"    \"fields\": {\r\n" + 
				"       \"project\":\r\n" + 
				"       {\r\n" + 
				"          \"key\": \"PRIYAD\"\r\n" + 
				"       },\r\n" + 
				"       \"summary\": \"Priya is Jira testing Rest API test\",\r\n" + 
				"       \"description\": \"Creating an issue in JIRA from listner\",\r\n" + 
				"       \"issuetype\": {\r\n" + 
				"          \"name\": \"Bug\"\r\n" + 
				"       }\r\n" + 
				"   }\r\n" + 
				"}";
		return createissue;
	}
}
