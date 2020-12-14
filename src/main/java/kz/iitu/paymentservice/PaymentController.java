package kz.iitu.paymentservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable String id) {
        return new ResponseEntity<>(ticketService.getPayment(id), HttpStatus.OK);
    }

    @GetMapping("/payment/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return new ResponseEntity<>(paymentRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/payment/create")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment order) {
        return new ResponseEntity<>(ticketService.createPayment(order), HttpStatus.CREATED);
    }

}
