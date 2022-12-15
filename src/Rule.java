import java.util.ArrayList;
import java.util.Collections;

enum operTypes {
    OR,AND
}
public class Rule {
    ArrayList<operTypes> operators = new ArrayList<>();
    ArrayList<VarSet> inputVariables = new ArrayList<>();
    VarSet outputVar;

    public ArrayList<operTypes> getOperators() {
        return operators;
    }

    public void setOperators(ArrayList<operTypes> operators) {
        this.operators = new ArrayList<>(operators);
    }

    public ArrayList<VarSet> getInputVariables() {
        return inputVariables;
    }

    public void setInputVariables(ArrayList<VarSet> inputVariables) {
        this.inputVariables = new ArrayList<>(inputVariables);
    }

    public VarSet getOutputVar() {
        return outputVar;
    }

    public void setOutputVar(VarSet outputVar) {
        this.outputVar = outputVar;
    }
}
