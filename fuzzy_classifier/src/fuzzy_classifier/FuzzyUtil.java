package fuzzy_classifier;

import java.util.ArrayList;
import java.util.List;

import dataSets.Dataset;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

/**
 * This class implements some basic operations used during the experiments.
 * For instance: operations to open and load input files.
 */
public abstract class FuzzyUtil 
{
	/**
	 * Load the fuzzy blocks from a input file (fuzzy_system.fcl)
	 * @return a Fuzzy inference system (FIS)
	 */
	public static FIS loadFuzzyBlocks()
	{
		String fileName = "src/fuzzy_files/fuzzy_system.fcl";
        FIS fis = FIS.load(fileName, true);
        
        if( fis == null )
        	throw new Error("Can't load file: '" + fileName + "'");
        
        return fis;
	}
	
	/**
	 * Show the inputs as fuzzy sets
	 * @param fis: a Fuzzy inference system.
	 */
	public static void showInputCharts(FIS fis)
	{
		JFuzzyChart.get().chart(fis);
	}
	
	/**
	 * This method uses the Wang-Mendel algorithm to create the fuzzy rules.
	 * @param fis: a Fuzzy inference system.
	 * @param dataset: the dataset for training
	 */
	public static void generateRules(FIS fis, Dataset dataset)
	{	
		// Starting the grid
		WangMendel wm = new WangMendel();
        wm.startGrid();
        
        // Reading variables from dataset
        for(int i = 0; i < dataset.getOutput().size(); i++)
        {
        	List<FuzzyVariable> variables = new ArrayList<FuzzyVariable>();
        	
        	fis.setVariable("variance", dataset.getVariance().get(i));
        	fis.setVariable("skewness", dataset.getSkewness().get(i));
        	fis.setVariable("curtosis", dataset.getCurtosis().get(i));
        	fis.setVariable("entropy", dataset.getEntropy().get(i));
        	
	        fis.evaluate();
        	
	        // Creating Fuzzy variables
	        FuzzyVariable variance = new FuzzyVariable("variance");
	        variance.addValue("high", fis.getVariable("variance").getMembership("high"));
	        variance.addValue("middle", fis.getVariable("variance").getMembership("middle"));
	        variance.addValue("low", fis.getVariable("variance").getMembership("low"));
	        variables.add(variance);
	        
	        FuzzyVariable skewness = new FuzzyVariable("skewness");
	        skewness.addValue("high", fis.getVariable("skewness").getMembership("high"));
	        skewness.addValue("middle", fis.getVariable("skewness").getMembership("middle"));
	        skewness.addValue("low", fis.getVariable("skewness").getMembership("low"));
	        variables.add(skewness);
	        
	        FuzzyVariable curtosis = new FuzzyVariable("curtosis");
	        curtosis.addValue("high", fis.getVariable("curtosis").getMembership("high"));
	        curtosis.addValue("middle", fis.getVariable("curtosis").getMembership("middle"));
	        curtosis.addValue("low", fis.getVariable("curtosis").getMembership("low"));
	        variables.add(curtosis);
	        
	        FuzzyVariable entropy = new FuzzyVariable("entropy");
	        entropy.addValue("high", fis.getVariable("entropy").getMembership("high"));
	        entropy.addValue("middle", fis.getVariable("entropy").getMembership("middle"));
	        entropy.addValue("low", fis.getVariable("entropy").getMembership("low"));
	        variables.add(entropy);
        	
        	FuzzyRule rule = new FuzzyRule(i, variables, dataset.getOutput().get(i));
        	wm.addRuleToGrid(rule.getCode(), rule);
        }
        
        // Showing the mapping of rules for grid cells
        wm.showRulesDistribution();
        
        // Showing generated rules on screen
        for(String rule : wm.run())
        	System.out.println(rule);
	}
	
	/**
	 * This method performs the test of fuzzy classifier.
	 * @param fis: a Fuzzy inference system.
	 * @param dataset: the dataset for testing.
	 */
	public static void testRuleBase(FIS fis, Dataset dataset)
	{	
		int truePositive = 0, falsePositive = 0, trueNegative = 0, falseNegative = 0;
        
        // Reading the variables from dataset
        for(int i = 0; i < dataset.getOutput().size(); i++)
        {       	
        	fis.setVariable("variance", dataset.getVariance().get(i));
        	fis.setVariable("skewness", dataset.getSkewness().get(i));
        	fis.setVariable("curtosis", dataset.getCurtosis().get(i));
        	fis.setVariable("entropy", dataset.getEntropy().get(i));
        	
	        fis.evaluate();

	        // Show output variable's chart
	        double value = fis.getVariable("class").getMembership("true");
	        System.out.println(value);
	        
	        if(value >= 0.5 && dataset.getOutput().get(i))
	        	truePositive++;
	        
	        else if(value >= 0.5 && !dataset.getOutput().get(i))
	        	falsePositive++;
	        
	        else if(value < 0.5 && !dataset.getOutput().get(i))
	        	trueNegative++;
	        
	        else
	        	falseNegative++;
        }
        
        // Showing results   
        int instances = truePositive + trueNegative + falsePositive + falseNegative;
        int hits = truePositive + trueNegative;
        
        System.out.println("Correctly Classified Instances: " + hits + " - " 
        + ((hits/ (double) instances) * 100) + " %");
        
        System.out.println("============================");
        
        System.out.println("Confusion Matrix:");
        System.out.println("a\tb\t\t<-- classified as:");
        System.out.println(trueNegative + "\t" + falseNegative + " | a = 0");
        System.out.println(falsePositive + "\t" + truePositive + " | b = 1");
	}
}