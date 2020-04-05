package foilp1;

import java.util.ArrayList;

import weka.core.Instance;

public class Regle {

	private ArrayList<Literal> liste_literaux = new ArrayList<>();
	private int nbrRegleCouvert = 0;

	public Regle() {}

	public ArrayList<Literal> getListe_literaux() {
		return liste_literaux;
	}

	public void setListe_literaux(ArrayList<Literal> liste_literaux) {
		this.liste_literaux = liste_literaux;
	}

	public void ajouterLiteral(Literal literal) {
		liste_literaux.add(literal);
	}

	public int getNbrRegleCouvert() {
		return nbrRegleCouvert;
	}

	public void setNbrRegleCouvert(int nbrRegleCouvert) {
		this.nbrRegleCouvert = nbrRegleCouvert;
	}

	public boolean instanceVerfieRegle(Instance instance) {

		for(int i = 0; i <instance.numAttributes() - 1; i++) {

			for(Literal literal : liste_literaux) {

				if(literal.getAttribut().equals(instance.attribute(i).name())) {

					if(!literal.getValue().equals(instance.stringValue(i))) {
						return false;
					}
				}
			}
		}
		return true;
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
}
