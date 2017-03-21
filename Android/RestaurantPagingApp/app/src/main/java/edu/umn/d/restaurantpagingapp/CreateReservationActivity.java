package edu.umn.d.restaurantpagingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.Calendar;

public class CreateReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);
    }

    public void onClickCreateReservation(View view){

        Calendar calendar = Calendar.getInstance();

        //Grab the edit texts
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText partySizeEditText = (EditText) findViewById(R.id.partySizeEditText);
        EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditTest);

        //Create an intent with reservation information
        Intent intent = new Intent();
        intent.putExtra("Name", nameEditText.getText().toString());
        if (!partySizeEditText.getText().toString().equals("")) {
            intent.putExtra("Party size", Integer.valueOf(partySizeEditText.getText().toString()));
        }
        else {
            intent.putExtra("Party size", 0);
        }
        intent.putExtra("Phone number", phoneNumEditText.getText().toString());

        //Finish activity and send info back to main activity
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private RPAPresenter presenter;
}
