package projet_ia_weka;

import java.util.ArrayList;

import weka.core.Instance;

public class Regle {
	private ArrayList<Literal> liste_literaux = new ArrayList<Literal>();
	private int nbrRegleCouvert;
	public Regle() {
		super();
		nbrRegleCouvert = 0 ;
	}

	/**
	 * @return the liste_literaux
	 */
	public ArrayList<Literal> getListe_literaux() {
		return liste_literaux;
	}

	/**
	 * @param liste_literaux the liste_literaux to set
	 */
	public void setListe_literaux(ArrayList<Literal> liste_literaux) {
		this.liste_literaux = liste_literaux;
	}

	public void ajouterLiteral(Literal literal) {
		liste_literaux.add(literal);
	}
	
	@Override
	public String toString() {
		Literal literal = liste_literaux.get(0);
		String chaine = literal.toString();

		for(int i = 1; i < liste_literaux.size(); i++) {
			chaine+=" ET "+liste_literaux.get(i).toString();
		}
		return chaine;
	}
	
	public boolean instanceVerfieRegle(Instance instance) {
		for(int i = 0; i <instance.numAttributes()-1;i++) {
			for(Literal literal : liste_literaux) {
				if(literal.getAttribut().equals(instance.attribute(i).name())) {
					if(!literal.getValeur().equals(instance.stringValue(i))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @return the nbrRegleCouvert
	 */
	public int getNbrRegleCouvert() {
		return nbrRegleCouvert;
	}

	/**
	 * @param nbrRegleCouvert the nbrRegleCouvert to set
	 */
	public void setNbrRegleCouvert(int nbrRegleCouvert) {
		this.nbrRegleCouvert = nbrRegleCouvert;
	}
	
}
