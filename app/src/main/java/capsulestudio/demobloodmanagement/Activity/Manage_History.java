package capsulestudio.demobloodmanagement.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import capsulestudio.demobloodmanagement.adapter.DonationHistoryAdapter;
import capsulestudio.demobloodmanagement.adapter.DonorAdapter;
import capsulestudio.demobloodmanagement.adapter.MemberAdapter;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListDonationModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.ListMemberModel;
import capsulestudio.demobloodmanagement.model.donation_model;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import capsulestudio.demobloodmanagement.utility.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_History extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    ListDonationModel listDonationModel;
    List<donation_model> listDonation;
    donation_model m;

    LayoutInflater inflater;
    Context context;

    private AlertDialog mOptionsDialog;
    String user;

    private SearchView searchView;
    private DonationHistoryAdapter donationHistoryAdapter;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        context=getApplicationContext();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = extras.getString("user");

            //The key argument here must match that used in the other activity
            Log.d("nurul", user);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_history);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listDonation=listDonationModel.getListPersonM();
                m=listDonation.get(position);


            }

            @Override
            public void onLongClick(View view,final int position)
            {

                if(user.equals("admin"))
                {
                    listDonation=listDonationModel.getListPersonM();
                    m=listDonation.get(position);


                    CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Manage_History.this);
                    builder.setTitle("Choose option");
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                showNoteDialog(true, m);
                            } else {
                                DeleteIt(m);
                            }
                        }
                    });
                    builder.show();
                }
                //else Toast.makeText(context,"Its happnes",Toast.LENGTH_SHORT).show();
            }
        }
        ));

        progressDialog = new ProgressDialog(this);


        getAllDataFood();
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

    private void showNoteDialog(final boolean shouldUpdate, final donation_model m)
    {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_donation_history, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Manage_History.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText pName = view.findViewById(R.id.edtPatientName);
        final EditText pDisease = view.findViewById(R.id.edtDisease);
        final EditText pHospital = view.findViewById(R.id.edtHospital);

        pName.setText(m.getpName());
        pDisease.setText(m.getDisease());
        pHospital.setText(m.getHospital());

        //inputNote.setText(m.getmName());
        //dialogTitle.setText(m.getmEmail());
        //inputGroup.setText(m.getmAddress());
        //inputContact.setText(m.getmContact());

        alertDialogBuilderUserInput
                .setCancelable(true)
                .setPositiveButton("update", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogBox, int id)
                    {


                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener()
                        {
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
                updateData(pName.getText().toString(),pDisease.getText().toString(),pHospital.getText().toString(),m.getId());
                alertDialog.dismiss();

            }
        });
    }


    private void DeleteIt(final donation_model m)
    {
        //Log.d("nurul",  "in deleteIT");
        int id=m.getId();
        //Log.d("nurul",email);

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.deleteDonationHistory(id);
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

    private void updateData(String a,String b,String c,int d)
    {

        pDialog = new ProgressDialog(Manage_History.this,
                R.style.AppTheme_PopupOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.editDonationHistory(a,b,c,d);
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

    private void showpDialog()
    {
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
        Call<ListDonationModel> call = apiService.getAllDonationHistory();
        call.enqueue(new Callback<ListDonationModel>()
        {
            @Override
            public void onResponse(Call<ListDonationModel> call, Response<ListDonationModel> response)
            {
                Log.d("nurul","onResponse");
                listDonationModel = response.body();
                //Log.d("nurul",listPersonModel.getMessage());

                if(listDonationModel.getStatus()==1)
                {
                    //Log.d("nurul","onResponse.getStatus");
                    listDonation = listDonationModel.getListPersonM();
                    if(listDonation==null)
                    {
                        Log.d("nurul","listFood is null");
                    }

                    donationHistoryAdapter = new DonationHistoryAdapter(listDonation, Manage_History.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Manage_History.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(donationHistoryAdapter);
                    progressDialog.dismiss();
                }else
                {
                    Log.d("nurul","onResponse.getStatus.else");

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListDonationModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }
}
