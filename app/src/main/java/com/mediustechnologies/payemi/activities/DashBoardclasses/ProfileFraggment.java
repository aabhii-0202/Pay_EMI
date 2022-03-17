package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediustechnologies.payemi.ApiResponse.ProfileInfoResponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ProfileFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFraggment extends Fragment {

    private static final int RESULT_OK = -1;
    private static final int REQUEST_CODE_CAMERA = 20;
    private ProfileFragmentBinding binding;
    private Context context;
    private Uri cam_uri;
    private InputStream is;
    private updateNameListner listner;

    public interface updateNameListner {
        void updatename(String name,String url);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
        if(context instanceof updateNameListner){
             listner = (updateNameListner) context;
        }else{
            throw new RuntimeException(context.toString()+" must implemetn updateNameListner");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listner = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.datalayout.setVisibility(View.GONE);
        Glide.with(binding.profileImage).load(utils.profileUrl).into(binding.profileImage);
        callapiforprofile();

        binding.editprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
            }
        });


        binding.profileupdate.setOnClickListener(view1 -> {

            binding.progress.setVisibility(View.VISIBLE);

            String name = binding.profilename.getText().toString();
            String mail = binding.profilemail.getText().toString();
            String username = binding.profileUsername.getText().toString();
            String address = binding.profileaddress.getText().toString();

            try {
                uploadImage(getBytes(is));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void callapiforprofile() {

        binding.progress.setVisibility(View.VISIBLE);

        Call<ProfileInfoResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().profileInfo(utils.access_token, utils.phone);

        call.enqueue(new Callback<ProfileInfoResponse>() {
            @Override
            public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {

                binding.progress.setVisibility(View.GONE);

                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        if (!response.body().getData().isEmpty()) {
                            try {
                                binding.profilephone.setText(utils.phone);
                                binding.profilename.setText(response.body().getData().get(0).getFullname());
                                binding.profilemail.setText(response.body().getData().get(0).getEmail());
                                binding.profileUsername.setText(response.body().getData().get(0).getUser_name());
                                binding.profileaddress.setText(response.body().getData().get(0).getAddress());

                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);

                                preferences.edit().putString("name", response.body().getData().get(0).getFullname()).apply();
                                preferences.edit().putString("profileid", response.body().getData().get(0).getId()).apply();
                                preferences.edit().putString("cutomerid", response.body().getData().get(0).getCustomer_id()).apply();
                                preferences.edit().putString("path", response.body().getData().get(0).getProfile_url()).apply();
                                utils.profileUrl = response.body().getData().get(0).getProfile_url();

                                listner.updatename(response.body().getData().get(0).getFullname(),response.body().getData().get(0).getProfile_url());
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }

                        binding.datalayout.setVisibility(View.VISIBLE);

                    } else {
                        try {
                            utils.errortoast(context, response.body().getMessage());
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
                        }
                    }
                } else {
                    Log.e("tag", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Log.e("tag", t.getMessage());
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void camera() {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.profile_picture_alert, null);
        builder.setCancelable(false);
        builder.setView(dialog);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        LinearLayout cam = dialog.findViewById(R.id.camera);
        LinearLayout gallary = dialog.findViewById(R.id.gallary);

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();
                takepermissionforcamera();


            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                mGetContent.launch("image/*");
            }
        });


    }

    private void takepermissionforcamera() {

        int camerapermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (camerapermission == PackageManager.PERMISSION_GRANTED) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
            cam_uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startCamera.launch(cameraIntent);
        } else {
            cameraPermissionRequest.launch(new String[]{
                    Manifest.permission.CAMERA
            });
        }
    }

    ActivityResultLauncher<String[]> cameraPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean camerapermission = result.get(
                                Manifest.permission.CAMERA);
                        if (camerapermission != null && camerapermission) {
                            Log.d("camera", "Granted");
                            camera();
                        } else {
                            Toast.makeText(context, "Please give permission to open camera.", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        binding.profileImage.setImageURI(cam_uri);
                        convert(cam_uri);


                    }
                }
            });

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    convert(uri);
                    Glide.with(binding.profileImage).load(uri).into(binding.profileImage);
                }
            });

    private void convert(Uri uri) {
        try {
            is = context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        try {


            while ((len = is.read(buff)) != -1) {
                byteBuff.write(buff, 0, len);
            }
        } catch (Exception e) {
            return null;
        }

        return byteBuff.toByteArray();
    }

    private JsonObject ApiJsonMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();

//            for (Map.Entry item:feilds.entrySet()){
//                jsonObj_.put((String) item.getKey(),item.getValue());
//            }
            jsonObj_.put("fullname", binding.profilename.getText().toString());
            jsonObj_.put("email", binding.profilemail.getText().toString());
            jsonObj_.put("user_name", binding.profileUsername.getText().toString());
            jsonObj_.put("address", binding.profileaddress.getText().toString());


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.d("tag", "AS PARAMETER  " + gsonObject);

        } catch (Exception e) {
            Log.d("tag", "Add loan Account JSON exception line 154");
        }

        return gsonObject;
    }

    private void uploadImage(byte[] imageBytes) {

        MultipartBody.Part body;
        Call<ProfileInfoResponse> call;

        String mail = binding.profilemail.getText().toString();
        String pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailregex = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailregex.matcher(mail);
        if(!matcher.find()){

            binding.profilemail.setError("Enter a valid mail.");
            binding.progress.setVisibility(View.GONE);
            return;
        }

        JsonObject josnbody = ApiJsonMap();
        if (imageBytes != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
            body = MultipartBody.Part.createFormData("profile_url", "image.jpg", requestFile);

            call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().updateProfilePic(utils.access_token, utils.phone, body);

            call.enqueue(new Callback<ProfileInfoResponse>() {
                @Override
                public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {
                    binding.progress.setVisibility(View.GONE);

                    if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                        if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                            if (!response.body().getData().isEmpty()) {
                                Log.d("tag","On Image Upload "+response.body().toString());
                                try {
                                    binding.profilephone.setText(utils.phone);
                                    binding.profilename.setText(response.body().getData().get(0).getFullname());
                                    binding.profilemail.setText(response.body().getData().get(0).getEmail());
                                    binding.profileUsername.setText(response.body().getData().get(0).getUser_name());
                                    binding.profileaddress.setText(response.body().getData().get(0).getAddress());

                                    SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);

                                    preferences.edit().putString("name", response.body().getData().get(0).getFullname()).apply();
                                    preferences.edit().putString("profileid", response.body().getData().get(0).getId()).apply();
                                    preferences.edit().putString("cutomerid", response.body().getData().get(0).getCustomer_id()).apply();
                                    preferences.edit().putString("path", response.body().getData().get(0).getProfile_url()).apply();
                                    utils.profileUrl = response.body().getData().get(0).getProfile_url();

                                    listner.updatename(response.body().getData().get(0).getFullname(),response.body().getData().get(0).getProfile_url());

                                    Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }


                        } else {
                            try {
                                utils.errortoast(context, response.body().getMessage());
                            } catch (Exception e) {
                                Log.e("tag", e.toString());
                            }
                        }
                    } else {
                        Log.e("tag", "" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {

                }
            });
        }

        call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().updateProfileInfo(utils.access_token, utils.phone, josnbody);


        call.enqueue(new Callback<ProfileInfoResponse>() {
            @Override
            public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {

                binding.progress.setVisibility(View.GONE);

                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    Log.d("tag","On No Image Upload "+response.body().toString());
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        if (!response.body().getData().isEmpty()) {


                            try {
                                binding.profilephone.setText(utils.phone);
                                binding.profilename.setText(response.body().getData().get(0).getFullname());
                                binding.profilemail.setText(response.body().getData().get(0).getEmail());
                                binding.profileUsername.setText(response.body().getData().get(0).getUser_name());
                                binding.profileaddress.setText(response.body().getData().get(0).getAddress());

                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);

                                preferences.edit().putString("name", response.body().getData().get(0).getFullname()).apply();
                                preferences.edit().putString("profileid", response.body().getData().get(0).getId()).apply();
                                preferences.edit().putString("cutomerid", response.body().getData().get(0).getCustomer_id()).apply();
                                preferences.edit().putString("path", response.body().getData().get(0).getProfile_url()).apply();
                                utils.profileUrl = response.body().getData().get(0).getProfile_url();


                                listner.updatename(response.body().getData().get(0).getFullname(),response.body().getData().get(0).getProfile_url());

                                Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }


                    } else {
                        try {
                            utils.errortoast(context, response.body().getMessage());
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
                        }
                    }
                } else {
                    Log.e("tag", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Log.e("tag", t.getMessage());
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
