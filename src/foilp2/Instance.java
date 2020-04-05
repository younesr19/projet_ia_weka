package foilp2;

import java.util.Map;
import java.util.stream.Collectors;

public class Instance {

    private Map<String, String> litterals;
    private boolean isPositive;

    public Instance(Map<String, String> litterals, boolean isPositive) {
        this.litterals = litterals;
        this.isPositive = isPositive;
    }

    public Map<String, String> getLitterals() {
        return litterals;
    }

    public String getValue(final String key) {
        return litterals.get(key);
    }

    public boolean isPositive() {
        return isPositive;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "litterals=" + litterals.keySet().stream().map(key -> key + "=" + litterals.get(key)).collect(Collectors.joining(", ", "{", "}")) +
                ", isPositive='" + isPositive + '\'' +
                '}';
    }
}
