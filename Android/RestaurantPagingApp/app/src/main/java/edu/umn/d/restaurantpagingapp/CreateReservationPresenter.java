package edu.umn.d.restaurantpagingapp;

/**
 * Created by tinar on 3/20/2017.
 */

public class CreateReservationPresenter{

    public CreateReservationPresenter(RPAModel model){
        this.model = model;
    }

    public void clickCreateReservation(String name, Integer partySize, String phoneNum) {
        model.createReservation(name, partySize, phoneNum);
    }

    private RPAModel model;
    private Reservation reservation;
}
