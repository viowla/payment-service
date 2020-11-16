package kz.iitu.paymentservice;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    @HystrixCommand(
            fallbackMethod = "getUserTicketFallback",
            threadPoolKey = "getUserTicket",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "100"),
                    @HystrixProperty(name = "maxQueueSize", value = "50")
            }
    )
    public UserTicket getUserTicket(String userId){
        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange("http://payment-info-service/ticket/info/" + userId,
                HttpMethod.GET, entity, UserTicket.class).getBody();
    }

    public UserTicket getUserMovieFallback(String movieId) {
        UserTicket userMovie = new UserTicket();
        List<Ticket> list = new ArrayList<>();
        list.add(new Ticket("-1", 0.0));
        userMovie.setUserTickets(list);

        return userMovie;
    }
}
