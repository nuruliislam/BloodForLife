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
import android.support.annotation.NonNull;
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
import capsulestudio.demobloodmanagement.adapter.CallAdminAdapter;
import capsulestudio.demobloodmanagement.adapter.DonorAdapter;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListAdminModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.admin_model;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import capsulestudio.demobloodmanagement.utility.RecyclerTouchListener;
import capsulestudio.demobloodmanagement.utility.callInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Call_Admin extends AppCompatActivity
{
    private RecyclerView recyclerViewFood;
    private ProgressDialog progressDialog;
    ListAdminModel listAdminModel;
    List<admin_model> listAdmin;
    admin_model m;

    LayoutInflater inflater;
    Context context;

    private AlertDialog mOptionsDialog;
    String user;

    private SearchView searchView;
    private CallAdminAdapter callAdminAdapter;

    private ProgressDialog pDialog;

    public static Activity activity;

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity=this;

        context=getApplicationContext();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = extras.getString("user");
            //The key argument here must match that used in the other activity
            Log.d("nurul",user);
        }

        recyclerViewFood = (RecyclerView) findViewById(R.id.recyclerview_call_admin);


        recyclerViewFood.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerViewFood,new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listAdmin=listAdminModel.getListPersonAdmin();
                m=listAdmin.get(position);
                if (checkPermission(Manifest.permission.CALL_PHONE))
                {
                    call(m);
                } else
                {
                    //dial.setEnabled(false);
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
                    call(m);
                }


            }

            @Override
            public void onLongClick(View view,final int position)
            {

                if(!user.equals("admin"))
                {
                    listAdmin=listAdminModel.getListPersonAdmin();
                    m=listAdmin.get(position);


                    CharSequence colors[] = new CharSequence[]{"Call"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Call_Admin.this);
                    builder.setTitle("Choose option");
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0)
                            {
                                call(m);
                            } else if (which == 1) {

                            }

                        }
                    });
                    builder.show();
                }
                else Toast.makeText(context,"Its happnes",Toast.LENGTH_SHORT).show();
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
        Call<ListAdminModel> call = apiService.getAllAdmin();
        call.enqueue(new Callback<ListAdminModel>()
        {
            @Override
            public void onResponse(Call<ListAdminModel> call, Response<ListAdminModel> response)
            {
                Log.d("nurul","onResponse");
                listAdminModel = response.body();
                //Log.d("nurul",listPersonModel.getMessage());

                if(listAdminModel.getStatus()==1)
                {
                    //Log.d("nurul","onResponse.getStatus");
                    listAdmin = listAdminModel.getListPersonAdmin();
                    if(listAdmin==null)
                    {
                        Log.d("nurul","listFood is null");
                    }

                    callAdminAdapter = new CallAdminAdapter(listAdmin,Call_Admin.this,activity);
                    recyclerViewFood.setLayoutManager(new LinearLayoutManager(Call_Admin.this));
                    recyclerViewFood.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewFood.setAdapter(callAdminAdapter);
                    progressDialog.dismiss();
                }else
                {
                    Log.d("nurul","onResponse.getStatus.else");
                    Toast.makeText(Call_Admin.this, listAdminModel.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListAdminModel> call, Throwable t)
            {
                Log.d("nurul","onFailure");
                Toast.makeText(Call_Admin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    private void call(final admin_model m)
    {
        String phoneNumber = m.getaContact();
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


}
