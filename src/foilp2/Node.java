package foilp2;

import java.util.*;
import java.util.stream.Collectors;

public class Node {

    private static final float ENTROPY_THRESHOLD = 0.6f;
    private static float MINIMUM_INSTANCES;
    private static String POSITIVE_VALUE;
    private static String POSITIVE_ATTRIBUT;

    private Node parent;
    private List<Node> children = new ArrayList<>();
    private Map<String, String> litterals;

    public Node(final Node parent, final Map<String, String> litterals) {
        this.parent = parent;
        this.litterals = litterals;
    }

    public Node(final Node parent, String positiveAttribut, String positiveValue, int minimumInstancesPerLeaf) {
        this.parent = parent;
        litterals = new LinkedHashMap<>();
        MINIMUM_INSTANCES = minimumInstancesPerLeaf;
        POSITIVE_ATTRIBUT = positiveAttribut;
        POSITIVE_VALUE = positiveValue;
    }

    public List<String> execute(List<Attribut> remainingAttributs, final List<Instance> remainingInstances) {

//        System.out.println("---------- remainingInstances ------------");
//        remainingInstances.forEach(System.out::println);

//        int p = 0;
//        int N = 0;
//        for(Instance instance : remainingInstances) {
//            if(instance.isPositive()) p++;
//            else N++;
//        }
//        System.out.println("p=" + p + ", n=" + N);

        // if we do not have anymore remainingInstances
        // or too few positive instances
        // then return;
        if(remainingInstances.size() == 0 || getNbPositive(remainingInstances) < MINIMUM_INSTANCES) {
//            System.out.println("no more remainingInstances to work on");
//            System.out.println("---------------");
//            printRule(remainingInstances);
            return Collections.emptyList();
        }

//        System.out.println("value=" + Math.abs(getNbPositive(remainingInstances) - getNbNegative(remainingInstances)) * 100 / remainingInstances.size());

        // if we do not hav anymore positive cases, then return
        if(getNbPositive(remainingInstances) == 0) {
//            System.out.println("no more positiv cases");
//            System.out.println("---------------");
            return Collections.emptyList();
        }
        // if the current entropy is lower than ENTROPY_THRESHOLD
        // we think this is sufficient
        if(getEntropy(getNbPositive(remainingInstances), getNbNegative(remainingInstances)) < ENTROPY_THRESHOLD && getNbPositive(remainingInstances) > getNbNegative(remainingInstances)) {
//            System.out.println("new rule");
//            System.out.println("------rule---------");
            return new ArrayList<String>(){{ add(getRule(remainingInstances)); }};
        }

        // if we do not have anymore attributes, then print the rule
        if(remainingAttributs.size() == 0) {
//            System.out.println("no more attributs to work on");
//            printRule(remainingInstances);
//            System.out.println("---------------");
            return Collections.emptyList();
        }

//        String mapAsString = this.litterals.keySet().stream()
//                .map(key -> key + "=" + this.litterals.get(key))
//                .collect(Collectors.joining(", ", "{", "}"));
//
//        System.out.println("CURRENT:" + mapAsString
//                + ", Positives=" + getNbPositive(remainingInstances)
//                + ", Negatives=" + getNbNegative(remainingInstances));

        // choose the best attribute to continue
//        System.out.println("before gain");
//        remainingAttributs.forEach(System.out::println);
        remainingAttributs.forEach(a -> setGain(a, remainingInstances));
        remainingAttributs = remainingAttributs
                .stream()
                .sorted(Comparator.comparingDouble(Attribut::getGain))
                .collect(Collectors.toList());

//        System.out.println("sort");
//        remainingAttributs.forEach(System.out::println);
//        System.out.println("attribut chosen : " + remainingAttributs.get(0).getName() + " with gain=" + remainingAttributs.get(0).getGain());

        final Attribut currentAttribut = remainingAttributs.get(0);

        // update attributes
        final List<Attribut> updatedAttributs = new ArrayList<>(remainingAttributs);
        updatedAttributs.remove(0);

        final List<String> rulesFound = new ArrayList<>();
        for(String value : currentAttribut.getValues()) {
            final Map<String, String> map = new HashMap<>(this.litterals);
            map.put(currentAttribut.getName(), value);

            Node n = new Node(this, map);
            this.children.add(n);

            // update instances
            final List<Instance> updatedInstances = new ArrayList<>();
            remainingInstances.forEach(instance -> {
                if(value.equals(instance.getValue(currentAttribut.getName()))) {
                    updatedInstances.add(instance);
                }
            });


            rulesFound.addAll(n.execute(updatedAttributs, updatedInstances));
        }

        return rulesFound;
    }

    private void setGain(Attribut attribut, List<foilp2.Instance> instances) {

        int totalPositif = (int)instances.stream().filter(Instance::isPositive).count();
        int totalNegatif = (int)instances.stream().filter(instance -> !instance.isPositive()).count();
        float gain = getEntropy(totalPositif, totalNegatif);
        StringBuilder strGain = new StringBuilder("Gain(" + attribut.getName() + ") = Ent(" + totalPositif + ", " + totalNegatif + ") ");

        for(String value : attribut.getValues()) {

            int positives = 0;
            int negatives = 0;

            for (foilp2.Instance instance : instances) {

                if(value.equals(instance.getValue(attribut.getName()))) {

                    if(instance.isPositive()) positives++;
                    else negatives++;
                }
            }

            gain -= ((float)(positives + negatives) / (totalPositif + totalNegatif)) * getEntropy(positives, negatives);
            strGain.append("- ").append(positives + negatives).append("/").append(totalPositif + totalNegatif).append(" * Ent(").append(positives).append(", ").append(negatives).append(") ");
        }

        strGain.append(" = ").append(gain);
//        System.out.println(strGain.toString());
        attribut.setGain(gain);
    }

    private float getEntropy(int casPositifs, int casNegatifs) {

//        System.out.println("getEntropy(" + casPositifs + ", " + casNegatifs + ")");
        if(casPositifs == 0 || casNegatifs == 0) return 0;
        if(casPositifs == casNegatifs) return 1;

        float p = (float)casPositifs / (float)(casPositifs + casNegatifs);
        float n = (float)casNegatifs / (float)(casPositifs + casNegatifs);

//        System.out.println("p=" + p + ", n=" + n);

//        System.out.println("- " + p + " * log2(" + p + ") - " + n + " * log2(" + n + ")");
        float res = - p * log2(p) - n * log2(n);
//        System.out.println("-------------- JE CALCUL ET JE TROUVE : " + res);

        return res;
    }

    private float log2(float n) {
        return (float)(Math.log(n) / Math.log(2));
    }

    public static int getNbPositive(final List<Instance> instances) {
        int res = 0;

        for(Instance instance : instances)
            if(instance.isPositive())
                res++;

        return res;
    }

    public static int getNbNegative(final List<Instance> instances) {
        return instances.size() - getNbPositive(instances);
    }

    private String getRule(final List<Instance> instances) {
        String mapAsString = this.litterals.keySet().stream()
                .map(key -> key + " = " + this.litterals.get(key))
                .collect(Collectors.joining(" ET ", "", ""));

        return "SI " + mapAsString
                + " ALORS " + POSITIVE_ATTRIBUT + "=" + POSITIVE_VALUE
                + ", Positives=" + getNbPositive(instances)
                + ", Negatives=" + getNbNegative(instances);
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Map<String, String> getLitterals() {
        return litterals;
    }
}
