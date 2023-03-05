package Request;

/**
 * Request to fill the database
 */
public class FillRequest {
    int numGenerations;

    public int getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(int numGenerations) {
        this.numGenerations = numGenerations;
    }

    /**
     * The wonderful default constructor
     */
    public FillRequest(){
    }

}
