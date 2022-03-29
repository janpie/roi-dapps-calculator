package misc.data_models;

public class Plan {

    private Integer days;
    private Double totalReturn;
    private Double withdrawalFee;

    public Plan(Integer days, Double totalReturn, Double withdrawalFee) {
        this.days = days;
        this.totalReturn = totalReturn;
        this.withdrawalFee = withdrawalFee;
    }

    public Plan() {
    }

    public Integer getDays() {
        return days;
    }

    public Double getTotalReturn() {
        return totalReturn;
    }

    public Double getWithdrawalFee() {
        return withdrawalFee;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "days=" + days +
                ", totalReturn=" + totalReturn +
                ", withdrawalFee=" + withdrawalFee +
                '}';
    }
}
