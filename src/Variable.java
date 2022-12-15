enum types {
    IN,OUT
}

public class Variable {
    String name;
    types type;
    int LowerRange;
    int upperRange;

    public Variable(String name, types type, int lowerRange, int upperRange) {
        this.name = name;
        this.type = type;
        LowerRange = lowerRange;
        this.upperRange = upperRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public types getType() {
        return type;
    }

    public void setType(types type) {
        this.type = type;
    }

    public int getLowerRange() {
        return LowerRange;
    }

    public void setLowerRange(int lowerRange) {
        LowerRange = lowerRange;
    }

    public int getUpperRange() {
        return upperRange;
    }

    public void setUpperRange(int upperRange) {
        this.upperRange = upperRange;
    }
}
