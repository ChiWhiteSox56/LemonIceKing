package com.lemonice.cws.lemoniceking;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lemonice.cws.lemoniceking.db.FlavorContract;
import com.lemonice.cws.lemoniceking.db.FlavorDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FlavorDbHelper mHelper;
    private ListView mFlavorListView;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new FlavorDbHelper(this);
        mFlavorListView = (ListView) findViewById(R.id.list_flavors);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
                                SQLiteDatabase db = mHelper.getWritableDatabase();
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
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(FlavorContract.FlavorEntry.TABLE,
                FlavorContract.FlavorEntry.COL_FLAVOR_TITLE + " = ?",
                new String[]{flavor});
        db.close();
        updateUI();
    }

    private void updateUI() {
        ArrayList<String> flavorList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(FlavorContract.FlavorEntry.TABLE,
                new String[]{FlavorContract.FlavorEntry._ID, FlavorContract.FlavorEntry.COL_FLAVOR_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(FlavorContract.FlavorEntry.COL_FLAVOR_TITLE);
            flavorList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_flavor,
                    R.id.name_flavor,
                    flavorList);
            mFlavorListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(flavorList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}
