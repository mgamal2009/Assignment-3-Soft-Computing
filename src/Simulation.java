import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class output {
    String varName;
    String name;
    double val;

    public output(String varName, String name, double val) {
        this.varName = varName;
        this.name = name;
        this.val = val;
    }
}

public class Simulation {
    ArrayList<Variable> variables = new ArrayList<>();
    ArrayList<FuzzySet> fuzzySets = new ArrayList<>();
    ArrayList<Rule> rules = new ArrayList<>();
    ArrayList<Main.Crisp> crispVal = new ArrayList<>();
    ArrayList<output> membership = new ArrayList();
    ArrayList<output> membershipOut = new ArrayList();

    public Simulation(ArrayList<Variable> variables, ArrayList<FuzzySet> fuzzySets, ArrayList<Rule> rules, ArrayList<Main.Crisp> crispVal) {
        this.variables = variables;
        this.fuzzySets = fuzzySets;
        this.rules = rules;
        this.crispVal = crispVal;
    }

    public void Fuzzification() {
        for (Main.Crisp val : crispVal) {
            for (FuzzySet fuzzy : fuzzySets) {
                if (fuzzy.getVariable().getType() == types.IN) {
                    if (val.name.equals(fuzzy.getVariable().name)) {
                        if (val.val >= Collections.min(fuzzy.getValues()) && val.val <= Collections.max(fuzzy.getValues())) {
                            for (int i = 0; i < fuzzy.points.size(); i++) {
                                if (val.val == fuzzy.getValues().get(i)) {
                                    membership.add(new output(fuzzy.getVariable().getName(), fuzzy.getSetName(), fuzzy.points.get(i).yAxis));
                                    break;
                                }
                                if (val.val >= fuzzy.getValues().get(i) && val.val <= fuzzy.getValues().get(i + 1)) {
                                    double slope = (fuzzy.points.get(i + 1).yAxis - fuzzy.points.get(i).yAxis) * 1.0 / (fuzzy.points.get(i + 1).xAxis - fuzzy.points.get(i).xAxis) * 1.0;
                                    double c = fuzzy.points.get(i).yAxis - (slope * fuzzy.points.get(i).xAxis);
                                    double out = slope * val.val + c;
                                    membership.add(new output(fuzzy.getVariable().getName(), fuzzy.getSetName(), out));
                                    break;
                                }
                            }
                        } else {
                            membership.add(new output(fuzzy.getVariable().getName(), fuzzy.getSetName(), 0.0));
                        }
                    }
                }
            }
        }
    }

    public double find(VarSet var , ArrayList<output> membership) {
        for (int i = 0; i < membership.size(); i++) {
            if (membership.get(i).varName.equals(var.varName) && membership.get(i).name.equals(var.set))
                if (var.not) {
                    return 1.0 - membership.get(i).val;
                } else
                    return membership.get(i).val;
        }
        return -1.0;
    }

    public void Inference() {
        ArrayList<output> tempOut = new ArrayList<>();
        for (Rule rule : rules) {
            double temp = 0.0;
            for (int i = 0; i < rule.operators.size(); i++) {
                if (i == 0) {
                    if (rule.operators.get(i).equals(operTypes.AND)) {
                        temp = Double.min(find(rule.inputVariables.get(i),membership), find(rule.inputVariables.get(i + 1),membership));
                    } else {
                        temp = Double.max(find(rule.inputVariables.get(i),membership), find(rule.inputVariables.get(i + 1),membership));
                    }
                } else {
                    if (rule.operators.get(i).equals(operTypes.AND)) {
                        temp = Double.min(temp, find(rule.inputVariables.get(i + 1),membership));
                    } else {
                        temp = Double.max(temp, find(rule.inputVariables.get(i + 1),membership));
                    }
                }
            }
            tempOut.add(new output(rule.getOutputVar().varName, rule.getOutputVar().set, temp));
        }
        ArrayList<Double> temp;
        ArrayList<String> sets = new ArrayList<>();
        for (FuzzySet set : fuzzySets) {
            if (set.getVariable().getType() == types.OUT) {
                sets.add(set.getSetName());
            }
        }
        for (int j = 0; j < sets.size(); j++) {
            temp = new ArrayList<>();
            String varName = "";
            for (int i = 0; i < tempOut.size(); i++) {
                if (tempOut.get(i).name.equals(sets.get(j))) {
                    varName = tempOut.get(i).varName;
                    temp.add(tempOut.get(i).val);
                }
            }
            membershipOut.add(new output(varName, sets.get(j), Collections.max(temp)));
        }

    }

    double find_Centroid(ArrayList<Point> points) {
        double[] ans = new double[2];

        int n = points.size();
        double signedArea = 0;

        // For all vertices
        for (int i = 0; i < n; i++) {

            double x0 = points.get(i).xAxis, y0 = points.get(i).yAxis;
            double x1 = points.get((i+1) % n).xAxis, y1 = points.get((i+1) % n).yAxis;

            // Calculate value of A
            // using shoelace formula
            double A = (x0 * y1) - (x1 * y0);
            signedArea += A;

            // Calculating coordinates of
            // centroid of polygon
            ans[0] += (x0 + x1) * A;
            ans[1] += (y0 + y1) * A;
        }

        signedArea *= 0.5;
        ans[0] = (ans[0]) / (6 * signedArea);
        ans[1] = (ans[1]) / (6 * signedArea);

        return ans[0];
    }


    public double Defuzzification() {
        double total = 0.0;
        double sum = 0.0;
        for (FuzzySet fuzzy : fuzzySets){
            if (fuzzy.getVariable().getType() == types.OUT){
                double tmp = find_Centroid(fuzzy.getPoints());
                VarSet dummy = new VarSet(fuzzy.variable.getName(),fuzzy.setName);
                double ret = find(dummy,membershipOut);
                total += tmp * ret ;
                sum += ret;
            }
        }
        return total/sum;
    }
}
