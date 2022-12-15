import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static class Crisp{
        public String name;
        public int val;

        public Crisp(String name, int val) {
            this.name = name;
            this.val = val;
        }
    }
    public static void main(String[] args) {
        ArrayList<Variable> variables = new ArrayList<>();
        ArrayList<FuzzySet> fuzzySets = new ArrayList<>();
        ArrayList<Rule> rules = new ArrayList<>();
        ArrayList<Crisp> crispVal = new ArrayList<>();

        Variable proj_funding = new Variable("proj_funding", types.IN,0,100);
        Variable exp_level = new Variable("exp_level", types.IN,0,60);
        Variable risk = new Variable("risk", types.OUT,0,100);
        variables.add(proj_funding);
        variables.add(exp_level);
        variables.add(risk);

        FuzzySet expSet1 = new FuzzySet(exp_level,dataTypes.TRI,"beginner",new ArrayList<>(Arrays.asList(0,15,30)));
        FuzzySet expSet2 = new FuzzySet(exp_level,dataTypes.TRI,"intermediate",new ArrayList<>(Arrays.asList(15,30,45)));
        FuzzySet expSet3 = new FuzzySet(exp_level,dataTypes.TRI,"expert",new ArrayList<>(Arrays.asList(30,60,60)));

        FuzzySet projSet1 = new FuzzySet(proj_funding,dataTypes.TRAP,"very_low",new ArrayList<>(Arrays.asList(0,0,10,30)));
        FuzzySet projSet2 = new FuzzySet(proj_funding,dataTypes.TRAP,"low",new ArrayList<>(Arrays.asList(10,30,40,60)));
        FuzzySet projSet3 = new FuzzySet(proj_funding,dataTypes.TRAP,"medium",new ArrayList<>(Arrays.asList(40,60,70,90)));
        FuzzySet projSet4 = new FuzzySet(proj_funding,dataTypes.TRAP,"high",new ArrayList<>(Arrays.asList(70,90,100,100)));

        FuzzySet riskSet1 = new FuzzySet(risk,dataTypes.TRI,"low",new ArrayList<>(Arrays.asList(0,25,50)));
        FuzzySet riskSet2 = new FuzzySet(risk,dataTypes.TRI,"normal",new ArrayList<>(Arrays.asList(25,50,75)));
        FuzzySet riskSet3 = new FuzzySet(risk,dataTypes.TRI,"high",new ArrayList<>(Arrays.asList(50,100,100)));

        fuzzySets.add(expSet1);
        fuzzySets.add(expSet2);
        fuzzySets.add(expSet3);

        fuzzySets.add(projSet1);
        fuzzySets.add(projSet2);
        fuzzySets.add(projSet3);
        fuzzySets.add(projSet4);

        fuzzySets.add(riskSet1);
        fuzzySets.add(riskSet2);
        fuzzySets.add(riskSet3);

        Rule rule1 = new Rule();
        rule1.operators.add(operTypes.OR);
        rule1.inputVariables.add(new VarSet("proj_funding","high"));
        rule1.inputVariables.add(new VarSet("exp_level","expert"));
        rule1.setOutputVar(new VarSet("risk","low"));

        Rule rule2 = new Rule();
        rule2.operators.add(operTypes.AND);
        rule2.inputVariables.add(new VarSet("proj_funding","medium"));
        rule2.inputVariables.add(new VarSet("exp_level","intermediate"));
        rule2.setOutputVar(new VarSet("risk","normal"));

        Rule rule3 = new Rule();
        rule3.operators.add(operTypes.AND);
        rule3.inputVariables.add(new VarSet("proj_funding","medium"));
        rule3.inputVariables.add(new VarSet("exp_level","beginner"));
        rule3.setOutputVar(new VarSet("risk","normal"));

        Rule rule4 = new Rule();
        rule4.operators.add(operTypes.AND);
        rule4.inputVariables.add(new VarSet("proj_funding","low"));
        rule4.inputVariables.add(new VarSet("exp_level","beginner"));
        rule4.setOutputVar(new VarSet("risk","high"));

        Rule rule5 = new Rule();
        rule5.operators.add(operTypes.AND);
        rule5.inputVariables.add(new VarSet("proj_funding","very_low"));
        rule5.inputVariables.add(new VarSet("exp_level","expert",true));
        rule5.setOutputVar(new VarSet("risk","high"));

        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);
        rules.add(rule4);
        rules.add(rule5);

        crispVal.add(new Crisp("proj_funding", 50));
        crispVal.add(new Crisp("exp_level",40));

        Simulation run = new Simulation(variables,fuzzySets,rules,crispVal);
        run.Fuzzification();
        for (int i = 0; i < run.membership.size(); i++){
            System.out.print(run.membership.get(i).name + "\t");
            System.out.print(run.membership.get(i).varName+ "\t");
            System.out.print(run.membership.get(i).val+"\n");
        }
        run.Inference();
        for (int i = 0; i < run.membershipOut.size(); i++){
            System.out.print(run.membershipOut.get(i).name + "\t");
            System.out.print(run.membershipOut.get(i).varName+ "\t");
            System.out.print(run.membershipOut.get(i).val+"\n");
        }
        System.out.println(run.Defuzzification());




    }
}