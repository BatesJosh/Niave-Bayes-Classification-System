import java.util.HashMap;
import java.util.ArrayList;

/**
 * This class represents an attribute.
 *
 */
public class Attribute
{
    private String name;
    private HashMap<String, Integer> attributeMap;
    private int numValues;

	/**
	* Constructor for Attribute that takes in an arraylist of data. 
	* The first item is the name of the attribute. The rest are the values.
	*
	* @param data
	*/
    public Attribute(ArrayList<String> data)
    {
	if(data.size()>1)
	    {
		name=data.get(0);
		attributeMap=new HashMap<>();
		numValues=data.size()-1;
		for(int i=1; i<= numValues; i++)
		    {
			attributeMap.put( data.get(i), i-1);
		    }
	    }
    }

	/**
	* Returns the name of the attribute
	*
	* @return
	*/
    public String getName()
    {
		return name;
    }

	/**
	* Returns the index of value or -1 if not found
	*
	* @param name of value
	* @return
	*/
    public int getAttributeIndex(String str)
    {
        if(attributeMap.containsKey(str))
	   {
		return attributeMap.get(str);
	    }
	else
	    {
		return -1;
	    }
    }

	/**
	* Returns the number of values the attribute has
	* @return
	*/
    public int size()
    {
	return numValues;
    }
}
