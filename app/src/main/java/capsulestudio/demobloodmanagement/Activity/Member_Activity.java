package capsulestudio.demobloodmanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import capsulestudio.demobloodmanagement.model.User;
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Member_Activity extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.memberLogout)Button b1;
    @BindView(R.id.btnMemberViewDonor) Button b2;
    @BindView(R.id.btnMemberAddCampaign) Button b5;
    @BindView(R.id.btnMemberViewCamp)  Button b4;
    @BindView(R.id.btnMemberViewReq) Button viewReq;

    String value,_name,_contact,_email,_address,_city,_post,_country;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("nurul","MemberActivity:onCreate");
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            value = extras.getString("email");
            //The key argument here must match that used in the other activity
            Log.d("nurul",value);
        }



        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        viewReq.setOnClickListener(this);
    }
    @Override
    protected void onStart()
    {
        //Log.d("nurul","MemberActivity:onStart");
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    /*@Override
    protected void onResume()
    {
        Log.d("nurul","MemberActivity:onResume");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Log.d("nurul","MemberActivity:onPause");
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        Log.d("nurul","MemberActivity:onRestart");

        super.onRestart();
    }

    @Override
    protected void onStop()
    {
        Log.d("nurul","MemberActivity:onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.d("nurul","MemberActivity:onDestroy");
        super.onDestroy();
    } */


    @Override
    public void onBackPressed()
    {
        this.finish();
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
            case R.id.memberLogout:
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.memberPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(Member_Activity.this, MainActivity.class));
                break;

            case R.id.btnMemberAddCampaign:
                startActivity(new Intent(Member_Activity.this, Add_Campaign.class));
                break;

            case R.id.btnMemberViewCamp:
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Campaign.class);
                    i.putExtra("user","member");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnMemberViewDonor:
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Donor.class);
                    i.putExtra("user","member");
                    i.putExtra("type","full");
                    i.putExtra("item","nothing");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnMemberViewReq:
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Request.class);
                    i.putExtra("user","member");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_member, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        boolean isConnected;

        if(id==android.R.id.home)
        {
            Intent i=new Intent(Member_Activity.this,MainActivity.class);
            startActivity(i);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile)
        {

            activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if (isConnected==true)
            {
                getUserData();
            }
            else
                {
                Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Member_Activity.this, Member_Profile.class);
                    i.putExtra("user","member");
                    i.putExtra("name", _name);
                    i.putExtra("email",value);
                    i.putExtra("phone", _contact);
                    i.putExtra("address",_address);
                    i.putExtra("city",_city);
                    i.putExtra("post",_post);
                    i.putExtra("country",_country);

                    startActivity(i);
                }
            }, 2000);




        }

        return super.onOptionsItemSelected(item);
    }


    private void getUserData()
    {
        Log.d("nurul","getUserData " + value);
        APIService service = ApiClient.getClient().create(APIService.class);
        //User user = new User(name, email, password);
        Call<member_model> userCall = service.getMemberData(value);

        userCall.enqueue(new Callback<member_model>() {
            @Override
            public void onResponse(Call<member_model> call, Response<member_model> response)
            {
                if(response!=null)
                {
                    Log.d("nurul","Respnose is not null");


                    _name=response.body().getmName();
                    _contact=response.body().getmContact();
                    _address=response.body().getmAddress();
                    _city=response.body().getmCity();
                    _post=response.body().getmPost();
                    _country=response.body().getmCountry();
                }

                else Log.d("nurul","Respnose is null");


            }

            @Override
            public void onFailure(Call<member_model> call, Throwable t)
            {
                Log.d("nurul","On Failure");

            }
        });
    }


}
