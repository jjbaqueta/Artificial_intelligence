package fuzzy_classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the Wang-Mendel method for training fuzzy classifiers
 */
public class WangMendel 
{
	private List<FuzzyVariable> variables; 
	private Map<String, List<FuzzyRule>> grid;
	
	public WangMendel() 
	{
		this.variables = new ArrayList<FuzzyVariable>();
		this.grid = new HashMap<String, List<FuzzyRule>>();
	}
	
	/**
	 * Initialize the grid of rules for executing of Wang-Mendel algorithm
	 */
	public void startGrid()
	{
		FuzzyVariable variance = new FuzzyVariable("variance");
        variance.addValue("high", 0);
        variance.addValue("middle", 0);
        variance.addValue("low", 0);
        variables.add(variance);
        
        FuzzyVariable skewness = new FuzzyVariable("skewness");
        skewness.addValue("high", 0);
        skewness.addValue("middle", 0);
        skewness.addValue("low", 0);
        variables.add(skewness);
        
        FuzzyVariable curtosis = new FuzzyVariable("curtosis");
        curtosis.addValue("high", 0);
        curtosis.addValue("middle", 0);
        curtosis.addValue("low", 0);
        variables.add(curtosis);
        
        FuzzyVariable entropy = new FuzzyVariable("entropy");
        entropy.addValue("high", 0);
        entropy.addValue("middle", 0);
        entropy.addValue("low", 0);
        variables.add(entropy);
		
		List<String> codes = new ArrayList<String>();
		generateCodes(0, new ArrayList<String>(), codes);
		
		for(String code : codes)
			grid.put(code, new ArrayList<FuzzyRule>());
	}
	
	/**
	 * This method generates all fuzzy entries combination.
	 * Each entry combination is used as an unique code, which is associated with a certain fuzzy rule.
	 * @param index: index of current fuzzy variable.
	 * @param path: a sequence of variables that represents a code. 
	 * @param paths: all sequences of variables found.
	 */
	private void generateCodes(int index, List<String> path, List<String> paths)
	{
		// Saving the current path
		if(index >= variables.size())
		{
			String code = "";
			
			for(int i = 0; i < path.size() - 1; i++)
			{
				code += path.get(i) + "-";
			}
			code += path.get(path.size() - 1);
			
			paths.add(code);
			return;
		}
		
		// Exploring the space of variables
		FuzzyVariable variable = variables.get(index);
		
		for(FuzzyValue value: variable.getValues())
		{
			path.add(variable.getName() + "-" + value.getLingTerm());
			generateCodes(index + 1,  path, paths);
			path.remove(path.get(path.size() - 1));
		}
	}
	
	/**
	 * Add a new rule to grid.
	 * @param code: code of rule.
	 * @param rule: new rule to be added to grid.
	 */
	public void addRuleToGrid(String code, FuzzyRule rule)
	{
		if(grid.containsKey(code))
			grid.get(code).add(rule);
		else
			throw new Error("This code is not valid: " + code);
	}
	
	/**
	 * Show how the rules are distributed on the grid.
	 */
	public void showRulesDistribution()
	{
		System.out.println("Number of partitions: " + grid.keySet().size());
		
		for(String key : grid.keySet())
			System.out.println(grid.get(key).size() + " - " + key);
	}
	
	/**
	 * Select the best rules to represent each code (class of rule)
	 * @return a list of rules in string format.
	 */
	public List<String> run()
	{	
		List<String> listR = new ArrayList<String>();
		
		// Compute the best rule for each code
		for(String code : grid.keySet())
		{
			List<FuzzyRule> rules = grid.get(code);
			
			// No rule for the grid cell
			if(rules.isEmpty())
			{
				findApproximateRule(code);
				listR.add(grid.get(code).get(0).toString());
			}
			// More than one rule by grid cell
			else
			{
				FuzzyRule bestRule = grid.get(code).get(0);
				double bestProduct = bestRule.computeProduct();
				
				for(FuzzyRule rule : grid.get(code))
				{
					double p = rule.computeProduct();
					
					if(p > bestProduct)
					{
						bestProduct = p;
						bestRule = rule;
					}
				}
				
				listR.add(bestRule.toString());
			}
		}
		return listR;
	}
	
	
	/**
	 * Find the most approximated rule from a given code.
	 * @param code: code of current rule
	 */
	private void findApproximateRule(String code)
	{	
		String[] array = code.split("-");
		String[] termsA = new String[3];
		
		if(array[1].equals("low"))
		{
			termsA[0] = "low";
			termsA[1] = "middle";
			termsA[2] = "high";
		}
		else if(array[1].equals("middle"))
		{
			termsA[0] = "middle";
			termsA[1] = "high";
			termsA[2] = "low";
		}
		else
		{
			termsA[0] = "high";
			termsA[1] = "low";
			termsA[2] = "high";
		}
		
		String[] termsB = new String[3];
		
		if(array[3].equals("low"))
		{
			termsB[0] = "low";
			termsB[1] = "middle";
			termsB[2] = "high";
		}
		else if(array[3].equals("middle"))
		{
			termsB[0] = "middle";
			termsB[1] = "high";
			termsB[2] = "low";
		}
		else
		{
			termsB[0] = "high";
			termsB[1] = "low";
			termsB[2] = "high";
		}
		
		String[] termsC = new String[3];
		
		if(array[5].equals("low"))
		{
			termsC[0] = "low";
			termsC[1] = "middle";
			termsC[2] = "high";
		}
		else if(array[3].equals("middle"))
		{
			termsC[0] = "middle";
			termsC[1] = "high";
			termsC[2] = "low";
		}
		else
		{
			termsC[0] = "high";
			termsC[1] = "low";
			termsC[2] = "high";
		}
		
		String[] termsD = new String[3];
		
		if(array[7].equals("low"))
		{
			termsD[0] = "low";
			termsD[1] = "middle";
			termsD[2] = "high";
		}
		else if(array[3].equals("middle"))
		{
			termsD[0] = "middle";
			termsD[1] = "high";
			termsD[2] = "low";
		}
		else
		{
			termsD[0] = "high";
			termsD[1] = "low";
			termsD[2] = "high";
		}
		
		for(String term1 : termsA)
		{
			for(String term2 : termsB)
			{
				for(String term3 : termsC)
				{
					for(String term4 : termsD)
					{
						array[1] = term1;
						array[3] = term2;
						array[5] = term3;
						array[7] = term4;
						
						String newCode = String.join("-", array);
						
						if(grid.get(newCode).size() > 2)
						{
							FuzzyRule rule = grid.get(newCode).remove(0);
							grid.get(code).add(rule);
							return;
						}
					}
				}
			}
		}
	}
}