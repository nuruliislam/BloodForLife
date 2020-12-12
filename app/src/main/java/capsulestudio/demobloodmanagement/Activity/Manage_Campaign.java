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
import capsulestudio.demobloodmanagement.adapter.CampaignAdapter;
import capsulestudio.demobloodmanagement.adapter.DonorAdapter;
import capsulestudio.demobloodmanagement.adapter.MemberAdapter;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListCampaignModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.ListMemberModel;
import capsulestudio.demobloodmanagement.model.campaign_model;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.model.request_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import capsulestudio.demobloodmanagement.utility.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_Campaign extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    ListCampaignModel listCampaignModel;
    List<campaign_model> listCampaign;
    campaign_model m;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    public static Activity activity;

    LayoutInflater inflater;
    Context context;

    private AlertDialog mOptionsDialog;
    String user;


    private CampaignAdapter campaignAdapter;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_campaign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        activity = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = extras.getString("user");
            //The key argument here must match that used in the other activity
            Log.d("nurul",user);
        }

        context=getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_campaign);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listCampaign=listCampaignModel.getListPersonM();
                m=listCampaign.get(position);


            }

            @Override
            public void onLongClick(View view,final int position)
            {

                if(user.equals("admin"))
                {
                    listCampaign=listCampaignModel.getListPersonM();
                    m=listCampaign.get(position);


                    CharSequence colors[] = new CharSequence[]{"Edit", "Delete","Call"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Manage_Campaign.this);
                    builder.setTitle("Choose option");
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0)
                            {
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
                //else Toast.makeText(context,"Its happnes",Toast.LENGTH_SHORT).show();
            }
        }
        ));

        progressDialog = new ProgressDialog(this);

        getAllData();
    }

    private void call(final campaign_model m)
    {
        String phoneNumber = m.getmContact();
        //Toast.makeText(getApplicationContext(),"number is " + phoneNumber,Toast.LENGTH_LONG).show();


        if (checkPermission(Manifest.permission.CALL_PHONE))
        {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        } else {
            Toast.makeText(getApplicationContext(), "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
        }



    }

    private boolean checkPermission(String permission)
    {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
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

    private void showNoteDialog(final boolean shouldUpdate, final campaign_model m)
    {


        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_campaign, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Manage_Campaign.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText _name = view.findViewById(R.id.edtName);
        final EditText _organizer = view.findViewById(R.id.edtOrganizer);
        final EditText _contact = view.findViewById(R.id.edtContact);
        final EditText _place = view.findViewById(R.id.edtPlace);
        final EditText _sponsor = view.findViewById(R.id.edtSponsor);

        _name.setText(m.getmName());
        _organizer.setText(m.getmOrganizer());
        _contact.setText(m.getmContact());
        _place.setText(m.getmPlace());
        _sponsor.setText(m.getmSponsor());

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
                updateData(_name.getText().toString(),_organizer.getText().toString(),_contact.getText().toString(),_place.getText().toString(),_sponsor.getText().toString(),m.getmId());
                alertDialog.dismiss();

            }
        });


    }


    private void DeleteIt(final campaign_model m)
    {
        //Log.d("nurul",  "in deleteIT");
        //String email=m.getmEmail();
        //Log.d("nurul",email);

        int id=m.getmId();

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.deleteCampaign(id);
        call.enqueue(new Callback<InsertDataResponseModel>()
        {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response)
            {
                getAllData();
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: "+ t.getMessage());
            }
        });
    }

    private void updateData(String a,String b,String c,String d,String e,int f)
    {

        pDialog = new ProgressDialog(Manage_Campaign.this,
                R.style.AppTheme_PopupOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.editCampaign(a,b,c,d,e,f);
        call.enqueue(new Callback<InsertDataResponseModel>()
        {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response)
            {
                hidepDialog();
                Log.d("nurul","Update data onResponse");
                getAllData();
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

    private void getAllData()
    {
        progressDialog.setTitle("Displaying data");
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ListCampaignModel> call = apiService.getAllCampaign();
        call.enqueue(new Callback<ListCampaignModel>()
        {
            @Override
            public void onResponse(Call<ListCampaignModel> call, Response<ListCampaignModel> response)
            {
                Log.d("nurul","onResponse");
                listCampaignModel = response.body();
                Log.d("nurul",listCampaignModel.getMessage());

                if(listCampaignModel.getStatus()==1)
                {
                    Log.d("nurul","onResponse.getStatus");
                    listCampaign = listCampaignModel.getListPersonM();
                    if(listCampaign==null)
                    {
                        Log.d("nurul","listFood is null");
                    }

                    campaignAdapter = new CampaignAdapter(listCampaign, Manage_Campaign.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Manage_Campaign.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(campaignAdapter);
                    progressDialog.dismiss();
                }else
                {
                    Log.d("nurul","onResponse.getStatus.else");

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListCampaignModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }
}
