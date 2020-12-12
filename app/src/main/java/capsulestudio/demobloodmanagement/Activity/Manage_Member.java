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

public class Manage_Member extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    ListMemberModel listMemberModel;
    List<member_model> listMember;
    member_model m;

    LayoutInflater inflater;
    Context context;

    private AlertDialog mOptionsDialog;
    String user;

    private SearchView searchView;
    private MemberAdapter memberAdapter;

    private ProgressDialog pDialog;
    public static Activity activity;

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        activity=this;

        context=getApplicationContext();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = extras.getString("user");

            //The key argument here must match that used in the other activity
            Log.d("nurul", user);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_member);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listMember=listMemberModel.getListPersonM();
                m=listMember.get(position);


            }

            @Override
            public void onLongClick(View view,final int position)
            {

                if(user.equals("admin"))
                {
                    listMember=listMemberModel.getListPersonM();
                    m=listMember.get(position);


                    CharSequence colors[] = new CharSequence[]{"Edit", "Delete","Call","View Profile"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Manage_Member.this);
                    builder.setTitle("Choose option");
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0)
                            {
                                showNoteDialog(true, m);
                            } else if(which==1)
                            {
                                DeleteIt(m);
                            }
                            else if(which==2)
                            {
                                if (checkPermission(Manifest.permission.CALL_PHONE))
                                {
                                    call(m);
                                } else
                                {
                                    //dial.setEnabled(false);
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
                                    call(m);
                                }
                            }else if(which==3)
                            {
                                Intent i =new Intent(Manage_Member.this,Member_Profile.class);
                                i.putExtra("name",m.getmName());
                                //i.putExtra("email",m.getmEmail());
                                i.putExtra("phone",m.getmContact());
                                i.putExtra("address",m.getmAddress());
                                i.putExtra("city",m.getmCity());
                                i.putExtra("post",m.getmPost());
                                i.putExtra("country",m.getmCountry());

                                startActivity(i);
                            }
                        }
                    });
                    builder.show();
                }
                //else Toast.makeText(context,"Only for ",Toast.LENGTH_SHORT).show();
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

    private void call(final member_model m)
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

    private void showNoteDialog(final boolean shouldUpdate, final member_model m)
    {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_member, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Manage_Member.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText mName = view.findViewById(R.id.editMemberName);
        final EditText mAddress = view.findViewById(R.id.editMemberAddress);
        final EditText mContact = view.findViewById(R.id.editMemberContact);

        mName.setText(m.getmName());
        mAddress.setText(m.getmAddress());
        mContact.setText(m.getmContact());

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
                updateData(mName.getText().toString(),m.getmEmail(),mAddress.getText().toString(),mContact.getText().toString());
                alertDialog.dismiss();

            }
        });
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

    private void updateData(String a,String b,String c,String d)
    {

        Log.d("nurul",a + " " + b + " " + c + " " + d);

        pDialog = new ProgressDialog(Manage_Member.this,
                R.style.AppTheme_PopupOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.editMember(a,b,c,d);
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
        Call<ListMemberModel> call = apiService.getAllMember();
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

                    memberAdapter = new MemberAdapter(listMember, Manage_Member.this,activity);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Manage_Member.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(memberAdapter);
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
