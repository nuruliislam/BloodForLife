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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Campaign extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.campaign_name)
    EditText campName;

    @BindView(R.id.campaign_organize)
    EditText campOrganizer;

    @BindView(R.id.campaign_contact)
    EditText campContact;

    @BindView(R.id.campaign_place)
    EditText campPlace;

    @BindView(R.id.campaign_date)
    EditText campDate;

    @BindView(R.id.campaign_starTime)
    EditText campStart;

    @BindView(R.id.campaign_endTime)
    EditText campEnd;

    @BindView(R.id.campaign_sponsor)
    EditText campSponsor;

    @BindView(R.id.btnCampSubmit)
    Button b1;

    DatePickerDialog datePickerDialog;
    int mYear,mMonth,mDay;
    Calendar calendar;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__campaign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        calendar=Calendar.getInstance();
        progressDialog = new ProgressDialog(this);

        date_init();
        time_init();

        campDate.setOnClickListener(this);
        campStart.setOnClickListener(this);
        campEnd.setOnClickListener(this);
        b1.setOnClickListener(this);


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }
    private void date_init()
    {

        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        //String dateFormat = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        campDate.setText( sdf.format(calendar.getTime()));

    }

    private void time_init()
    {
        int mHour = calendar.get(Calendar.HOUR);
        int mMinute = calendar.get(Calendar.MINUTE);
        //time.setText(mHour +":"+ mMinute );
        boolean isPM = (mHour >= 12);
        campStart.setText(String.format("%02d:%02d %s",mHour, mMinute, isPM ? "PM" : "AM"));
        campEnd.setText(String.format("%02d:%02d %s",mHour, mMinute, isPM ? "PM" : "AM"));
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.campaign_date:

                //final Calendar c = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Add_Campaign.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                campDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;

            case R.id.campaign_endTime:
                setDateData();
                break;
            case R.id.campaign_starTime:
                setDateData();
                break;

            case R.id.btnCampSubmit:
                popupInsertData();
                break;
        }

    }


    public void setDateData()
    {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Add_Campaign.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                        /*if(selectedHour<12)
                        {
                            time.setText(selectedHour + ":" + selectedMinute + "");
                        }
                        else time.setText(selectedHour + ":" + selectedMinute); */

                boolean isPM = (selectedHour >= 12);
                campStart.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));
                campEnd.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void popupInsertData()
    {
        String _campName=campName.getText().toString();
        String organizerName=campOrganizer.getText().toString();
        String organizerContact=campContact.getText().toString();
        String placeName =campPlace.getText().toString();
        String placeDate =campDate.getText().toString();
        String placeStart =campStart.getText().toString();
        String placeEnd =campEnd.getText().toString();
        String placeSponsor =campSponsor.getText().toString();



        if(TextUtils.isEmpty(placeName)){
            Toast.makeText(Add_Campaign.this, "Place Name is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(placeDate))
        {
            Toast.makeText(Add_Campaign.this, "Date is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(placeStart)){
            Toast.makeText(Add_Campaign.this, "Start Time is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(placeEnd))
        {
            Toast.makeText(Add_Campaign.this, "End time is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(_campName))
        {
            Toast.makeText(Add_Campaign.this, "Campaign Name is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(organizerName)){
            Toast.makeText(Add_Campaign.this, "Organizer Name is required", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(organizerContact))
        {
            Toast.makeText(Add_Campaign.this, "Organizer Contact is required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            insertData(_campName,organizerName,organizerContact,placeName,placeDate,placeStart,placeEnd,placeSponsor);
        }


    }


    private void insertData(String a, String b,String c,String d,String e,String f,String g,String h)
    {
        progressDialog.setTitle("Inserting");
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.insertCampData(a, b,c,d,e,f,g,h);
        call.enqueue(new Callback<InsertDataResponseModel>()
        {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response)
            {
                InsertDataResponseModel insertDataResponseModel = response.body();

                //check the status code
                if(insertDataResponseModel.getStatus()==1)
                {
                    Toast.makeText(Add_Campaign.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(Add_Campaign.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: " + t.getMessage());
                Toast.makeText(Add_Campaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
