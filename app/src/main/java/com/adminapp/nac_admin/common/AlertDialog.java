package com.adminapp.nac_admin.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adminapp.nac_admin.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AlertDialog {

    public void RounderCornerDialog(Context context, String title, String Message)
    {
        final androidx.appcompat.app.AlertDialog alertDialog =  new MaterialAlertDialogBuilder(context,R.style.MyRounded_MaterialComponents_MaterialAlertDialog)  // for fragment you can use getActivity() instead of this
                .setView(R.layout.commonalertlayout) // custom layout is here
                .show();

        TextView txtletsgo = alertDialog.findViewById(R.id.txtcontinue);
        TextView txttitle = alertDialog.findViewById(R.id.txtpopup_title);
        TextView txtmessage = alertDialog.findViewById(R.id.txtpopup_message);
        ImageView imgalert = alertDialog.findViewById(R.id.imgalert);

        //txttitle.setText(title);

        String titleuppercase=title.substring(0,1).toUpperCase()+title.substring(1).toLowerCase();
        txttitle.setText(titleuppercase);

        //txtmessage.setText(Message);

        String messageuppercase=Message.substring(0,1).toUpperCase()+Message.substring(1).toLowerCase();
        txtmessage.setText(messageuppercase);


        if(title.equalsIgnoreCase("Success"))
        {
            //imgalert.setImageResource(R.drawable.tick);
            imgalert.setImageResource(R.drawable.success);
        }
        else if(title.equalsIgnoreCase("Sorry"))
        {
            txtletsgo.setText("Close");
            imgalert.setVisibility(View.GONE);
        }
        else if(title.equalsIgnoreCase("Alert")){
            txtletsgo.setText("Close");
            imgalert.setImageResource(R.drawable.failed);
        }
        else
        {
            txtletsgo.setText("Close");
            imgalert.setImageResource(R.drawable.failed);
            //txtletsgo.setText("let's get signed up");
        }

       // alertDialog.setCanceledOnTouchOutside(false);
       // alertDialog.show();

        txtletsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
    }
}
