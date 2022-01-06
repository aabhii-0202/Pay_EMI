package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.Models.bankSubItem;
import com.mediustechnologies.payemi.R;
import java.util.List;

public class bankSublistAdapter extends RecyclerView.Adapter<bankSublistAdapter.Viewholder> {

    private List<bankSubItem> bankSubList;
    private bankListAdapter.onItemClicked mListner;



    public interface onItemClicked{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(bankListAdapter.onItemClicked listner){
        mListner = listner;
    }
    public bankSublistAdapter(List<bankSubItem> bankSubList){ this.bankSubList = bankSubList; }

    @NonNull
    @Override
    public bankSublistAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) .inflate(R.layout.bank_sublist_items,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bankSublistAdapter.Viewholder holder, int position) {

        String billerid = bankSubList.get(position).getBillerId();
        String bankname = bankSubList.get(position).getBiller_bank__bank_name();
        String billername = bankSubList.get(position).getBillerName();
        String logourl = bankSubList.get(position).getLogo_url();
        holder.setData(billerid,bankname,billername,logourl);
    }

    @Override
    public int getItemCount() {
        return bankSubList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView SubImage;


        public Viewholder(View view) {
            super(view);
            SubImage = view.findViewById(R.id.sub_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){
                        int pos = getAbsoluteAdapterPosition();
                        mListner.onItemClick(pos);
                    }
                }
            });


        }

        public void setData(int imageRes) {
            SubImage.setImageResource(imageRes);

        }

        public void setData(String billerid, String bankname, String billername, String logourl) {
            Glide.with(SubImage).load(logourl).into(SubImage);

        }
    }
}
