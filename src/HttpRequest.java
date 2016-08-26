import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import constants.GeneralConstants;
import constants.UrlConstants;
import interfaces.IhttpRequests;
import models.EuscreenUser;

public class HttpRequest implements IhttpRequests{
	private HttpClient httpClient = null;
	private final String USER_AGENT = "Mozilla/5.0";
	
	public HttpRequest() {
		 this.httpClient = HttpClientBuilder.create().build();
	};
	
	@Override
	public List<EuscreenUser> getUsers(String url) {

		Document document = doGet(url);
		
		if(document != null){
	        List<Node> usersNodeList = document.selectNodes("//fsxml/user");
	        
	        List<String> users = new ArrayList<String>();
	        		
	        usersNodeList.forEach(user -> {
	        	org.dom4j.Element userAsElement = (org.dom4j.Element) user;
	        	if(userAsElement.attributeValue("id").startsWith("eu_")){
	        		users.add(userAsElement.attributeValue("id"));
	        	}
	        });
	        return this.filterUser(users);
		}
		
		return null;
        
	}

	public List<EuscreenUser> filterUser(List<String> userNames) {
		List<EuscreenUser> users = new ArrayList<EuscreenUser>();
		
		userNames.forEach(userName -> {
			System.out.println("Getting: " + userName);
			
			Document documentVideo = doGet(UrlConstants.USER_BASE_URL + userName + "/video");
			
			if(documentVideo != null){
				System.out.println("Get video");
		        List<Node> videoNodeList = documentVideo.selectNodes("//fsxml/video");
				System.out.println("Video: " + videoNodeList.size());

			}
			
			Document documentPicture = doGet(UrlConstants.USER_BASE_URL + userName + "/picture");
	        
			if(documentPicture != null){
				System.out.println("Get picture");
				List<Node> pictureNodeList = documentPicture.selectNodes("//fsxml/picture");
				System.out.println("Pictures: " + pictureNodeList.size());

			}else {
				System.out.println("KUR");
			}
			
			Document documentSeries = doGet(UrlConstants.USER_BASE_URL + userName + "/series");
			
			if(documentSeries != null){
				System.out.println("Get Series");
		        List<Node> seriesNodeList = documentSeries.selectNodes("//fsxml/series");
				System.out.println("Series: " + seriesNodeList.size());

			}
			
			Document documentAudio = doGet(UrlConstants.USER_BASE_URL + userName + "/audio");
			
			if(documentAudio != null){
				System.out.println("Get Audio");
		        List<Node> audioNodeList = documentAudio.selectNodes("//fsxml/audio");
				System.out.println("Audio: " + audioNodeList.size());

			}
			
			Document documentTeaser = doGet(UrlConstants.USER_BASE_URL + userName + "/teaser");

			if(documentTeaser != null){
				System.out.println("Get Teaser");
		        List<Node> teaserNodeList = documentTeaser.selectNodes("//fsxml/teaser");
				System.out.println("Teaser: " + teaserNodeList.size());

			}
			
			Document documentDoc = doGet(UrlConstants.USER_BASE_URL + userName + "/doc");
			
			if(documentDoc != null){
		        List<Node> docNodeList = documentDoc.selectNodes("//fsxml/doc");
				System.out.println("Docs: " + docNodeList.size());

			}
			
			Document documentCollection = doGet(UrlConstants.USER_BASE_URL + userName + "/collection");
			
			if(documentCollection != null){
		        List<Node> collectioneNodeList = documentCollection.selectNodes("//fsxml/collection");
				System.out.println("Collection: " + collectioneNodeList.size());

			}
	        

		});
		return users;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Document doGet(String url) {
		HttpGet request = new HttpGet(url);
		System.out.println("HttpRequest.doGet(" + url + ")");
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader(BasicScheme.authenticate(
				 new UsernamePasswordCredentials(GeneralConstants.getBartUsername(), GeneralConstants.getBartPassword()),
				 "UTF-8", false));
		
		Document document = null;
		try {
			HttpResponse response = response = this.httpClient.execute(request);
			
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
		}
		
		return document;
	}
	


}
