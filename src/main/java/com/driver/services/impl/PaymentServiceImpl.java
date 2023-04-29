package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

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
}
