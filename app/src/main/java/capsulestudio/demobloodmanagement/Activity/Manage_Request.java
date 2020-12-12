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
import capsulestudio.demobloodmanagement.adapter.DonationHistoryAdapter;
import capsulestudio.demobloodmanagement.adapter.DonorAdapter;
import capsulestudio.demobloodmanagement.adapter.MemberAdapter;
import capsulestudio.demobloodmanagement.adapter.RequestAdapter;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListDonationModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.ListMemberModel;
import capsulestudio.demobloodmanagement.model.ListRequestModel;
import capsulestudio.demobloodmanagement.model.donation_model;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.model.request_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import capsulestudio.demobloodmanagement.utility.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_Request extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    ListRequestModel listRequestModel;
    List<request_model> listRequest;
    request_model model;

    LayoutInflater inflater;
    Context context;

    private AlertDialog mOptionsDialog;
    //String s="Admin";
    String user;
    public static Activity activity;

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;


    private SearchView searchView;
    private RequestAdapter requestAdapter;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        context=getApplicationContext();
        activity=this;

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = extras.getString("user");

            //The key argument here must match that used in the other activity
            Log.d("nurul", user);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_request);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listRequest=listRequestModel.getListPersonM();
                model=listRequest.get(position);


            }

            @Override
            public void onLongClick(View view,final int position)
            {

                if(user.equals("admin"))
                {
                    listRequest=listRequestModel.getListPersonM();
                    model=listRequest.get(position);


                    CharSequence colors[] = new CharSequence[]{"Edit", "Delete","Call"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Manage_Request.this);
                    builder.setTitle("Choose option");
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                showNoteDialog(true, model);
                            } else if (which == 1)
                            {
                                DeleteIt(model);
                            }else if (which == 1)
                            {
                                if (checkPermission(Manifest.permission.CALL_PHONE))
                                {
                                    call(model);
                                } else
                                {
                                    //dial.setEnabled(false);
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
                                    call(model);
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



        getAllDataFood();
    }

    private void call(final request_model m)
    {
        String phoneNumber = m.getuContact();
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

    private void showNoteDialog(final boolean shouldUpdate, final request_model m)
    {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_request, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Manage_Request.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText uName = view.findViewById(R.id.edtUser);
        final EditText uContact = view.findViewById(R.id.edtUserContact);
        final EditText pName = view.findViewById(R.id.editPatientName);
        final EditText pContact = view.findViewById(R.id.editPatientContact);
        final EditText pHospital = view.findViewById(R.id.editHospital);

        uName.setText(m.getuName());
        uContact.setText(m.getuContact());
        pName.setText(m.getpName());
        pContact.setText(m.getpContact());
        pHospital.setText(m.getHospital());

        alertDialogBuilderUserInput
                .setCancelable(true)
                .setPositiveButton("update", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogBox, int id)
                    { }
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
                updateData(uName.getText().toString(),uContact.getText().toString(),pName.getText().toString(),pContact.getText().toString(),pHospital.getText().toString(),m.getId());
                alertDialog.dismiss();

            }
        });
    }


    private void DeleteIt(final request_model m)
    {
        //Log.d("nurul",  "in deleteIT");
        int id=m.getId();
        Log.d("nurul",String.valueOf(id));

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.deleteBloodRequest(id);
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

    private void updateData(String a,String b,String c,String d,String e,int f)
    {

        pDialog = new ProgressDialog(Manage_Request.this,
                R.style.AppTheme_PopupOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.editBloodRequest(a,b,c,d,e,f);
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
        Call<ListRequestModel> call = apiService.getAllBloodRequest();
        call.enqueue(new Callback<ListRequestModel>()
        {
            @Override
            public void onResponse(Call<ListRequestModel> call, Response<ListRequestModel> response)
            {
                Log.d("nurul","onResponse");
                listRequestModel = response.body();
                //Log.d("nurul",listPersonModel.getMessage());

                if(listRequestModel.getStatus()==1)
                {
                    //Log.d("nurul","onResponse.getStatus");
                    listRequest = listRequestModel.getListPersonM();
                    if(listRequest==null)
                    {
                        Log.d("nurul","listFood is null");
                    }

                    requestAdapter = new RequestAdapter(listRequest, Manage_Request.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Manage_Request.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(requestAdapter);
                    progressDialog.dismiss();
                }else
                {
                    Log.d("nurul","No Data Available");

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListRequestModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }
}
