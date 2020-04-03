package foilp2;

import main.Executable;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FOILP2 implements Executable {

    private final String filename;

    public FOILP2(final String filename) {
        this.filename = filename;
    }

    @Override
    public void execute() throws IOException {

        System.out.println("\n--------FOILP2------");

        // get the positive value we are looking for
        Attribut lastAttribut = this.getLastAttribut();
        String positiveValue = this.getPositiveValue(lastAttribut);
//        System.out.println("positiveValue=" + positiveValue);

        // get every attributes, instances & last attribut
        List<Attribut> attributs = this.getAttributs();
        attributs.remove(attributs.size() - 1);
        List<foilp2.Instance> instances = this.getInstances(positiveValue);

        // order attributes by entropy increasing
        attributs = attributs.stream()
                .sorted(Comparator.comparingDouble(Attribut::getGain))
                .collect(Collectors.toList());

        Result result = new Result(Node.getNbPositive(instances), Node.getNbNegative(instances));
        Node node = new Node(null, lastAttribut.getName(), positiveValue, instances.size(), result);
        node.execute(attributs, instances);

        System.out.println(result.toString());
    }

    private String getPositiveValue(final Attribut lastAttribut) {
        for(String value : lastAttribut.getValues()) {
            if("oui".equals(value)
                    || "1".equals(value)
                    || "positive".equals(value)
                    || "yes".equals(value))
                return value;

        }
        return lastAttribut.getValues().get(0);
    }

    private List<Attribut> getAttributs() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // Récupération de toutes les instances du fichier et indéxation de toutes les classes
        weka.core.Instances jeuDeTest = new Instances(br);
        Enumeration<Instance> liste_instance = jeuDeTest.enumerateInstances();
        Enumeration<Attribute> liste_attr = jeuDeTest.enumerateAttributes();

        List<Instance> liste = Collections.list(liste_instance);
        List<Attribute> liste_attribut = Collections.list(liste_attr);

        // get every attributes
        List<Attribut> attributs = new ArrayList<>();
        liste_attribut.forEach(a -> {
            List<String> values = new ArrayList<>();

            // get each attributs except the last one (which should be the result)
            for(int i = 0; i < a.numValues(); i++) {
                values.add(a.value(i));
            }
            Attribut attribut = new Attribut(a.name(), values);
            attributs.add(attribut);
//            System.out.println(attribut);
        });

        return attributs;
    }

    private Attribut getLastAttribut() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // Récupération de toutes les instances du fichier et indéxation de toutes les classes
        weka.core.Instances jeuDeTest = new Instances(br);
        Enumeration<Attribute> liste_attr = jeuDeTest.enumerateAttributes();

        List<Attribute> liste_attribut = Collections.list(liste_attr);

        // get the last attribut (which should be the result)
        List<String> values = new ArrayList<>();
        for(int i = 0; i < liste_attribut.get(liste_attribut.size() - 1).numValues() - 1; i++) {
            values.add(liste_attribut.get(liste_attribut.size() - 1).value(i));
        }
        return new Attribut(liste_attribut.get(liste_attribut.size() - 1).name(), values);
    }

    private List<foilp2.Instance> getInstances(final String positiveValue) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filename));

        // Récupération de toutes les instances du fichier et indéxation de toutes les classes
        weka.core.Instances jeuDeTest = new Instances(br);
        Enumeration<Instance> liste_instance = jeuDeTest.enumerateInstances();
        List<Instance> liste = Collections.list(liste_instance);

        // get every instances
        List<foilp2.Instance> instances = new ArrayList<>();
        liste.forEach(instance -> {
            Map<String, String> map = new HashMap<>();

            // get every litterals, except the last one (which should be the result)
            for(int i = 0; i < instance.numAttributes() - 1; i++) {
                map.put(instance.attribute(i).name(), instance.stringValue(i));
            }

            foilp2.Instance instance1 = new foilp2.Instance(map, positiveValue.equals(instance.stringValue(instance.numAttributes() - 1)));
//            System.out.println(instance1.toString());

            instances.add(instance1);
        });

        return instances;
    }
}
