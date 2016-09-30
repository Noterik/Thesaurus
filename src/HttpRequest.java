import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import constants.GeneralConstants;
import constants.UrlConstants;
import interfaces.IhttpRequests;
import models.EuscreenUser;

public class HttpRequest implements IhttpRequests{
	private static final MyLogger LOGGER = MyLogger.getInstance();	private HttpClient httpClient = null;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	public HttpRequest() {
		 this.httpClient = HttpClientBuilder.create().setMaxConnPerRoute(1000000).build();
	};
	
	@Override
	public List<EuscreenUser> getUsers(String url) {

		Document document = doGet(url);
		
		if(document != null){
	        List<Node> usersNodeList = document.selectNodes("//fsxml/user");
	        
	        List<String> users = new ArrayList<String>();
	        		
	        usersNodeList.forEach(user -> {
	        	org.dom4j.Element userAsElement = (org.dom4j.Element) user;
	        	
//	        	if(userAsElement.attributeValue("id").startsWith("eu_")){
	        	//Make only for dr
	        	if(userAsElement.attributeValue("id").equals("eu_kb")){
//	        	if(userAsElement.attributeValue("id").startsWith("eu_")){
	        		users.add(userAsElement.attributeValue("id"));
	        	}
	        });
	        return filterUser(users);
		}
		
		return null;
        
	}

