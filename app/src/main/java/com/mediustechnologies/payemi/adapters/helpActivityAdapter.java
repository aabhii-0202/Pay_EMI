package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.DTO.GetHelpQuestionAnswerDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.HelpSubcatagoryItemExpandableBinding;
import com.mediustechnologies.payemi.ApiResponse.GetHelpQuestionAnswer;

import java.util.List;

public class helpActivityAdapter extends RecyclerView.Adapter<helpActivityAdapter.vh> {

    List<GetHelpQuestionAnswerDTO> list ;
    private onQuestionAskd listner;

    public interface onQuestionAskd{
        void OnQuestion(int position);
    }

    public void SetOnQuestionClicked(onQuestionAskd listner){
        this.listner = listner;
    }

    public helpActivityAdapter(List<GetHelpQuestionAnswerDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public helpActivityAdapter.vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_subcatagory_item_expandable,parent,false);
        return new vh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helpActivityAdapter.vh holder, int position) {
        String question = list.get(position).getQuestion();
        String answer = list.get(position).getAnswer();
        holder.set(question,answer);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class vh extends RecyclerView.ViewHolder {

        HelpSubcatagoryItemExpandableBinding binding;
        public vh(@NonNull View itemView) {
            super(itemView);
            binding = HelpSubcatagoryItemExpandableBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner!=null){
                        int position  = getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listner.OnQuestion(position);
                            binding.view.setVisibility(View.GONE);
                            binding.answer.setVisibility(View.VISIBLE);

                        }
                    }
                }
            });

        }

        public void set(String question, String answer) {
            binding.title.setText(question);
            binding.answer.setVisibility(View.GONE);
            binding.answer.setText(answer);
            binding.view.setVisibility(View.VISIBLE);
        }
    }
}
