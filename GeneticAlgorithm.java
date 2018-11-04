public class GeneticAlgorithm
{
	/**
	 * Main method. Just used to run in IDEs other than BlueJ.
	 * @param args Command line arguments for the program. Usually empty.
	 */
	public static void main(String[] args)
	{
		run(100, 1000, 0.001);
	}
	
	/**
	 * A method that runs the genetic algorithm for this population.
	 * @param populationSize The number of Agents in this population.
	 * @param numberGenerations The number of generations that the genetic
	 * 							algorithm will simulate.
	 * @param mutateAmount The amount to mutate each Agent between generations when
	 * 						restocking.
	 */
	public static void run(int populationSize, int numberGenerations, double mutateAmount)
	{
		//Declare and initialize population.
		Population population = new Population(populationSize);
		
		//Evaluate each generation of the population and mutate.
		for (int i = 0 ; i < numberGenerations ; i++)
		{
			System.out.println("Generation " + (i + 1) + "\n");
			
			population.evaluate();
			population.restock(mutateAmount);
			System.out.println("Done!");
		}
		
		//Print out the population at the end of the genetic algorithm.
		System.out.println(population.agents);
		
	}
}
