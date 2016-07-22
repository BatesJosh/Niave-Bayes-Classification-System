
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * This reads in files and out prints files to be used with the classification system
 * @author jabates
 */
public class MyFileReader {
   
	/*
	* Takes in a meta file and outputs an arraylist of arraylist of strings. This method
	* is used with the constructor for ClassificationTable.
	* @param filename
	* @returns 
	*/
    public static ArrayList<ArrayList<String>> inputMetaFile(String filename)
    {
        return inputFile(filename, 0);
    }
    
	/*
	* This file takes in files and outputs an arraylist of arraylist of strings.
	* This method is used with the ClassificationTable.train() method.
	* @param filename
	* @returns
	*/
    public static ArrayList<ArrayList<String>> inputFile(String filename)
    {
        return inputFile(filename, 1);
    }
    
	// Private method to read in files
    private static ArrayList<ArrayList<String>> inputFile(String filename, int num){
                ArrayList<ArrayList<String>> attr=new ArrayList<>();
		try{
                    BufferedReader br=new BufferedReader(new java.io.FileReader(filename));
			String line=br.readLine();
			while(line!=null)
			{
				attr.add(parseLine(line, num));
				line=br.readLine();
			}
                    
                }catch(IOException e){
			System.err.println("Caught IOException: " +  e.getMessage());
                        
		}
                return attr;
    }

	//private method to read lines
    private static ArrayList<String> parseLine(String line, int num) {
        ArrayList<String> attr=new ArrayList<>();
        String str="";
        int index, eIndex;
        if(num==0){
            index=line.indexOf(":");
           str=line.substring(0, index);
            attr.add(str);
            index++;
        }
        else
        {
            index=0;
            
        }
       // attr.add(str);
        eIndex=line.indexOf(",");
        while(eIndex!=-1)
        {
            str=line.substring(index, eIndex);
            attr.add(str);
            index=eIndex+1;
            eIndex=line.indexOf(",", index);
        }
        if(eIndex==-1 && index!=-1)
        {
            str=line.substring(index);
            attr.add(str);
        }
        return attr; 
    }
    
	/**
	* Method that takes in arraylist and writes the data to a filename.
	* @param arr
	* @param filename
	*/
    public static void write(ArrayList<ArrayList<String>> arr, String filename) throws FileNotFoundException
    {
        try
        {
            PrintWriter writer = new PrintWriter(filename);
            for(int i=0; i<arr.size(); i++)
            {
                for(int j=0; j < arr.get(i).size(); j++)
                {
                    writer.print(arr.get(i).get(j));
                    if(j<arr.get(i).size()-1)
                    {
                        writer.print(",");
                    }
                }
                 if(i<arr.size()-1)
                    {
                        writer.print("\n");
                    }
            }
            writer.close();
        }catch(FileNotFoundException e)
        {
            System.out.println(e);
        }
    }
}
