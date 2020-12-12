package capsulestudio.demobloodmanagement.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.model.MSG;
import capsulestudio.demobloodmanagement.model.User;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Donation extends AppCompatActivity implements View.OnClickListener
{

    private ProgressDialog pDialog;
    @BindView(R.id.input_patient)
    EditText patient;

    @BindView(R.id.input_date)
    EditText date;



    @BindView(R.id.input_disease)
    EditText disease;

    @BindView(R.id.input_hospital)
    EditText hospital;

    @BindView(R.id.btn_signup)
    Button submit;

    String donor_email,donor_name,donor_group;
    DatePickerDialog datePickerDialog;
    int mYear,mMonth,mDay;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__donation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            donor_email = extras.getString("email");
            donor_name = extras.getString("name");
            donor_group = extras.getString("group");
            //The key argument here must match that used in the other activity
            Log.d("nurul",donor_email + " " + donor_name + " " + donor_group);
        }

        calendar=Calendar.getInstance();


        date_init();
        date.setOnClickListener(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitData();
            }
        });


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

    public void SubmitData()
    {
        //Log.d(TAG, "Signup");

        if (validate() == false) {
            onSubmitFailed();
            return;
        }



        saveToServerDB();

    }




    public void onSubmitFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        submit.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String patientName = patient.getText().toString();
        String diseaseName = disease.getText().toString();
        String hospitalName = hospital.getText().toString();


        if (patientName.isEmpty() || patientName.length() < 3) {
            patient.setError("at least 3 characters");
            valid = false;
        } else {
            patient.setError(null);
        }

        if (diseaseName.isEmpty() || diseaseName.length() < 3) {
            disease.setError("at least 3 characters");
            valid = false;
        } else {
            disease.setError(null);
        }

        if (hospitalName.isEmpty() || hospitalName.length() < 3) {
            hospital.setError("at least 3 characters");
            valid = false;
        } else {
            hospital.setError(null);
        }

        return valid;
    }


    private void saveToServerDB()
    {

        Log.d("test", "saveToServerDB");
        pDialog = new ProgressDialog(Update_Donation.this,
                R.style.AppTheme_PopupOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Updating Donation...");
        pDialog.setCancelable(false);

        //showpDialog();

        Log.d("nurul","Save to server Db");

        String patientName = patient.getText().toString();
        String dateData = date.getText().toString();
        String diseaseName=disease.getText().toString();
        String hospitalName=hospital.getText().toString();



        APIService service = ApiClient.getClient().create(APIService.class);
        //User user = new User(name, email, password);
        Log.d("nurul",donor_name + " " + donor_group + " " + patientName + " " + dateData +  " " + diseaseName
        + " " + hospitalName);

        Call<MSG> userCall = service.updateDonation(donor_email,donor_name,donor_group,patientName,dateData,diseaseName,hospitalName);


        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response)
            {
                hidepDialog();
                //onSignupSuccess();
                Log.d("nurul","onResponse");
                if (response.body().getSuccess() == 1)
                {
                    Toast.makeText(Update_Donation.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    //Intent i=new Intent(Update_Donation.this,Donor_Activity.class);
                    //i.putExtra("email",donor_email);

                    //startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Update_Donation.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t)
            {
                Toast.makeText(Update_Donation.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("nurul","onFailure: " + t.getMessage());
                hidepDialog();

            }
        });

    }

    private void showpDialog()
    {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog()
    {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void date_init()
    {

        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        //String dateFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date.setText( sdf.format(calendar.getTime()));

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.input_date:

                //final Calendar c = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Update_Donation.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;
        }
    }
}
