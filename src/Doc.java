
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RickyLo
 */
class Doc implements Serializable{
	public String title;
	public String lastModifiedDate;
	public int size=-1;
	public ArrayList<String> childLink;

	Doc(String titlex, String lastModifiedDatex, int sizex, ArrayList<String> childLinkx){
		title=titlex;
		lastModifiedDate=lastModifiedDatex;
		size=sizex;
		childLink=new ArrayList<String>();
		for(int i=0;i<childLinkx.size();i++){
			childLink.add(childLinkx.get(i));
		}
	}
	Doc(String titlex, String lastModifiedDatex, int sizex){
		title=titlex;
		lastModifiedDate=lastModifiedDatex;
		size=sizex;
	}
	public void addChildLink(String childLinkx){
		childLink.add(childLinkx);
	}
}
