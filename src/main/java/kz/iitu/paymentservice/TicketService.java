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

    @Autowired
    private PaymentRepository paymentRepository;
    /*private UserRepository userRepository;*/

    @HystrixCommand(fallbackMethod = "getPayment",
            threadPoolKey = "getPaymentPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="20"),
                    @HystrixProperty(name="maxQueueSize", value="10"),
            })
    public Payment getPayment(String id) {

        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("http://payment-service/id/"+id, HttpMethod.GET, entity,Payment.class).getBody();

        //return paymentRepository.findById(id).get();
    }

    @HystrixCommand(fallbackMethod = "createPayment",
            threadPoolKey = "createPaymentPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="20"),
                    @HystrixProperty(name="maxQueueSize", value="10"),
            })
    public Payment createPayment(Payment order) {

        return paymentRepository.save(order);
    }

    public Payment pay(String id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("There is no payment check with such id");
        }
        Payment payment = paymentRepository.findById(id).get();
        return paymentRepository.save(payment);
    }

}
