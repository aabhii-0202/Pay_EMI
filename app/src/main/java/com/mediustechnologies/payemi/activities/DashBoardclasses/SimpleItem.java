package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mediustechnologies.payemi.R;

public class SimpleItem extends DrawerItems<SimpleItem.ViewHolder> {

    private int selectedItemIconTint;
    private int selectedItemTextTint;

    private int normalItemsIconTint;
    private int normalItemTextTint;

    private Drawable icon;
    private String title;

    public SimpleItem(Drawable icon,String title){
        this.icon = icon;
        this.title = title;
    }




    @Override
    public SimpleItem.ViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_option,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(SimpleItem.ViewHolder holder) {
        holder.title.setText(title);
        holder.icon.setImageDrawable(icon);


        //todo
//        holder.title.setText(isChecked? selectedItemTextTint: normalItemTextTint);
//        holder.icon.setColorFilter(isChecked ? selectedItemIconTint : normalItemsIconTint);
        holder.title.setText("normalItemTextTint");
        holder.icon.setColorFilter(normalItemsIconTint);
    }

    public SimpleItem withSelectedIconTint(int selectedItemTextTint){
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

    public SimpleItem withSelectedTextTint(int selectedItemTextTint){
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

    public SimpleItem withIconTint(int normalItemsIconTint){
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }

    public SimpleItem withTextTint(int normalItemTextTint){
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }


    static class ViewHolder extends DrawerAdapter.ViewHolder{
        private ImageView icon;
        private TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.drawericon);
            title = itemView.findViewById(R.id.draweritemtitle);

        }
    }
}
