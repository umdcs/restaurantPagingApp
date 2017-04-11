package edu.umn.d.customerapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RPAPresenter mPresenter = new RPAPresenter(this);
    private CreateReservationFragment firstFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(mPresenter.getReservation() != null){
            this.reservationCreated = true;
        }

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        // Create a new Fragment to be placed in the activity layout
        firstFragment = new CreateReservationFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment).commit();
    }

    public void deleteReservation(View view){
        reservationCreated = false;
        mPresenter.getReservation();
        TextView message = (TextView)findViewById(R.id.messageTextView);
        TextView reservationTextView = (TextView) findViewById(R.id.reservationTextView);
        message.setText("You haven't created  a reservation yet!");
        reservationTextView.setText("");

        CreateReservationFragment createReservationFragment = new CreateReservationFragment();


        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,createReservationFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * Called when the user clicks the createReservation button
     */
    public void onClickCreateReservation(View view) {

        Intent intent = new Intent(this, CreateReservationActivity.class);

        if (!reservationCreated) {
            intent.putExtra("Editting", false);
            startActivityForResult(intent, 1);
        }
        else {
            intent.putExtra("Name", mPresenter.getReservation().getName());
            intent.putExtra("Phone Number", mPresenter.getReservation().getPhoneNumber());
            intent.putExtra("Party Size", mPresenter.getReservation().getPartySize());
            intent.putExtra("Time", mPresenter.getReservation().getTime());
            intent.putExtra("Editting", true);

            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                reservationCreated = true;
                //Get information from the intent created in the create reservation activity
                String name = intent.getStringExtra("Name");
                int partySize = intent.getIntExtra("Party Size", 0);
                String phoneNum = intent.getStringExtra("Phone Number");
                String time = intent.getStringExtra("Time");

                //Create the reservation
                mPresenter.clickCreateReservation(name, partySize, phoneNum, time);

                TextView reservationTextView = (TextView) findViewById(R.id.reservationTextView);
                if (mPresenter.getReservation() != null) {
                    reservationTextView.setText(mPresenter.getReservation().toString());
                    TextView message = (TextView)findViewById(R.id.messageTextView);
                    message.setText("My Reservation");
                    DeleteEditFragment deleteEditFragment = new DeleteEditFragment();


                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,deleteEditFragment);
                    transaction.commitAllowingStateLoss();
                }
            }
        }
    }

    private Boolean reservationCreated = false;
}
