package foilp1;

public class Literal {

	private String attribut;
	private String value;
	private int nbIteration;
	private double gain;

	public Literal(String attribut, String value) {
		this.attribut = attribut;
		this.value = value;
		this.nbIteration = 0;
		this.gain = 0;
	}

	public String getAttribut() {
		return attribut;
	}

	public void setAttribut(String attribut) {
		this.attribut = attribut;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getNbIteration() {
		return nbIteration;
	}

	public void setNbIteration(int nbIteration) {
		this.nbIteration = nbIteration;
	}

	public void resetNbrIteration() {
		this.nbIteration =0;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribut == null) ? 0 : attribut.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Literal other = (Literal) obj;
		if (attribut == null) {
			if (other.attribut != null)
				return false;
		} else if (!attribut.equals(other.attribut))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  attribut + " = " + value;
	}
}
