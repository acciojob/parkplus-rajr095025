package com.driver.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;
     int pricePerHour;

    @Enumerated(value = EnumType.STRING)  //data type of spottype
     SpotType spotType;

    //The name of the table that contains the column. If absent the column is assumed to be in the primary table.
    @Column(columnDefinition = "TINYINT(1)")
     boolean occupied;


    @ManyToOne  // spot to parking lot relation (child-parent)
    @JoinColumn
    private ParkingLot parkingLot;
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
     List<Reservation> reservationList = new ArrayList<>();

    public Spot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
