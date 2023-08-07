package com.adminapp.nac_admin.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adminapp.nac_admin.R;

import java.util.ArrayList;

public class Adapter_Admin extends RecyclerView.Adapter<Adapter_Admin.ViewHolder> {

    Context context;
    ArrayList<Admin_list> array_admin;

    public Adapter_Admin(Context context, ArrayList<Admin_list> array_admin) {
        this.context = context;
        this.array_admin = array_admin;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_name;
        TextView txt_Email;
        TextView txt_mobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name=itemView.findViewById(R.id.txt_name);
            txt_Email=itemView.findViewById(R.id.txt_mail);
            txt_mobile=itemView.findViewById(R.id.txt_mobile);

        }
    }

    @NonNull
    @Override
    public Adapter_Admin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.admin_list,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Admin.ViewHolder holder, int position) {

        holder.txt_name.setText(array_admin.get(position).getName());
        holder.txt_Email.setText(array_admin.get(position).getEmail());
        holder.txt_mobile.setText(array_admin.get(position).getMobile());

    }

    @Override
    public int getItemCount() {
        return array_admin.size();
    }
}
