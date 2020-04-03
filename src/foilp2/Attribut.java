package foilp2;

import java.util.List;

public class Attribut {

    private String name;
    private List<String> values;
    private float gain;

    public Attribut(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

    public float getGain() {
        return gain;
    }

    public void setGain(float gain) {
        this.gain = gain;
    }

    @Override
    public String toString() {
        return "Attribut{" +
                "name='" + name + '\'' +
                ", gain=" + gain +
                ", possibilities=" + String.join(",", values) +
                "}";
    }
}
