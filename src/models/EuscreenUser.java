package models;

import java.util.List;

public class EuscreenUser {
	private String username;
	private List<String> videoIds;
	private List<String> pictureIds;
	private List<String> seriesIds;
	private List<String> audioIds;
	private List<String> teaserIds;
	private List<String> docIds;
	private List<String> collectionIds;
	
	public String getUsername() {
		return username;
	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
	public List<String> getVideoIds() {
		return videoIds;
	}
	public void setVideoIds(List<String> videoIds) {
		this.videoIds = videoIds;
	}
	public List<String> getPictureIds() {
		return pictureIds;
	}
	public void setPictureIds(List<String> pictureIds) {
		this.pictureIds = pictureIds;
	}
	public List<String> getSeriesIds() {
		return seriesIds;
	}
	public void setSeriesIds(List<String> seriesIds) {
		this.seriesIds = seriesIds;
	}
	public List<String> getAudioIds() {
		return audioIds;
	}
	public void setAudioIds(List<String> audioIds) {
		this.audioIds = audioIds;
	}
	public List<String> getTeaserIds() {
		return teaserIds;
	}
	public void setTeaserIds(List<String> teaserIds) {
		this.teaserIds = teaserIds;
	}
	public List<String> getDocIds() {
		return docIds;
	}
	public void setDocIds(List<String> docIds) {
		this.docIds = docIds;
	}
	public List<String> getCollectionIds() {
		return collectionIds;
	}
	public void setCollectionIds(List<String> collectionIds) {
		this.collectionIds = collectionIds;
	}
	
	public EuscreenUser(String username) {
		this.username = username;
	}
	
}
