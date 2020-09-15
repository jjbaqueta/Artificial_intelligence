package dataSets;

import java.util.Random;

/**
 * This class generates the files for training and testing the fuzzy classifier
 * The input files are stored in .\input_analysis\datasets
 */
public class DataGenerator 
{
	private final Double TRAINING_PERCENTAGE = 0.8; 
	
	private Dataset training;
	private Dataset test;
	
	public DataGenerator(Dataset base) 
	{
		training = new Dataset("training");
		createTrainingSet(base);
		
		test = new Dataset("test");
		createTestSet();
	}
	
	/**
	 * This method initializes the training set.
	 * The training set is a copy of original input dataset.
	 * @param originalSet: original input dataset
	 */
	private void createTrainingSet(Dataset originalSet)
	{
		for(int i = 0; i < originalSet.getSize(); i++)
		{
			double variance = originalSet.getVariance().get(i);
			double skewness = originalSet.getSkewness().get(i);
			double curtosis = originalSet.getCurtosis().get(i);
			double entropy = originalSet.getEntropy().get(i);
			boolean output = originalSet.getOutput().get(i);
			
			training.addEntry(variance, skewness, curtosis, entropy, output);
		}
	}
	
	/**
	 * This method initializes the test set. 
	 */
	private void createTestSet()
	{
		Random rand = new Random();
		int testInputs = (int) (training.getSize() * (1 - TRAINING_PERCENTAGE)); 
		
		// Remove a entry from training set and put it in the test set.
		while(testInputs > 0)
		{
			int index = rand.nextInt(training.getSize());
			
			double variance = training.getVariance().remove(index);
			double skewness = training.getSkewness().remove(index);
			double curtosis = training.getCurtosis().remove(index);
			double entropy = training.getEntropy().remove(index);
			boolean output = training.getOutput().remove(index);
			
			test.addEntry(variance, skewness, curtosis, entropy, output);
			training.setSize(training.getSize() - 1);
			testInputs--;
		}
	}

	public Dataset getTraining() 
	{
		return training;
	}

	public Dataset getTest() 
	{
		return test;
	}
	
	/**
	 * This method is used to test this class.
	 * In particular, it may be used to check the generation of training and test dataset out
	 */
	public static void main(String[] args) 
	{
		Dataset base = new Dataset("base");
		base.loadDatasetFromFile("../input_analysis/datasets/full_dataset.txt");
		
		DataGenerator generator = new DataGenerator(base);
		System.out.println(generator.getTraining());
		System.out.println(generator.getTest());
		
		generator.getTraining().save();
		generator.getTest().save();
	}
}