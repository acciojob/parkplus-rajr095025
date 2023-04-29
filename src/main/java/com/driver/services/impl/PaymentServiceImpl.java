package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    //Attempt a payment of amountSent for reservationId using the given mode ("cASh", "card", or "upi")
    //If the amountSent is less than bill, throw "Insufficient Amount" exception, otherwise update payment attributes
    //If the mode contains a string other than "cash", "card", or "upi" (any character in uppercase or lowercase), throw "Payment mode not detected" exception.
    //Note that the reservationId always exists
        /*
         Implement a payment functionality that allows users to make a payment for a specific reservation using different modes of payment (cash, card, or UPI).
         The system should validate the amount sent by the user and compare it with the bill amount for the reservation
        If the amount sent is less than the bill, the system should throw an exception "Insufficient Amount".
         */

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Payment payment = new Payment();
        PaymentMode paymentMode;


        if(mode.equalsIgnoreCase("cash")){
            paymentMode=PaymentMode.CASH;
        }
        else if (mode.equalsIgnoreCase("card")) {
            paymentMode=PaymentMode.CARD;
        }
        else if (mode.equalsIgnoreCase("upi")) {
            paymentMode=PaymentMode.UPI;
        }
        else{
            throw new Exception("Payment mode not detected");
        }

        Reservation reservation = reservationRepository2.findById(reservationId).get();
        reservation.getSpot().setOccupied(false);
        int totalAmount = reservation.getSpot().getPricePerHour() * reservation.getNumberOfHours();
        if(totalAmount>amountSent){
            throw new Exception("Insufficient Amount");
        }
        payment.setPaymentMode(paymentMode);
        payment.setReservation(reservation);
        payment.setPaymentCompleted(true);
        reservationRepository2.save(reservation);

        return  payment;
    }
    /*
    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.findById(reservationId).get();

        int bill = reservation.getSpot().getPricePerHour() * reservation.getNumberOfHours();
        if(amountSent < bill){
            throw new Exception("Insufficient Amount");
        }
        Payment payment = new Payment();
        mode = mode.toUpperCase();
        if(mode != "CARD" || !mode.equals("CASH") && mode != "UPI"){
            throw new Exception("Payment mode not detected");
        }
        payment.setPaymentMode(PaymentMode.valueOf(mode));
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return payment;
    }
    */

}
