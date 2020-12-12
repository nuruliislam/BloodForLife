package capsulestudio.demobloodmanagement.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import capsulestudio.demobloodmanagement.R;

public class General_User extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.btnUserMember)
    Button userMember;

    @BindView(R.id.btnUserDonor)
    Button userDonor;

    @BindView(R.id.btnUserBloodReq)
    Button userReq;

    @BindView(R.id.btnUserViewCamp)
    Button userViewCamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general__user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




        ButterKnife.bind(this);
        userMember.setOnClickListener(this);
        userDonor.setOnClickListener(this);
        userReq.setOnClickListener(this);
        userViewCamp.setOnClickListener(this);


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
    @Override
    protected void onStart()
    {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }


    @Override
    public void onClick(View v)
    {
        Intent i;

        switch (v.getId())
        {
            case R.id.btnUserMember:
                i = new Intent(this, Member_signup.class);
                startActivity(i);
                break;

            case R.id.btnUserDonor:
                i = new Intent(this, Donor_signup.class);
                startActivity(i);

                break;

            case R.id.btnUserBloodReq:

                showNoteDialog(true);

                break;

            case R.id.btnUserViewCamp:
                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected==true)
                {
                    i = new Intent(this, Manage_Campaign.class);
                    i.putExtra("user","general_user");
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "No networkAvailable", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }


    private void showNoteDialog(final boolean shouldUpdate)
    {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.user_input, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(General_User.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText uName = view.findViewById(R.id.user_name);
        final EditText uContact = view.findViewById(R.id.user_contact);



        Log.d("nurul",uName.getText().toString() + " " + uContact.getText().toString());

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
                String userName=uName.getText().toString();
                String userContact=uContact.getText().toString();

                //Log.d("nurul","Update");
                Log.d("nurul",userName + " " + userContact);

                if(userName.equals("") || userContact.equals(""))
                {
                   Toast.makeText(getApplicationContext(),"Please Enter User Name and Password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(General_User.this, Blood_Request.class);
                    i.putExtra("email","no email");
                    i.putExtra("name",userName);
                    i.putExtra("contact",userContact);
                    startActivity(i);
                    alertDialog.dismiss();
                }
            }
        });
    }
}
