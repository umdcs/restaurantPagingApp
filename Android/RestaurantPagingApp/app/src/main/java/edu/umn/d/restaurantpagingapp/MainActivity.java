package edu.umn.d.restaurantpagingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ModelViewPresenterComponents.View {

    private ModelViewPresenterComponents.RPAPresenterContract mPresenter;
    private ArrayAdapter waitingListAdapter;
    private ArrayAdapter seatedListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupModelViewPresenterComponents();


        ListView waitingList = (ListView) findViewById(R.id.waitingList);
        waitingListAdapter = new ArrayAdapter<String>(this, R.layout.row);
        waitingList.setAdapter(waitingListAdapter);
        waitingList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


        // Waiting list listener
        waitingList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("listener",String.valueOf(position));
            }
        });

        ListView seatedList = (ListView) findViewById(R.id.seatedList);
        seatedListAdapter = new ArrayAdapter<String>(this, R.layout.row);
        seatedList.setAdapter(seatedListAdapter);
        seatedList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // Seated list listener
        seatedList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("listener",String.valueOf(position));
            }
        });


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

    public void moveToSeated(View view){
        ListView waitingList = (ListView) findViewById(R.id.waitingList);
        mPresenter.moveToSeated(waitingList.getCheckedItemPosition());
        waitingList.setAdapter(waitingList.getAdapter());   // This is a workaround to reset the choices.
    }

    @Override
    public void notifyCustomerListUpdated() {

        List reservationData = mPresenter.getReservation();
        reservationData.toString();
        waitingListAdapter.clear();

        for (Object obj : reservationData){
            Reservation res = (Reservation) obj;
            waitingListAdapter.add(res.toString());
        }


        // Seated update
        List seatedData = mPresenter.getSeated();
        seatedData.toString();
        seatedListAdapter.clear();

        for (Object obj : seatedData){
            Reservation res = (Reservation) obj;
            seatedListAdapter.add(res.toString());
        }

    }

}
