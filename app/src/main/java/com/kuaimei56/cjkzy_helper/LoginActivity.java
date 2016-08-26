package com.kuaimei56.cjkzy_helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.kuaimei56.cjkzy_helper.utils.FileUtils;

/**
 * Created by CLEVO on 2016/8/26.
 */
public class LoginActivity extends Activity {

    private Button loginButton;
    private EditText nameText;
    private EditText wxText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);

        initUI();

    }

    private void initUI() {
        loginButton = (Button) findViewById(R.id.loginButton);
        nameText = (EditText) findViewById(R.id.nameEditText);
        wxText = (EditText) findViewById(R.id.wxEditText);

        resetLastUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.userRealName = nameText.getText().toString().trim();
                MyApplication.userWx = wxText.getText().toString().trim();

                FileUtils.write(LoginActivity.this, MyApplication.userRealName + "," + MyApplication.userWx);

                Intent intent = new Intent(LoginActivity.this, ClipBoardActivity.class);
                intent.putExtra("name", MyApplication.userRealName);
                intent.putExtra("wx", MyApplication.userWx);
                startActivity(intent);

                LoginActivity.this.finish();
            }
        });
    }

    private void resetLastUser() {
        String userInfo = FileUtils.read(this);
        if (userInfo != null) {
            String[] strings = userInfo.split(",");
            if (strings.length > 1) {
                nameText.setText(strings[0]);
                wxText.setText(strings[1]);
            }
        }
    }
}
