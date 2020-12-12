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
import capsulestudio.demobloodmanagement.adapter.MemberAdapter;
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.ListMemberModel;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import capsulestudio.demobloodmanagement.utility.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Member_Request extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    ListMemberModel listMemberModel;
    List<member_model> listMember;
    member_model m;

    LayoutInflater inflater;
    Context context;

    TextView requestTextview;

    private AlertDialog mOptionsDialog;
    String s="Admin";

    private SearchView searchView;
    private MemberAdapter memberAdapter;

    private ProgressDialog pDialog;
    public static Activity activity;

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestTextview=(TextView)findViewById(R.id.requestTextview);
        requestTextview.setVisibility(View.GONE);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        activity=this;

        context=getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_memmer_request);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listMember=listMemberModel.getListPersonM();
                m=listMember.get(position);


            }

            @Override
            public void onLongClick(View view,final int position)
            {
                    listMember=listMemberModel.getListPersonM();
                    m=listMember.get(position);


                    CharSequence colors[] = new CharSequence[]{"Accept", "Delete"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Member_Request.this);
                    builder.setTitle("Choose option");
                    builder.setItems(colors, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if (which == 0)
                            {
                                InsertDataIntoMember(m);
                            } else if(which==1)
                            {
                                DeleteIt(m);
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
    @Override
    protected void onStart()
    {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }


    private void InsertDataIntoMember(final member_model model)
    {
        progressDialog.setTitle("Request Accepting");
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        Log.d("nurul",m.getmPassword());
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.insertMemberData(m.getmName(),m.getmEmail(),m.getmContact(),m.getmPassword()
        ,m.getmAddress(),m.getmCity(),m.getmPost(),m.getmCountry());

        call.enqueue(new Callback<InsertDataResponseModel>() {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response)
            {
                Toast.makeText(getApplicationContext(),"Member Request Accepted",Toast.LENGTH_SHORT).show();
                DeleteIt(model);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

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
    private void DeleteIt(final member_model m)
    {
        //Log.d("nurul",  "in deleteIT");
        String email=m.getmEmail();
        //Log.d("nurul",email);

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.deleteMember(email);
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
        Call<ListMemberModel> call = apiService.getAllMemberRequest();
        call.enqueue(new Callback<ListMemberModel>()
        {
            @Override
            public void onResponse(Call<ListMemberModel> call, Response<ListMemberModel> response)
            {
                //Log.d("nurul","onResponse");
                listMemberModel = response.body();
                //Log.d("nurul",listPersonModel.getMessage());

                if(listMemberModel.getStatus()==1)
                {
                    //Log.d("nurul","onResponse.getStatus");
                    listMember = listMemberModel.getListPersonM();
                    if(listMember==null)
                    {
                        Log.d("nurul","listFood is null");
                    }

                    memberAdapter = new MemberAdapter(listMember, Member_Request.this,activity);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Member_Request.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(memberAdapter);

                    if(listMember.isEmpty()==true)
                    {
                        requestTextview.setVisibility(View.VISIBLE);
                    }
                    progressDialog.dismiss();

                }else
                {
                    Log.d("nurul","onResponse.getStatus.else");

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListMemberModel> call, Throwable t)
            {
                Log.d("nurul","onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }
}
