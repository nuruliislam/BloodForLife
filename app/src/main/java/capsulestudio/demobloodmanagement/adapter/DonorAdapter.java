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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.utility.RandomColor;


public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.ViewHolder>
{

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    private List<donor_model> listPersonModel;
    //private List<memberModel> listPersonFiltered;
    private static Context context;
    private static donor_model model;
    public static Activity activity;
    private int lastPosition = -1;


    public DonorAdapter(List<donor_model> listPersonModel, Context context,Activity activity)
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_donor,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Log.d("nurul","FoodAdapter.onBindViewHolder");
        model = listPersonModel.get(position);

        holder.linearLayoutColor.setBackgroundColor(RandomColor.getColor(context));

        /**
         *  Animation Part
         */

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.bottom_from_up);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        try
        {
            holder.name.setText(model.getdName());
            holder.group.setText("Blood Group: " + model.getdGroup());
            holder.email.setText(model.getdEmail()+"");
            holder.contact.setText(model.getdContact()+"");

            if(model.getdCity() == "")
            {
                holder.city.setVisibility(View.GONE);

            }else holder.city.setText("City: " + model.getdCity());

            holder.donation.setText("Last Donation Date: " + model.getdDonation());
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

        public TextView name;
        public TextView group;
        public TextView email;
        public TextView contact;
        public TextView city;
        public TextView donation;
        public LinearLayout linearLayoutColor;

        private ImageButton imageButton;

        final Context ct;

        public ViewHolder(View itemView,final Context ct)
        {
            super(itemView);
            this.ct=ct;
            //Log.d("nurul","FoodAdapter.ViewHolder");
            name = (TextView) itemView.findViewById(R.id.textview_name);
            group = (TextView) itemView.findViewById(R.id.textview_group);
            email = (TextView) itemView.findViewById(R.id.textview_email);
            contact = (TextView) itemView.findViewById(R.id.textview_contact);
            city = (TextView) itemView.findViewById(R.id.textview_city);
            donation = (TextView) itemView.findViewById(R.id.textview_donation);
            linearLayoutColor = (LinearLayout) itemView.findViewById(R.id.linearColor);
        }
    }

}