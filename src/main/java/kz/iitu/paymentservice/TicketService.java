package kz.iitu.paymentservice;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableHystrix
@EnableHystrixDashboard
public class TicketService {

    @Autowired
    private RestTemplate restTemplate;

    private PaymentRepository paymentRepository;
    private UserRepository userRepository;

    public Payment getPayment(String id) {

        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("There is no order with such id");
        }

        return paymentRepository.findById(id).get();

    }

    @HystrixCommand(fallbackMethod = "createPayment",
            threadPoolKey = "createPaymentPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="20"),
                    @HystrixProperty(name="maxQueueSize", value="10"),
            })
    public Payment createPayment(Payment order) {
        Payment payment = new Payment();
        if (!userRepository.existsById(order.getUser().getId())) {
            throw new RuntimeException("There is no user with such id");
        }
        payment.setUser(userRepository.findById(order.getUser().getId()).get());
        for (Ticket ticket : order.getMovies()) {
            Ticket existingMovie = restTemplate.getForObject("http://ticket-service/ticket/" + ticket.getId(), Ticket.class);
            payment.getMovies().add(existingMovie);
        }
        return paymentRepository.save(payment);
    }

    public Payment pay(String id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("There is no payment check with such id");
        }
        Payment payment = paymentRepository.findById(id).get();
        return paymentRepository.save(payment);
    }

}
