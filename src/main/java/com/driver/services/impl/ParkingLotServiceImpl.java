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

import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    /*
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot(name,address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) throws Exception {
        /*
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("parking lot id is not valid");
        }
        */
    /*
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
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
        /*
        Spot spot;
        try{
            spot = spotRepository1.findById(spotId).get();
        }
        catch (Exception e){
            throw new Exception("spot id is not valid");
        }
        */
    /*
        Spot spot = spotRepository1.findById(spotId).get();
        spotRepository1.delete(spot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) throws Exception {
        /*
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("parking lot id is not valid");
        }
        */
    /*
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        /*
        Spot spot;
        try{
            spot = spotRepository1.findById(spotId).get();
        }
        catch (Exception e){
            throw new Exception("spot id is not valid");
        }
        */
        /*
        Spot spot = spotRepository1.findById(spotId).get();
        spot.setPricePerHour(pricePerHour);

        spotRepository1.save(spot);
        return spot;
    }
    */
    /*
    @Override
    public void deleteParkingLot(int parkingLotId) throws Exception {
        /*
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("parking lot id is not valid");
        }
        */
        /*
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        parkingLotRepository1.delete(parkingLot);
    }
    */
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot(name,address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }
        catch (Exception e){

        }
        Spot spot = new Spot();
        spot.setPricePerHour(pricePerHour);
        if(numberOfWheels<=2){
            spot.setSpotType(SpotType.TWO_WHEELER);
        }else if(numberOfWheels<=4){
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }else{
            spot.setSpotType(SpotType.OTHERS);
        }
        spot.setOccupied(false);
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();

        spotList.add(spot);
        spot.setParkingLot(parkingLot);
        parkingLot.setSpotList(spotList);
        parkingLotRepository1.save(parkingLot);

        return  spot;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        Spot spot=null;
        List<Spot> spotList=parkingLot.getSpotList();
        for(Spot spot1:spotList){
            if(spot1.getId()==spotId)
                spot=spot1;
        }



        spot.setPricePerHour(pricePerHour);



        spotList.add(spot);
        for (Spot spots : spotList) {
            if (spots.getId() == spotId) {
                spots.setPricePerHour(pricePerHour);
            }
        }
        spot.setParkingLot(parkingLot);

        spotRepository1.save(spot);


        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        ParkingLot parkingLot = null;
        if( parkingLotRepository1.existsById(parkingLotId)) {
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
            List<Spot> spotList = parkingLot.getSpotList();
            for (Spot spot : spotList) {
                if( spotRepository1.existsById(spot.getId())) {
                    deleteSpot(spot.getId());
                }
            }
        }
        parkingLotRepository1.deleteById(parkingLotId);

    }
}
