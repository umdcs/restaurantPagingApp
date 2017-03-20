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

    protected void onActivityResult(int requestCode, int resultCode, Intent intent ){
        String name = intent.getStringExtra("Name");
        int partySize = intent.getIntExtra("Party size", 0);
        String phoneNum = intent.getStringExtra("Phone number");
        mPresenter.clickCreateReservation(name, partySize, phoneNum);
    }

    /**
     * Called when the user clicks the createReservation button
     */
    public void onClickCreateReservation(View view) {
        // Provide this information to the Presenter

        Intent intent = new Intent(this, CreateReservationActivity.class);
        startActivityForResult(intent, 1);
        //startActivity(intent);

        /**
        String nameText = "Name";
        int partySizeText = 1;
        String phoneNumberText = "1";
        mPresenter.clickCreateReservation(nameText, partySizeText, phoneNumberText);
         */
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
