import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
/**
 * Main file to run classification.
 *
 */
public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
	Scanner in=new Scanner(System.in);
	String str="train";
	ClassificationTable classify=null;
	while(!str.equalsIgnoreCase("exit"))
	    {
		if(str.equalsIgnoreCase("train"))
		   {
		       System.out.println("Please give a Meta file and training file  separated by a comma(Example: meta,train) to setup Classification System:");
		       str=in.nextLine();
		       String[] files=str.split(",");
		       try{
			   ArrayList<ArrayList<String>> meta=MyFileReader.inputMetaFile(files[0].trim());
			   ArrayList<ArrayList<String>> train=MyFileReader.inputFile(files[1].trim());
			   classify=new ClassificationTable(meta);
			   classify.train(train);
		       }catch(Exception e)
			   {
			       System.out.println("Unable to find file");
			   }
		   }else if(str.equalsIgnoreCase("classify") && classify!=null)
		    {
			System.out.println("Please enter a file to classify and output file (ex: classify,output)");
			str=in.nextLine();
			String[] files=str.split(",");
		       try{
			   String input=files[0].trim();
			  String output=files[1].trim();
			   MyFileReader.write(classify.classifyMany(MyFileReader.inputFile(input)), output);
		       }catch(Exception e)
			   {
			       System.out.println("Unable to find file");
			   }
		    }else if(str.equalsIgnoreCase("accuracy") && classify!=null)
		    {
			System.out.println("Please enter a file to test accuracy:");
			str=in.nextLine();
		       try{
			   System.out.println("Accuracy of system:"+ classify.accuracy(MyFileReader.inputFile(str.trim())));
		       }catch(Exception e)
			   {
			       System.out.println("Unable to find file");
			   }
		    }else if(str.equalsIgnoreCase("exit"))
		    {
			in.close();
			System.exit(0);
		    }else
		    {
			System.out.println("Please enter train, classify, accuracy, or exit");
			str=in.nextLine();
		    }
			   

	}
 in.close();

    }


}
