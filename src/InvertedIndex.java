import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.htree.HTree;
import jdbm.helper.FastIterator;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import org.htmlparser.beans.LinkBean;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;

public class InvertedIndex
{
	private RecordManager recman;
	private HTree hashtable;

	InvertedIndex(String recordmanager, String objectname) throws IOException
	{
		recman = RecordManagerFactory.createRecordManager(recordmanager); //Create a record manager with this name
		long recid = recman.getNamedObject(objectname); //

		if (recid != 0)
			hashtable = HTree.load(recman, recid);
		else
		{
			hashtable = HTree.createInstance(recman);
			recman.setNamedObject( objectname, hashtable.getRecid());
		}
	}


	public void finalize() throws IOException
	{
		recman.commit();
		recman.close();				
	} 

	public void addEntry(String word, String x, int y) throws IOException
	{
		// Add a "docX Y" entry for the key "word" into hashtable
		// ADD YOUR CODES HERE
		Content tempContent=new Content();
		tempContent.docURL=x;
		tempContent.location.add(y);
		ArrayList<Content> tempArrayList;

		if(hashtable.get(word)==null){
			tempArrayList=new ArrayList<Content>();
			tempArrayList.add(tempContent);
		}
		else{			
			boolean edited = false;
			tempArrayList=(ArrayList<Content>)hashtable.get(word);
			for(int i=0; i<tempArrayList.size();i++){
				if(tempArrayList.get(i).docURL.equals(x)){
					//Error handle: repeat appear for a word in same doc same position
					if(tempArrayList.get(i).location.indexOf(y)!=-1){  
						edited=true;
						break;
					}
					//In x.doc, key.freq >1
					else{
						tempArrayList.get(i).location.add(y);
						edited=true;
						break;
					}
				}
			}
			if(!edited){
				tempArrayList.add(tempContent);
			}
		}
		hashtable.put(word,tempArrayList);
	}

	public void  addEntry(String URL, Doc docDetail) throws IOException
	{
		if(hashtable.get(URL)==null){
			hashtable.put(URL,docDetail);
		}
	}

	public void delEntry(String word) throws IOException
	{
		// Delete the word and its list from the hashtable
		// ADD YOUR CODES HERE
		hashtable.remove(word);
	} 

	public void printAll() throws IOException
	{
		// Print all the data in the hashtable
		// ADD YOUR CODES HERE
		FastIterator iter = hashtable.keys();
		String key;
		key=(String)iter.next();
		while(key!=null){
			System.out.print(key+": ");
			ArrayList<Content> tempArrayList=(ArrayList<Content>)hashtable.get(key);
			for(int i=0;i<tempArrayList.size();i++){
				Content tempContent=tempArrayList.get(i);
				System.out.print(tempContent.docURL+" ");
				for(int y=0;y<tempContent.location.size();y++){
					System.out.print(tempContent.location.get(y)+",");	
				}
			}
			System.out.println("");
			key=(String)iter.next();
		}
	}	
	public void printAll_doc() throws IOException
	{
		// Print all the data in the hashtable
		// ADD YOUR CODES HERE
		FastIterator iter = hashtable.keys();
		String key;
		key=(String)iter.next();
		while(key!=null){
			Doc tempDoc = (Doc)hashtable.get(key);
			System.out.println(tempDoc.title);
			System.out.println(key);
			System.out.println(tempDoc.lastModifiedDate+","+tempDoc.size);
			for(int i=0;i<tempDoc.childLink.size();i++){
				System.out.println(tempDoc.childLink.get(i));	
			}
			System.out.println("-------------------------------------------------------------------------------------------");
			key=(String)iter.next();
		}
	}	
	public FastIterator getKeys() throws IOException{
		FastIterator iter = hashtable.keys();
		return iter;
	}
        
        
	public Object getHashtable(String key) throws IOException{
		return hashtable.get(key);
	}
		public static void main(String[] args){  
			ArrayList<Crawler> crawlerList=new ArrayList<Crawler>();
                        StopStem ss = new StopStem();
			try{
				//initialize docs
				Crawler crawler = new Crawler("http://www.cse.ust.hk");//doc1
				crawlerList.add(crawler);
				crawler = new Crawler("http://www.cse.ust.hk/admin/welcome/");//doc2
				crawlerList.add(crawler);
				crawler = new Crawler("http://www.cse.ust.hk/admin/about/");//doc3
				crawlerList.add(crawler);
				crawler = new Crawler("http://www.cse.ust.hk/admin/factsheet/");//doc4
				crawlerList.add(crawler);
				crawler = new Crawler("http://www.cse.ust.hk/News/?type=news|achievement");//doc5
				crawlerList.add(crawler);
				//link to db--ht1,handle word database
				InvertedIndex index = new InvertedIndex("4321phase1","ht1");
				for(int i=0;i<crawlerList.size();i++){
					Crawler tempCrawler = crawlerList.get(i);
					Vector<String> words = tempCrawler.extractWords();
                                        
					for(int position = 0; position < words.size(); position++){
                                            String tempWord = ss.stem(words.get(position).replaceAll(" ", ""));
                                            if(tempWord != '')
                                            if(!ss.isStopWord(tempWord)){
						index.addEntry(tempWord, tempCrawler.geturl(), position);
                                            }else{
                                                System.out.println(words.get(position) + " should be stopped");
                                            }
				}
				}
				index.printAll();
				index.finalize();
				System.out.println("\n\n");
				// link to db--ht2,start handling doc database
				index = new InvertedIndex("4321phase1","ht2");
				for(int i=0;i<crawlerList.size();i++){
					Crawler tempCrawler = crawlerList.get(i);
					//task: add title to crawler class
					String tempTitle="doc"+i;
					//task: add last modifiedDate to crawler class
					String tempLastModifiedDate="today";
					//task: add size
					int tempSize=-1;
					ArrayList <String> tempLinksList=new ArrayList <String>();
					Vector<String> links = tempCrawler.extractLinks();
					for(int linkNo = 0; linkNo < links.size(); linkNo++)	{	
						tempLinksList.add(links.get(linkNo));
					}
					Doc tempDoc = new Doc(i, tempTitle,tempLastModifiedDate,tempSize,tempLinksList);
					index.addEntry(tempCrawler.geturl(), tempDoc);
				}
				index.printAll_doc();
				index.finalize();
				
			}
			catch (ParserException e)
			{
				e.printStackTrace ();
			}
			catch(IOException ex)
			{
				System.err.println(ex.toString());
			}
		}
}