package com.example.lenovo.inkredoemi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.inkredoemi.R;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

public class MainActivity extends AppCompatActivity implements ITrueCallback{

    private TrueClient mTrueClient;
    Button proceedButton;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = (EditText)findViewById(R.id.edit_text_mobile_number);

        proceedButton = (Button)findViewById(R.id.button_proceed);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().toString().compareTo("")!=0 && number.getText().toString().length()==10) {
                    Intent intent = new Intent(MainActivity.this, EMIActivity.class);
                    intent.putExtra("Number", number.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(MainActivity.this,"Enter a valid mobile number !",Toast.LENGTH_SHORT).show();
            }
        });
        mTrueClient = new TrueClient(this,this);
        ((TrueButton)findViewById(R.id.com_truecaller_android_sdk_truebutton)).setTrueClient(mTrueClient);

    }

    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {
        Log.i("----", "OnSuccess");
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Log.i("----", "OnFailure " + trueError.toString() + trueError.getErrorType());
        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mTrueClient.onActivityResult(requestCode, resultCode, data)) {
            Log.i("----", "OnResult "+resultCode);
            return;
        }
    }
}
