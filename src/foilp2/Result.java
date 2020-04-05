package foilp2;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private List<Rule> rules;
    private int totalPositives;
    private int totalNegatives;

    public Result(int totalPositives, int totalNegatives) {
        this.rules = new ArrayList<>();
        this.totalPositives = totalPositives;
        this.totalNegatives = totalNegatives;
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    public void addRule(final Rule rule) {
        this.rules.add(rule);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < rules.size(); i++) {

            str.append("R").append(i+1).append(" : ").append(this.rules.get(i).toString()).append("\n");
        }

        int positiveCovered = this.rules.stream().map(Rule::getPositiveInstances).reduce(0, Integer::sum);
        int negativeCovered = this.rules.stream().map(Rule::getNegativeInstances).reduce(0, Integer::sum);

        return "Rules : \n"
                + str.toString() + "\n"
                + "positive covered : " + positiveCovered + "/" + this.totalPositives + "(" + (positiveCovered * 100 / (float)totalPositives) + "%, we would like this to be close to 100% to cover most of positive instances)\n"
                + "negative covered : " + negativeCovered + "/" + this.totalNegatives + "(" + (negativeCovered * 100 / (float)totalNegatives) + "%, we would like this to be close to 0% to cover no negative instances)";
    }
}
