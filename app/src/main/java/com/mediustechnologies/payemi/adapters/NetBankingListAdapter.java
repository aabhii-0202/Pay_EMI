package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.NetBankingListBinding;
import com.mediustechnologies.payemi.recyclerItems.NetBankingListItem;

import java.util.ArrayList;
import java.util.List;


public class NetBankingListAdapter extends RecyclerView.Adapter<NetBankingListAdapter.vh> implements Filterable {

    private List<NetBankingListItem> netBankingList;
    private List<NetBankingListItem> netBankingListFull;


    public NetBankingListAdapter(List<NetBankingListItem> netBankingList){
        this.netBankingList = netBankingList;
        netBankingListFull = new ArrayList<>(netBankingList);
    }

    @NonNull
    @Override
    public NetBankingListAdapter.vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.net_banking_list,parent,false);
        return new vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetBankingListAdapter.vh holder, int position) {
        String name = netBankingList.get(position).getName();
        holder.set(name);
    }

    @Override
    public int getItemCount() {
        return netBankingList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<NetBankingListItem>  filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0){
                filteredList.addAll(netBankingListFull);
            }else {
                String filterpattern = charSequence.toString().toLowerCase().trim();
                for (NetBankingListItem itm: netBankingListFull){
                    if(itm.getName().toLowerCase().contains(filterpattern)){
                        filteredList.add(itm);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            netBankingList.clear();
            netBankingList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class vh extends RecyclerView.ViewHolder {

        NetBankingListBinding binding;
        public vh(@NonNull View itemView) {
            super(itemView);
            binding = NetBankingListBinding.bind(itemView);


        }

        public void set(String name) {
            binding.newbankingname.setText(name);
        }
    }
}
