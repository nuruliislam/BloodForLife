package capsulestudio.demobloodmanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;

public class Admin_Activity extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.btnAdminManageDonor)
    Button b1;

    @BindView(R.id.btnAdminAddAdmin)
    Button b2;

    @BindView(R.id.btnAdminAddHospital)
    Button b3;

    @BindView(R.id.btnAdminManageReq)
    Button b4;

    @BindView(R.id.btnAdminManageMember)
    Button b5;

    @BindView(R.id.btnAdminManageHospital)
    Button b6;

    @BindView(R.id.btnAdminDonationHistory)
    Button b7;

    @BindView(R.id.btnAdminManageCampaign)
    Button b8;

    @BindView(R.id.btnAdminLogout)
    Button b9;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);
        b1.setOnClickListener(this);
        b5.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b2.setOnClickListener(this);
        b6.setOnClickListener(this);
        b8.setOnClickListener(this);
        b7.setOnClickListener(this);
        b9.setOnClickListener(this);

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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        if(item.getItemId()==R.id.action_member_request)
        {
            Intent i=new Intent(Admin_Activity.this,Member_Request.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        Intent i;
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        boolean isConnected;
        switch (v.getId())
        {
            case R.id.btnAdminManageDonor:

                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Donor.class);
                    i.putExtra("user","admin");
                    i.putExtra("type","full");
                    i.putExtra("item","nothing");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.btnAdminManageMember:
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Member.class);
                    i.putExtra("user","admin");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnAdminAddHospital:
                i = new Intent(this, Add_Hospital.class);
                i.putExtra("user","admin");
                startActivity(i);
                break;

            case R.id.btnAdminAddAdmin:
                i = new Intent(this, Add_Admin.class);
                startActivity(i);
                break;
            case R.id.btnAdminManageHospital:
                //Log.d("nurul","here");

                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {

                    i = new Intent(this, Manage_Hospital.class);
                    i.putExtra("user","admin");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnAdminManageCampaign:

                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Campaign.class);
                    i.putExtra("user","admin");
                    startActivity(i);
                }
                else
                    {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnAdminDonationHistory:
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_History.class);
                    i.putExtra("user","admin");
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnAdminManageReq:
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Request.class);
                    i.putExtra("user","admin");
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnAdminLogout:
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.adminPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(Admin_Activity.this, MainActivity.class));
                break;



        }
    }

}
