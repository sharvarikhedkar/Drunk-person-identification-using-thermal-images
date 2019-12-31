package com.techbuzz.katraj.drunkpersondetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SelectChoice extends AppCompatActivity {
    TextView txtUSername;
    EditText editTextRemark,editTextPersonid;
    ImageButton btnLogout;
    Button btnStart;
    ProgressBar progressBar;
    String Userid;
    Button btnTrain,btnDetect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_choice);

        txtUSername=(TextView)findViewById(R.id.txtUsername);
        btnTrain=(Button)findViewById(R.id.btnTrain);
        btnDetect=(Button)findViewById(R.id.btnDetection);
        btnLogout=(ImageButton)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SharedPrefmanager.getInstance(getApplicationContext()).logout();
            }
        });

        User user = SharedPrefmanager.getInstance(this).getUser();

        txtUSername.setText(user.getEmailid());

        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            startActivity(new Intent(getApplicationContext(),DetectionImage.class));
            }
        });
    }
}
