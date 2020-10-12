package kz.iitu.paymentservice;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Component
public class TicketService {

    @Autowired
    private RestTemplate restTemplate;

    public Ticket getTicketById(String id){
        Ticket ticket = restTemplate.getForObject("http://ticket-info-service/ticket/"+
                id, Ticket.class);
        return ticket;
    }

}
