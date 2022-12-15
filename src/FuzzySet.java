import java.util.ArrayList;
import java.util.Collections;

enum dataTypes {
    TRI, TRAP
}

public class FuzzySet {
    Variable variable;
    dataTypes type;
    String setName;
    ArrayList<Integer> values;
    ArrayList<Point> points = new ArrayList<>();

    public FuzzySet(Variable variable, dataTypes type, String setName, ArrayList<Integer> values) {
        this.variable = variable;
        this.type = type;
        this.setName = setName;
        this.values = new ArrayList<>(values);
        if (type == dataTypes.TRI) {
            for (int i = 0; i < values.size(); i++) {
                if (i == 1){
                    this.points.add(new Point(values.get(i), 1));
                }else{
                    this.points.add(new Point(values.get(i), 0));
                }
            }
        }else{
            for (int i =0; i < values.size(); i++) {
                if (i == 1 || i ==2){
                    this.points.add(new Point(values.get(i), 1));
                }else{
                    this.points.add(new Point(values.get(i), 0));
                }
            }
        }
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public dataTypes getType() {
        return type;
    }

    public void setType(dataTypes type) {
        this.type = type;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }
}
