package foilp2;

import java.util.Map;
import java.util.stream.Collectors;

public class Rule {

    private Map<String, String> litterals;
    private int positiveInstances;
    private int negativeInstances;

    public Rule(final Map<String, String> litterals, int positiveInstances, int negativeInstances) {
        this.litterals = litterals;
        this.positiveInstances = positiveInstances;
        this.negativeInstances = negativeInstances;
    }

    public Map<String, String> getLitterals() {
        return litterals;
    }

    public int getPositiveInstances() {
        return positiveInstances;
    }

    public int getNegativeInstances() {
        return negativeInstances;
    }

    @Override
    public String toString() {
        String mapAsString = this.litterals.keySet().stream()
                .map(key -> key + " = " + this.litterals.get(key))
                .collect(Collectors.joining(" ET ", "", ""));

        return "SI " + mapAsString
                + " ALORS " + Node.getPositiveAttribut() + " = " + Node.getPositiveValue()
                + ", Positives=" + positiveInstances
                + ", Negatives=" + negativeInstances;
    }
}
