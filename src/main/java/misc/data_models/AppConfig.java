package misc.data_models;

import java.util.List;

 class AppConfig {

    private String chain;

    private String contractAddress;

    private double devFee;

    private List<Plan> plans;

    public String getChain() {
        return chain;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public double getDevFee() {
        return devFee;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public void setDevFee(double devFee) {
        this.devFee = devFee;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "chain='" + chain + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", devFee=" + devFee +
                ", plans=" + plans +
                '}';
    }
}
