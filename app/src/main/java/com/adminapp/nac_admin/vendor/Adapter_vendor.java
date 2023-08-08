package com.adminapp.nac_admin.vendor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adminapp.nac_admin.Admin.Adapter_Admin;
import com.adminapp.nac_admin.R;

import java.util.ArrayList;

public class Adapter_vendor extends RecyclerView.Adapter<Adapter_vendor.ViewHolder> {

    Context context;
    ArrayList<list_vendor> array_vendor;

    public Adapter_vendor(Context context, ArrayList<list_vendor> array_vendor) {
        this.context = context;
        this.array_vendor = array_vendor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView vendor_name;
        TextView vendor_mobile;
        TextView vendor_address;
        LinearLayout click_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vendor_name=itemView.findViewById(R.id.vendor_name);
            vendor_mobile=itemView.findViewById(R.id.vendor_mobile);
            vendor_address=itemView.findViewById(R.id.vendor_address);
            click_lay=itemView.findViewById(R.id.click_layout);

        }
    }

    @NonNull
    @Override
    public Adapter_vendor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.vendor_list,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_vendor.ViewHolder holder, int position) {

        holder.vendor_name.setText("Vendor Name"+" : "+array_vendor.get(position).getVendor_name());
        holder.vendor_mobile.setText("Shop mobile"+" : "+array_vendor.get(position).getShop_mobile());
        holder.vendor_address.setText("Shop Address"+" : "+array_vendor.get(position).getShop_address());

        holder.click_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,activity_vendordetail.class);
                intent.putExtra("email",array_vendor.get(position).getEmail());
                intent.putExtra("contact_number",array_vendor.get(position).getContact_number());
                intent.putExtra("contact_person",array_vendor.get(position).getContact_person());
                intent.putExtra("PAN",array_vendor.get(position).getPan());
                intent.putExtra("GST",array_vendor.get(position).getGst_number());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return array_vendor.size();
    }
}
