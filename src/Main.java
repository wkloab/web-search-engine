

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import jdbm.helper.FastIterator;

public class Main {
	public static void main(String[] args){  
		try{    
                    File file = new File ("file.txt");
                    PrintWriter printWriter = new PrintWriter ("file.txt");
			//link to db--ht1,handle word database
			InvertedIndex indexht2 = new InvertedIndex("4321phase1","ht2");
			FastIterator iterht2 = indexht2.getKeys();
			String keyht2;
			keyht2=(String)iterht2.next();
			while(keyht2!=null){
				Doc tempDoc=(Doc)indexht2.getHashtable(keyht2);
				printWriter.println(tempDoc.title);
				printWriter.println(keyht2);
				printWriter.println(tempDoc.lastModifiedDate+","+tempDoc.size);
				try{
					InvertedIndex indexht1 = new InvertedIndex("4321phase1","ht1");
					FastIterator iterht1 = indexht1.getKeys();
					String keyht1;
					keyht1=(String)iterht1.next();
					while(keyht1!=null){
						ArrayList<Content> tempContentList=(ArrayList<Content>)indexht1.getHashtable(keyht1);
						for(int a=0;a<tempContentList.size();a++){
//							printWriter.print("web1:"+tempContentList.get(a).docURL+"\n"+"web2:"+keyht2+"\n");
							if(tempContentList.get(a).docURL.equals(keyht2)){
								printWriter.print(keyht1+" "+tempContentList.get(a).location.size()+"; ");
							}
						}
						keyht1=(String)iterht1.next();
					}
					printWriter.print("\n\n");
					for(int a=0;a<tempDoc.childLink.size();a++){
						printWriter.println(tempDoc.childLink.get(a));
					}
					indexht1.finalize();
				}
				catch(IOException ex)
				{
					System.err.println(ex.toString());
				}
				keyht2=(String)iterht2.next();
				printWriter.println("-------------------------------------------------------------------------------------------");
			}
			indexht2.finalize();
                        printWriter.close();
		}
		catch(IOException ex)
		{
			System.err.println(ex.toString());
		}
	}
}
