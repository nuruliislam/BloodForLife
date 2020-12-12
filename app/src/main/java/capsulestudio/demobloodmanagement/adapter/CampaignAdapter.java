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
import capsulestudio.demobloodmanagement.model.donor_model;
import capsulestudio.demobloodmanagement.model.member_model;
import capsulestudio.demobloodmanagement.utility.RandomColor;


public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder>
{


    private List<campaign_model> listCampaignModel;
    //private List<memberModel> listPersonFiltered;
    private Context context;
    private int lastPosition = -1;

    public CampaignAdapter(List<campaign_model> listCampaignModel, Context context)
    {
        Log.d("nurul","FoodAdapter.FoodAdapter");
        this.listCampaignModel = listCampaignModel;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Log.d("nurul","FoodAdapter.onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_campaign,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Log.d("nurul","FoodAdapter.onBindViewHolder");
        campaign_model model = listCampaignModel.get(position);

        holder.linearLayoutColor.setBackgroundColor(RandomColor.getColor(context));

        /**
         *  Animation Part
         */

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.bottom_from_up);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
        //Log.d("nurul",model.getmPlace());

        try{

            holder.cName.setText(model.getmName()+"");
            holder.cOrganizer.setText("Organizer: " +model.getmOrganizer()+"");
            holder.cContact.setText(model.getmContact());

            holder.cPlace.setText("Location: "+model.getmPlace()+"");
            holder.cDate.setText("Date: " +model.getmDate()+"");
            holder.cStart.setText("Start Time: "+model.getmStart());
            holder.cEnd.setText("End Time: "+model.getmEnd());
            holder.cSponsor.setText("Sponsored by: " +model.getmSponsor());

            Log.d("nurul",model.getmPlace() + " " + model.getmSponsor());


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        //Log.d("nurul","FoodAdapter.getItemCount");
        return listCampaignModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView cId;
        public TextView cName;
        public TextView cOrganizer;
        public TextView cContact;
        public TextView cPlace;
        public TextView cDate;
        public TextView cStart;
        public TextView cEnd;
        public TextView cSponsor;
        public LinearLayout linearLayoutColor;

        final Context ct;

        public ViewHolder(View itemView,final Context ct)
        {

            super(itemView);
            this.ct=ct;
            //Log.d("nurul","FoodAdapter.ViewHolder");


            cName = (TextView) itemView.findViewById(R.id.textview_campaign_name);
            cOrganizer = (TextView) itemView.findViewById(R.id.textview_campaign_organizer);
            cContact = (TextView) itemView.findViewById(R.id.textview_campaign_contact);
            cPlace = (TextView) itemView.findViewById(R.id.textview_place);
            cDate = (TextView) itemView.findViewById(R.id.textview_date);
            cStart = (TextView) itemView.findViewById(R.id.textview_start);
            cEnd = (TextView) itemView.findViewById(R.id.textview_end);
            cSponsor = (TextView) itemView.findViewById(R.id.textview_sponsor);
            linearLayoutColor = (LinearLayout) itemView.findViewById(R.id.linearColor);

        }


    }

}