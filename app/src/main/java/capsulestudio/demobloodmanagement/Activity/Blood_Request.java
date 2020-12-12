package capsulestudio.demobloodmanagement.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Blood_Request extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener
{
    @BindView(R.id.btnBloodRequest)
    Button btnBloodReq;

    @BindView(R.id.edtPatientName)
    EditText patientName;

    @BindView(R.id.edtRequireBag)
    EditText requireBag;

    @BindView(R.id.edtDiseaseName)
    EditText diseaseName;

    @BindView(R.id.edtHospital)
    EditText hospitalName;

    @BindView(R.id.edtContact)
    EditText contactNumber;

    @BindView(R.id.edtRequireDate)
    EditText requireDate;

    @BindView(R.id.edtRequirTime)
    EditText requireTime;


    private ProgressDialog progressDialog;
    String item;
    String email,name,contact;

    DatePickerDialog datePickerDialog;
    int mYear,mMonth,mDay;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);


        calendar=Calendar.getInstance();
        progressDialog = new ProgressDialog(this);

        date_init();
        time_init();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("O+");
        categories.add("O-");
        categories.add("AB+");
        categories.add("AB-");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            email = extras.getString("email");
            name = extras.getString("name");
            contact = extras.getString("contact");
            //The key argument here must match that used in the other activity
            Log.d("nurul",email + " " + name + " " + contact);
        }

        requireDate.setOnClickListener(this);
        requireTime.setOnClickListener(this);


        btnBloodReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                popupInsertData();
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


    private void date_init()
    {

        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        //String dateFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        requireDate.setText( sdf.format(calendar.getTime()));

    }

    private void time_init()
    {
        int mHour = calendar.get(Calendar.HOUR);
        int mMinute = calendar.get(Calendar.MINUTE);
        //time.setText(mHour +":"+ mMinute );
        boolean isPM = (mHour >= 12);
        requireTime.setText(String.format("%02d:%02d %s",mHour, mMinute, isPM ? "PM" : "AM"));

    }

    private void popupInsertData()
    {


        Log.d("nurul"," after popupInsertData");


        String sPN = patientName.getText().toString();
        String sRB = requireBag.getText().toString();
        String sDN = diseaseName.getText().toString();
        String sHN = hospitalName.getText().toString();

        String sCN = contactNumber.getText().toString();
        String sRD = requireDate.getText().toString();
        String sRT = requireTime.getText().toString();


        if(TextUtils.isEmpty(sPN))
        {
            Toast.makeText(Blood_Request.this, "Patient Name is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sRB))
        {
            Toast.makeText(Blood_Request.this, "Bag Quantity is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sCN))
        {
            Toast.makeText(Blood_Request.this, "Patient contact is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sDN)){
            Toast.makeText(Blood_Request.this, "Disease Name is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sHN))
        {
            Toast.makeText(Blood_Request.this, "Hospital Name is required", Toast.LENGTH_SHORT).show();
        }else
        {
            insertData(name,contact,sPN,item,sRB,sDN,sHN,sCN,sRD,sRT);
        }
        //progressDialog.dismiss();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    private void insertData(String name,String contact,String a, String b,String c,String d,String e,String f,String g,String h)
    {
        progressDialog.setTitle("Inserting");
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        //Log.d("nurul","insertData");
        //Log.d("nurul",a + " " + b + " " + c + " ");
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.insertReqData(name,contact,a, b,c,d,e,f,g,h);
        call.enqueue(new Callback<InsertDataResponseModel>()
        {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response)
            {
                Log.d("nurul","onResponse");

                if(response == null)
                {
                    Log.d("nurul","onResponse is null");
                }

                InsertDataResponseModel insertDataResponseModel = response.body();

                //check the status code
                if(insertDataResponseModel.getStatus()==1)
                {
                    Toast.makeText(Blood_Request.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(Blood_Request.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: " + t.getMessage());
                Toast.makeText(Blood_Request.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.edtRequireDate:

                //final Calendar c = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Blood_Request.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                requireDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;

            case R.id.edtRequirTime:
                setDateData();
                break;

                }

    }


    public void setDateData()
    {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Blood_Request.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                        /*if(selectedHour<12)
                        {
                            time.setText(selectedHour + ":" + selectedMinute + "");
                        }
                        else time.setText(selectedHour + ":" + selectedMinute); */

                boolean isPM = (selectedHour >= 12);
                requireTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));


            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
