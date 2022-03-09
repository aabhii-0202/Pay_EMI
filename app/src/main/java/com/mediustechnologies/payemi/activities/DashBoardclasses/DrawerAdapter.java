package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ItemOptionBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {


    private List<DrawerItems> items;
    private Map<Class<? extends DrawerItems>, Integer> viewType;
    private SparseArray<DrawerItems> holderFactories;
    static OnItemSelectedListener listener;
    static onRegisterClickListner listnerreg;
    static onTrackingClickListner listnertrac;


    public interface onRegisterClickListner{
        void onRegClicked(int position);
    }

    public void setRegClickListner(onRegisterClickListner listnerreg){
        this.listnerreg = listnerreg;
    }

    public interface onTrackingClickListner{
        void onTracClicked(int position);
    }

    public void setTraclistner(onTrackingClickListner listnertrac){
        this.listnertrac = listnertrac;
    }



    public DrawerAdapter(List<DrawerItems> items) {
        this.items = items;
        this.viewType = new HashMap<>();
        this.holderFactories = new SparseArray<>();
        processViewType();
    }

    private void processViewType() {
        int type = 0;
        for (DrawerItems items : items) {
            if (!viewType.containsKey(items.getClass())) {
                viewType.put(items.getClass(), type);
                holderFactories.put(type, items);
                type++;
            }
        }

    }

    public void setSelected(int position) {
        DrawerItems newChecked = items.get(position);
        if (!newChecked.isSelectable()) {
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            DrawerItems item = items.get(i);
            if (item.isChecked()) {
                item.setChecked(false);
                notifyItemChanged(i);
                break;
            }
        }

        newChecked.setChecked(true);
        notifyItemChanged(position);

        if (listener != null) {
            listener.onItemSelected(position);
        }
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnItemSelectedListener {
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
        if(position == Home_Nav.POS_COMPLAINT){
            holder.binding.dropdown.setVisibility(View.VISIBLE);
        }
        else{
            holder.binding.dropdown.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {

        return viewType.get(items.get(position).getClass());
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DrawerAdapter drawerAdapter;
        private ItemOptionBinding binding;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemOptionBinding.bind(itemView);
            itemView.setOnClickListener(this);


            binding.register.setOnClickListener(view -> {
                if(listnerreg!=null){
                    int pos = getAbsoluteAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        listnerreg.onRegClicked(pos);
                        binding.register.setTextColor(Color.rgb(3,218,197));
                        binding.tracking.setTextColor(Color.rgb(153,153,153));
                    }
                }
            });

            binding.tracking.setOnClickListener(view -> {
                if(listnertrac!=null){
                    int pos = getAbsoluteAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        listnertrac.onTracClicked(pos);
                        binding.tracking.setTextColor(Color.rgb(3,218,197));
                        binding.register.setTextColor(Color.rgb(153,153,153));
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAbsoluteAdapterPosition()==Home_Nav.POS_COMPLAINT){
                        if(binding.subcatagory.getVisibility()==View.VISIBLE){
                            binding.subcatagory.setVisibility(View.GONE);
                        }else binding.subcatagory.setVisibility(View.VISIBLE);

                    }
                    else {
//                        binding.subcatagory.setVisibility(View.GONE);
//                        binding.tracking.setTextColor(Color.rgb(153,153,153));
//                        binding.register.setTextColor(Color.rgb(153,153,153));
                        drawerAdapter.setSelected(getAbsoluteAdapterPosition());
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            drawerAdapter.setSelected(getAbsoluteAdapterPosition());
        }
    }
}
