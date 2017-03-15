package edu.umn.d.restaurantpagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ModelViewPresenterComponents.View {

    private ModelViewPresenterComponents.RPAPresenterContract mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupModelViewPresenterComponents();
    }

    public void onClickSwitchView(View view) {
        Intent intent = new Intent(this, );
        startActivity(intent);
    }

    /**
     * Called when the user clicks the createReservation button
     */
    public void onClickCalcMPG(View view) {
        // Extract the Editable text fields
        EditText nameText = (EditText) findViewById(R.id.textview_name);
        EditText partySizeText = (EditText) findViewById(R.id.textview_partysize);
        EditText timeArrivedText = (EditText) findViewById(R.id.textview_timearrived);

        // Provide this information to the Presenter
        mPresenter.clickCreateReservation(nameText, partySizeText, timeArrivedText);
    }

    public void setupModelViewPresenterComponents() {
        // Create the MPGPresenter
        mPresenter = new RPAPresenter(this);
    }

    @Override
    public void notifyCustomerListUpdated() {
        String mpgData = mPresenter.getReservation();
        TextView mpgTV = (TextView) findViewById(R.id.res);
        mpgTV.setText( mpgData );
    }

}
