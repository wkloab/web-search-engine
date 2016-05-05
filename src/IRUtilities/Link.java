package IRUtilities;

import java.util.Collections;
import java.util.Vector;

public class Link implements Comparable<Link>{
	private double score;
	private String title;
	private String url;
	private String lastModified;
	private int pageSize;
	private Vector<String> parentLinks;
	private Vector<String> childLinks;
	private Vector<Word> keywords;
	
	public int compareTo(Link a) {
	       //return either 1, 0, or -1
	       //that you compare between this object and object a
		double temp = a.getScore() - score;
		if(temp < 0.0){
			return -1;
		}else if(temp > 0.0){
			return 1;
		}else{
			return 0;
		}
	}
	
	public String toString(){
		String result = score+"\n";
		result += title+"\n";
		result += url+"\n";
		result += lastModified+":"+pageSize+"\n";
		for(int i = 0; i < keywords.size(); i++){
			Word temp = keywords.elementAt(i);
			result += temp.getText()+" "+temp.getFreq()+"; ";
		}
		result+="\n";
		for(int i = 0; i < parentLinks.size(); i++){
			result += parentLinks.elementAt(i)+"\n";
		}
		for(int i = 0; i < childLinks.size(); i++){
			result += childLinks.elementAt(i)+"\n";
		}
		return result+"\n";
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastUpdate) {
		this.lastModified = lastUpdate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Link(){
		parentLinks = new Vector<String>();
		childLinks = new Vector<String>();
		keywords = new Vector<Word>();
	}
	
	public void setScore(double score){
		this.score = score;
	}
	
	public double getScore(){
		return score;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void addParentLink(String link){
		parentLinks.add(link);
	}
	
	public Vector<String> getParentLink(){
		return parentLinks;
	}
	
	public void addChildLink(String link){
		childLinks.add(link);
	}
	
	public Vector<String> getChildLink(){
		return childLinks;
	}

	public void addKeyword(Word keyword){
		keywords.add(keyword);
	}
	
	public Vector<Word> getKeywords(){
		return keywords;
	}
	
	public void sortKeyword(){
		Collections.sort(keywords);
	}
}
