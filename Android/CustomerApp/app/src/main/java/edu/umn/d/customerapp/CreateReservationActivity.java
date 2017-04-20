package edu.umn.d.customerapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class CreateReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        populateEditTexts();

    }

    public void onClickCreateReservation(View view){

        //Grab the edit texts
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText partySizeEditText = (EditText) findViewById(R.id.partySizeEditText);
        EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditTest);

        //Check Boxes
        CheckBox highChairCheckBox = (CheckBox) findViewById(R.id.highChairCheckBox);
        CheckBox boothCheckBox = (CheckBox) findViewById(R.id.boothSeatingCheckBox);
        CheckBox wheelChairCheckBox = (CheckBox) findViewById(R.id.wheelChairCheckBox);
        CheckBox willSplitCheckBox = (CheckBox) findViewById(R.id.willSplitCheckBox);

        //Other Request
        EditText otherRequestString = (EditText) findViewById(R.id.otherRequestEditText);

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
            intent.putExtra("Party Size", Integer.valueOf(partySizeEditText.getText().toString()));

            intent.putExtra("Phone Number", phoneNumEditText.getText().toString());

            if (this.getIntent().getBooleanExtra("Editting", false) == true){
                intent.putExtra("Time", this.getIntent().getStringExtra("Time"));
            }
            else {
                intent.putExtra("Time", time());
            }

            boolean[] accomodations = {highChairCheckBox.isChecked(),boothCheckBox.isChecked(),wheelChairCheckBox.isChecked(),willSplitCheckBox.isChecked()};
            intent.putExtra("Accomodations", accomodations);
            intent.putExtra("Other Request",otherRequestString.getText());

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
    public String time(){
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

    private void populateEditTexts(){
        if (this.getIntent().getBooleanExtra("Editting", false) == true) {

            EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
            nameEditText.setText(this.getIntent().getStringExtra("Name"));

            EditText partySizeEditText = (EditText) findViewById(R.id.partySizeEditText);
            partySizeEditText.setText(String.valueOf(this.getIntent().getIntExtra("Party Size",2)));

            EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditTest);
            phoneNumEditText.setText(this.getIntent().getStringExtra("Phone Number"));

            CheckBox highChairCheckBox = (CheckBox) findViewById(R.id.highChairCheckBox);
            highChairCheckBox.setChecked(this.getIntent().getBooleanArrayExtra("Accomodations")[0]);

            CheckBox boothCheckBox = (CheckBox) findViewById(R.id.boothSeatingCheckBox);
            boothCheckBox.setChecked(this.getIntent().getBooleanArrayExtra("Accomodations")[1]);

            CheckBox wheelChairCheckBox = (CheckBox) findViewById(R.id.wheelChairCheckBox);
            wheelChairCheckBox.setChecked(this.getIntent().getBooleanArrayExtra("Accomodations")[2]);

            CheckBox willSplitCheckBox = (CheckBox) findViewById(R.id.willSplitCheckBox);
            willSplitCheckBox.setChecked(this.getIntent().getBooleanArrayExtra("Accomodations")[3]);

            EditText otherRequestEditText = (EditText) findViewById(R.id.otherRequestEditText);
            otherRequestEditText.setText(this.getIntent().getStringExtra("Other Request"));
        }
    }
}
