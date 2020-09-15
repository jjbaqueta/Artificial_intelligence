package fuzzy_classifier;

import dataSets.Dataset;
import net.sourceforge.jFuzzyLogic.FIS;

/**
 * @author Baqueta
 * 
 * This project implements a fuzzy classifier.
 * The classifier is used to decide whether a money bill is genuine or fake.
 * The learning data are loaded from input files, which describe the money bills through Wavelet components.
 * For creating the universe of fuzzy values, we are using the jfuzzyLogic lib.
 * In order to create the rule base (RB), we implement the Wang-Mendel algoritm.
 * The Wang-Mendel algorithm is used to generate fuzzy rules based on the input learning data.
 * The generated rules are mapped to a decision grid, where each grid cell represents a clustering of similar rules 
 * In order to solve the conflicts of mapping among rules, after the mapping the rules are redistributed through grid according to their similarity.
 * 
 * For more information:
 * - Fuzzy logic: @see: https://en.wikipedia.org/wiki/Fuzzy_logic
 * - Wang-Mendel: @see: https://www.researchgate.net/publication/322981881_Revisiting_the_Wang-Mendel_algorithm_for_fuzzy_classification
 * - jfuzzyLogic: @see: http://jfuzzylogic.sourceforge.net/html/index.html
 */

public class Main 
{
	public static void main(String[] args)
	{
		// Opening dataset for testing
		Dataset test = new Dataset("dataset");
		test.loadDatasetFromFile("../input_analysis/datasets/test2.txt");
		
		// Opening dataset for training
		Dataset training = new Dataset("dataset");
		training.loadDatasetFromFile("../input_analysis/datasets/training2.txt");
		
		// Loading fuzzy variables and RB
		FIS fis = FuzzyUtil.loadFuzzyBlocks();
		
		FuzzyUtil.showInputCharts(fis);
		FuzzyUtil.generateRules(fis, training);
		FuzzyUtil.testRuleBase(fis, test);
	}
}