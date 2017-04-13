package edu.umn.d.restaurantpagingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class CreateReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText partySizeEditText = (EditText) findViewById(R.id.partySizeEditText);
        EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditTest);
        Intent intent = getIntent();



        nameEditText.setText(intent.getStringExtra("Name"));
        partySizeEditText.setText(intent.getStringExtra("Party Size"));
        phoneNumEditText.setText(intent.getStringExtra("Phone Number"));

    }

    public void onClickCreateReservation(View view){

        //Grab the edit texts
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText partySizeEditText = (EditText) findViewById(R.id.partySizeEditText);
        EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditTest);

        //Check to see that all information is entered
        if (nameEditText.getText().toString().equals("") || partySizeEditText.getText().toString().equals("") || phoneNumEditText.getText().toString().equals("")) {

            //Display message indicating that more info is needed
            TextView enterInfoMessage = (TextView) findViewById(R.id.enterInfoMessage);
            enterInfoMessage.setText("Please enter all information.");
        }
        else if (phoneNumEditText.getText().toString().length() < 10) {

            //Display message indicating that an invalid phone number was entered
            TextView enterInfoMessage = (TextView) findViewById(R.id.enterInfoMessage);
            enterInfoMessage.setText("Please enter a valid phone number.");
        }
        else {

            //Create an intent with reservation information
            Intent intent = new Intent();
            intent.putExtra("Name", nameEditText.getText().toString());
            intent.putExtra("Party size", Integer.valueOf(partySizeEditText.getText().toString()));

            intent.putExtra("Phone number", phoneNumEditText.getText().toString());
            intent.putExtra("Time", time());

            //Finish activity and send info back to main activity
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    public void onClickCancel(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    //Helper method creates the time string
    private String time(){
        Calendar calendar = Calendar.getInstance();
        String time;

        time = calendar.get(Calendar.HOUR)+":";
        if (calendar.get(Calendar.MINUTE) >= 10){
            time += calendar.get(Calendar.MINUTE);
        }
        else {
            time += "0";
            time += calendar.get(Calendar.MINUTE);
        }
        if(Integer.valueOf(calendar.get(Calendar.AM_PM)) == 0){
            time += "AM";
        }
        else {
            time += "PM";
        }

        return time;
    }

}
