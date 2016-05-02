package IRUtilities;


import jdbm.RecordManager;
import jdbm.htree.HTree;
import jdbm.helper.FastIterator;
import java.io.IOException;
import java.io.Serializable;


class Posting implements Serializable {
	public String doc;
	public int freq;

	Posting(String doc, int freq) {
		this.doc = doc;
		this.freq = freq;
	}
}

public class InvertedIndex {
	private RecordManager recman;
	private HTree hashtable;

	public InvertedIndex(RecordManager recman1, String objectname) throws IOException {
		recman = recman1;
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

	public void finalize() throws IOException {
		recman.commit();
		recman.close();
	}

	public void addEntry(String word, int x, int y) throws IOException {
		
		if (hashtable.get(word)!=null && ((String) hashtable.get(word)).contains( "doc" + x+ " " + y))
		{
			return;
		}
		String new_entry = x + ":" + y + " ";
	
		String existed_entry = "";
		if (hashtable.get(word) != null) {
			existed_entry = (String) hashtable.get(word);
		}

		hashtable.put(word, existed_entry + new_entry);

	}
	
	public String getValue(String index) throws IOException{
		if(hashtable.get(index) == null){
			return "-1";
		}
		return String.valueOf(hashtable.get(index));
	}
	
	public void addEntry2(String word, String value) throws IOException {

		
		if (hashtable.get(word)!=null && ((String) hashtable.get(word)).contains(value))
		{
			return;
		}
		String new_entry = value + " ";

		String existed_entry = "";
		if (hashtable.get(word) != null) {
			existed_entry = (String) hashtable.get(word);
		}

		hashtable.put(word, existed_entry + new_entry);

	}

	public void delEntry(String word) throws IOException {
		
		hashtable.remove(word);
	}
	
	public int numOfElement(String word) throws IOException{
		if (hashtable.get(word) != null) {
			String existed_entry = (String) hashtable.get(word);
			String[] temp = existed_entry.split(" ");
			return temp.length;
		}
		return 0;
	}
	
	public String getElement(String word, int index) throws IOException{
		if (hashtable.get(word) != null) {
			String existed_entry = (String) hashtable.get(word);
			String[] temp = existed_entry.split(" ");
			return temp[index];
		}
		return null;
	}
	
	public void delElement(String word, String new_element) throws IOException{
		String existed_entry = "";
		if (hashtable.get(word) != null) {
			existed_entry = (String) hashtable.get(word);
		}
		String newstr = existed_entry.replaceAll("\\s["+new_element+"]*\\s", " ");
		hashtable.put(word, newstr);
	}
	
	public FastIterator AllKey() throws IOException{
		return hashtable.keys();
	}

	public void printAll() throws IOException {
		
		FastIterator iter = hashtable.keys();
		String key;
		int num = 0;
		while ((key = (String) iter.next()) != null) {
			System.out.println(key + ": " + hashtable.get(key));
			num++;
		}
	}
}