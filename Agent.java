public class Agent implements Comparable<Agent>
{
	//Enumeration representing the possible plays in a game of RPSWL.
	public enum Action {R, P, S, W, L};
	
	//Instance fields
    public int points;
    double[] probabilities;
    double p1, p2, p3, p4, p5; //Variables to determine coefficients.
    
    /**
     * Default constructor. Initializes instance fields, and ensures probabilities
     * of picking each choice add to 1.
     */
    public Agent()
    {
        this.points = 0;
        double sum = 0;
        this.probabilities = new double[5]; //Represents each of the 5 responses.
        
        //Initialize probability array.
        for (int i = 0 ; i < 5 ; i++)
        {
        	probabilities[i] = Math.random();
        	sum += probabilities[i];
        }
        
        //Normalize the probabilities so that they add to 1.
        for (int i = 0 ; i < 5 ; i++)
        {
        	probabilities[i] = probabilities[i] / sum;
        }
        
        //Assign probabilities to determine coefficients later.
        this.p1 = this.probabilities[0];
        this.p2 = this.probabilities[1];
        this.p3 = this.probabilities[2];
        this.p4 = this.probabilities[3];
        this.p5 = this.probabilities[4];
        
    }
    
    /**
     * A method that evaluates each Agent based off of gameplay against an expert.
     * The expert will always choose the option that will result in the Agent losing
     * the most points. Thus, the expert only plays the option with the most negative
     * coefficient from the cross-product of the expert's probabilities and the Agent's
     * probabilities.
     * @return The most negative coefficient from the cross-product of the expert's
     * 			probabilities and the Agent's probabilities.
     */
    public double evaluate()
    {
    	//Declare each coefficient.
       	double coeff1 = p2 - p3 + p4 - p5;
    	double coeff2 = p3 - p1 + p4 - p5;
    	double coeff3 = p1 - p2 + p4 - p5;
 	    double coeff4 = p5 - p1 - p2 - p3;
   	    double coeff5 = p1 + p2 + p3 - p4;
    	
   	    //Find the minimum by calling a helper function.
   	    double minimum = findMin(coeff1, coeff2, coeff3, coeff4, coeff5);
   	    
   	    //Return the found value.
   	    return minimum;
    }
    
    /**
     * A helper method that finds the minimum value of a list of doubles.
     * @param coeffs A list of doubles
     * @return The lowest aka most negative double.
     */
    public double findMin(double... coeffs)
    {
    	//Initialize minimum value.
    	double min = coeffs[0];
    	
    	//Loop through list and find the lowest double.
    	for (int i = 1 ; i < coeffs.length ; i++)
    	{
    		if (min > coeffs[i])
    		{
    			min = coeffs[i];
    		}
    	}

    	//Return the lowest found value.
    	return min;
    }
    
    /**
     * A method that mutates the parameters of the Agent by a designated amount.
     * The parameters of the Agent will lessen or increase, but not more than by
     * the specified amount.
     * @param amount Specified max for how much the parameters should mutate.
     * @return A reference to the Agent that was just mutated.
     */
    public Agent mutate(double amount)
    {
    	//Mutate each parameter by a random number within the specified amount.
    	for (int i = 0 ; i < this.probabilities.length ; i++)
    	{
    		double r = amount * (2 * Math.random() - 1);
    		this.probabilities[i] += r;
    	}
    	
    	//Normalize the probabilities again.
    	double sum = 0;
    	for (int i = 0 ; i < this.probabilities.length ; i++)
    		sum += this.probabilities[i];
    	
    	for (int i = 0 ; i < this.probabilities.length ; i++)
    		this.probabilities[i] /= sum;
    	
    	//Assign the coefficients again so that Agent can be evaluated again.
    	this.p1 = this.probabilities[0];
        this.p2 = this.probabilities[1];
        this.p3 = this.probabilities[2];
        this.p4 = this.probabilities[3];
        this.p5 = this.probabilities[4];
    	
        //Return a copy of this Agent.
    	return this;
    }
    
    /**
     * A method that creates a clone of the current Agent.
     * @return A copy of the current Agent object.
     */
    public Agent clone()
    {
    	Agent copy = new Agent();
    	
    	copy.points = this.points;
    	copy.probabilities = this.probabilities.clone();
    	
    	return copy;
    }
    
    /**
     * A method that compares one Agent to another. Allows use of the Collections.sort()
     * method to sort Agents in ascending order of points.
     */
    public int compareTo(Agent other)
    {
        if (this.points < other.points)
            return -1;
        else if (this.points == other.points)
            return 0;
        else
            return 1;
    }
    
    /**
     * A method that returns a String representation of the current Agent.
     * @return A String containing each turn0 probability, and each response
     * 			probability.
     */
    public String toString()
    {
        String rR = String.format( "%.2f", probabilities[0] );
        String rP = String.format( "%.2f", probabilities[1] );
        String rS = String.format( "%.2f", probabilities[2] );
        String rW = String.format( "%.2f", probabilities[3] );
        String rL = String.format( "%.2f", probabilities[4] );
        
        return "Points: " + points + " R: " + rR + " P: " + rP 
        		+ " S: " + rS + " W: " + rW + " L: " + rL + "\n";
    }
}