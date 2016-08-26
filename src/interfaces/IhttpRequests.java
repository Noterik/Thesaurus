package interfaces;

import java.util.List;

import org.apache.http.HttpResponse;
import org.dom4j.Document;

import models.EuscreenUser;



public interface IhttpRequests {
	List<EuscreenUser> getUsers(String url);
	Document doGet(String url);
}
