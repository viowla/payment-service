package kz.iitu.paymentservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payment/api")
public class PaymentController {


    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets/{ticketId}")
    public Ticket getTicketBuId(@PathVariable String ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return ticket;
    }

}
