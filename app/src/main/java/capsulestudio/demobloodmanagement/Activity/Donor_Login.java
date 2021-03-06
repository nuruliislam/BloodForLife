package capsulestudio.demobloodmanagement.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.model.MSG;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Donor_Login extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog pDialog;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    public static final String donorPREFERENCES = "donorPrefs" ;
    public static final String donor_email_key = "donor_email";
    public static final String donor_password_key = "donor_password";

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor__login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        sharedpreferences = getSharedPreferences(donorPREFERENCES, Context.MODE_PRIVATE);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Donor_signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
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
    protected void onStart()
    {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }


    public void login() {
        Log.d(TAG, "Login");

        if (validate() == false) {
            onLoginFailed();
            return;
        }

        //_loginButton.setEnabled(false);

        loginByServer();
    }

    private void loginByServer()
    {
        pDialog = new ProgressDialog(Donor_Login.this,
                R.style.AppTheme_AppBarOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Plz wait.....");
        pDialog.setCancelable(false);

        showpDialog();

        String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);

        Call<MSG> userCall = service.donorLogIn(email,password);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
                Log.d("nurul", "" + response.body().getMessage());


                if(response.body().getSuccess() == 1)
                {
                    Log.d("nurul", "onResponse.getSuccess");

                    String e  = _emailText.getText().toString();
                    String p  = _passwordText.getText().toString();

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(donor_email_key, e);
                    editor.putString(donor_password_key, p);

                    editor.commit();

                    Intent i=new Intent(Donor_Login.this,Donor_Activity.class);
                    i.putExtra("email",e);

                    startActivity(i);
                    Toast.makeText(getApplicationContext(),"Log in Successful",Toast.LENGTH_SHORT).show();

                    finish();
                }else
                {
                    Log.d("nurul", "onResponse.getSuccess.else");
                    Toast.makeText(Donor_Login.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t)
            {
                Log.d("nurul", "onFailure");
                hidepDialog();
                Log.d("nurul", t.toString());
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }



    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            requestFocus(_emailText);
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Password is empty");
            requestFocus(_passwordText);
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view)
    {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}