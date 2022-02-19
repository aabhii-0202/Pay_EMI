package com.mediustechnologies.payemi.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.DTO.mandatoryParmsDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.AddLoanAccount;
import com.mediustechnologies.payemi.databinding.InputParamsRecyclerItemBinding;
import com.mediustechnologies.payemi.helper.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class inputParametersAdapter extends RecyclerView.Adapter<inputParametersAdapter.viewHolder> implements DatePickerDialog.OnDateSetListener {

    private LinkedHashMap<String,mandatoryParmsDTO> inputList;
    private final List<String> keys;
    private LinkedHashMap<String,String> returnvalues;
    boolean isOnTextChanged = false;
    private Context context;

    private onItemClick mListner;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        // add date
    }

    public interface onItemClick{
        void onItemClick (int postion);
    }
    public void setOnItemClickListner(inputParametersAdapter.onItemClick listner){
        mListner = listner;
    }





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
        context = parent.getContext();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){

                        int position = getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }


                    }
                }
            });



        }

        public void setData(String title,String type) {

            title = format(title);
            binding.title.setText(title);
            try{
                if(title.toLowerCase().contains("date")||title.toLowerCase().contains("dob")){
                    binding.input.setFocusable(false);

                    binding.input.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Calendar cal = Calendar.getInstance();
                            int year = cal.get(Calendar.YEAR);
                            int month = cal.get(Calendar.MONTH);
                            int day = cal.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog dialog = new DatePickerDialog(
                                    context,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                            m++;
                                            String month = Integer.toString(m);
                                            String day = Integer.toString(d);

                                            if(m<10)month = "0"+month;
                                            if(d<10)day = "0"+day;


                                            String s = day+"-"+month+"-"+y;
                                            binding.input.setText(s);
                                        }
                                    },year,month,day);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(1,135,134)));
                            dialog.show();

                        }
                    });
                }
            }catch (Exception e){}
            binding.input.setHint("Enter Your "+title);


//            binding.errorMessage2.setText("Please Enter Proper "+title);
//            if(type.equals("NUMERIC")) binding.input.setInputType(InputType.TYPE_CLASS_NUMBER);
//            else
                binding.input.setInputType(InputType.TYPE_CLASS_TEXT);

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
