import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import constants.UrlConstants;

public class Thesaurus {

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("/Users/shukri/Documents/workspace/Thesaurus/json/ThesaurusInJson.json"));
			JSONObject jsonObject = (JSONObject) obj;
			
			jsonObject.forEach((k,v)->{
				//URL k  
				JSONObject wordObject = (JSONObject) v;
				
//				System.out.println(" Object : " + wordObject.get("en"));
				
			});
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.getUsers(UrlConstants.USER_BASE_URL);
	}

}
