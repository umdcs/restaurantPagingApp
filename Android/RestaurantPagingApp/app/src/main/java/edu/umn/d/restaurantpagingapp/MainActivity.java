package edu.umn.d.restaurantpagingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;

import android.view.MenuInflater;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.Collections;

import java.util.List;

import static edu.umn.d.restaurantpagingapp.Reservation.PARTY_SIZE_ASC;
import static edu.umn.d.restaurantpagingapp.Reservation.PARTY_SIZE_DESC;
import static edu.umn.d.restaurantpagingapp.Reservation.PHONE_NUMBER_ASC;
import static edu.umn.d.restaurantpagingapp.Reservation.TIME_CREATED_ASC;
import static edu.umn.d.restaurantpagingapp.Reservation.TIME_CREATED_DESC;

public class MainActivity extends AppCompatActivity implements ModelViewPresenterComponents.View {
    private final String version = "0.2.0";
    private ModelViewPresenterComponents.RPAPresenterContract mPresenter;
    private ArrayAdapter waitingListAdapter;
    private ArrayAdapter seatedListAdapter;

    private int editedPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupModelViewPresenterComponents();
        

        final ListView waitingList = (ListView) findViewById(R.id.waitingList);
        registerForContextMenu(waitingList);
        waitingListAdapter = new ArrayAdapter(this, R.layout.row);
        waitingList.setAdapter(waitingListAdapter);

        final ListView seatedList = (ListView) findViewById(R.id.seatedList);
        registerForContextMenu(seatedList);
        seatedListAdapter = new ArrayAdapter(this, R.layout.row);
        seatedList.setAdapter(seatedListAdapter);

        final Button sortButton = (Button) findViewById(R.id.sort);

        sortButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                registerForContextMenu(sortButton);
                openContextMenu(sortButton);
                return true;
            }
        });

    }

    /**
     * Called automatically when a context menu is created.
     * @param menu what the menu is?
     * @param v where this method was called from. From the seated/waiting listviews for Jeffs menu.
     * @param menuInfo infor attached to the menu.
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        Log.d("List",String.valueOf(v));
        if(v.equals(findViewById(R.id.waitingList)) || v.equals(findViewById(R.id.seatedList))) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            Log.d("Menu","Jeff");
        }else {
            menu.setHeaderTitle("Sort by:");
            menu.add(Menu.NONE, 1, Menu.NONE, "Name");
            menu.add(Menu.NONE, 2, Menu.NONE, "Party Size (Low-High)");
            menu.add(Menu.NONE, 5, Menu.NONE, "Party Size (High-Low)");
            menu.add(Menu.NONE, 6, Menu.NONE, "Time Created (Newest)");
            menu.add(Menu.NONE, 3, Menu.NONE, "Time Created (Oldest)");
            menu.add(Menu.NONE, 4, Menu.NONE, "Phone Number");
        }

    }

    /**
     * Called automatically when a context menu item is selected.
     * @param item The menu item that was selected.
     * @return I think this determines whether the click event is consumed or not.
     */
    public boolean onContextItemSelected(MenuItem item){
        int arrayAdapterPosition;
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String list;
        int requestCode;
        if(menuInfo!=null){ // We got here from the listviews
            arrayAdapterPosition = menuInfo.position;
            ListView listView = (ListView)menuInfo.targetView.getParent();
            ListView seatedList = (ListView)findViewById(R.id.seatedList);
            ListView waitingList = (ListView)findViewById(R.id.waitingList);

            if(listView.equals(waitingList)){   // Check which list the view is from.
                list = "master";
                requestCode = 2;
            }
            else{

                list = "seated";
                requestCode = 3;
            }
            Log.d("List",list);
            Log.d("requestcode",String.valueOf(requestCode));
        }
        else{   // We got here from the button
            arrayAdapterPosition = -1;
            requestCode = -1;
            list = "BAD LIST";
        }




        switch(item.getItemId()){
            case R.id.edit:
                Log.d("Edit","Open editor");
                Log.d("Request code",String.valueOf(requestCode));
                editedPosition = arrayAdapterPosition;

                Intent intent = new Intent(this, CreateReservationActivity.class);
                Reservation res = mPresenter.getReservation(arrayAdapterPosition,list);
                intent.putExtra("Name",res.getName());
                intent.putExtra("Party Size",String.valueOf(res.getPartySize()));
                intent.putExtra("Phone Number",res.getPhoneNumber());

                startActivityForResult(intent, requestCode);
                break;
            case R.id.delete:
                Log.d("Delete","Deleted");
                mPresenter.deleteReservation(arrayAdapterPosition,list);
                break;
            case R.id.notify:

                Reservation reservation = mPresenter.getReservation(arrayAdapterPosition,list);
                String phone = reservation.getPhoneNumber();
                sendSMSMessage(phone,this.getString(R.string.message));
            case 1:
                Collections.sort(mPresenter.getReservations(""));
                Collections.sort(mPresenter.getReservations("seated"));
                notifyCustomerListUpdated();

            break;
            case 2: {
                Collections.sort(mPresenter.getReservations(""), PARTY_SIZE_ASC);
                Collections.sort(mPresenter.getReservations("seated"), PARTY_SIZE_ASC);
                notifyCustomerListUpdated();
            }
            break;
            case 3: {
                Collections.sort(mPresenter.getReservations(""), TIME_CREATED_ASC);
                Collections.sort(mPresenter.getReservations("seated"), TIME_CREATED_ASC);
                notifyCustomerListUpdated();
            }
            break;
            case 4: {
                Collections.sort(mPresenter.getReservations(""), PHONE_NUMBER_ASC);
                Collections.sort(mPresenter.getReservations("seated"), PHONE_NUMBER_ASC);
                notifyCustomerListUpdated();
                break;
            }
            case 5: {
                Collections.sort(mPresenter.getReservations(""), PARTY_SIZE_DESC);
                Collections.sort(mPresenter.getReservations("seated"), PARTY_SIZE_DESC);
                notifyCustomerListUpdated();
            }
            break;
            case 6: {
                Collections.sort(mPresenter.getReservations(""), TIME_CREATED_DESC);
                Collections.sort(mPresenter.getReservations("seated"), TIME_CREATED_DESC);
                notifyCustomerListUpdated();
            }
            break;
        }
        return true;    // This consumes the long click or whatever input made this call.
    }
    private void sendSMSMessage(String phoneNo, String message) {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1);
                sendText(phoneNo,message);
            }
        }
        else{
            sendText(phoneNo,message);
        }
    }

    private void sendText(String phoneNo, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Nothing special happens when we get permission
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;

                }
            }

        }

    }


    /**
     * Automatically called when an activity started with startActivityForResult is finished.
     * @param requestCode   The number given in startActivityForResult
     * @param resultCode    A code indicating how getting the result went. Activity.RESULT_OK for good result.
     * @param intent    An intent carrying any data that is supposed to come back.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent ){
        if(resultCode == Activity.RESULT_OK) {


            if (requestCode == 1) {
                //Get information from the intent created in the create reservation activity
                String name = intent.getStringExtra("Name");
                int partySize = intent.getIntExtra("Party size", 0);
                String phoneNum = intent.getStringExtra("Phone number");
                String time = intent.getStringExtra("Time");
                ListView waitingList = (ListView) findViewById(R.id.waitingList);
                waitingList.setSelection(-1);
                waitingList.setItemChecked(-1, false);
                waitingList.clearChoices();

                //Create the reservation
                mPresenter.clickCreateReservation(name, partySize, phoneNum, time);

                } else if (requestCode == 2) {
                    String name = intent.getStringExtra("Name");
                    int partySize = intent.getIntExtra("Party size", 0);
                    String phoneNum = intent.getStringExtra("Phone number");
                    String time = intent.getStringExtra("Time");
                    mPresenter.editReservation(editedPosition, name, partySize, phoneNum, "master");
                } else if (requestCode == 3) {
                    String name = intent.getStringExtra("Name");
                    int partySize = intent.getIntExtra("Party size", 0);
                    String phoneNum = intent.getStringExtra("Phone number");
                    String time = intent.getStringExtra("Time");
                    mPresenter.editReservation(editedPosition, name, partySize, phoneNum, "seated");
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }


    /**
     * Called when the user clicks the createReservation button
     */
    public void onClickCreateReservation(View view) {


        // Provide this information to the Presenter
        Intent intent = new Intent(this, CreateReservationActivity.class);
        intent.putExtra("Name","");
        intent.putExtra("Party Size","");
        intent.putExtra("Phone Number","");
        startActivityForResult(intent, 1);
    }

    /**
     * This method is a button listener for the sort seated button
     * @param view the view where the sort button is
     */
    public void onClickSort(View view){
        Collections.sort(mPresenter.getReservations(""));
        Collections.sort(mPresenter.getReservations("seated"));
        notifyCustomerListUpdated();
    }

    /**
     * Initialize the MVP components
     */
    private void setupModelViewPresenterComponents() {
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
        ListView seatedList = (ListView) findViewById(R.id.seatedList);

        int index = waitingList.getCheckedItemPosition();
        Log.d("Seated",String.valueOf(seatedList.getCheckedItemPosition()));
        seatedList.setItemChecked(0,false); // This makes it so the item doesn't start selected when it ends up on the seated list. I think this is a workaround and might reflect an overarching bug.
        if (index >= 0 && index < waitingList.getCount()){
            Log.d("index",String.valueOf(index));
            mPresenter.moveReservation(index,"master");
        }
        else{
            Log.d("ERROR", "ARRAY INDEX OUT OF BOUNDS IN moveToSeated in MainActivity array is of length " + waitingList.getCount()+ " got index of "+ index);
        }

    }

    /**
     * Adds a reservation to the end of the waitingListAdapter and therefore the waitingList
     * @param reservation   The reservation to add to the list.
     */
    @Override
    public void addReservationToList(Reservation reservation){
        waitingListAdapter.add(reservation);
    }

    /**
     * Updates the waiting list and master list to reflect the data in the model
     */
    @Override
    public void notifyCustomerListUpdated() {

        List reservationData = mPresenter.getReservations("master");
        waitingListAdapter.clear();

        for (Object obj : reservationData){
            Reservation res = (Reservation) obj;
            waitingListAdapter.add(res);
        }



        // Seated update
        List seatedData = mPresenter.getReservations("seated");
        seatedListAdapter.clear();

        for (Object obj : seatedData){
            Reservation res = (Reservation) obj;
            seatedListAdapter.add(res);
        }

    }


}
