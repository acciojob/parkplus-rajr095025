package com.driver.controllers;

import com.driver.model.Payment;
import com.driver.services.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/payment")
public class PaymentController {
	
	@Autowired
    PaymentServiceImpl paymentService;

    @PostMapping("/pay")
    public Payment pay(@RequestParam Integer reservationId, @RequestParam Integer amountSent, @RequestParam String mode) throws Exception{
        //Attempt a payment of amountSent for reservationId using the given mode ("cASh", "card", or "upi")
        //If the amountSent is less than bill, throw "Insufficient Amount" exception, otherwise update payment attributes
        //If the mode contains a string other than "cash", "card", or "upi" (any character in uppercase or lowercase), throw "Payment mode not detected" exception.
        //Note that the reservationId always exists
        /*
         Implement a payment functionality that allows users to make a payment for a specific reservation using different modes of payment (cash, card, or UPI).
         The system should validate the amount sent by the user and compare it with the bill amount for the reservation
        If the amount sent is less than the bill, the system should throw an exception "Insufficient Amount".
         */
        return paymentService.pay(reservationId, amountSent, mode);
    }
}
