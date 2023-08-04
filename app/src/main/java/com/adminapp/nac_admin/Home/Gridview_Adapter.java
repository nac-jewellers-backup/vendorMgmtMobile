package com.adminapp.nac_admin.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adminapp.nac_admin.R;

public class Gridview_Adapter extends BaseAdapter {
    Context context;
    private LayoutInflater layoutInflater;
    ViewHolder holder;
    int[] listicon={R.drawable.enquiry_icon,R.drawable.vendor_icon,R.drawable.order_icon,R.drawable.payment_icon,R.drawable.admin_icon};
    String[] list_text={"Enquiry","Vendor","Order","Payment","Admin"};

    public Gridview_Adapter(Context context) {
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        holder = new ViewHolder();
        view = layoutInflater.inflate(R.layout.list_menu, parent, false);

        holder.img_view=view.findViewById(R.id.menu_img);
        holder.txt_menu=view.findViewById(R.id.text_menu);

        holder.img_view.setImageResource(listicon[position]);
        holder.txt_menu.setText(list_text[position]);

        return view;
    }

    private static class ViewHolder{

        TextView txt_menu;
        ImageView img_view;

    }
}
