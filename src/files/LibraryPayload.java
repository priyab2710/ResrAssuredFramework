package files;

public class LibraryPayload {

	
	public static String getaddbookpayload(String Authorname, String isbn, String aisle)
	{
		String addbook = "{\r\n" + 
				"\r\n" + 
				"\"name\":\"Learn Appium Automation with Java\",\r\n" + 
				"\"isbn\":\""+isbn+"\",\r\n" + 
				"\"aisle\":\""+aisle+"\",\r\n" + 
				"\"author\":\""+Authorname+"\"\r\n" + 
				"}\r\n" + 
				" \r\n" + 
				"";
		return addbook;
	}
	
	public static String deleteaddbookpayload(String ID)
	{
		String deletebook = "{\r\n" + 
				" \r\n" + 
				"\"ID\" : \""+ID+"\"\r\n" + 
				" \r\n" + 
				"} \r\n" + 
				"";
		return deletebook;
	}
}
