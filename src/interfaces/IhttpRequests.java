package interfaces;

import java.util.List;

import org.apache.http.HttpResponse;
import org.dom4j.Document;

import models.EuscreenUser;



public interface IhttpRequests {
	List<EuscreenUser> getUsers(String url);
	Document doGet(String url);
	String getThesaurusKeyword(String id, String userName, String type);
	void doPost(String id, String userName, String type, String field, String value);
}
