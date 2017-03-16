package edu.umn.d.restaurantpagingapp;

import android.util.Log;
import android.widget.EditText;

/**
 * Created by melissa on 3/15/17.
 */

public class RPAPresenter implements ModelViewPresenterComponents.RPAPresenterContract {

    // presenter needs to talk with this model
    private ModelViewPresenterComponents.Model mModel;
    private ModelViewPresenterComponents.View mView;

    public RPAPresenter(ModelViewPresenterComponents.View rpaView)
    {
        mView = rpaView;
        mModel = new RPAModel(this);
    }

    // setting up recycler adapter
    @Override
    public String getReservation()
    {
        float f = mModel.getAllReservations();
        Log.d("presenter", Float.toString(f));
        return Float.toString(f);
    }

    // When the view receives input from the user (after the createReservation button is clicked,
    // this will be called by the view to relay the user data here.  Once here, it can
    // be checked and potentially sent on to the model.
    @Override
    public void clickCreateReservation(EditText name, EditText partySize, EditText arrivalTime) {
        float nameGiven = Float.valueOf(name.getText().toString());
        float party = Float.valueOf(partySize.getText().toString());
        float time = Float.valueOf(arrivalTime.getText().toString());

        boolean error = false;
        // doing some error checking here on the inputs would be good before we proceed with
        // other calculations...
        if (!error) {
            mModel.setReservationData(nameGiven, party, time);
            mView.notifyCustomerListUpdated();
        }
    }
}

