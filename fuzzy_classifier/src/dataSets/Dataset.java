package dataSets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a Dataset. 
 */

public class Dataset 
{
	private String name;
	private Integer size;
	private List<Double> variance;
	private List<Double> skewness;
	private List<Double> curtosis;
	private List<Double> entropy;
	private List<Boolean> output;
	
	public Dataset(String name) 
	{
		this.name = name;
		this.size = 0;
		this.variance = new ArrayList<Double>();
		this.skewness = new ArrayList<Double>();
		this.curtosis = new ArrayList<Double>();
		this.entropy = new ArrayList<Double>();
		this.output = new ArrayList<Boolean>();	
	}
	
	/**
	 * Load a dataset from a file.
	 * @param fileName: name of file
	 */
	public void loadDatasetFromFile(String fileName)
	{
		File file = new File(fileName);
		BufferedReader reader = null;
		
		try 
		{
		    reader = new BufferedReader(new FileReader(file));
		    String line = null;

		    while ((line = reader.readLine()) != null)
		    {
		    	String fields[] = line.split(",");	
		    	double variance = Double.parseDouble(fields[0]);
		    	double skewness = Double.parseDouble(fields[1]);
		    	double curtosis = Double.parseDouble(fields[2]);
		    	double entropy = Double.parseDouble(fields[3]);
		    	
		    	if(Integer.parseInt(fields[4]) == 1)
		    		this.addEntry(variance, skewness, curtosis, entropy, true);	
		    	else
		    		this.addEntry(variance, skewness, curtosis, entropy, false);
		    }
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		    e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		    e.printStackTrace();
		} 
		finally 
		{
		    try 
		    {
		        if (reader != null) 		        
		            reader.close();		        
		    } 
		    catch (IOException e) 
		    {
		    	System.out.println(e.getMessage());
			    e.printStackTrace();
		    }
		}
	}
	
	/**
	 * Write a new file with the data from dataset.
	 */
	public void save()
	{
		try 
		{
			FileWriter file = new FileWriter("../input_analysis/datasets/" + this.getName() + ".txt");
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < this.getOutput().size(); i++)
			{
				sb.append(this.getVariance().get(i)).append(",");
				sb.append(this.getSkewness().get(i)).append(",");
				sb.append(this.getCurtosis().get(i)).append(",");
				sb.append(this.getEntropy().get(i)).append(",");
				
				if (this.getOutput().get(i))
					sb.append(1).append("\n");
				else
					sb.append(0).append("\n");
			}
			
			file.write(sb.toString());
			file.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Error to create the file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new entry into dataset.
	 * @param variance: value of variance
	 * @param skewness: value  of skewness
	 * @param curtosis: value of curtosis
	 * @param entropy: value of entropy
	 * @param output: a boolean value, class of the money bill.
	 */
	public void addEntry(double variance, double skewness, double curtosis, double entropy, boolean output)
	{
		this.variance.add(variance);
		this.skewness.add(skewness);
		this.curtosis.add(curtosis);
		this.entropy.add(entropy);
		this.output.add(output);
		this.size++;
	}
	
	/**
	 * Get an input column from dataset.
	 * @param attribute: attribute used to select the column.
	 * @return corresponding column for selected attribute.
	 */
	private List<Double> getInputColumn(MoneyProperty attribute)
	{
		switch(attribute)
		{
			case VARIANCE:
				return this.variance;
			
			case SKEWNESS:
				return this.skewness;
				
			case CURTOSIS:
				return this.curtosis;
			
			case ENTROPY:
				return this.entropy;
				
			default:
				throw new Error("Informed attribute is not valid: " + attribute);
		}
	}
	
	/**
	 * @return all entries that active the output
	 */
	public List<Double> getTrueEntries(MoneyProperty attribute)
	{
		List<Double> values = getInputColumn(attribute);
		List<Double> entries = new ArrayList<Double>();
		
		for(int i = 0; i < values.size(); i++)
		{
			if(this.output.get(i))
				entries.add(values.get(i));
		}
		return entries;
	}
	
	/**
	 * @return all entries that not active the output
	 */
	public List<Double> getFalseEntries(MoneyProperty attribute)
	{
		List<Double> values = getInputColumn(attribute);
		List<Double> entries = new ArrayList<Double>();
		
		for(int i = 0; i < values.size(); i++)
		{
			if(!this.output.get(i))
				entries.add(values.get(i));
		}
		return entries;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public List<Double> getVariance() 
	{
		return variance;
	}

	public List<Double> getSkewness() 
	{
		return skewness;
	}

	public List<Double> getCurtosis() 
	{
		return curtosis;
	}

	public List<Double> getEntropy() 
	{
		return entropy;
	}

	public List<Boolean> getOutput() 
	{
		return output;
	}
	
	/**
	 * Show the dataset on screen
	 */
	public void show()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("Data set NAME: ").append(name).append("\n");
		sb.append("Data set SIZE: ").append(size).append("\n");
		sb.append("Entries(variance, skewness, curtosis, entropy); Ouput(true/fase): \n");
		
		for(int i = 0; i < this.output.size(); i++)
		{
			sb.append("Entry: (");
			sb.append(variance.get(i)).append(",");
			sb.append(skewness.get(i)).append(",");
			sb.append(curtosis.get(i)).append(",");
			sb.append(entropy.get(i)).append("); ");
			sb.append("Output: (").append(output.get(i)).append(")\n");
		}
		System.out.println(sb.toString());
	}

	@Override
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("Data set NAME: ").append(name).append("\n");
		sb.append("Data set SIZE: ").append(size).append("\n");
		
		return sb.toString();
	}
}