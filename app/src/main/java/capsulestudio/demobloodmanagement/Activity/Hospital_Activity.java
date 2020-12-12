package capsulestudio.demobloodmanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;

public class Hospital_Activity extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.hospitalLogout)Button b5;
    @BindView(R.id.btnHospitalAddCamp) Button b2;
    @BindView(R.id.btnHospitalViewCamp) Button b3;
    @BindView(R.id.btnHospitalCallAdmin) Button b1;
    @BindView(R.id.btnHospitalSearchDonor) Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);

        b5.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b1.setOnClickListener(this);
        b4.setOnClickListener(this);


    }
    @Override
    protected void onStart()
    {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        Intent i;
        switch (v.getId())
        {
            case R.id.hospitalLogout:
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.hospitalPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(Hospital_Activity.this, MainActivity.class));
                break;

            case R.id.btnHospitalAddCamp:
                i=new Intent(getApplicationContext(),Add_Campaign.class);
                startActivity(i);
                break;

            case R.id.btnHospitalViewCamp:
                i=new Intent(getApplicationContext(),Manage_Campaign.class);
                i.putExtra("user","hospital");
                startActivity(i);
                break;

            case R.id.btnHospitalCallAdmin:
                i=new Intent(getApplicationContext(),Call_Admin.class);
                i.putExtra("user","hospital");
                startActivity(i);
                break;

            case R.id.btnHospitalSearchDonor:
                i=new Intent(getApplicationContext(),Search_Donor.class);
                i.putExtra("user","hospital");
                startActivity(i);
                break;

        }

    }


}
