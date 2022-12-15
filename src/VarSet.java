public class VarSet {
    String varName;
    String set;
    Boolean not = false;


    public VarSet(String varName, String set) {
        this.varName = varName;
        this.set = set;
        this.not = false;
    }
    public VarSet(String varName, String set,Boolean not) {
        this.varName = varName;
        this.set = set;
        this.not = not;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
