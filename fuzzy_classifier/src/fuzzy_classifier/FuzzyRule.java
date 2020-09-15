package fuzzy_classifier;

import java.util.List;

/**
 * This class implements a fuzzy rule.
 * The fuzzy rules are used by fuzzy classifier in order to decide the class of output.
 * In this work, the variables of a rule are associated by AND operators.
 */
public class FuzzyRule 
{
	private Integer id;
	private Boolean output;
	private List<FuzzyVariable> variables;
	
	public FuzzyRule(int id, List<FuzzyVariable> variables, Boolean output) 
	{
		this.id = id;
		this.output = output;
		this.variables = variables;
	}

	/**
	 * Generate a code for rule.
	 * @return a string that represents a code.
	 */
	public String getCode()
	{
		String code = "";
		
		for(int i = 0; i < variables.size() - 1; i++)
		{
			code += variables.get(i).getName() + "-" + variables.get(i).getDominantLingTerm() + "-";
		}
		code += variables.get(variables.size() - 1).getName() + "-" + variables.get(variables.size() - 1).getDominantLingTerm();
		
		return code;
	}
	
	/**
	 * Compute the product of most significant terms
	 * @return the product of terms
	 */
	public double computeProduct()
	{
		double product = 1.0; 
		
		for(FuzzyVariable variable : variables)	
			product *= variable.getValue(variable.getDominantLingTerm()).getValue();
		
		return product;
	}
	
	public Integer getId() 
	{
		return id;
	}
	
	public List<FuzzyVariable> getVariables() 
	{
		return variables;
	}
	
	public Boolean getOutput() 
	{
		return output;
	}

	@Override
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("RULE " + id + ": IF ");
		
		for(int i = 0; i < variables.size() - 1; i++)
		{
			sb.append(variables.get(i).getName()).append(" IS ");
			sb.append(variables.get(i).getDominantLingTerm()).append(" AND ");
		}
		
		sb.append(variables.get(variables.size() - 1).getName()).append(" IS ");
		sb.append(variables.get(variables.size() - 1).getDominantLingTerm()).append(" THEN class IS ");
		sb.append(output).append(";");
		
		return sb.toString();
	}
}