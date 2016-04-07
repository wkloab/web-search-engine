/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RickyLo
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Vector;
import org.htmlparser.beans.StringBean;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import java.util.StringTokenizer;
import org.htmlparser.beans.LinkBean;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;


public class Crawler
{
	private String url;
	Crawler(String _url)
	{
		url = _url;
	}
        
        public String geturl()
        {
            return this.url;
        }
        
	public Vector<String> extractWords() throws ParserException

	{
            // extract words in url and return them
            // use StringTokenizer to tokenize the result from StringBean
            // ADD YOUR CODES HERE
            StringBean sb = new StringBean ();
            sb.setLinks (false);
            sb.setReplaceNonBreakingSpaces (true);
            sb.setCollapse (true);
            sb.setURL (this.url); // the HTTP is performed here
            String s = sb.getStrings ();
//          System.out.println(s);
            Vector<String> words = new Vector<String>(0);
            StringTokenizer st = new StringTokenizer (s);
            while(st.hasMoreTokens()) {
                words.addElement(st.nextToken());       
            }
            return words;  
	}
	public Vector<String> extractLinks() throws ParserException

	{
            // extract links in url and return them
            // ADD YOUR CODES HERE
            Vector<String> links = new Vector<String>();
            LinkBean linkBean = new LinkBean();  
            linkBean.setURL(this.url);
            String strUrlFilters=this.url;
            URL[] urls = linkBean.getLinks();  
            for (int i = 0; i < urls.length; i++) { 
                String url=urls[i].toString();
                String[] UrlFilters=strUrlFilters.trim().split(",");
                for (String urlFilter : UrlFilters)
                    if(url.startsWith(urlFilter)) links.add(url);
            }
            return links;
	}
        
        public int getSize() throws MalformedURLException, IOException

	{
            // extract links in url and return them
            // ADD YOUR CODES HERE
            HttpURLConnection content = (HttpURLConnection) new URL(this.url).openConnection();
            content .setRequestProperty("Accept-Encoding", "identity"); 
            content.connect();
            int length = content.getContentLength();
            return length;
	}
        
        public Date getLastModified() throws MalformedURLException, IOException

	{
            // extract links in url and return them
            // ADD YOUR CODES HERE
            HttpURLConnection content = (HttpURLConnection) new URL(this.url).openConnection();
            content.connect();
            Date date = new Date(content.getLastModified());
            return date;
	}
        
	public static void main (String[] args) throws MalformedURLException, IOException
	{
            try
            {
                Crawler crawler = new Crawler("http://www.cse.ust.hk");

//                Enumeration e=links.elements();
//                while (e.hasMoreElements()) {         
//                    System.out.println("link :" + e.nextElement());
//                }  
//                        Enumeration e=words.elements();
//                        while (e.hasMoreElements()) {         
//                            System.out.println("token :" + e.nextElement());
//                        }
                int length = crawler.getSize();
                System.out.println("Size of page: "+length);
                
                Date date = crawler.getLastModified();
                System.out.println("Last modified: "+date); 
                
                Vector<String> words = crawler.extractWords();		

                System.out.println("Words in "+crawler.geturl()+":");
                for(int i = 0; i < words.size(); i++)
                        System.out.print(words.get(i)+" ");
                System.out.println("\n\n");

                Vector<String> links = crawler.extractLinks();
                System.out.println("Links in "+crawler.geturl()+":");
                for(int i = 0; i < links.size(); i++)		
                        System.out.println(links.get(i));
                System.out.println("");

            }
            catch (ParserException e)
            {
                    e.printStackTrace ();
            }

	}
}
