package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.mediustechnologies.payemi.ApiResponse.ProfileInfoResponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ProfileFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFraggment extends Fragment {

    private static final int RESULT_OK = -1;
    private static final int REQUEST_CODE_CAMERA = 20;
    private ProfileFragmentBinding binding;
    private Context context ;
    private Uri cam_uri;

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
            Bitmap imgurl = null;




            Call<ProfileInfoResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().updateProfileInfo(utils.access_token,utils.phone,name,mail,username,address,imgurl);

            call.enqueue(new Callback<ProfileInfoResponse>() {
                @Override
                public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {

                    binding.progress.setVisibility(View.GONE);

                    if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                        if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                            try {
                                binding.profilephone.setText(utils.phone);
                                binding.profilename.setText(utils.name);
                                binding.profilemail.setText(response.body().getData().get(0).getEmail());
                                binding.profileUsername.setText(response.body().getData().get(0).getUser());
                                binding.profileaddress.setText(response.body().getData().get(0).getAddress());


                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);

                                preferences.edit().putString("name",response.body().getData().get(0).getUser_name()).apply();
                                preferences.edit().putString("profileid", response.body().getData().get(0).getId()).apply();
                                preferences.edit().putString("cutomerid", response.body().getData().get(0).getCustomer_id()).apply();

                                Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();

                            }
                            catch (Exception e){
                                e.printStackTrace();

                            }



                        } else {
                            try {
                                utils.errortoast(context, response.body().getMessage());
                            } catch (Exception e) {
                                Log.e("tag", e.toString());
                            }
                        }
                    }
                    else {
                        Log.e("tag",""+response.code());
                    }
                }

                @Override
                public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {
                    binding.progress.setVisibility(View.GONE);
                    Log.e("tag",t.getMessage());
                    Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });




        });


    }

    private void callapiforprofile() {

        binding.progress.setVisibility(View.VISIBLE);

        Call<ProfileInfoResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().profileInfo(utils.access_token,utils.phone);

        call.enqueue(new Callback<ProfileInfoResponse>() {
            @Override
            public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {

                binding.progress.setVisibility(View.GONE);

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        try {
                            binding.profilephone.setText(utils.phone);
                            binding.profilename.setText(utils.name);
                            binding.profilemail.setText(response.body().getData().get(0).getEmail());
                            binding.profileUsername.setText(response.body().getData().get(0).getUser_name());
                            binding.profileaddress.setText(response.body().getData().get(0).getAddress());
                        }
                        catch (Exception e){
                            e.printStackTrace();

                        }

                        binding.datalayout.setVisibility(View.VISIBLE);

                    } else {
                        try {
                            utils.errortoast(context, response.body().getMessage());
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
                        }
                    }
                }
                else {
                    Log.e("tag",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Log.e("tag",t.getMessage());
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void camera(){


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.profile_picture_alert,null);
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
        if(camerapermission == PackageManager.PERMISSION_GRANTED){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
            cam_uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startCamera.launch(cameraIntent);
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_CAMERA && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
            cam_uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startCamera.launch(cameraIntent);
        }
        else{
            Toast.makeText(context, "Permission Not Granted", Toast.LENGTH_SHORT).show();
        }
    }

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

    private void convert(Uri uri){



    }
}
