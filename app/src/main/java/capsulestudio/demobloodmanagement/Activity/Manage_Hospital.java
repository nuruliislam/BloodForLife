package capsulestudio.demobloodmanagement.Activity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.adapter.DonorAdapter;
import capsulestudio.demobloodmanagement.adapter.HospitalAdapter;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.ListHospitalModel;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.hospital_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import capsulestudio.demobloodmanagement.utility.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_Hospital extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    ListHospitalModel listHospitalModel;
    List<hospital_model> listHospital;
    hospital_model m;

    LayoutInflater inflater;
    Context context;

    private AlertDialog mOptionsDialog;
    //String s="Admin";

    private HospitalAdapter hospitalAdapter;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    public static Activity activity;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__hospital);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        activity = this;



        context=getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_hospital);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listHospital=listHospitalModel.getListHospitalModel();
                m=listHospital.get(position);


            }

            @Override
            public void onLongClick(View view,final int position)
            {


                listHospital=listHospitalModel.getListHospitalModel();
                m=listHospital.get(position);


                CharSequence colors[] = new CharSequence[]{"Edit", "Delete","Call"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Manage_Hospital.this);
                builder.setTitle("Choose option");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            showNoteDialog(true, m);
                        } else if (which == 1)
                        {
                            DeleteIt(m);
                        }else if (which == 2)
                        {
                            if (checkPermission(Manifest.permission.CALL_PHONE)) {
                                call(m);
                            } else {
                                //dial.setEnabled(false);
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
                                call(m);
                            }
                        }
                    }
                });
                builder.show();

            }
        }
        ));

        progressDialog = new ProgressDialog(this);

        getAllDataFood();
    }
    private void call(final hospital_model m)
    {
        String phoneNumber = m.gethContact();
        //Toast.makeText(getApplicationContext(), "number is " + phoneNumber, Toast.LENGTH_LONG).show();


        if (checkPermission(Manifest.permission.CALL_PHONE)) {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        } else {
            Toast.makeText(getApplicationContext(), "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
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

    private void showNoteDialog(final boolean shouldUpdate, final hospital_model m)
    {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_hospital, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Manage_Hospital.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText hName = view.findViewById(R.id.editHospitalName);
        final EditText hContact = view.findViewById(R.id.editHospitalContact);
        final EditText hAddress = view.findViewById(R.id.editHospitalAddress);
        final EditText hCity = view.findViewById(R.id.editHospitalCity);

        hName.setText(m.gethName());
        hContact.setText(m.gethContact());
        hAddress.setText(m.gethAddress());
        hCity.setText(m.gethCity());




        /*if (shouldUpdate && note != null) {
            inputNote.setText(note.getNote());
        } */
        alertDialogBuilderUserInput
                .setCancelable(true)
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id)
                    {


                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Log.d("nurul","Update");
                updateData(hName.getText().toString(),hContact.getText().toString(),hAddress.getText().toString(),hCity.getText().toString(),m.gethId());
                alertDialog.dismiss();

            }
        });
    }


    private void DeleteIt(final hospital_model m)
    {
        //Log.d("nurul",  "in deleteIT");
        String email=m.gethEmail();
        //Log.d("nurul",email);

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.deleteHospital(email);
        call.enqueue(new Callback<InsertDataResponseModel>()
        {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response)
            {
                getAllDataFood();
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: "+ t.getMessage());
            }
        });
    }

    private void updateData(String a,String b,String c,String d,int e)
    {

        pDialog = new ProgressDialog(Manage_Hospital.this,
                R.style.AppTheme_PopupOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.editHospital(a,b,c,d,e);
        call.enqueue(new Callback<InsertDataResponseModel>()
        {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response)
            {
                hidepDialog();
                Log.d("nurul","Update data onResponse");
                getAllDataFood();
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t)
            {
                hidepDialog();
                Log.d("nurul","onFailure: "+ t.getMessage());
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

    private void getAllDataFood()
    {
        progressDialog.setTitle("Displaying data");
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ListHospitalModel> call = apiService.getAllHospital();

        Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<ListHospitalModel>()
        {
            @Override
            public void onResponse(Call<ListHospitalModel> call, Response<ListHospitalModel> response)
            {
                Log.d("nurul","onResponse");
                listHospitalModel = response.body();
                //Log.d("nurul",listPersonModel.getMessage());

                if(listHospitalModel.getStatus()==1)
                {
                    //Log.d("nurul","onResponse.getStatus");
                    listHospital = listHospitalModel.getListHospitalModel();
                    if(listHospital==null)
                    {
                        Log.d("nurul","listFood is null");
                    }

                    hospitalAdapter = new HospitalAdapter(listHospital, Manage_Hospital.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Manage_Hospital.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(hospitalAdapter);
                    progressDialog.dismiss();
                }else
                {
                    Log.d("nurul","onResponse.getStatus.else");
                    Toast.makeText(Manage_Hospital.this, listHospitalModel.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListHospitalModel> call, Throwable t)
            {
                Log.d("nurul","onFailure");
                Toast.makeText(Manage_Hospital.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}
