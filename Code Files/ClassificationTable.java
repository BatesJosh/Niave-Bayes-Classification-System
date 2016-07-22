import java.util.ArrayList;
import java.util.HashMap;

public class ClassificationTable
{
	//keeps track of the data
   ArrayList<ArrayList<ArrayList<Integer>>> table;
   //the different attributes for the classification
    ArrayList<Attribute> attributes;
	//The different types of classification
    ArrayList<String> classification;
	//the classification map used for quick look up.
    HashMap<String, Integer> classMap;
	//the number of samples used.
    int samplesize;
	//the classification totals used for probability 
    ArrayList<Integer> classtotals;

	/**
	* Takes in a list of meta data and sets up for training.
	* @param data
	*/
    ClassificationTable(ArrayList<ArrayList<String>> data)
	{
	    if(data.size()>0)
		{
		    attributes=new ArrayList<>();
		    for(int i=0; i<data.size()-1; i++)
			{
			    ArrayList<String> attrdata=data.get(i);
			    Attribute attr=new Attribute(attrdata);
			    attributes.add(attr);
			}
		    ArrayList<String> classArr=data.get(data.size()-1);
		    samplesize=0;
		    classification=new ArrayList<>();
		    classMap=new HashMap<>();
		    table=new ArrayList<>();
		    classtotals=new ArrayList<>();
		    for(int i=0; i<classArr.size(); i++)
			{
			    classification.add(classArr.get(i));
			    classMap.put(classArr.get(i), i);
			    classtotals.add(0);
			}
		    for(int i=0; i<attributes.size(); i++)
			{
			    ArrayList<ArrayList<Integer>> arr1=new ArrayList<>();
			    for(int j=0; j< attributes.get(i).size(); j++)
				{
				    ArrayList<Integer> arr2=new ArrayList<>();
				    for(int k=0; k< classArr.size(); k++)
					{
					    arr2.add(0);
					}
				    arr1.add(arr2);

				}
			    table.add(arr1);
			}
		}
	}
    
	/**
	* Takes in list of data to train
	* @param list
	*
	*/
    public void train(ArrayList<ArrayList<String>> list)
    {
		// Checks to see if training data has already been used.
		if(add.size()>0)
		{
			add=new ArrayList<>();
		}
	for(int i=0; i<list.size(); i++)
	    {
		ArrayList<String> add=list.get(i);
		add(add);
	    }
    }

	//Private method to add to array
    private void add(ArrayList<String> toadd)
    {
	if(toadd.size() == attributes.size()+1)
	    {
		samplesize++;
		int classIndex=classMap.get(toadd.get(toadd.size()-1));
		int numclass=classtotals.get(classIndex);
		classtotals.set(classIndex, numclass+1);    
		for(int i=0; i<attributes.size(); i++)
		    {
			String curr=toadd.get(i);
			
			add(i, curr, classIndex);
		    }
	    }
    }
	
	//private helper method to add attributes to
    private void add(int attribute, String value, int classIndex)
    {
       
	int valueNum=attributes.get(attribute).getAttributeIndex(value);
	if(valueNum!=-1)
	    {
		int update=table.get(attribute).get(valueNum).get(classIndex);
		table.get(attribute).get(valueNum).set(classIndex, update+1);
	    }
    }

	/**
	* Takes in a list of data and checks the accuracy of data and returns the accuracy.
	* @param arr	
	* @return
	*/
    public double accuracy(ArrayList<ArrayList<String>> arr)
    {
	int total=0;
	int correct=0;
	for(int i=0; i<arr.size(); i++)
	    {
		ArrayList<String> curr=arr.get(i);
		String hypothesis=classify(curr);
		if(curr.size()>attributes.size())
		    {
			if(hypothesis.trim().equalsIgnoreCase(curr.get(attributes.size()).trim()))
			   {
			       correct++;
			   }
			   total++;
		    }
	
	    }
	double accuracy=(double) correct/total;
	return accuracy;
    }
	
	/**
	* Takes in a list of data and classifies the data based of the table and returns the classification.
	* @arr the data being taken in
	* @return the classification for the data. The index of the original data responds to the classification for each data.
	*/
    public ArrayList<ArrayList<String>> classifyMany(ArrayList<ArrayList<String>> arr)
    {
	for(int i=0; i<arr.size(); i++)
	    {
		ArrayList<String> curr=arr.get(i);
		String hypothesis=classify(curr);
		if(curr.size()==attributes.size())
		    {
			curr.add(hypothesis);
		    }
		else
		    {
			curr.set(curr.size()-1, hypothesis);
		    }

	    }
	return arr;
    }

	/**
	* Takes in one data and classifies the data.
	* @param arr the data being classified
	* @return the classification
	*/
    public String classify(ArrayList<String> arr)
    {
	double[] probClass=new double[classification.size()];
	for(int i=0; i<probClass.length ;i++)
	    {
		probClass[i]=getHypothesis(i);
	    }
	for(int i=0; i<attributes.size(); i++)
	    {
		int valueIndex=attributes.get(i).getAttributeIndex(arr.get(i));
		for(int k=0; k<classification.size();k++)
		    {
			probClass[k]=probClass[k]*getProbability(i,valueIndex,k); 
		    }
	    }

	double sum=0;
	for(int i=0; i<probClass.length; i++)
	    {
		sum=sum+probClass[i];
	    }
	for(int i=0; i<probClass.length; i++)
	    {
		probClass[i]=probClass[i]/sum;
	    }
	int highest=0;
	for(int i=1; i<probClass.length; i++)
	    {
		if(probClass[highest]<probClass[i])
		    {
			highest=i;
		    }
	    }
    return classification.get(highest);
}

	//helper method to get the probability
    private double getProbability(int attrNum, int attrName, int classIndex)
    {
	double prob=(double)((double)getCount(attrNum, attrName, classIndex)+1)/((double)classtotals.get(classIndex)+classtotals.size());
	return prob;
    }

	//helper method to get the count from the table.
    private int getCount(int attrNum, int attrName, int classIndex)
    {
	return table.get(attrNum).get(attrName).get(classIndex);
    }
	//helper method to get individual probability for each classification.
    private double getHypothesis(int classIndex)
    {
	double hypothesis=(double)( classtotals.get(classIndex)+1)/(samplesize+classification.size());
	return hypothesis;
    }
}
