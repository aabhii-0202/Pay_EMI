package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {


    private List<DrawerItems> items;
    private Map<Class<? extends DrawerItems>,Integer> viewType;
    private SparseArray<DrawerItems> holderFactories;
    private OnItemSelectedListener listener;

    public DrawerAdapter(List<DrawerItems> items){
        this.items = items;
        this.viewType = new HashMap<>();
        this.holderFactories = new SparseArray<>();
        processViewType();
    }

    private void processViewType() {
        int type = 0;
        for(DrawerItems items: items){
            if(!viewType.containsKey(items.getClass())){
                viewType.put(items.getClass(),type);
                holderFactories.put(type,items);
                type++;
            }
        }

    }

    public void setSelected(int position){
        DrawerItems newChecked = items.get(position);
        if(!newChecked.isSelectable()){
            return;
        }
        for(int i=0;i<items.size();i++){
            DrawerItems item = items.get(i);

            //todo
//            if(item.isChecked()){
                item.setChecked(false);
                notifyItemChanged(i);
                break;
//            }
        }

        newChecked.setChecked(true);
        notifyItemChanged(position);

        if(listener != null){
            listener.onItemSelected(position);
        }
    }

    public void setListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = holderFactories.get(viewType).createViewHolder(parent);
        holder.drawerAdapter = this;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        items.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType.get(items.get(position).getClass());
    }











    public static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private DrawerAdapter drawerAdapter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            drawerAdapter.setSelected(getAbsoluteAdapterPosition());
        }
    }
}
