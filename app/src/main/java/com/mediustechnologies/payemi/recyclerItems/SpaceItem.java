package com.mediustechnologies.payemi.recyclerItems;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mediustechnologies.payemi.adapters.DrawerAdapter;

public class SpaceItem extends DrawerItems<SpaceItem.ViewHolder>{

    private int spaceDp;

    public SpaceItem(int spaceDp){
        this.spaceDp = spaceDp;

    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        Context c = parent.getContext();
        View view = new View(c);
        int height = (int)(c.getResources().getDisplayMetrics().density*spaceDp);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,height
        ));

        return new ViewHolder(view);
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

    }

    public class ViewHolder extends DrawerAdapter.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
