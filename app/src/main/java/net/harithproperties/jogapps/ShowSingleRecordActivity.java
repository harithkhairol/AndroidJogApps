package net.harithproperties.jogapps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowSingleRecordActivity  extends AppCompatActivity {

    String IDholder;
    private TextView dateTxt, timeTxt, distanceTxt,speedTxt,durationTxt;
    SQLiteDatabase sqLiteDatabase;
    DBHelper sqLiteHelper;
    Cursor cursor;
    Button Delete, Edit;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    private AlertDialog.Builder build;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        dateTxt=(TextView)findViewById(R.id.dateTxt);
        timeTxt=(TextView)findViewById(R.id.timeTxt);
        distanceTxt=(TextView)findViewById(R.id.distanceTxt);
        speedTxt=(TextView)findViewById(R.id.speedTxt);
        durationTxt=(TextView)findViewById(R.id.durationTxt);

        Delete = (Button)findViewById(R.id.buttonDelete);
        Edit = (Button)findViewById(R.id.buttonEdit);

        sqLiteHelper = new DBHelper(this);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //invoking AlertDialog box
                build = new AlertDialog.Builder(ShowSingleRecordActivity.this);
                build.setTitle("Delete this record " + dateTxt.getText());
                build.setMessage("Are you sure you want to delete the record?");

                //user select UPDATE
                build.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                OpenSQLiteDataBase();

                                SQLiteDataBaseQueryHolder = "DELETE FROM "+DBHelper.TABLE_NAME+" WHERE jogid = "+IDholder+"";

                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                                sqLiteDatabase.close();

                                finish();
                                Toast.makeText(
                                        getApplicationContext(),
                                        dateTxt.getText()+
                                                " is deleted.", Toast.LENGTH_SHORT).show();


                            }
                        });//end UPDATE

                //user select DELETE
                build.setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });//end DELETE
                AlertDialog alert = build.create();
                alert.show();





            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),UpdateRecord.class);

                intent.putExtra("EditID", IDholder);

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {

        ShowSingleRecordInTextView();

        super.onResume();
    }

    public void ShowSingleRecordInTextView() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        IDholder = getIntent().getStringExtra("jogid");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE jogid = " + IDholder + "", null);

        if (cursor.moveToFirst()) {

            do {

                //get data from field and transfer to EditText
                dateTxt.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.DATE)));
                timeTxt.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.TIME)));
                distanceTxt.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.DISTANCEKM)));
                speedTxt.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.SPEED)));
                durationTxt.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.DURATION)));
            }
            while (cursor.moveToNext());

            cursor.close();

        }
    }

    public void OpenSQLiteDataBase(){

        sqLiteDatabaseObj = openOrCreateDatabase(sqLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
}