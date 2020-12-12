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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import capsulestudio.demobloodmanagement.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button b1,b2,b3,b4,b5;
    LinearLayout layout;

    SharedPreferences membersharedpreferences,hospitalsharedpreferences,donorsharedpreferences,adminsharedpreferences;

    public static final String hospitalPREFERENCES = "hospitalPrefs" ;
    public static final String hospital_email_key = "hospital_email";
    public static final String hospital_password_key = "hospital_password";


    public static final String memberPREFERENCES = "memberPrefs" ;
    public static final String member_email_key = "member_email";
    public static final String member_password_key = "member_password";

    public static final String donorPREFERENCES = "donorPrefs" ;
    public static final String donor_email_key = "donor_email";
    public static final String donor_password_key = "donor_password";

    public static final String adminPREFERENCES = "adminPrefs" ;
    public static final String admin_email_key = "admin_email";
    public static final String admin_password_key = "admin_password";

    static int n=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("nurul","MainActivity:onCreate");

        hospitalsharedpreferences = getSharedPreferences(hospitalPREFERENCES, Context.MODE_PRIVATE);
        membersharedpreferences = getSharedPreferences(memberPREFERENCES, Context.MODE_PRIVATE);
        donorsharedpreferences = getSharedPreferences(donorPREFERENCES, Context.MODE_PRIVATE);
        adminsharedpreferences = getSharedPreferences(adminPREFERENCES, Context.MODE_PRIVATE);


        b1=(Button)findViewById(R.id.btnUser);
        b1.setOnClickListener(this);
        b2=(Button)findViewById(R.id.btnDonor);
        b3=(Button)findViewById(R.id.btnMember);
        b4=(Button)findViewById(R.id.btnHospital);
        layout=(LinearLayout) findViewById(R.id.layoutAdmin);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                n++;
                Log.d("nurul","num: " +n);

              if(n>2)
              {
                  Intent i;
                  String admin_email=adminsharedpreferences.getString(admin_email_key,"no");
                  String admin_pass=adminsharedpreferences.getString(admin_password_key,"no");

                  if(!admin_email.equals("no") && !admin_pass.equals("no"))
                  {

                      i=new Intent(getApplicationContext(),Admin_Activity.class);
                      i.putExtra("email",admin_email);

                      startActivity(i);
                  }
                  else
                  {
                      i = new Intent(getApplicationContext(), Admin_Login.class);
                      startActivity(i);
                  }
                  n=0;
              }
            }
        });
        //b5.setVisibility(View.GONE);
    }

    @Override
    protected void onStart()
    {
        //Log.d("nurul","MainActivity:onStart");
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    @Override
    protected void onResume()
    {
        //Log.d("nurul","MainActivity:onResume");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        n=0;
        //Log.d("nurul","MainActivity:onPause");
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        //Log.d("nurul","MainActivity:onRestart");

        super.onRestart();
    }

    @Override
    protected void onStop()
    {
        //Log.d("nurul","MainActivity:onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        //Log.d("nurul","MainActivity:onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_advice) {
            return true;
        }
        if (id == R.id.action_about) {
            return true;
        }
        if (id == R.id.action_contact) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        Intent i;
        switch (v.getId())
        {
            case R.id.btnUser:
                i = new Intent(this, General_User.class);
                startActivity(i);
                break;

            case R.id.btnDonor:
                String donor_email=donorsharedpreferences.getString(donor_email_key,"no");
                String donor_pass=donorsharedpreferences.getString(donor_password_key,"no");

                if(!donor_email.equals("no") && !donor_pass.equals("no"))
                {

                    i=new Intent(getApplicationContext(),Donor_Activity.class);
                    i.putExtra("email",donor_email);

                    startActivity(i);
                }
                else
                {
                    i = new Intent(getApplicationContext(), Donor_Login.class);
                    startActivity(i);
                }
                break;

            case R.id.btnMember:
                String member_email=membersharedpreferences.getString(member_email_key,"no");
                String member_pass=membersharedpreferences.getString(member_password_key,"no");

                if(!member_email.equals("no") && !member_pass.equals("no"))
                {


                    i=new Intent(getApplicationContext(),Member_Activity.class);
                    i.putExtra("email",member_email);

                    startActivity(i);


                }
                else
                {
                    i = new Intent(getApplicationContext(), Member_login.class);
                    startActivity(i);
                }
                break;

            case R.id.btnHospital:
                String email=hospitalsharedpreferences.getString(hospital_email_key,"no");
                String pass=hospitalsharedpreferences.getString(hospital_password_key,"no");

                if(!email.equals("no") && !pass.equals("no"))
                {

                    i=new Intent(getApplicationContext(),Hospital_Activity.class);

                    startActivity(i);
                }
                else
                {
                    i = new Intent(getApplicationContext(), Hospital_Login.class);
                    startActivity(i);
                }
                break;

                default:
                    break;
        }
    }
}
