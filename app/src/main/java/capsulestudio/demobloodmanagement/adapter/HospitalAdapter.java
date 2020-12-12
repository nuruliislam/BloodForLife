package capsulestudio.demobloodmanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import capsulestudio.demobloodmanagement.R;
import capsulestudio.demobloodmanagement.model.hospital_model;
import capsulestudio.demobloodmanagement.utility.RandomColor;



public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder>
{
    private List<hospital_model> listHospitalModel;
    //private List<memberModel> listPersonFiltered;
    private Context context;
    private int lastPosition = -1;

    public HospitalAdapter(List<hospital_model> listHospitalModel, Context context)
    {
        //Log.d("nurul","FoodAdapter.FoodAdapter");
        this.listHospitalModel = listHospitalModel;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Log.d("nurul","FoodAdapter.onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_hospital,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Log.d("nurul","FoodAdapter.onBindViewHolder");
        hospital_model model = listHospitalModel.get(position);

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
            holder.name.setText(model.gethName());
            holder.email.setText(model.gethEmail()+"");
            holder.contact.setText(model.gethContact()+"");
            holder.address.setText(model.gethAddress());
            holder.city.setText(model.gethCity());



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        //Log.d("nurul","FoodAdapter.getItemCount");
        return listHospitalModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name;
        public TextView email;
        public TextView contact;
        public TextView address;
        public TextView city;
        public LinearLayout linearLayoutColor;


        private ImageButton imageButton;

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
            linearLayoutColor = (LinearLayout) itemView.findViewById(R.id.linearColor);
        }
    }

}