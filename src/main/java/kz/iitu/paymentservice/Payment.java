package kz.iitu.paymentservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private double cost;
    private String userName;

    public Payment() {
    }

    public Payment(double cost, String userName) {
        this.cost = cost;
        this.userName = userName;
    }
}
