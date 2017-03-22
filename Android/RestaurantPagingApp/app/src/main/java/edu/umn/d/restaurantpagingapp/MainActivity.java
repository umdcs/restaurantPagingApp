package edu.umn.d.restaurantpagingapp;

import android.app.Activity;
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
    private SelectableAdapter waitingListAdapter;
    private SelectableAdapter seatedListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupModelViewPresenterComponents();



        ListView waitingList = (ListView) findViewById(R.id.waitingList);
        waitingListAdapter = new SelectableAdapter(this, R.layout.row);
        waitingList.setAdapter(waitingListAdapter);
        waitingList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


        // Waiting list listener
        waitingList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                view.setSelected(true);
                waitingListAdapter.setSelectedPosition(position);
                Log.d("listener",String.valueOf(position));
            }
        });


        ListView seatedList = (ListView) findViewById(R.id.seatedList);

        seatedListAdapter = new SelectableAdapter(this, R.layout.row);
        seatedList.setAdapter(seatedListAdapter);
        seatedList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // Seated list listener
        seatedList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                seatedListAdapter.setSelectedPosition(position);
                Log.d("listener",String.valueOf(position));
            }
        });
    }


    /**
     * Automatically called when an activity started with startActvitiyForResult is finished.
     * @param requestCode   The number given in startActivityForResult
     * @param resultCode    A code indicating how getting the result went. Activity.RESULT_OK for good result.
     * @param intent    An intent carrying any data that is supposed to come back.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent ){

        if (resultCode == Activity.RESULT_OK) {
            //Get information from the intent created in the create reservation activity
            String name = intent.getStringExtra("Name");
            int partySize = intent.getIntExtra("Party size", 0);
            String phoneNum = intent.getStringExtra("Phone number");
            String time = intent.getStringExtra("Time");

            //Create the reservation
            mPresenter.clickCreateReservation(name, partySize, phoneNum, time);
        }
        else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }


    /**
     * Called when the user clicks the createReservation button
     */
    public void onClickCreateReservation(View view) {


        // Provide this information to the Presenter
        Intent intent = new Intent(this, CreateReservationActivity.class);
        startActivityForResult(intent, 1);
    }


    /**
     * Initalize the MVP components
     */
    public void setupModelViewPresenterComponents() {
        // Create the MPGPresenter
        mPresenter = new RPAPresenter(this);
    }

    /**
     * This method is a button listener for the move to seated button
     * it asks the presenter to move the selected item in the waiting list to the seated list
     * @param view View is the view that this listener is linked to. Button in this case.
     */
    public void moveToSeated(View view){
        ListView waitingList = (ListView) findViewById(R.id.waitingList);
        int index = waitingList.getCheckedItemPosition();
        if (index >= 0 && index < waitingList.getCount()){
            Log.d("index",String.valueOf(index));
            mPresenter.moveToSeated(index);
            updateListSelections();
        }
        else{
            Log.d("ERROR", "ARRAY INDEX OUT OF BOUNDS IN moveToSeated in MainActivity");
        }

    }

    /**
     * Updates the waiting list and master list to reflect the data in the model
     */
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


    /**
     * Private helper method to update the selections in the listviews.
     */
    private void updateListSelections(){
        ListView waitingList = (ListView) findViewById(R.id.waitingList);
        waitingList.clearChoices();
        waitingList.setAdapter(waitingList.getAdapter());   // This is a workaround to reset the choices.

        ListView seatedList = (ListView) findViewById(R.id.seatedList);
        seatedList.clearChoices();
        seatedList.setAdapter(seatedList.getAdapter());   // This is a workaround to reset the choices.
    }


}
