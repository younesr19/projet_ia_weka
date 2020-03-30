package projet_ia_weka;

import java.util.ArrayList;

import weka.core.Instance;

public class EnsembleInstance {
	ArrayList<Instance> positiveInstance;
	ArrayList<Instance> negativeInstance;
	public EnsembleInstance(ArrayList<Instance> positiveInstance, ArrayList<Instance> negativeInstance) {
		super();
		this.positiveInstance = positiveInstance;
		this.negativeInstance = negativeInstance;
	}
	/**
	 * @return the positiveInstance
	 */
	public ArrayList<Instance> getPositiveInstance() {
		return positiveInstance;
	}
	/**
	 * @param positiveInstance the positiveInstance to set
	 */
	public void setPositiveInstance(ArrayList<Instance> positiveInstance) {
		this.positiveInstance = positiveInstance;
	}
	/**
	 * @return the negativeInstance
	 */
	public ArrayList<Instance> getNegativeInstance() {
		return negativeInstance;
	}
	/**
	 * @param negativeInstance the negativeInstance to set
	 */
	public void setNegativeInstance(ArrayList<Instance> negativeInstance) {
		this.negativeInstance = negativeInstance;
	}
	

	
	
}
