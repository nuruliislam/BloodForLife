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
import capsulestudio.demobloodmanagement.model.User;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Donor_Activity extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.donorLogout)Button b5;
    @BindView(R.id.btnDonorUpdateDonation) Button b3;
    @BindView(R.id.btnDonorBloodReq) Button b4;
    @BindView(R.id.btnDonorViewCamp) Button b2;
    @BindView(R.id.btnDonorViewReq) Button b1;



    String value,donorName,blood_group,contact,address,city;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        b5.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b2.setOnClickListener(this);
        getUserData();

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

        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile)
        {

            activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if (isConnected==true)
            {
                Intent i = new Intent(this, Donor_Profile.class);
                i.putExtra("user","donor");
                i.putExtra("name", donorName);
                i.putExtra("email",value);
                i.putExtra("phone", contact);
                i.putExtra("address",address);
                i.putExtra("city",city);
                i.putExtra("group",blood_group);
                //i.putExtra("post",_post);
                //i.putExtra("country",_country);

                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
            }


        }

        return super.onOptionsItemSelected(item);
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
    public void onClick(View v)
    {
        Intent i;
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        boolean isConnected;

        switch (v.getId())
        {
            case R.id.donorLogout:
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.donorPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(Donor_Activity.this, MainActivity.class));
                break;

            case R.id.btnDonorUpdateDonation:

                i=new Intent(getApplicationContext(),Update_Donation.class);
                i.putExtra("email",value);
                i.putExtra("name",donorName);
                i.putExtra("group",blood_group);
                startActivity(i);
                break;

            case R.id.btnDonorBloodReq:

                i=new Intent(getApplicationContext(),Blood_Request.class);
                i.putExtra("email",value);
                i.putExtra("name",donorName);
                i.putExtra("contact",contact);
                startActivity(i);
                break;

            case R.id.btnDonorViewCamp:

                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i=new Intent(getApplicationContext(),Manage_Campaign.class);
                    i.putExtra("user","donor");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnDonorViewReq:
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i=new Intent(getApplicationContext(),Manage_Request.class);
                    i.putExtra("user","donor");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void getUserData()
    {
        Log.d("nurul","getUserData");
        APIService service = ApiClient.getClient().create(APIService.class);
        //User user = new User(name, email, password);
        Call<User> userCall = service.getDonorData(value);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                donorName=response.body().getName();
                blood_group=response.body().getBlood_group();
                contact=response.body().getPhone();
                address=response.body().getAddress();
                city=response.body().getCity();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                Log.d("nurul","On Failure");

            }
        });
    }
}
