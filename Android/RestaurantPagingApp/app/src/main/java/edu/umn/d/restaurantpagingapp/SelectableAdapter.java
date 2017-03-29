package edu.umn.d.restaurantpagingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by Jeff Smith on 3/21/2017.
 */

public class SelectableAdapter extends ArrayAdapter {
    // used to keep selected position in ListView
    private int selectedPos = -1;   // init value for not-selected
    private int selitem;
    private Context context;
    public SelectableAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public void setSelectedPosition(int pos){

        selectedPos = pos;

        // inform the view of this change
        notifyDataSetChanged();
    }

    public int getSelectedPosition(){
        return selectedPos;
    }


    /**
     * This handles creating the view for the adapter when it needs to be created.
     * @param position  Where in the adapter the view is
     * @param convertView   The view that is being converted. See http://stackoverflow.com/questions/10120119/how-does-the-getview-method-work-when-creating-your-own-custom-adapter for questions.
     * @param parent    The parent of the view. Usually a listView or something similar.
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ){
            //We must create a View:
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
        }
        //Here we can do changes to the convertView, such as set a text on a TextView
        //or an image on an ImageView.


        TextView textView = (TextView)convertView.findViewById(R.id.rowTextView);
        /*textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("Long click","Long click");
                return false;
            }
        });*/
        textView.setText(this.getItem(position).toString());
        ListView listView = (ListView)parent;

        Log.d("Selectable",String.valueOf(listView));
        if(position != -1 && position == selectedPos){
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorPurple));
        }
        else{
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorRed));

        }
        return convertView;
    }

}
