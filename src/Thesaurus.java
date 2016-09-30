import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import constants.UrlConstants;
import models.EuscreenUser;

public class Thesaurus {
	private static final MyLogger LOGGER = MyLogger.getInstance();

	private static JSONObject jsonObject = null;
	
	public static String splitKeywordFindTranslation(String keyword){
		String nonEnglishThesaurusKeyword = "";
		if(keyword != null) {
//			String[] keywordsArray = keywords.split(",");
//			for(int i=0; i < keywordsArray.length; i++){
				
//				String currentWord = keywordsArray[i];
				for(Object v : jsonObject.keySet()){
					//URL k  
					JSONObject wordObject = (JSONObject) jsonObject.get(v);
					try{
						if(wordObject.get("en") !=  null){
							
							String enWord = (String) wordObject.get("en");

							if(keyword.toLowerCase().equals(enWord.toLowerCase())){
								List<String> nonEnglishThesaurusKeywordArr= new ArrayList<String>();
								for(Object key : wordObject.keySet()){
									nonEnglishThesaurusKeywordArr.add((String) wordObject.get(key));
								}
								
								nonEnglishThesaurusKeyword += String.join(",", nonEnglishThesaurusKeywordArr);
	
							}
						}
					}catch(NullPointerException e){
						LOGGER.myLogger.severe("For word " + keyword + " word object doesn't contain en version");
						LOGGER.myLogger.log(Level.SEVERE, e.getMessage(), e);

					}
					
					
				}
			}
//		}
		
		return nonEnglishThesaurusKeyword;
	}
	

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date startDate = new Date();
		System.out.println("Start at:" +dateFormat.format(startDate));
		
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(UrlConstants.THESAURUS_JSON_URL));
			jsonObject = (JSONObject) obj;
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		
		
		HttpRequest httpRequest = new HttpRequest();
		List<EuscreenUser> euscreenUsers = httpRequest.getUsers(UrlConstants.USER_BASE_URL);
		LOGGER.myLogger.info("Euscreen users found: " + euscreenUsers.size());  
		euscreenUsers.forEach(euscreenUser ->{
			if(euscreenUser.getVideoIds().isEmpty() != true){
				//VIDEO PROCESSING
				euscreenUser.getVideoIds().forEach(videoId -> {
//					String videoId = "EUS_44A6B3749F214FE6BA56570EC1D9A475";
//					String videoId = "EUS_2C11E6300BE229B8052686F43A2EBBF7";
					
					String keyword = httpRequest.getThesaurusKeyword(videoId, euscreenUser.getUsername(), "video");
					
					String nonEnglishThesaurusKeyword = Thesaurus.splitKeywordFindTranslation(keyword);
					
					System.out.println("========================VIDEO====================");  
					System.out.println("Id: " + videoId);  
					System.out.println("Value: " + nonEnglishThesaurusKeyword);  
					System.out.println("============================================");
					
					
					
					//Save to database
//					httpRequest.doPost(videoId, euscreenUser.getUsername(), "video", "ThesaurusNonEnglishTerm", nonEnglishThesaurusKeyword);
				});
			}
			
			if(euscreenUser.getAudioIds().isEmpty() != true){
				//AUDIO PROCESSING
				euscreenUser.getAudioIds().forEach(audioId -> {
					
					String keyword = httpRequest.getThesaurusKeyword(audioId, euscreenUser.getUsername(), "audio");
					
					String nonEnglishThesaurusKeyword = Thesaurus.splitKeywordFindTranslation(keyword);
					
					System.out.println("========================AUDIO====================");  
					System.out.println("Id: " + audioId);  
					System.out.println("Value: " + nonEnglishThesaurusKeyword);  
					System.out.println("============================================");
					
					//Save to database
//					httpRequest.doPost(videoId, euscreenUser.getUsername(), "video", "ThesaurusNonEnglishTerm", nonEnglishThesaurusKeyword);
				});
			}
			
			if(euscreenUser.getPictureIds().isEmpty() != true){
				//PICTURE PROCESSING
				euscreenUser.getPictureIds().forEach(pictureId -> {
					
					String keyword = httpRequest.getThesaurusKeyword(pictureId, euscreenUser.getUsername(), "picture");
					
					String nonEnglishThesaurusKeyword = Thesaurus.splitKeywordFindTranslation(keyword);
					
					System.out.println("========================PICTURE====================");  
					System.out.println("Id: " + pictureId);  
					System.out.println("Value: " + nonEnglishThesaurusKeyword);  
					System.out.println("============================================");
					
					//Save to database
//					httpRequest.doPost(videoId, euscreenUser.getUsername(), "video", "ThesaurusNonEnglishTerm", nonEnglishThesaurusKeyword);
				});
			}
			
			if(euscreenUser.getDocIds().isEmpty() != true){
				//DOCUMENT PROCESSING
				euscreenUser.getDocIds().forEach(documentId -> {
					
					String keyword = httpRequest.getThesaurusKeyword(documentId, euscreenUser.getUsername(), "picture");
					
					String nonEnglishThesaurusKeyword = Thesaurus.splitKeywordFindTranslation(keyword);
					
					System.out.println("========================DOCUMENT====================");  
					System.out.println("Id: " + documentId);  
					System.out.println("Value: " + nonEnglishThesaurusKeyword);  
					System.out.println("============================================");
					
					//Save to database
//					httpRequest.doPost(videoId, euscreenUser.getUsername(), "video", "ThesaurusNonEnglishTerm", nonEnglishThesaurusKeyword);
				});
			}
			
			if(euscreenUser.getSeriesIds().isEmpty() != true){
				//SERIA PROCESSING
				euscreenUser.getSeriesIds().forEach(seriesId -> {
					
					String keyword = httpRequest.getThesaurusKeyword(seriesId, euscreenUser.getUsername(), "picture");
					
					String nonEnglishThesaurusKeyword = Thesaurus.splitKeywordFindTranslation(keyword);
					
					System.out.println("========================SERIA====================");  
					System.out.println("Id: " + seriesId);  
					System.out.println("Value: " + nonEnglishThesaurusKeyword);  
					System.out.println("============================================");
					
					//Save to database
//					httpRequest.doPost(videoId, euscreenUser.getUsername(), "video", "ThesaurusNonEnglishTerm", nonEnglishThesaurusKeyword);
				});
				
			}
			
			if(euscreenUser.getTeaserIds().isEmpty() != true){
				//TEASER PROCESSING
				euscreenUser.getTeaserIds().forEach(teaserId -> {
					
					String keyword = httpRequest.getThesaurusKeyword(teaserId, euscreenUser.getUsername(), "picture");
					
					String nonEnglishThesaurusKeyword = Thesaurus.splitKeywordFindTranslation(keyword);
					
					System.out.println("========================TEASER====================");  
					System.out.println("Id: " + teaserId);  
					System.out.println("Value: " + nonEnglishThesaurusKeyword);  
					System.out.println("============================================");
					
					//Save to database
//					httpRequest.doPost(videoId, euscreenUser.getUsername(), "video", "ThesaurusNonEnglishTerm", nonEnglishThesaurusKeyword);
				});
			}
			
			if(euscreenUser.getCollectionIds().isEmpty() != true){
				//COLLECTION PROCESSING
				euscreenUser.getCollectionIds().forEach(collectionId -> {
					
					String keyword = httpRequest.getThesaurusKeyword(collectionId, euscreenUser.getUsername(), "picture");
					
					String nonEnglishThesaurusKeyword = Thesaurus.splitKeywordFindTranslation(keyword);
					
					System.out.println("========================COLLECTION====================");  
					System.out.println("Id: " + collectionId);  
					System.out.println("Value: " + nonEnglishThesaurusKeyword);  
					System.out.println("============================================");
					
					//Save to database
//					httpRequest.doPost(videoId, euscreenUser.getUsername(), "video", "ThesaurusNonEnglishTerm", nonEnglishThesaurusKeyword);
				});
			}
		});
		
		Date endDate = new Date();
		System.out.println("End at:" +dateFormat.format(endDate));
	}

}
