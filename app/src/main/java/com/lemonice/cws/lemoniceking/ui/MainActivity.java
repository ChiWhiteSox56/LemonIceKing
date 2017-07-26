package com.lemonice.cws.lemoniceking.ui;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.lemonice.cws.lemoniceking.R;
import com.lemonice.cws.lemoniceking.adapters.FlavorAdapter;
import com.lemonice.cws.lemoniceking.db.FlavorContract;
import com.lemonice.cws.lemoniceking.db.FlavorDbOpenHelper;

import java.util.ArrayList;


// The FlavorContract class defines constants which are used to access the data in the database
// The helper class FlavorDbOpenHelper opens the database

public class  MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_FLAVORTEXT = "sp_key_flavortext";
    private static final String KEY_ISCHECKED = "sp_key_ischecked";
    private FlavorDbOpenHelper mOpenHelper;

    private ListView mFlavorListView;
    private CheckedTextView mCheckedTextView;
    private FlavorAdapter mFlavorAdapter;

    private static final String PREFS_FILE = "com.lemonice.cws.lemoniceking.preferences";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOpenHelper = new FlavorDbOpenHelper(this);

        // initiate a ListView
        mFlavorListView = (ListView) findViewById(R.id.listView);

        // initiate a SharedPreferences object and a SharedPrefrences editor
        mSharedPreferences = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        // create adapter and populate list in UpdateUI()
        updateUI();
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

        mEditor.putString(KEY_FLAVORTEXT, mCheckedTextView.getText().toString());
        mEditor.putBoolean(KEY_ISCHECKED, mCheckedTextView.isChecked());
        mEditor.commit();
    }

    /* UNCOMMENT AFTER SHARED PREFERENCES ARE FIXED
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

        if (mFlavorAdapter == null) { // if adapter is empty, create new adapter
            mFlavorAdapter = new FlavorAdapter(getApplicationContext(), flavorList);
            mFlavorListView.setAdapter(mFlavorAdapter);
            System.out.println("Wait here");
        } else {
            //mFlavorAdapter.clear();
            //mFlavorAdapter.addAll(flavorList);
            //mFlavorAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }



}
