package capsulestudio.demobloodmanagement.adapter;

import android.content.Context;
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
import capsulestudio.demobloodmanagement.model.campaign_model;
import capsulestudio.demobloodmanagement.model.donation_model;
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.ViewHolder>
{


    private List<donation_model> listDonationModel;
    //private List<memberModel> listPersonFiltered;
    private Context context;

    public DonationHistoryAdapter(List<donation_model> listDonationModel, Context context)
    {
        Log.d("nurul","FoodAdapter.FoodAdapter");
        this.listDonationModel = listDonationModel;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Log.d("nurul","FoodAdapter.onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation_history,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Log.d("nurul","FoodAdapter.onBindViewHolder");
        donation_model model = listDonationModel.get(position);
        //Log.d("nurul",model.getmPlace());

        try
        {
            holder.donor_name.setText(String.valueOf(model.getdName()));
            holder.blood_group.setText(model.getdGroup()+"");
            holder.patient_name.setText(model.getpName()+"");
            holder.date.setText(model.getDate());
            holder.disease.setText(model.getDisease());
            holder.hospital_name.setText(model.getHospital());
            //Log.d("nurul",model.getmPlace() + " " + model.getmSponsor());


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        //Log.d("nurul","FoodAdapter.getItemCount");
        return listDonationModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView id,donor_name,blood_group,patient_name,date,qty,disease,hospital_name;

        private ImageButton imageButton;

        final Context ct;

        public ViewHolder(View itemView,final Context ct)
        {
            super(itemView);
            this.ct=ct;
            //Log.d("nurul","FoodAdapter.ViewHolder");
            donor_name  = (TextView) itemView.findViewById(R.id.textview_donor_name);
            blood_group = (TextView) itemView.findViewById(R.id.textview_group);
            patient_name = (TextView) itemView.findViewById(R.id.textview_patient);
            date = (TextView) itemView.findViewById(R.id.textview_date);
            disease = (TextView) itemView.findViewById(R.id.textview_disease);
            hospital_name = (TextView) itemView.findViewById(R.id.textview_hospital);
        }


    }

}