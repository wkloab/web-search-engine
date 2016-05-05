package IRUtilities;


import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.htree.HTree;
import jdbm.helper.FastIterator;
import java.util.Vector;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.text.html.parser.Parser;

class Property implements Serializable {
	public String title;
	public String url;
	public String lastModifiedDate;
	public int size;

	Property(String title, String url, String lastModifyDate, int size){
		this.title = title;
		this.url = url;
		this.lastModifiedDate = lastModifyDate;
		this.size = size;
	}
}

public class PageInfo {
	private RecordManager recman;
	private HTree hashtable;

	public PageInfo(RecordManager recman, String objectname) throws IOException {
		this.recman = recman;
		long recid = recman.getNamedObject(objectname);

		if (recid != 0) {
			hashtable = HTree.load(recman, recid);
		}
		else 
		{
			hashtable = HTree.createInstance(recman);
			recman.setNamedObject(objectname, hashtable.getRecid());
		}
	}
	
	public boolean isContain(String word) throws IOException{
		return hashtable.get(word)!=null;
	}
	
	public void finalize() throws IOException {
		recman.commit();
		recman.close();
	}
	
	public void addEntry(String key, String title, String url, String lastModifyDate, int size) throws IOException {
		if (hashtable.get(key) != null){
			return;
		}
		hashtable.put(key, new Property(title, url, lastModifyDate, size));
	}
	
	public String getTitle(String key) throws IOException{
		Property temp = (Property)hashtable.get(key);
		return temp.title;
	}
	
	public String getUrl(String key) throws IOException{
		Property temp = (Property)hashtable.get(key);
		return temp.url;
	}
	
	public String getLastDate(String key) throws IOException{
		Property temp = (Property)hashtable.get(key);
		return temp.lastModifiedDate;
	}
	
	public int getPageSize(String key) throws IOException{
		Property temp = (Property)hashtable.get(key);
		return temp.size;
	}
	
	public void update(String key, String title, String url, String lastModifyDate, int size) throws IOException{
		if (hashtable.get(key) == null){
			return;
		}
		Property temp = (Property)hashtable.get(key);
		temp.title = title;
		temp.url = url;
		temp.lastModifiedDate = lastModifyDate;
		temp.size = size;
	}
	
	public void delEntry(String word) throws IOException {
		hashtable.remove(word);
	}
	
	public void printAll() throws IOException {
		FastIterator iter = hashtable.keys();
		String key;
		while ((key = (String) iter.next()) != null) {
			Property temp = (Property)hashtable.get(key);
			System.out.println(key + ": " + temp.title+","+temp.url+","+temp.lastModifiedDate+","+temp.size);
		}
	}
	
}
