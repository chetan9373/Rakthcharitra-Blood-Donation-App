package com.harsh1310.rakkktcharitr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class donorsadapter extends FirebaseRecyclerAdapter<donorsmodel,donorsadapter.MyviewHolder>
        //RecyclerView.Adapter<donorsadapter.MyviewHolder>
 {


    private Context context;
    private ArrayList<donorsmodel> courseModelArrayList;
    public   OnItemClickListener ItemClickListener;
    public interface OnItemClickListener
    {
        void onclick(int pos);
        void delete(int pos);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.ItemClickListener = (OnItemClickListener) onItemClickListener;
    }
   // public donorsadapter(Context context, ArrayList<donorsmodel> courseModelArrayList) {
     //   super();
       // this.context = context;
       // this.courseModelArrayList = courseModelArrayList;
    //}
     public  donorsadapter(@NonNull FirebaseRecyclerOptions<donorsmodel> options, Context con)
     {
super(options);
this.context=con;
     }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofdonors, parent, false);
        return new MyviewHolder(view,ItemClickListener);
    }


     @Override
     protected void onBindViewHolder(@NonNull MyviewHolder holder, int position, @NonNull donorsmodel model) {
        // donorsmodel model = courseModelArrayList.get(position);
         holder.name.setText(model.getName());
         holder.add.setText(model.getCity());
         holder.phone.setText(model.getPhonenum());
        // Toast.makeText(context,model.getGrp(),Toast.LENGTH_SHORT).show();
         holder.avalabilty.setText(model.getAvailability());
         holder.gender.setText(model.getGender());
         holder.medtext.setText(model.getDisease());
         holder.allergytext.setText(model.getAllergy());
         holder.grptext.setText(model.getGrp());
     //  if(model.availability.equals("No"))
       //    holder.phone.setText("");
      //   Toast.makeText(context,"click"+model.getGrp(),Toast.LENGTH_SHORT).show();
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,Profileofdonors.class);
        ArrayList<String>arlist=new ArrayList<>();
        arlist.add(model.getPic());

//        arlist.add(model.getGrp());
  //      arlist.add(model.getId());arlist.add(model.getPincode());
       arlist.add(model.getName());
       arlist.add(model.getProfession());
    arlist.add(model.getDtype());
       arlist.add(model.getGender());
        arlist.add(model.getAllergy());

        arlist.add(model.getAvailability());

        arlist.add(model.getCity());
        arlist.add(model.getPickup());

        intent.putExtra("key6",arlist);
        v.getContext().startActivity(intent);
      //  Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show();
    }
});
     }
     @Override
     public int getItemCount() {
         return super.getItemCount();
     }


    public class MyviewHolder extends RecyclerView.ViewHolder {
    TextView name,phone,add,avalabilty,gender,nameicon,locicon,allergyicon,medicon,contacticon,allergytext,medtext,grpicon,grptext,id,pincode;
    CircleImageView dimg;

        public MyviewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            name=itemView.findViewById(R.id.dpnametext);
            nameicon=itemView.findViewById(R.id.dpnameicon);

            phone=itemView.findViewById(R.id.dpcontacttext);
            contacticon=itemView.findViewById(R.id.dpcontacticon);
            locicon=itemView.findViewById(R.id.dplocicon);
            grpicon=itemView.findViewById(R.id.dpbfrpicon);
            grptext=itemView.findViewById(R.id.dpbfrptext);
            add=itemView.findViewById(R.id.dploctext);
            allergyicon=itemView.findViewById(R.id.dpallergyicon);
            allergytext=itemView.findViewById(R.id.dpallergytext);
            medicon=itemView.findViewById(R.id.dpmedicon);
            medtext=itemView.findViewById(R.id.dpmedtext);
            gender=itemView.findViewById(R.id.dpgendertext);
          avalabilty=itemView.findViewById(R.id.dpavailabilitytext);
 pincode=itemView.findViewById(R.id.dpincode);
 id=itemView.findViewById(R.id.did);
         itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemClickListener.onclick(position);

                        }
                    }
                }
            });
        }
    }
}
