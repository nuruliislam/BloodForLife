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
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.utility.RandomColor;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>
{
    private List<member_model> listPersonModel;
    //private List<memberModel> listPersonFiltered;
    private Context context;
    public static Activity activity;
    private static member_model model;
    private int lastPosition = -1;

    public MemberAdapter(List<member_model> listPersonModel, Context context,Activity activity)
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_member,parent,false);
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

        try{
            holder.name.setText(model.getmName());
            holder.email.setText(model.getmEmail()+"");
            holder.contact.setText(model.getmContact()+"");
            holder.address.setText("Address: " + model.getmAddress());

            if(model.getmCity() == "")
            {
                holder.city.setVisibility(View.GONE);

            }else holder.city.setText("City: " + model.getmCity());

            if(model.getmCity() == "")
            {
                holder.country.setVisibility(View.GONE);

            }else holder.country.setText("Country: " +model.getmCountry());




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
        public TextView email;
        public TextView contact;
        public TextView address;
        public TextView city;
        public TextView country;
        public LinearLayout linearLayoutColor;



        final Context ct;

        public ViewHolder(View itemView,final Context ct)
        {
            super(itemView);
            this.ct=ct;
            //Log.d("nurul","FoodAdapter.ViewHolder");

            name = (TextView) itemView.findViewById(R.id.textview_name);
            email = (TextView) itemView.findViewById(R.id.textview_email);
            contact = (TextView) itemView.findViewById(R.id.textview_contact);
            address = (TextView) itemView.findViewById(R.id.textview_address);
            city = (TextView) itemView.findViewById(R.id.textview_city);
            country = (TextView) itemView.findViewById(R.id.textview_country);
            linearLayoutColor = (LinearLayout) itemView.findViewById(R.id.linearColor);
        }
    }

}