package net.harithproperties.jogapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRun, btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRun=(Button)findViewById(R.id.btnRun);
        btnSearch=(Button)findViewById(R.id.btnSearch);

        btnRun.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRun:
                Intent intent = new Intent(MainActivity.this, Run.class);
                this.startActivity(intent);
                break;

            case R.id.btnSearch:
                Intent intent2 = new Intent(MainActivity.this, Search.class);
                this.startActivity(intent2);
                break;




            default:
                break;
        }

    }
}