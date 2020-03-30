package projet_ia_weka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		if(args.length < 1){
			System.err.println("----Donner un fichier .arff en paramètre-----");
		}
		else{
			try {
				br = new BufferedReader(new FileReader(args[0]));
			} catch (FileNotFoundException ex) {
				System.err.println("----File not found: " + args[0]+ "-----");
				
			}
			// Récupération de toutes les instances du fichier et indéxation de toutes les classes
			Instances jeuDeTest = new Instances(br);
			Enumeration<Instance> liste_instance = jeuDeTest.enumerateInstances();
			Enumeration<Instance> liste_instance2 = jeuDeTest.enumerateInstances();
			Enumeration<Attribute> liste_attr = jeuDeTest.enumerateAttributes();

			ArrayList<Instance> liste = Collections.list(liste_instance);
			ArrayList<Instance> liste2 = Collections.list(liste_instance2);
			ArrayList<Attribute> liste_attribut = Collections.list(liste_attr);
			ArrayList<Literal> listeLiteraux = getListeLiteraux(liste);

			
			
			
			
			System.out.println("Liste des instances : ");
			listerInstance(liste);
			
			
			
			
			System.out.println("________________________________");
			Literal literal_pos = new Literal(liste_attribut.get(liste_attribut.size()-1).name(),liste_attribut.get(liste_attribut.size()-1).value(0));

			EnsembleInstance ensembleInstance = getEnsembleInstance(liste2);
			int cmp = 0;
			ArrayList<Regle> regles_apprises = new ArrayList<Regle>();
			while(ensembleInstance.getPositiveInstance().size()!=0) {
				
				Literal regleGenerale = getReglePlusGenerale(ensembleInstance, listeLiteraux);
				//System.out.println("Regle la plus générale : "+regleGenerale);
				@SuppressWarnings("unchecked")
				ArrayList<Instance> negNewRegle = (ArrayList<Instance>) ensembleInstance.getNegativeInstance().clone();
				@SuppressWarnings("unchecked")
				ArrayList<Instance> posNewRegle = (ArrayList<Instance>) ensembleInstance.getPositiveInstance().clone();
				ArrayList<Literal> listeNewLiteraux = listeLiteraux;
				Regle regle = new Regle();
				cmp++;
				while(negNewRegle.size()!=0) {
					Literal meilleurLiteral = meilleurGain(listeNewLiteraux,negNewRegle,posNewRegle);
					regle.ajouterLiteral(meilleurLiteral);

					
					retirerInstanceSatisfaisantPasLiteral(negNewRegle, meilleurLiteral);
					retirerInstanceSatisfaisantPasLiteral(posNewRegle, meilleurLiteral);

					//retirerListeLiteraux(listeNewLiteraux, meilleurLiteral);
					regleGenerale=meilleurLiteral;
					//System.out.println("\tMeilleur Literal : "+meilleurLiteral);	

				}
				regles_apprises.add(regle);
				System.out.println("R"+cmp+" => SI "+regle+" ALORS "+literal_pos);
				//retirerListeLiteraux(listeLiteraux,regleGenerale);
				//System.out.println("Etat des positifs " + regleGenerale);

				//retirerInstanceSatisfaisantLiteral(ensembleInstance.getPositiveInstance(), regleGenerale);
				retirerInstanceRegle(regle, ensembleInstance.getPositiveInstance());
				for(Instance instance : ensembleInstance.getNegativeInstance()) {
					//System.out.println("\t "+instance);
				}
				for(Instance instance : ensembleInstance.getPositiveInstance()) {
					//System.out.println("\t "+instance);
				}
				
		
			}
			System.out.println("________________________________");

			listerInstanceVerifiantRegles(liste,regles_apprises);
			
		}
		
	}
	
	public static EnsembleInstance getEnsembleInstance(ArrayList<Instance> liste_instance){
		ArrayList<Instance> negativeInstanceListe = new ArrayList<>();
		ArrayList<Instance> positiveInstanceListe = new ArrayList<>();
		for(Instance instance : liste_instance){
			String negativeAttribut = instance.attributeSparse(instance.numAttributes()-1).value(1);
			String negativeInstance = instance.stringValue(instance.numAttributes()-1);
			if(negativeAttribut.equals(negativeInstance)) {
				
				negativeInstanceListe.add(instance);
			}
			else {
				
				positiveInstanceListe.add(instance);
			}
		}
		
		return new EnsembleInstance(positiveInstanceListe,negativeInstanceListe);
		
	}
	
	public static ArrayList<Literal> getListeLiteraux(ArrayList<Instance> liste_instance){
		ArrayList<Literal> listeLiteral = new ArrayList<>();
		
		for(Instance instance : liste_instance) {
			for(int i = 0 ;i < instance.numAttributes()-1; i++) {
				String nomAttribut = instance.attribute(i).name();
				String nomValeur = instance.stringValue(i);
				Literal literal = new Literal(nomAttribut,nomValeur);
				if(!listeLiteral.contains(literal))
					listeLiteral.add(literal);

			}		
		}
		return listeLiteral;
	}
	public static Literal getReglePlusGenerale(EnsembleInstance ensembleinstance, ArrayList<Literal> listeLiteraux) {
		
		for(Literal literal : listeLiteraux) {
			literal.resetNbrIteration();
			for(Instance instance : ensembleinstance.getPositiveInstance()) {
				for(int i = 0 ;i < instance.numAttributes()-1; i++) {
					if(instance.attribute(i).name().equals(literal.getAttribut()) && instance.stringValue(i).equals(literal.getValeur())) {
						literal.setNbrIteration(literal.getNbrIteration()+1);
					}
				}
			}
		}
		 Literal maxLiteral =  Collections.max(listeLiteraux, Comparator.comparing(s -> s.getNbrIteration()));		
		 return maxLiteral;
	}
	public static  Literal meilleurGain(ArrayList<Literal> listeliteraux, ArrayList<Instance> negativeInstance, ArrayList<Instance> positiveInstance) {
		
		for(Literal literal : listeliteraux) {
			int nbExPosS = 0;
			int nbExNegS = 0;
			double gain = 0.0;
			for(Instance negInstance : negativeInstance) {
				for(int i = 0 ;i < negInstance.numAttributes()-1; i++) {
					if(negInstance.attribute(i).name().equals(literal.getAttribut()) && negInstance.stringValue(i).equals(literal.getValeur())) {
						nbExNegS++;
					}
				}			
			}
			for(Instance posInstance : positiveInstance) {
				for(int i = 0 ;i < posInstance.numAttributes()-1; i++) {
					if(posInstance.attribute(i).name().equals(literal.getAttribut()) && posInstance.stringValue(i).equals(literal.getValeur())) {
						nbExPosS++;
					}
				}			
			}

			gain = gain(nbExPosS,nbExNegS,positiveInstance.size(),negativeInstance.size());
			//System.out.println("\t"+literal+" "+gain);

			literal.setGain(gain);
		}
		 Literal maxLiteral =  Collections.max(listeliteraux, Comparator.comparing(s -> s.getGain()));		

		return maxLiteral;
	}
	public static void retirerInstanceSatisfaisantPasLiteral(ArrayList<Instance> listeInstances, Literal literal) {
		
		for (Iterator<Instance> it = listeInstances.iterator(); it.hasNext(); ) {
			Instance instance = it.next();
			for(int i = 0 ; i<instance.numAttributes()-1; i++) {
				if(instance.attribute(i).name().equals(literal.getAttribut())) {
					if(!instance.stringValue(i).equals(literal.getValeur())){
						it.remove();
					}
				}
			}
			
		}
		
		
	}
	public static void retirerInstanceSatisfaisantLiteral(ArrayList<Instance> listeInstances, Literal literal) {
		for (Iterator<Instance> it = listeInstances.iterator(); it.hasNext(); ) {
			Instance instance = it.next();
			for(int i = 0 ; i<instance.numAttributes()-1; i++) {
				if(instance.attribute(i).name().equals(literal.getAttribut())) {

					if(instance.stringValue(i).equals(literal.getValeur())){
						it.remove();
					}
				}
			}
			
		}
		
	}
	public static void retirerListeLiteraux(ArrayList<Literal> listeLiteraux, Literal literal) {
		for (Iterator<Literal> it = listeLiteraux.iterator(); it.hasNext(); ) {
			Literal l = it.next();
			if(literal.getAttribut().equals(l.getAttribut())&& literal.getValeur().equals(l.getValeur())) {
				it.remove();
			}
			
		}
	}
	
	public static double gain(int p, int n, int P, int N) {
		double generalVal = (double) P/(P+N);
		double litteralVal = (double) p/(p+n);
		double logGeneral = Math.log(generalVal) / Math.log(2.0);
		double logLitteral = Math.log(litteralVal) / Math.log(2.0);
		double ret = -1;
		//System.out.println("\tp = "+p+" n = "+n+" P = "+P+" N = "+N);
		if(p == 0){
			return -1;
		}
		ret = p  * (logLitteral - logGeneral);
		return ret;
	}
	
	public static void listerInstance(ArrayList<Instance> listeInstances) {
		
		for(int i = 0; i <listeInstances.size(); i++) {
			System.out.println(i+1+" : "+listeInstances.get(i));
		}
	}
	public static void listerInstanceVerifiantRegles(ArrayList<Instance> listeInstances, ArrayList<Regle> listeRegles) {
		
        
		for(int i = 0 ; i < listeInstances.size(); i++) {
			
			for(int j = 0; j < listeRegles.size(); j++) {
				
				if(listeRegles.get(j).instanceVerfieRegle(listeInstances.get(i))) {
					listeRegles.get(j).setNbrRegleCouvert(listeRegles.get(j).getNbrRegleCouvert()+1);
					System.out.println((i+1)+" -> R"+(j+1));
				}
				
				
				
			}
			
			
		}
		System.out.println("______________________");
		for(int i = 0; i < listeRegles.size(); i++) {
			System.out.println("R"+(i+1)+" = "+listeRegles.get(i).getNbrRegleCouvert());
		}
		int cmp = 0;
		for(Instance instance : listeInstances) {
			if(instance.value(instance.numAttributes()-1)==0) {
				cmp++;
			}
		}
		System.out.println("Total des positives : "+cmp);
	}
	
	public static void retirerInstanceRegle(Regle regle, ArrayList<Instance> listeInstances) {
		
		for (Iterator<Instance> it = listeInstances.iterator(); it.hasNext(); ) {
			Instance instance = it.next();
			if(regle.instanceVerfieRegle(instance)) {
				it.remove();
			}
				
			
			
		}
	}
} 


