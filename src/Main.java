
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import jdbm.helper.FastIterator;
import java.util.Comparator;

class MyIntComparable implements Comparator<Integer>{

	@Override
	public int compare(Integer o1, Integer o2) {
		return (o1>o2 ? -1 : (o1==o2 ? 0 : 1));
	}
}
public class Main {
	public static void main(String[] args){  
		try{
			//link to db--ht1,handle word database
			InvertedIndex indexht2 = new InvertedIndex("4321phase1","ht2");
			FastIterator iterht2 = indexht2.getKeys();
			String keyht2;
			keyht2=(String)iterht2.next();
			while(keyht2!=null){
				Doc tempDoc=(Doc)indexht2.getHashtable(keyht2);
				System.out.println(tempDoc.title);
				System.out.println(keyht2);
				System.out.println(tempDoc.lastModifiedDate+","+tempDoc.size);
				try{
					InvertedIndex indexht1 = new InvertedIndex("4321phase1","ht1");
					FastIterator iterht1 = indexht1.getKeys();
					String keyht1;
					keyht1=(String)iterht1.next();
					ArrayList<Integer> tempFreq=new ArrayList<Integer> ();
					ArrayList<String> tempWordList=new ArrayList<String> ();
					while(keyht1!=null){
						ArrayList<Content> tempContentList=(ArrayList<Content>)indexht1.getHashtable(keyht1);
						for(int a=0;a<tempContentList.size();a++){
							//							System.out.print("web1:"+tempContentList.get(a).docURL+"\n"+"web2:"+keyht2+"\n");
							if(tempContentList.get(a).docURL.equals(keyht2)){
								tempWordList.add(keyht1);
								tempFreq.add(tempContentList.get(a).location.size());
								System.out.print(keyht1+" "+tempContentList.get(a).location.size()+"; ");
							}
						}
						//						Collections.sort(tempFreq, new MyIntComparable());
						//						int lastFreq;
						//						int count=0;
						//						for(int a=0;a<tempFreq.size();a++){
						//							System.out.print(tempFreq.get(a));
						//						}
						//						System.out.println("tempFreqsize="+tempFreq.size());
						keyht1=(String)iterht1.next();
					}
					System.out.print("\n");
					Collections.sort(tempFreq, new MyIntComparable());
					int count=1;
					int lastFreq=tempFreq.get(0);
					ArrayList<Integer>tempFreqList=new ArrayList<Integer>();
					tempFreqList.add(lastFreq);
					for(int i=0;i<tempFreq.size();i++){
						if(lastFreq>tempFreq.get(i)){
							lastFreq=tempFreq.get(i);
							tempFreqList.add(tempFreq.get(i));
							count++;
						}
						if(count==5){
							break;
						}
					}
					for(int i=0;i<tempFreqList.size();i++){
						for(int a=0;a<tempFreq.size();a++){
							if(tempFreq.get(a)==tempFreqList.get(i)){
								System.out.print(tempWordList.get(a)+" "+tempFreq.get(a)+";");
							}
						}
					}
					
					System.out.print("\n");
					for(int a=0;a<tempDoc.childLink.size();a++){
						System.out.println(tempDoc.childLink.get(a));
					}
					indexht1.finalize();
				}
				catch(IOException ex)
				{
					System.err.println(ex.toString());
				}
				keyht2=(String)iterht2.next();
				System.out.println("-------------------------------------------------------------------------------------------");
			}
			indexht2.finalize();
		}
		catch(IOException ex)
		{
			System.err.println(ex.toString());
		}
	}
}
