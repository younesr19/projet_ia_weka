package foilp1;

import java.util.ArrayList;

import weka.core.Instance;

public class EnsembleInstance {

	ArrayList<Instance> positiveInstance;
	ArrayList<Instance> negativeInstance;

	public EnsembleInstance(ArrayList<Instance> positiveInstance, ArrayList<Instance> negativeInstance) {
		this.positiveInstance = positiveInstance;
		this.negativeInstance = negativeInstance;
	}

	public ArrayList<Instance> getPositiveInstance() {
		return positiveInstance;
	}

	public void setPositiveInstance(ArrayList<Instance> positiveInstance) {
		this.positiveInstance = positiveInstance;
	}

	public ArrayList<Instance> getNegativeInstance() {
		return negativeInstance;
	}

	public void setNegativeInstance(ArrayList<Instance> negativeInstance) {
		this.negativeInstance = negativeInstance;
	}
	

	
	
}
