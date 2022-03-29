package misc.data_models;

import java.time.LocalDateTime;

public class Transaction {

    private Double value;
    private Boolean isSuccess;
    private LocalDateTime dateTime;
    private Integer plan;

    public Double getValue() {
        return value;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "value=" + value +
                ", isSuccess=" + isSuccess +
                ", dateTime=" + dateTime +
                ", plan=" + plan +
                '}';
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }
}
