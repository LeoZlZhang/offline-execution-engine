package engineFoundation.Gear;


import engineFoundation.Flow.Flow;

public class Gear {
    private String name;

    private Flow[] flows;

    public String getName() {
        return name;
    }

    public Flow[] getFlows() {
        return flows;
    }


    @Override
    public String toString() {
        return name;
    }
}
