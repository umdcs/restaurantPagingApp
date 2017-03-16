package edu.umn.d.restaurantpagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ModelViewPresenterComponents.View {

    private ModelViewPresenterComponents.RPAPresenterContract mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupModelViewPresenterComponents();
    }

    public void onClickSwitchView(View view) {
        //Intent intent = new Intent(this, );
        //startActivity(intent);
    }

    /**
     * Called when the user clicks the createReservation button
     */
    public void onClickCreateReservation(View view) {
        // Provide this information to the Presenter
        String nameText = "Name";
        int partySizeText = 1;
        int timeArrivedText = 1;
        mPresenter.clickCreateReservation(nameText, partySizeText, timeArrivedText);
    }

    public void setupModelViewPresenterComponents() {
        // Create the MPGPresenter
        mPresenter = new RPAPresenter(this);
    }

    @Override
    public void notifyCustomerListUpdated() {

        List reservationData = mPresenter.getReservation();
        ListView waitingList = (ListView) findViewById(R.id.waitingList);
        ArrayAdapter listAdapter = new ArrayAdapter<Reservation>(this, R.layout.row,reservationData);

        waitingList.setAdapter(listAdapter);

    }

}
