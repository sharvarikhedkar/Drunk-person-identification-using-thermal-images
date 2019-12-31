package com.techbuzz.katraj.drunkpersondetection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private final String TAG = this.getClass().getName();
    private ImageView ivCamera, ivGallery, ivUpload, ivImage,ivstop;
    private EditText etName,etrecipeeid,etuserid,etremark;
    final int IMG_REQUEST = 1;
    final int CAMERA_REQUEST = 1888;
    final int GALLERY_REQUEST = 22131;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonInfo personInfo = SharedPrefmanager.getInstance(this).PersonDetailsInfo();
        ivCamera = (ImageView) findViewById(R.id.ImageViewCamera);
        ivGallery = (ImageView) findViewById(R.id.ImageViewGallery);
        ivUpload = (ImageView) findViewById(R.id.ImageViewUpload);
        ivImage = (ImageView) findViewById(R.id.Image);
        ivstop = (ImageView) findViewById(R.id.ImageViewStop);

        /*ivstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), SelectRemark.class));
            }
        });*/

        etName = (EditText) findViewById(R.id.imgName);
        //etrecipeeid = (EditText) findViewById(R.id.recipeeid);
        etuserid = (EditText) findViewById(R.id.userid);
        //etremark=(EditText) findViewById(R.id.remark);

        //etrecipeeid.setText(personInfo.getRecipeeId());
        etuserid.setText(personInfo.getUserid());
        //etremark.setText(personInfo.getRemark());

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    etName.setError("Pleas e Enter Image Name");
                    etName.requestFocus();
                    return;
                }
                String image = getStringImage(bitmap);
                Log.d(TAG, image);
                uploadImage(image);
            }
        });

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ivImage.setImageBitmap(bitmap);
                etName.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            bitmap = (Bitmap) data.getExtras().get("data");
            ivImage.setImageBitmap(bitmap);
            etName.setVisibility(View.VISIBLE);
        }
    }

    void uploadImage(final String image)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(MainActivity.this,Response, Toast.LENGTH_SHORT).show();
                            //ivImage.setImageResource(0);
                            //ivImage.setVisibility(View.GONE);
                            etName.setText("");
                            //etName.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("image", image);
                param.put("name", etName.getText().toString());
                param.put("userid", etuserid.getText().toString());
                return param;
            }
        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(40000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        Singleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }


}