	public List<EuscreenUser> filterUser(List<String> userNames) {
		List<EuscreenUser> users = new ArrayList<EuscreenUser>();
		
		userNames.forEach(userName -> {
			LOGGER.myLogger.info("Getting: " + userName);
			System.out.println("Getting: " + userName);

			EuscreenUser euscreenUser = new EuscreenUser(userName);
			
			Document documentVideo = doGet(UrlConstants.USER_BASE_URL + userName + "/video");
			
			if(documentVideo != null){
		        List<Node> videoNodeList = documentVideo.selectNodes("//fsxml/video");
				LOGGER.myLogger.info("Video found: " + videoNodeList.size());
				System.out.println("Video found: " + videoNodeList.size());

				List<String> videoIdList = new ArrayList<String>();
				
				videoNodeList.forEach(videoNode -> {
					Element videoElement = (Element) videoNode;
					videoIdList.add(videoElement.attributeValue("id"));
				});
				
				euscreenUser.setVideoIds(videoIdList);
			}
			
			Document documentPicture = doGet(UrlConstants.USER_BASE_URL + userName + "/picture");
	  
			if(documentPicture != null){
				List<Node> pictureNodeList = documentPicture.selectNodes("//fsxml/picture");
			
				LOGGER.myLogger.info("Pictures found: " + pictureNodeList.size());
				System.out.println("Pictures found: " + pictureNodeList.size());
				
				List<String> pictureIdList = new ArrayList<String>();
				
				pictureNodeList.forEach(pictureNode -> {
					Element pictureElement = (Element) pictureNode;
					pictureIdList.add(pictureElement.attributeValue("id"));
				});
				
				euscreenUser.setPictureIds(pictureIdList);

			}
			
			Document documentSeries = doGet(UrlConstants.USER_BASE_URL + userName + "/series");
			
			if(documentSeries != null){
		        List<Node> seriesNodeList = documentSeries.selectNodes("//fsxml/series");
		        
				LOGGER.myLogger.info("Series found: " + seriesNodeList.size());
				System.out.println("Series found: " + seriesNodeList.size());
				
				List<String> seriesIdList = new ArrayList<String>();
				
				seriesNodeList.forEach(seriesNode -> {
					Element seriesElement = (Element) seriesNode;
					seriesIdList.add(seriesElement.attributeValue("id"));
				});
				
				euscreenUser.setSeriesIds(seriesIdList);
			}
			
			Document documentAudio = doGet(UrlConstants.USER_BASE_URL + userName + "/audio");
			
			if(documentAudio != null){
		        List<Node> audioNodeList = documentAudio.selectNodes("//fsxml/audio");
		       
		        LOGGER.myLogger.info("Audio found: " + audioNodeList.size());
		        System.out.println("Audio found: " + audioNodeList.size());
		        
				List<String> audioIdList = new ArrayList<String>();
				
				audioNodeList.forEach(audioNode -> {
					Element audioElement = (Element) audioNode;
					audioIdList.add(audioElement.attributeValue("id"));
				});
				
				euscreenUser.setAudioIds(audioIdList);
			}
			
			Document documentTeaser = doGet(UrlConstants.USER_BASE_URL + userName + "/teaser");

			if(documentTeaser != null){
		        List<Node> teaserNodeList = documentTeaser.selectNodes("//fsxml/teaser");
		        
		        LOGGER.myLogger.info("Teaser found: " + teaserNodeList.size());
		        System.out.println("Teaser found: " + teaserNodeList.size());
		       
		        List<String> teaserIdList = new ArrayList<String>();
				
				teaserNodeList.forEach(teaserNode -> {
					Element teaserElement = (Element) teaserNode;
					teaserIdList.add(teaserElement.attributeValue("id"));
				});
				
				euscreenUser.setTeaserIds(teaserIdList);
			}
			
			Document documentDoc = doGet(UrlConstants.USER_BASE_URL + userName + "/doc");
			System.out.println("Docs");
			System.out.println(documentDoc);
			if(documentDoc != null){
		        List<Node> docNodeList = documentDoc.selectNodes("//fsxml/doc");
		       
		        LOGGER.myLogger.info("Docs found: " + docNodeList.size());
		        System.out.println("Docs found: " + docNodeList.size());
		    
		        List<String> docIdList = new ArrayList<String>();
				
				docNodeList.forEach(docNode -> {
					Element docElement = (Element) docNode;
					docIdList.add(docElement.attributeValue("id"));
				});

				euscreenUser.setDocIds(docIdList);
			}
			
			Document documentCollection = doGet(UrlConstants.USER_BASE_URL + userName + "/collection");
			
			if(documentCollection != null){
		        List<Node> collectionNodeList = documentCollection.selectNodes("//fsxml/collection");
		       
		        LOGGER.myLogger.info("Collection found: " + collectionNodeList.size());
		        System.out.println("Collection found: " + collectionNodeList.size());
		        
		        List<String> collectionIdList = new ArrayList<String>();
				
				collectionNodeList.forEach(collectionNode -> {
					Element collectionElement = (Element) collectionNode;
					collectionIdList.add(collectionElement.attributeValue("id"));
				});
				
				euscreenUser.setCollectionIds(collectionIdList);
			}

			users.add(euscreenUser);
		});
		
		
		
		return users;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Document doGet(String url) {
		HttpGet request = new HttpGet(url);
		LOGGER.myLogger.info("HttpRequest.doGet(" + url + ")");
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader(BasicScheme.authenticate(
				 new UsernamePasswordCredentials(GeneralConstants.getBartUsername(), GeneralConstants.getBartPassword()),
				 "UTF-8", false));
		
		Document document = null;
		try {
		
			HttpResponse response = this.httpClient.execute(request);
			if(response.getStatusLine().getStatusCode() == 200){
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
	
				StringBuffer userRequestResultBuilder = new StringBuffer();
				String line = "";
				
				while ((line = rd.readLine()) != null) {
					userRequestResultBuilder.append(line);
				}
					
				SAXReader reader = new SAXReader();
		        document = reader.read(new ByteArrayInputStream(userRequestResultBuilder.toString().getBytes()));
			}
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.myLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return document;
	}

	@Override
	public String getThesaurusKeyword(String id, String userName, String type) {
		Document itemDocument = doGet(UrlConstants.USER_BASE_URL + userName + "/" + type + "/" + id);
		String thesaurusKeyword = null;
		try{
			Node thesaurusKeyWordNode = itemDocument.selectSingleNode("//fsxml/video/properties/ThesaurusTerm");
			Element thesaurusKeywordelement = (Element) thesaurusKeyWordNode;
			thesaurusKeyword = thesaurusKeywordelement.getText();
		}catch (NullPointerException e){
			LOGGER.myLogger.severe("Node does not exist at " + id);
		}
		return thesaurusKeyword;

	}

	@Override
	public void doPost(String id, String userName, String type, String field, String value) {
		String url = UrlConstants.USER_SMITHERS2_BASE_URL + userName + "/" + type + "/" + id + "/properties/" + field;
		if(value != "" && value != null){
	        try {
	            HttpPut request = new HttpPut(url);
	            
	    		request.addHeader("User-Agent", USER_AGENT);
	    		request.addHeader(BasicScheme.authenticate(
	    				 new UsernamePasswordCredentials(GeneralConstants.getBartUsername(), GeneralConstants.getBartPassword()),
	    				 "UTF-8", false));
	    		request.addHeader("Content-Type", "text/plain;charset=UTF-8");
				request.setEntity(new StringEntity(value, "UTF-8"));
		        HttpResponse response = this.httpClient.execute(request);
				LOGGER.myLogger.info("============================================");  
				LOGGER.myLogger.info("Id: " + id);  
				LOGGER.myLogger.info("Value: " + value);  
				LOGGER.myLogger.info("Response code: " + response.getStatusLine().getStatusCode());  
				LOGGER.myLogger.info("============================================");  

			} catch (UnsupportedEncodingException e) {
				LOGGER.myLogger.log(Level.SEVERE, e.getMessage(), e);
				
			} catch (IOException e) {
				LOGGER.myLogger.log(Level.SEVERE, e.getMessage(), e);

			}

		}
	}

}
