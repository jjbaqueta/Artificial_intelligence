package fuzzy_classifier;

/**
 * This class implements a fuzzy value.
 * A fuzzy value is defined by a linguistic term and its value.
 */
public class FuzzyValue implements Comparable<FuzzyValue>
{
	private String lingTerm;
	private Double value;
	
	public FuzzyValue(String lingTerm, Double value) 
	{
		this.lingTerm = lingTerm;
		this.value = value;
	}

	public String getLingTerm() 
	{
		return lingTerm;
	}
	
	public Double getValue() 
	{
		return value;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lingTerm == null) ? 0 : lingTerm.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		FuzzyValue other = (FuzzyValue) obj;
		if (lingTerm == null) 
		{
			if (other.lingTerm != null)
				return false;
		} else if (!lingTerm.equals(other.lingTerm))
			return false;
		if (value == null) 
		{
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "FuzzyValue [lingTerm=" + lingTerm + ", value=" + value + "]";
	}

	@Override
	public int compareTo(FuzzyValue other) 
	{
		if(this.lingTerm.equals(other.lingTerm) && this.value == other.value)
		{
			return 0;
		}
		return 1;
	}
}