package edu.umn.d.customerapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RPAPresenter mPresenter = new RPAPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user clicks the createReservation button
     */
    public void onClickCreateReservation(View view) {

        Intent intent = new Intent(this, CreateReservationActivity.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                //Get information from the intent created in the create reservation activity
                String name = intent.getStringExtra("Name");
                int partySize = intent.getIntExtra("Party size", 0);
                String phoneNum = intent.getStringExtra("Phone number");
                String time = intent.getStringExtra("Time");

                //Create the reservation
                mPresenter.clickCreateReservation(name, partySize, phoneNum, time);

                TextView reservationTextView = (TextView) findViewById(R.id.reservationTextView);
                if (mPresenter.getReservation() != null) {
                    reservationTextView.setText(mPresenter.getReservation().toString());
                    TextView message = (TextView)findViewById(R.id.textView3);
                    message.setText("My Reservation");
                }
            }
        }
    }
}
