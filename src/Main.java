

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jdbm.helper.FastIterator;

public class Main {
	public static void main(String[] args){  
		try{    
                    File file = new File ("spider_result.txt");
                    PrintWriter printWriter = new PrintWriter ("spider_result.txt");
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
                                                System.out.println("List");
						ArrayList<Content> tempContentList=(ArrayList<Content>)indexht1.getHashtable(keyht1);
                                                ArrayList<KeySize> keySizeList = new ArrayList<KeySize>();
						for(int a=0;a<tempContentList.size();a++){
//							printWriter.print("web1:"+tempContentList.get(a).docURL+"\n"+"web2:"+keyht2+"\n");
							if(tempContentList.get(a).docURL.equals(keyht2)){
                                                                
								keySizeList.add(new KeySize(keyht1, tempContentList.get(a).location.size()));
							}
						}
                                                Comparator<KeySize> comparator = new Comparator<KeySize>() {
                                                
                                                    @Override
                                                    public int compare(KeySize o1, KeySize o2) {
                                                        return new Integer(o1.getSize()).compareTo(o2.getSize()); //To change body of generated methods, choose Tools | Templates.
                                                    }
                                                };
                                                Collections.sort(keySizeList, comparator);
                                                
                                                for(int a=0;a< keySizeList.size();a++){
                                                    
                                                    System.out.println(keySizeList.get(a).getSize());
                                                    printWriter.print(keySizeList.get(a).getKey()+" "+keySizeList.get(a).getSize()+"; ");
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
