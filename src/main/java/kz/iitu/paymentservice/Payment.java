package kz.iitu.paymentservice;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "payment_tickets",
            joinColumns = {@JoinColumn(name = "payment_id", referencedColumnName = "payment_id")},
            inverseJoinColumns = {@JoinColumn(name = "ticket_id", referencedColumnName = "id")}
    )
    private List<Ticket> movies = new ArrayList<>();

}
