package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;

    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        try {
            Reservation reservation = new Reservation();
            reservation.setNumberOfHours(timeInHours);
            if (userRepository3.existsById(userId) || parkingLotRepository3.existsById(parkingLotId)) {
                throw new Exception("Cannot make reservation");

            }
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
            int minimumPrice = Integer.MAX_VALUE;
            Spot getSpot = null;
            List<Spot> spotList = parkingLot.getSpotList();
            for (Spot spot : spotList) {
                boolean availableSpot = false;
                if (numberOfWheels > 4 && spot.getSpotType().equals(SpotType.OTHERS)) {
                    availableSpot = true;
                } else if (numberOfWheels > 2 && numberOfWheels <= 4 && (spot.getSpotType().equals(SpotType.OTHERS) || spot.getSpotType().equals(SpotType.FOUR_WHEELER))) {
                    availableSpot = true;
                } else if (numberOfWheels <= 2) {
                    availableSpot = true;
                }


                if (!spot.getOccupied() && availableSpot) {
                    minimumPrice = Math.min(minimumPrice, spot.getPricePerHour());
                    getSpot = spot;
                }
            }
            if (getSpot == null) {
                throw new Exception("Cannot make reservation");
            }
            List<Reservation> reservationList = getSpot.getReservationList();

            reservationList.add(reservation);
            reservation.setSpot(getSpot);
            User user = userRepository3.findById(userId).get();
            reservation.setUser(user);
            List<Reservation> reservationList1 = user.getReservationList();
            reservationList1.add(reservation);
            getSpot.setOccupied(true);
            getSpot.setReservationList(reservationList);
            user.setReservationList(reservationList1);
            spotRepository3.save(getSpot);
            userRepository3.save(user);

            return reservation;
        }catch (Exception e){
            return  null;
        }
    }
    /*
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
        ParkingLot parkingLot;
        User user;
        List<Spot> spotList;
        try{
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();
            user = userRepository3.findById(userId).get();
            spotList = spotRepository3.findAll();
        }
        catch (Exception e){
            throw new Exception("Cannot make reservation");
        }
        Spot spotAvailable = null;
        for(Spot spot : spotList){
            int spotTypeWheel = 0;
            if(spot.getSpotType() == SpotType.TWO_WHEELER){
                spotTypeWheel = 2;
            }
            else if(spot.getSpotType() == SpotType.FOUR_WHEELER){
                spotTypeWheel = 4;
            }
            else {
                spotTypeWheel = Integer.MAX_VALUE;
            }
            int minPrice = Integer.MAX_VALUE;
            if(!spot.isOccupied() && spotTypeWheel >= numberOfWheels && spot.getPricePerHour() < minPrice){
                minPrice = spot.getPricePerHour();
                spotAvailable = spot;
            }
        }
        if(spotAvailable == null){
            throw new Exception("Cannot make reservation");
        }

        spotAvailable.setOccupied(true);
        int bill = spotAvailable.getPricePerHour() * timeInHours;

        Reservation reservation = new Reservation();
        reservation.setSpot(spotAvailable);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);

        user.getReservationList().add(reservation);

        spotAvailable.getReservationList().add(reservation);

        reservationRepository3.save(reservation);
        //spotRepository3.save(spotAvailable);
        return reservation;
    }
    */

}
