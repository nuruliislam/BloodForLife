package capsulestudio.demobloodmanagement.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;

public class User_Profile extends AppCompatActivity
{
    String name,email,phone,address,city;
    @BindView(R.id.tv_name) TextView _name;
    @BindView(R.id.tv_email) TextView _email;
    @BindView(R.id.tv_phone) TextView _phone;
    @BindView(R.id.tv_address) TextView _address;
    @BindView(R.id.tv_city) TextView _city;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            name = extras.getString("name");
            email=extras.getString("email");
            phone=extras.getString("phone");
            address=extras.getString("address");
            city=extras.getString("city");

            //The key argument here must match that used in the other activity
            Log.d("nurul",name);
        }
        setContentView(R.layout.activity_user__profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        _name.setText(name);
        _email.setText(email);
        _phone.setText(phone);
        _address.setText(address);
        _city.setText(city);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

}
