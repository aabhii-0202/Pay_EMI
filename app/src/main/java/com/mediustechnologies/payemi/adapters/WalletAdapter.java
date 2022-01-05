package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.WalletListBinding;
import com.mediustechnologies.payemi.recyclerItems.walletItem;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.viewholder> {

    private List<walletItem> walletlist;
    private WalletAdapter.onItemClicked mListner;

    public WalletAdapter(List<walletItem> walletlist) {
        this.walletlist = walletlist;
    }


    public interface onItemClicked{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(WalletAdapter.onItemClicked listner){
        mListner = listner;
    }



    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_list,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        int image = walletlist.get(position).getImage();
        String name = walletlist.get(position).getWalletname();

        holder.set(image,name);

    }

    @Override
    public int getItemCount() {
        return walletlist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        WalletListBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding = WalletListBinding.bind(itemView);

            binding.link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListner!=null){
                        int pos = getAbsoluteAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(pos);
                        }
                    }
                }
            });

        }

        public void set(int image, String name) {
            binding.walletimage.setImageResource(image);
            binding.walletname.setText(name);
        }
    }
}
