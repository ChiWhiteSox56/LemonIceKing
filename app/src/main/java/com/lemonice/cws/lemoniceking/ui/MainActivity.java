package com.lemonice.cws.lemoniceking.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.lemonice.cws.lemoniceking.R;
import com.lemonice.cws.lemoniceking.adapters.FlavorAdapter;
import com.lemonice.cws.lemoniceking.db.FlavorContract;
import com.lemonice.cws.lemoniceking.db.FlavorDbOpenHelper;
import com.lemonice.cws.lemoniceking.flavor.Flavor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// The FlavorContract class defines constants which are used to access the data in the database
// The helper class FlavorDbOpenHelper opens the database

public class MainActivity extends ListActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String KEY_CHECKED = "sp_key_checked";

    private FlavorDbOpenHelper mOpenHelper;

    private Flavor[] mFlavors;
    private FlavorAdapter mFlavorAdapter;

    private static final String PREFS_FILE = "com.lemonice.cws.lemoniceking.preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate a SharedPreferences object and a SharedPreferences editor
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mOpenHelper = new FlavorDbOpenHelper(this);

        // initialize flavor list
        mFlavors = new Flavor[getFlavorListFromDatabase().size()];

        ArrayList<String> h = getFlavorListFromDatabase();

        boolean isChecked = false;
        for (int i = 0; i < mFlavors.length; i++) {
            isChecked = mSharedPreferences.getBoolean(KEY_CHECKED + i, false);
            mFlavors[i] = new Flavor(h.get(i), isChecked);
        }

        mFlavorAdapter = new FlavorAdapter(this, mFlavors);
        setListAdapter(mFlavorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // onPause is a good place to update SharedPreferences xml
    @Override
    protected void onPause() {
        super.onPause();

        for (int i = 0; i < mFlavors.length; i++) {
            // make different keys for SharedPreferences to store different values KEY_CHECKED0, KEY_CHECKED1, etc.)
            mEditor.putBoolean(KEY_CHECKED + i, mFlavors[i].getValue()); // GET VALUE OF CHECKBOX FOR EACH ROW
        }

        mEditor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_suggest_flavor:

                // initialize a new Alert dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // specify the alert dialog title
                builder.setTitle("Today's suggested flavor is ...");

                // initialize a new TextView instance for the generated flavor
                final TextView suggestedFlavorTextView = new TextView(this);
                suggestedFlavorTextView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                suggestedFlavorTextView.setGravity(Gravity.CENTER);
                suggestedFlavorTextView.setText(flavorGenerator() + "!");
                suggestedFlavorTextView.setTextSize(24);
                suggestedFlavorTextView.setPadding(16, 32, 16, 32);
                builder.setView(suggestedFlavorTextView);

                builder.setPositiveButton("Let's do it!", null);
                builder.setNeutralButton("Nah, try again", null);

                AlertDialog dialog = builder.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                suggestedFlavorTextView.setText(flavorGenerator() + "!");
                            }
                        });
                    }
                });

                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String flavorGenerator() {

        // create a list of all unchecked flavor names
        List<String> uncheckedFlavors = new ArrayList<String>();
        for (int i = 0; i < mFlavors.length; i++) {
            if (mFlavors[i].getValue() == false) {
                uncheckedFlavors.add(mFlavors[i].getLabel());
            }
        }

        // generate a random number between 1 and uncheckedFlavors.size()
        Random rand = new Random();
        return uncheckedFlavors.get(rand.nextInt(uncheckedFlavors.size()));
    }

/*
    private void updateUI() {
        ArrayList<String> flavorList = new ArrayList<>(); // flavorList is Array List stat stores list of flavors retrieved from database
        SQLiteDatabase db = mOpenHelper.getReadableDatabase(); // db is the database; mOpenHelper is an instance of the class that opens the database
        Cursor cursor = db.query(FlavorContract.FlavorEntry.TABLE, // flavorContract class defines constants which are used to access the data in the database
                new String[]{FlavorContract.FlavorEntry.COL_FLAVOR_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) { // cursor iterates through database
            int idx = cursor.getColumnIndex(FlavorContract.FlavorEntry.COL_FLAVOR_TITLE);
            flavorList.add(cursor.getString(idx));
        }

        for (String f : flavorList) {
            System.out.println(f);
        }

        if (mFlavorAdapter == null) { // if adapter is empty, create new adapter
            mFlavorAdapter = new FlavorAdapter(getApplicationContext(), flavorList);
            setListAdapter(mFlavorAdapter);
        } else {
            //mFlavorAdapter.clear();
            //mFlavorAdapter.addAll(flavorList);
            //mFlavorAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
*/

// get the list of flavors from the allFlavors.db database
    private ArrayList<String> getFlavorListFromDatabase() {
        ArrayList<String> flavorList = new ArrayList<>(); // flavorList is Array List stat stores list of flavors retrieved from database
        SQLiteDatabase db = mOpenHelper.getReadableDatabase(); // db is the database; mOpenHelper is an instance of the class that opens the database
        Cursor cursor = db.query(FlavorContract.FlavorEntry.TABLE, // flavorContract class defines constants which are used to access the data in the database
                //new String[]{FlavorContract.FlavorEntry.COL_FLAVOR_TITLE},
                //null, null, null, null, null);
                new String[]{FlavorContract.FlavorEntry.COL_FLAVOR_TITLE}, null, null, null, null, FlavorContract.FlavorEntry.COL_FLAVOR_NUM_ORDER);

        while (cursor.moveToNext()) { // cursor iterates through database
            int idx = cursor.getColumnIndex(FlavorContract.FlavorEntry.COL_FLAVOR_TITLE);
            flavorList.add(cursor.getString(idx));
        }

        cursor.close();
        db.close();

        return flavorList;
    }

    /*
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_flavor:
                final EditText flavorEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a New Flavor")
                        .setMessage("Enter the name of the new flavor:")
                        .setView(flavorEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String flavor = String.valueOf(flavorEditText.getText());
                                SQLiteDatabase db = mOpenHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(FlavorContract.FlavorEntry.COL_FLAVOR_TITLE, flavor);
                                db.insertWithOnConflict(FlavorContract.FlavorEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();

                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteFlavor(View view) {
        View parent = (View) view.getParent();
        TextView flavorTextView = (TextView) parent.findViewById(R.id.name_flavor);
        String flavor = String.valueOf(flavorTextView.getText());
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.delete(FlavorContract.FlavorEntry.TABLE,
                FlavorContract.FlavorEntry.COL_FLAVOR_TITLE + " = ?",
                new String[]{flavor});
        db.close();
        updateUI();
    }
     */


}
