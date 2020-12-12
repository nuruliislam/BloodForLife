package capsulestudio.demobloodmanagement.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;

public class Donor_Profile extends AppCompatActivity
{
    String name,email,phone,address,city,post,country,group,user;
    @BindView(R.id.textview_name) TextView _name;
    @BindView(R.id.textview_email) TextView _email;
    @BindView(R.id.textview_contact) TextView _phone;
    @BindView(R.id.textview_address) TextView _address;
    @BindView(R.id.textview_city) TextView _city;
    //@BindView(R.id.textview_post) TextView _post;
    @BindView(R.id.textview_group) TextView _group;

    @BindView(R.id.tv1) TextView _tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_donor__profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (extras != null)
        {
            user=extras.getString("user");
            name = extras.getString("name");
            email=extras.getString("email");
            phone=extras.getString("phone");
            address=extras.getString("address");
            city=extras.getString("city");
            group=extras.getString("group");
            //post=extras.getString("post");
            //country=extras.getString("country");

            //The key argument here must match that used in the other activity
            Log.d("nurul",name);
        }

        _name.setText(name);
        _email.setText(email);
        _phone.setText(phone);
        _address.setText(address);
        _city.setText(city);
        _group.setText(group);

        if(user.equals("donor"))
        {
            _tv1.setText("My Profile");
        }else
        {
            _tv1.setText("Donor Profile");
        }
        //_post.setText(post);
        //_country.setText(country);

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


}
