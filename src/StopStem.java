import java.io.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StopStem
{
	private Porter porter;
	private java.util.HashSet stopWords;
	public boolean isStopWord(String str)
	{
		return stopWords.contains(str);	
	}
	public StopStem()
	{
		super();
		porter = new Porter();
		stopWords = new java.util.HashSet();
				
		String line;
                try (
                    InputStream fis = new FileInputStream("stopwords.txt");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    
                ) {
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                        stopWords.add(line);
                    }
                } catch (IOException ex) {
                Logger.getLogger(StopStem.class.getName()).log(Level.SEVERE, null, ex);
            }

		
	}
        
	public String stem(String str)
	{
		return porter.stripAffixes(str);
	}
	public static void main(String[] arg)
	{
		StopStem stopStem = new StopStem();
		String input="";
		try{
			do
			{
				System.out.print("Please enter a single English word: ");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				input = in.readLine();
				if(input.length()>0)
				{	
					if (stopStem.isStopWord(input))
						System.out.println("It should be stopped");
					else
			   			System.out.println("The stem of it is \"" + stopStem.stem(input)+"\"");
				}
			}
			while(input.length()>0);
		}
		catch(IOException ioe)
		{
			System.err.println(ioe.toString());
		}
	}
}