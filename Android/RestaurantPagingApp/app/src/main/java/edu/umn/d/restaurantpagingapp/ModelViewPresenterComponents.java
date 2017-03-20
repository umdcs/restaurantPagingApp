package edu.umn.d.restaurantpagingapp;

import android.widget.EditText;

import java.util.List;


/**
 * Created by melissa on 3/15/17.
 */

public interface ModelViewPresenterComponents{

    // View
    interface View
    {
        void notifyCustomerListUpdated();
    }

    /** RPAPresenter operations are meant for the views that deal with presenting customer list information.
     * This interface contains a "contract" of the functions that will be implemented by any
     * Presenters that can work with reservation data.
     */
    interface RPAPresenterContract
    {
        // Called by the view when user clicks the Create reservation button
        // provides access to the info the user entered
        // - in this way, the views communicate data entered by the user
        //   to be sent to the master list
        void clickCreateReservation(final String name, final int partySize, final String phoneNumber);

        List getReservation();
    }

    /** Model Operations
     * Interface for Models that Presenters will need to use to acquire and set data about the model. In this case,
     * the model is our reservation creation and data, such as list of customers
     */
    interface Model
    {
        List getAllReservations();
        void createReservation(String name, int partySize, String phoneNumber);
        void addReservation(Reservation reservation);
    }
}
