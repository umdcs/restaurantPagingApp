package edu.umn.d.restaurantpagingapp;

import android.util.Log;
import android.widget.EditText;

import java.util.List;

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
    public List getReservation()
    {
        return mModel.getAllReservations();

    }

    public List getSeated(){
        return mModel.getSeatedReservations();
    }

    public void moveToSeated(int index){
        mModel.moveToSeated(index);
        mView.notifyCustomerListUpdated();
    }

    // When the view receives input from the user (after the createReservation button is clicked,
    // this will be called by the view to relay the user data here.  Once here, it can
    // be checked and potentially sent on to the model.
    @Override
    public void clickCreateReservation(String name, int partySize, String phoneNum) {
        mModel.createReservation(name,partySize,phoneNum);
        mView.notifyCustomerListUpdated();
    }
}

