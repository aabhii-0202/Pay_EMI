package com.mediustechnologies.payemi.adapters;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.DTO.mandatoryParmsDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.InputParamsRecyclerItemBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class inputParametersAdapter extends RecyclerView.Adapter<inputParametersAdapter.viewHolder> {

    private LinkedHashMap<String,mandatoryParmsDTO> inputList;
    private final List<String> keys;
    private LinkedHashMap<String,String> returnvalues;

    boolean isOnTextChanged = false;

    public LinkedHashMap<String,String> getfeilds(){
        return returnvalues;
    }







    public inputParametersAdapter(LinkedHashMap<String, mandatoryParmsDTO> inputList) {
        this.inputList = inputList;

        keys = new ArrayList<>();
        keys.addAll(inputList.keySet());

        returnvalues = new LinkedHashMap<>();
        for(int i=0;i<keys.size();i++){
            String k = inputList.get(keys.get(i)).getKey();
            returnvalues.put(k,"");
        }
    }



    @NonNull
    @Override
    public inputParametersAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.input_params_recycler_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull inputParametersAdapter.viewHolder holder, int position) {
        String title = keys.get(position);
        String type = inputList.get(title).getType();


        holder.setData(title,type);

        EditText input = holder.binding.input;
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOnTextChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isOnTextChanged){
                    isOnTextChanged = false;
                    try{
                        String k = inputList.get(title).getKey();
                        returnvalues.put(k,editable.toString());
//                        System.out.println(returnvalues.toString());
                    }
                    catch (Exception e){
                        Log.d("tag","inputParametersAdapter line 86 -- ExceptionExceptionExceptionException");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        InputParamsRecyclerItemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = InputParamsRecyclerItemBinding.bind(itemView);



        }

        public void setData(String title,String type) {

            title = format(title);
            binding.title.setText(title);
            binding.input.setHint("Enter Your "+title);
            binding.errorMessage2.setText("Please Enter Proper "+title);
            if(type.equals("NUMERIC")) binding.input.setInputType(InputType.TYPE_CLASS_NUMBER);
            else binding.input.setInputType(InputType.TYPE_CLASS_TEXT);

        }

        private String format(String s) {
            String ans = "";
            if (s.length() > 0) {
                s = s.replace('_', ' ');
                s = s.trim();
                String temp = s.charAt(0) + "";
                ans += temp.toUpperCase() + s.substring(1);

            }else ans = s;
            return ans;

        }
    }
}
