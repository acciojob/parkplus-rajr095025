package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot(name,address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) throws Exception {
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("parking lot id is not valid");
        }
        Spot spot = new Spot();
        spot.setOccupied(false);
        spot.setPricePerHour(pricePerHour);
        if(numberOfWheels == 2){
            spot.setSpotType(SpotType.TWO_WHEELER);
        }
        else if(numberOfWheels == 4){
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }
        else{
            spot.setSpotType(SpotType.OTHERS);
        }
        spot.setParkingLot(parkingLot);
        parkingLot.getSpotList().add(spot);
        parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteSpot(int spotId) throws Exception {
        Spot spot;
        try{
            spot = spotRepository1.findById(spotId).get();
        }
        catch (Exception e){
            throw new Exception("spot id is not valid");
        }
        spotRepository1.delete(spot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) throws Exception {
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("parking lot id is not valid");
        }

        Spot spot;
        try{
            spot = spotRepository1.findById(spotId).get();
        }
        catch (Exception e){
            throw new Exception("spot id is not valid");
        }

        spot.setPricePerHour(pricePerHour);

        spotRepository1.save(spot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) throws Exception {
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("parking lot id is not valid");
        }
        parkingLotRepository1.delete(parkingLot);
    }
}
