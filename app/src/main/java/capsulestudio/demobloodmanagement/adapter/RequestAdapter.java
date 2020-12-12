package capsulestudio.demobloodmanagement.adapter;

import android.content.Context;
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
import capsulestudio.demobloodmanagement.model.campaign_model;
import capsulestudio.demobloodmanagement.model.donation_model;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.model.request_model;
import capsulestudio.demobloodmanagement.utility.RandomColor;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>
{


    private List<request_model> listRequestModel;
    //private List<memberModel> listPersonFiltered;
    private Context context;
    private int lastPosition = -1;

    public RequestAdapter(List<request_model> listRequestModel, Context context)
    {
        Log.d("nurul","FoodAdapter.FoodAdapter");
        this.listRequestModel = listRequestModel;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Log.d("nurul","FoodAdapter.onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_request,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Log.d("nurul","FoodAdapter.onBindViewHolder");
        request_model model = listRequestModel.get(position);

        holder.linearLayoutColor.setBackgroundColor(RandomColor.getColor(context));

        /**
         *  Animation Part
         */

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.bottom_from_up);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
        Log.d("nurul",model.getpName());

        try
        {

            holder.uName.setText("Request by "+ model.getuName());
            holder.uContact.setText(model.getuContact()+"");
            holder.pName.setText("Patient Name: "+model.getpName()+"");
            holder.group.setText("Blood Group: "+model.getpGroup());
            holder.rBag.setText("Require Bag: "+model.getRequire_bag());
            holder.disease.setText("Disease: "+model.getDisease());
            holder.hospital.setText("Hospital: "+model.getHospital());
            holder.rDate.setText("Required Date: "+model.getRequire_date());
            holder.rTime.setText("Required Time: "+model.getRequire_time());

            if(model.getpContact() == "")
            {
                holder.pContact.setVisibility(View.GONE);

            }else holder.pContact.setText(model.getpContact());




        }catch (Exception e)
        {
            Log.d("nurul",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        //Log.d("nurul","FoodAdapter.getItemCount");
        return listRequestModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView uName,uContact,pName,group,rBag,disease,hospital,pContact,rDate,rTime;
        public LinearLayout linearLayoutColor;

        private ImageButton imageButton;

        final Context ct;

        public ViewHolder(View itemView,final Context ct)
        {

            super(itemView);
            this.ct=ct;
            //Log.d("nurul","FoodAdapter.ViewHolder");
            uName  = (TextView) itemView.findViewById(R.id.textview_user_name);
            uContact= (TextView) itemView.findViewById(R.id.textview_user_contact);
            pName= (TextView) itemView.findViewById(R.id.textview_patient);
            group= (TextView) itemView.findViewById(R.id.textview_group);
            rBag = (TextView) itemView.findViewById(R.id.textview_require_bag);
            disease= (TextView) itemView.findViewById(R.id.textview_disease);
            hospital= (TextView) itemView.findViewById(R.id.textview_hospital);
            rDate= (TextView) itemView.findViewById(R.id.textview_require_date);
            rTime= (TextView) itemView.findViewById(R.id.textview_require_time);
            pContact= (TextView) itemView.findViewById(R.id.textview_patient_contact);
            linearLayoutColor = (LinearLayout) itemView.findViewById(R.id.linearColor);
        }
    }
}