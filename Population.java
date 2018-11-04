import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Population
{
	//Instance fields
	public ArrayList<Agent> agents;
	public double rockAvg, paperAvg, scissorAvg, winAvg, loseAvg;
	public double rockStdDev, paperStdDev, scissorStdDev, winStdDev, loseStdDev;
	
	/**
	 * Default constructor. Initializes the Agents in the population.
	 * @param num The number of Agents in the population
	 */
	public Population(int num)
	{
		this.agents = new ArrayList<>(num);
		
		for (int i = 0 ; i < num ; i++)
		{
			Agent agent = new Agent();
			this.agents.add(agent);
		}
	}
	
	/**
	 * A method that evaluates each Agent in the population. Each Agent's
	 * points are multiplied by 10000 to simulate 10000 games with the expert.
	 * The population is then sorted.
	 */
	public void evaluate()
	{
		for (Agent bond : agents)
		{
			bond.points = (int) (bond.evaluate() * 10000);
		}
		
		//Sort Agents according to points.
		Collections.sort(agents);
		
		/*
		//Print out each Agent for debugging purposes.
		for (Agent bond : agents)
		{
			System.out.println(bond);
		}
		*/
		
	}
	
	/**
	 * A method that restocks the population with the Agents that performed
	 * the best. That is, the first half of the population is replaced with
	 * mutated copies of the second half.
	 * @param mutateAmount Amount to mutate each Agent copy by.
	 */
	public void restock(double mutateAmount)
    {
        Collections.sort(agents);
        
        int half = agents.size()/2;
        for (int n = 0; n < half; n++)
        {
            Agent bond = agents.get(n + half).clone().mutate(mutateAmount);
            agents.set(n, bond); 
        }
    }
    
    /**
     * A method that recalcualtes the average and standard deviation of each
     * option for the Agents in the population.
     */
    public void recalculateStats()
    {
        //DescriptiveStatistics objects allow for easy access to many statistical analysis functions.
        DescriptiveStatistics dsr = new DescriptiveStatistics();
        DescriptiveStatistics dsp = new DescriptiveStatistics();
        DescriptiveStatistics dss = new DescriptiveStatistics();
        DescriptiveStatistics dsw = new DescriptiveStatistics();
        DescriptiveStatistics dsl = new DescriptiveStatistics();
        
        //Add values for each probability.
        for (Agent a : agents)
        {
            dsr.addValue( a.p1 );
            dsp.addValue( a.p2 );
            dss.addValue( a.p3 );
            dsw.addValue( a.p4 );
            dsl.addValue( a.p5 );
        }
        
        //Update average values.
        rockAvg = dsr.getMean();
        paperAvg = dsp.getMean();
        scissorAvg = dss.getMean();
        winAvg = dsw.getMean();
        loseAvg = dsl.getMean();
        
        //Update standard deviation values.
        rockStdDev = dsr.getStandardDeviation();
        paperStdDev = dsp.getStandardDeviation();
        scissorStdDev = dss.getStandardDeviation();
        winStdDev = dsw.getStandardDeviation();
        loseStdDev = dsl.getStandardDeviation();
    }

}
