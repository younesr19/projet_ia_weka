package projet_ia_weka;

public class Literal {

	private String attribut;
	private String valeur;
	private int nbrIteration;
	private double gain;
	public Literal(String attribut, String valeur) {
		super();
		this.attribut = attribut;
		this.valeur = valeur;
		this.nbrIteration = 0;
		this.gain = 0;
	}
	/**
	 * @return the attribut
	 */
	public String getAttribut() {
		return attribut;
	}
	/**
	 * @param attribut the attribut to set
	 */
	public void setAttribut(String attribut) {
		this.attribut = attribut;
	}
	/**
	 * @return the valeur
	 */
	public String getValeur() {
		return valeur;
	}
	/**
	 * @param valeur the valeur to set
	 */
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	@Override
	public String toString() {
		return  attribut + " = " + valeur;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribut == null) ? 0 : attribut.hashCode());
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
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
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
		return true;
	}
	/**
	 * @return the nbrIteration
	 */
	public int getNbrIteration() {
		return nbrIteration;
	}
	/**
	 * @param nbrIteration the nbrIteration to set
	 */
	public void setNbrIteration(int nbrIteration) {
		this.nbrIteration = nbrIteration;
	}
	/**
	 * @return the gain
	 */
	public double getGain() {
		return gain;
	}
	/**
	 * @param gain the gain to set
	 */
	public void setGain(double gain) {
		this.gain = gain;
	}
	
	public void resetNbrIteration() {
		this.nbrIteration=0;
	}
	
}
