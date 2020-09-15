package fuzzy_classifier;

import java.util.Set;
import java.util.TreeSet;

/**
 * This class implements a fuzzy variable.
 * Each variable has its own space of fuzzy values
 */
public class FuzzyVariable 
{
	private String name;
	private Set<FuzzyValue> values;
	
	public FuzzyVariable(String name) 
	{
		this.name = name;
		this.values = new TreeSet<FuzzyValue>();
	}
	
	/**
	 * Add a new value in the fuzzy variable space
	 * @param lingTerm: linguistic term associated with variable
	 * @param value: value of linguistic term.
	 */
	public void addValue(String lingTerm, double value)
	{
		values.add(new FuzzyValue(lingTerm, value));
	}
	
	/**
	 * Remove a value from fuzzy space
	 * @param lingTerm: linguistic term associated with variable
	 */
	public void removeValue(String lingTerm)
	{
		for(FuzzyValue term: values)
		{
			if (term.getLingTerm().equals(lingTerm))
			{
				values.remove(term);
				break;
			}
		}
	}
	
	/**
	 * Get the term with the most highest value.
	 * @return the linguistic term.
	 */
	public String getDominantLingTerm()
	{	
		FuzzyValue[] values = this.values.toArray(new FuzzyValue[this.values.size()]);
		String term = values[0].getLingTerm();
		double max = values[0].getValue();
		
		for(FuzzyValue value : values)
		{
			if(value.getValue() >= max)
			{
				max = value.getValue();
				term = value.getLingTerm();
			}
		}
		return term;
	}
	
	/**
	 * Get the value of a linguistic term
	 * @param lingTerm: linguistic term associated with variable
	 * @return a fuzzy value (linguistic term, value), if linguistic term exists, otherwise returns null.
	 */
	public FuzzyValue getValue(String lingTerm)
	{
		for(FuzzyValue term: values)
		{
			if (term.getLingTerm().equals(lingTerm))
				return term;
		}
		throw new Error("linguistic term is not valid " + lingTerm);
	}
	
	/**
	 * Get all combinations among the name of variable and its linguistic terms
	 * @return a set of string
	 */
	public Set<String> getClasses()
	{
		Set<String> varClasses = new TreeSet<String>();
		
		for(FuzzyValue fv: this.values)
			varClasses.add(name + "-" + fv.getLingTerm());
		
		return varClasses;
	}
	
	public String getName() 
	{
		return name;
	}

	public Set<FuzzyValue> getValues() 
	{
		return values;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuzzyVariable other = (FuzzyVariable) obj;
		if (name == null) 
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		
		for(FuzzyValue term : values)
		{
			sb.append(term.getLingTerm()).append("(");
			sb.append(term.getValue()).append(")").append(" ");
		}
		
		return "FuzzyVariable [name=" + name + ", values=" + sb.toString() + "]";
	}
}