package edu.umn.d.restaurantpagingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);
    }

    public void onClickCreateReservation(View view){
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText partySizeEditText = (EditText) findViewById(R.id.partySizeEditText);
        EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditTest);
        //presenter.clickCreateReservation(nameEditText.getText().toString(), Integer.valueOf(partySizeEditText.getText().toString()), Integer.valueOf(phoneNumEditText.getText().toString()));
        Intent intent = new Intent();
        intent.putExtra("Name", nameEditText.getText().toString());
        intent.putExtra("Party size", Integer.valueOf(partySizeEditText.getText().toString()));
        intent.putExtra("Phone number", phoneNumEditText.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private RPAPresenter presenter;
}
