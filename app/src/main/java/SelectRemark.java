package com.techbuzz.katraj.drunkpersondetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SelectRemark extends Activity {

    TextView txtUSername;
    EditText editTextRemark,editTextPersonid;
    ImageButton btnLogout;
    Button btnStart;
    ProgressBar progressBar;
    String Userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_remark);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        editTextRemark=(EditText) findViewById(R.id.editTextRemark);
        editTextPersonid=(EditText) findViewById(R.id.editTextPersonno);

      /*  txtUSername=(TextView)findViewById(R.id.txtUsername);
        btnLogout=(ImageButton)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SharedPrefmanager.getInstance(getApplicationContext()).logout();
            }
        });
*/
        User user = SharedPrefmanager.getInstance(this).getUser();
        Random rn = new Random();
        int maximum=99999;
        int minimum=00000;
        int range = maximum - minimum + 1;

        int randomNum =  rn.nextInt(range) + minimum;


  //      txtUSername.setText(user.getEmailid());

        editTextPersonid.setText(String.valueOf(randomNum));
         Userid= String.valueOf(user.getId());
    //    Toast.makeText(this, Userid, Toast.LENGTH_LONG).show();
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              UpdateReamark();
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        InsertId();
        Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
    }

    private void InsertId() {
        //first getting the values
        //final String remark = editTextRemark.getText().toString();
        final String remark = "";
        final String recippeeid = editTextPersonid.getText().toString();

        //validating inputs
       /* if (TextUtils.isEmpty(remark)) {
            editTextRemark.setError("Please enter your Reamark");
            editTextRemark.requestFocus();
            return;
        }*/


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_INSERTID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SelectRemark.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject recioeeJson = obj.getJSONObject("person");

                                //creating a new user object
                                Person person = new Person(
                                        recioeeJson.getInt("RecipeeId"),
                                        recioeeJson.getInt("id")

                                );

                                Toast.makeText(SelectRemark.this, String.valueOf(recioeeJson.getInt("id")), Toast.LENGTH_SHORT).show();
                                //storing the user in shared preferences
                                SharedPrefmanager.getInstance(getApplicationContext()).recipeeInfo(person);
                                //starting the profile activity
                               // finish();

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("remark",remark);
                params.put("recipeeid",recippeeid);
                params.put("userid", Userid);
                return params;
            }
        };

        Singleton.getInstance(this).addToRequestQue(stringRequest);
    }

    private void UpdateReamark() {
        //first getting the values
        final String remark = editTextRemark.getText().toString();
        final String recipeeid = editTextPersonid.getText().toString();
       // Toast.makeText(this, String.valueOf(recipee.getRecipeeid()), Toast.LENGTH_SHORT).show();
        //validating inputs
        if (TextUtils.isEmpty(remark)) {
            editTextRemark.setError("Please enter your Reamark");
            editTextRemark.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATEREMARK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), obj.getString("PersonInfo"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject PersonJson = obj.getJSONObject("PersonInfo");

                                //creating a new user object
                               PersonInfo personinfo = new PersonInfo(
                                       PersonJson.getString("rid"),
                                       PersonJson.getString("RecipeeId"),
                                       PersonJson.getString("Remark"),
                                       PersonJson.getString("Date"),
                                       PersonJson.getString("Userid")

                                );
                                //storing the user in shared preferences
                                SharedPrefmanager.getInstance(getApplicationContext()).PersonDetailsInfo(personinfo);
                                //starting the profile activity

                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("remark", remark);
                params.put("recipeeid",recipeeid);
                params.put("userid", Userid);
                return params;
            }
        };

        Singleton.getInstance(this).addToRequestQue(stringRequest);
    }
}
