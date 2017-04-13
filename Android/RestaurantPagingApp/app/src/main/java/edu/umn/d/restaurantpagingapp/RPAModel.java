package edu.umn.d.restaurantpagingapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff Smith on 3/15/2017.
 */

public class RPAModel implements ModelViewPresenterComponents.Model {
    public RPAModel(){

    }

    /**
     * Here we will instantiate any of the classes that are used by the model
     * for calculations or otherwise.
     */
    public RPAModel(){
    }

    /**
     * Gets one reservation from the waiting list
     * @param index index of the element in the list
     * @return  The element in the waiting list at index.
     */
    public Reservation getReservation(int index){return (Reservation)waitingReservations.get(index);}

    /**
     * Get one reservation from the seated list
     * @param index index of the element in the list
     * @return  The element in the seated list at index.
     */
    public Reservation getSeatedReservation(int index){return (Reservation)seatedReservations.get(index);}

    /**
     * Get a list of reservations from the waiting list
     * @return  The list of reservations
     */
    public List getReservations(){
        return waitingReservations;
    }

    /**
     * Get a list of reservation from the seated list
     * @return  The list of reservations
     */
    public List getSeatedReservations() {return seatedReservations;}


    /**
     * Create a reservation and add it to the waiting list.
     * @param name  The name of the reservation
     * @param partySize The size of the party
     * @param phoneNumber   The phone number of the customer
     * @param time  The time when the reservation was created
     * @return  A reference to the reservation that was created.
     */
    public Reservation createReservation(String name, int partySize, String phoneNumber, String time){
        
        Reservation res = new Reservation(name,partySize,phoneNumber,time);
        waitingReservations.add(res);


        return res;

    }

    /**
     * Edit a reservation at a certain index in the waiting list.
     * @param index Index of the reservation
     * @param name  New name for the reservation
     * @param partySize New party size for the reservation
     * @param phoneNumber   New phone number for the reservation
     */
    public void editReservation(int index, String name, int partySize, String phoneNumber){
        Reservation res = (Reservation)waitingReservations.remove(index);
        res.setName(name);
        res.setPartySize(partySize);
        res.setPhoneNumber(phoneNumber);
        waitingReservations.add(index, res);
    }

    /**
     * Edit a reservation at a certain index in the waiting list.
     * @param index Index of the reservation
     * @param name  New name for the reservation
     * @param partySize New party size for the reservation
     * @param phoneNumber   New phone number for the reservation
     */
    public void editSeatedReservation(int index, String name, int partySize, String phoneNumber){
        Reservation res = (Reservation)seatedReservations.remove(index);
        res.setName(name);
        res.setPartySize(partySize);
        res.setPhoneNumber(phoneNumber);
        seatedReservations.add(index, res);
    }

    /**
     * Delete a reservation in the waiting list at a specific index
     * @param index Index of the element
     */
    public void deleteReservation(int index){
        waitingReservations.remove(index);
    }
    /**
     * Delete a reservation in the seated list at a specific index
     * @param index Index of the element
     */
    public void deleteSeatedReservation(int index){
        seatedReservations.remove(index);
    }

    /**
     * Move a reservation from the waiting list to the seated list
     * @param position  The position of the reservation to be moved
     */
    public void moveToSeated(int position){
        Object res = waitingReservations.remove(position);
        seatedReservations.add(res);
        Reservation reservation = (Reservation) res;

    }

    /**
     * Move a reservation from the seated list to the waiting list
     * @param position  The position of the reservation to be moved
     */
    public void moveToMaster(int position){
        Object res = seatedReservations.remove(position);
        waitingReservations.add(res);
        Reservation reservation = (Reservation) res;

    }

    private List waitingReservations = new ArrayList();
    private List seatedReservations = new ArrayList();
}
