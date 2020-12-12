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
import capsulestudio.demobloodmanagement.model.InsertDataResponseModel;
import capsulestudio.demobloodmanagement.model.ListDonorModel;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.network.APIService;
import capsulestudio.demobloodmanagement.network.ApiClient;
import capsulestudio.demobloodmanagement.utility.DividerItemDecoration;
import capsulestudio.demobloodmanagement.utility.RecyclerTouchListener;
import capsulestudio.demobloodmanagement.utility.callInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_Donor extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    ListDonorModel listDonorModel;
    List<donor_model> listDonor;
    donor_model m;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    public static Activity activity;

    LayoutInflater inflater;
    Context context;

    private AlertDialog mOptionsDialog;
    String user, type, item;


    private DonorAdapter donorAdapter;

    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__donor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        activity = this;

        context = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = extras.getString("user");
            type = extras.getString("type");
            item = extras.getString("item");
            //The key argument here must match that used in the other activity
            Log.d("nurul", user);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_donor);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                listDonor = listDonorModel.getListPersonM();
                m = listDonor.get(position);


            }

            @Override
            public void onLongClick(View view, final int position)
            {

                if (user.equals("admin"))
                {
                    listDonor = listDonorModel.getListPersonM();
                    m = listDonor.get(position);


                    CharSequence colors[] = new CharSequence[]{"Edit", "Delete", "Call", "View Profile"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Manage_Donor.this);
                    builder.setTitle("Choose option");
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                showNoteDialog(true, m);
                            } else if (which == 1) {
                                DeleteIt(m);
                            } else if (which == 2)
                            {
                                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                                    call(m);
                                } else {
                                    //dial.setEnabled(false);
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
                                    call(m);
                                }
                            } else if (which == 3) {
                                Intent i = new Intent(Manage_Donor.this, Donor_Profile.class);
                                i.putExtra("user","admin");
                                i.putExtra("name", m.getdName());
                                i.putExtra("email", m.getdEmail());
                                i.putExtra("phone", m.getdContact());
                                i.putExtra("address", m.getdAddress());
                                i.putExtra("city", m.getdCity());
                                i.putExtra("group",m.getdGroup());
                                startActivity(i);
                            }

                            //call(m);
                        }
                    });
                    builder.show();
                } //else Toast.makeText(context, "Its happnes", Toast.LENGTH_SHORT).show();
            }
        }
        ));

        progressDialog = new ProgressDialog(this);

        if (type.equals("search")) {
            getSearchData();
        } else getAllData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void call(final donor_model m) {
        String phoneNumber = m.getdContact();
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

    private void showNoteDialog(final boolean shouldUpdate, final donor_model m) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_donor, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Manage_Donor.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.title);
        final EditText dialogTitle = view.findViewById(R.id.author);
        final EditText inputGroup = view.findViewById(R.id.editDonorGroup);
        final EditText inputContact = view.findViewById(R.id.editDonorContact);

        inputNote.setText(m.getdName());
        dialogTitle.setText(m.getdCity());
        inputGroup.setText(m.getdGroup());
        inputContact.setText(m.getdContact());


        /*if (shouldUpdate && note != null) {
            inputNote.setText(note.getNote());
        } */
        alertDialogBuilderUserInput
                .setCancelable(true)
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {


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
            public void onClick(View v) {

                Log.d("nurul", "Update");
                updateData(inputNote.getText().toString(), dialogTitle.getText().toString(), inputGroup.getText().toString(), inputContact.getText().toString(), m.getdEmail());
                alertDialog.dismiss();

            }
        });
    }


    private void DeleteIt(final donor_model m) {
        //Log.d("nurul",  "in deleteIT");
        String email = m.getdEmail();
        //Log.d("nurul",email);

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.deleteDonor(email);
        call.enqueue(new Callback<InsertDataResponseModel>() {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response) {
                getAllData();
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t) {
                Log.d("nurul", "onFailure: " + t.getMessage());
            }
        });
    }

    private void updateData(String a, String b, String c, String d, String e) {

        pDialog = new ProgressDialog(Manage_Donor.this,
                R.style.AppTheme_PopupOverlay);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InsertDataResponseModel> call = apiService.editPost(a, b, c, d, e);
        call.enqueue(new Callback<InsertDataResponseModel>() {
            @Override
            public void onResponse(Call<InsertDataResponseModel> call, Response<InsertDataResponseModel> response) {
                hidepDialog();
                Log.d("nurul", "Update data onResponse");
                getAllData();
            }

            @Override
            public void onFailure(Call<InsertDataResponseModel> call, Throwable t) {
                hidepDialog();
                Log.d("nurul", "onFailure: " + t.getMessage());
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

    private void getAllData() {
        progressDialog.setTitle("Displaying data");
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ListDonorModel> call = apiService.getAllDonor();
        call.enqueue(new Callback<ListDonorModel>() {
            @Override
            public void onResponse(Call<ListDonorModel> call, Response<ListDonorModel> response) {
                Log.d("nurul", "onResponse");
                listDonorModel = response.body();
                //Log.d("nurul",listPersonModel.getMessage());

                if (listDonorModel.getStatus() == 1) {
                    //Log.d("nurul","onResponse.getStatus");
                    listDonor = listDonorModel.getListPersonM();
                    if (listDonor == null) {
                        Log.d("nurul", "listFood is null");
                    }

                    donorAdapter = new DonorAdapter(listDonor, Manage_Donor.this, activity);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Manage_Donor.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(donorAdapter);
                    progressDialog.dismiss();
                } else {
                    Log.d("nurul", "onResponse.getStatus.else");
                    Toast.makeText(Manage_Donor.this, listDonorModel.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListDonorModel> call, Throwable t) {
                Log.d("nurul", "onFailure");
                Toast.makeText(Manage_Donor.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    private void getSearchData() {
        progressDialog.setTitle("Displaying data");
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ListDonorModel> call = apiService.getAllSearchDonor(item);
        call.enqueue(new Callback<ListDonorModel>() {
            @Override
            public void onResponse(Call<ListDonorModel> call, Response<ListDonorModel> response) {
                Log.d("nurul", "onResponse");
                listDonorModel = response.body();
                //Log.d("nurul",listPersonModel.getMessage());

                if (listDonorModel.getStatus() == 1) {
                    //Log.d("nurul","onResponse.getStatus");
                    listDonor = listDonorModel.getListPersonM();
                    if (listDonor == null) {
                        Log.d("nurul", "listFood is null");
                    }

                    donorAdapter = new DonorAdapter(listDonor, Manage_Donor.this, activity);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Manage_Donor.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(donorAdapter);
                    progressDialog.dismiss();
                } else {
                    Log.d("nurul", "onResponse.getStatus.else");
                    Toast.makeText(Manage_Donor.this, listDonorModel.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ListDonorModel> call, Throwable t) {
                Log.d("nurul", "onFailure");
                Toast.makeText(Manage_Donor.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


}
