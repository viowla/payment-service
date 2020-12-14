package kz.iitu.paymentservice;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private double cost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ticket_movie",
            joinColumns = {@JoinColumn(name = "ticket_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")}
    )
    private List<Movie> movies = new ArrayList<>();

}
