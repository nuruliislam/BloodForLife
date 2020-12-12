package capsulestudio.demobloodmanagement.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.model.admin_model;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;



public class CallAdminAdapter extends RecyclerView.Adapter<CallAdminAdapter.ViewHolder>
{
    private List<admin_model> listPersonModel;
    //private List<memberModel> listPersonFiltered;
    private Context context;
    public static Activity activity;
    private  String contaact;
    //private static admin_model model;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    public CallAdminAdapter(List<admin_model> listPersonModel, Context context,Activity activity)
    {
        //Log.d("nurul","FoodAdapter.FoodAdapter");
        this.listPersonModel = listPersonModel;

        this.context = context;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Log.d("nurul","FoodAdapter.onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_call_admin,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Log.d("nurul","FoodAdapter.onBindViewHolder");
        admin_model model = listPersonModel.get(position);

        try
        {
            holder.name.setText(model.getaName());
            holder.number.setText(model.getaContact());
            contaact=model.getaContact();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        //Log.d("nurul","FoodAdapter.getItemCount");
        return listPersonModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name,number;


        private ImageButton imageButton;

        final Context ct;

        public ViewHolder(View itemView,final Context ct)
        {

            super(itemView);
            this.ct=ct;


            name = (TextView) itemView.findViewById(R.id.admin_name);
            number = (TextView) itemView.findViewById(R.id.admin_number);

        }

      }

}