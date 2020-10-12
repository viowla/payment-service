package kz.iitu.paymentservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {

    private String id;
    private double cost;

    public Ticket() {
    }

    public Ticket(String id, double cost) {
        this.id = id;
        this.cost = cost;
    }
}
