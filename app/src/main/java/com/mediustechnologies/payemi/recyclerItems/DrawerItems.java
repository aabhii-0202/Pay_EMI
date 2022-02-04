package com.mediustechnologies.payemi.recyclerItems;

import android.view.ViewGroup;

import com.mediustechnologies.payemi.adapters.DrawerAdapter;

public abstract class DrawerItems<T extends DrawerAdapter.ViewHolder> {

    protected boolean isChecked;
    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder);

    public DrawerItems<T> setChecked(boolean isChecked){
        this.isChecked = isChecked;
        return this;
    }

    public boolean isChecked(){
        return isChecked();
    }

    public boolean isSelected(){
        return true;
    }


}
