package edu.umn.d.restaurantpagingapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jeff Smith on 4/12/2017.
 */

public class ListTest {
    private Reservation reservation1 = new Reservation("Jeff", 1, "5555555555","3:00");
    private Reservation reservation2 = new Reservation("Tina", 2, "1111111111", "3:00");


    /**
     * Creates a reservation and makes sure that the same reservation is returned
     */
    @Test
    public void create_reservation(){
        RPAModel model = new RPAModel();

        model.createReservation("Jeff", 1, "5555555555","3:00");
        testReservationsEqual(reservation1, model.getReservation(0));
    }

    /**
     * Checks to see that an edit with the same values does not change the reservation's data
     */
    @Test
    public void same_edit(){
        RPAModel model = new RPAModel();

        model.createReservation("Jeff", 1, "5555555555","3:00");
        model.editReservation(0,"Jeff", 1, "5555555555");
        testReservationsEqual(reservation1,model.getReservation(0));
    }

    /**
     * Checks to see that editing a reservation does change the values
     */
    @Test
    public void different_edit(){
        RPAModel model = new RPAModel();

        model.createReservation("Jeff", 1, "5555555555","3:00");
        model.editReservation(0,"Tina", 2, "1111111111");
        testReservationsEqual(reservation2,model.getReservation(0));

    }

    /**
     * Makes sure that the lists returned when getting all reservations are of the right length
     */
    @Test
    public void length_test(){
        RPAModel model = new RPAModel();


        model.createReservation("Alice",1,"1234567890","2:00");
        model.createReservation("Bob",2,"0987654321","1:50");
        assertEquals(model.getReservations().size(), 2);
    }

    /**
     * Makes sure that the lists remove elements for deleting and moving cases
     */
    @Test
    public void delete_move(){
        RPAModel model = new RPAModel();

        model.createReservation("Jeff", 1, "5555555555","3:00");
        model.createReservation("Alice",1,"1234567890","2:00");
        model.createReservation("Bob",2,"0987654321","1:50");
        model.deleteReservation(2);
        model.moveToSeated(1);
        assertEquals(model.getReservations().size(), 1);
        testReservationsEqual(reservation1,model.getReservation(0));
    }

    /**
     * Used to make sure that all data is equal. If you just use assertEquals(res1,res2) it falsely raises an error for some reason.
     * @param a First reservation
     * @param b Second reservation
     */
    private void testReservationsEqual(Reservation a, Reservation b) {
        assertEquals(a.getName(),b.getName());
        assertEquals(a.getPartySize(),b.getPartySize());
        assertEquals(a.getPhoneNumber(),b.getPhoneNumber());
        assertEquals(a.getTime(),b.getTime());
    }
}
