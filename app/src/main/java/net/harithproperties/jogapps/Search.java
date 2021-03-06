package net.harithproperties.jogapps;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static net.harithproperties.jogapps.DBHelper.DATE;
import static net.harithproperties.jogapps.DBHelper.TABLE_NAME;
import static net.harithproperties.jogapps.DBHelper.TIME;


public class Search extends AppCompatActivity {

    ListView listView;
    ArrayList<Jogging> StudentList = new ArrayList<Jogging>();
    //variables to hold staff records

    ListAdapterJogging listAdapter;
    DBHelper sqLiteHelper;
    static TextView editText;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    TextView txtEmpty;
    Cursor c;
// RecordCursorAdapter extends CursorAdapter class


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.listView1);

        editText = (TextView) findViewById(R.id.edittext1);

        txtEmpty = (TextView) findViewById(R.id.txtEmpty);

        listView.setTextFilterEnabled(true);

        sqLiteHelper = new DBHelper(this);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Getting Search ListView clicked item.
                Jogging ListViewClickData = (Jogging) parent.getItemAtPosition(position);

                // printing clicked item on screen using Toast message.
                // Toast.makeText(SearchSQLiteActivity.this, ListViewClickData.getDate(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),
                     ShowSingleRecordActivity.class);
                           i.putExtra("jogid", ListViewClickData.getJoggingid());
                          startActivity(i);
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);

            }
        });






        editText.addTextChangedListener(new TextWatcher() {




            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }


            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                listAdapter.getFilter().filter(stringVar.toString());
            }
        });



        DisplayDataInToListView();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
     //   editText.setText(day+ "/" + (month+1) + "/" + year);



        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),
                        Run.class);
                i.putExtra("update", false);
                startActivity(i);

            }
        });

    }



    public void showTruitonDatePickerDialog(View v){
        DialogFragment newFragment = new Search.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstancState){

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            editText.setText(day+ "/" + (month+1) + "/" + year);

        }

    }




    public void DisplayDataInToListView() {

        if(listView==null){

        }
        else{

            sqLiteDatabase = sqLiteHelper.getWritableDatabase();

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME+" ORDER BY "+DATE+" ", null);



            Jogging exercise;
            String tempExerciseid;
            String tempExercise;
            String tempWeight;
            String tempRep;

            String tempDate;

            String tempTime;

            StudentList.clear();

            if (cursor.moveToFirst()) {
                do {

                    //get data from field
                    tempExerciseid = cursor.getString(cursor.getColumnIndex(DBHelper.JOGID));
                    tempExercise = cursor.getString(cursor.getColumnIndex(DBHelper.DATE));
                    tempWeight= cursor.getString(cursor.getColumnIndex(DBHelper.TIME));
                    tempRep = cursor.getString(cursor.getColumnIndex(DBHelper.DISTANCEKM));
                    tempDate= cursor.getString(cursor.getColumnIndex(DBHelper.SPEED));
                    tempTime= cursor.getString(cursor.getColumnIndex(DBHelper.DURATION));
                    exercise = new Jogging(tempExercise, tempWeight,tempRep,tempDate, tempTime, tempExerciseid);
                    StudentList.add(exercise);




                } while (cursor.moveToNext());
            }

            listAdapter = new ListAdapterJogging(Search.this, R.layout.searchlistcell, StudentList);

            listView.setAdapter(listAdapter);
            listView.setEmptyView(txtEmpty);

            cursor.close();}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent refresh = new Intent(this, Search.class);
        startActivity(refresh);
        this.finish();
    }

    @Override
    protected void onResume() {

        listAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "TAP date to search date \n TAP exercise for edit", Toast.LENGTH_LONG).show();
        super.onResume();


    }

}