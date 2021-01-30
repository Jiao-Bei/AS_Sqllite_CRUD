package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class User extends AppCompatActivity implements View.OnClickListener{

    private EditText Et_Old_Password;
    private EditText Et_New_Password;
    private Button Btn_Password_Reset;
    private Button Btn_Back_to_Main;
    private Button Btn_Delete_Myself;
    private TextView title;
    private DBOpenHelper db;
    private Intent intent;
    private UserInfo you;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        title = (TextView)findViewById(R.id.User_Title);
        Et_Old_Password = (EditText)findViewById(R.id.User_Old_Password);
        Et_New_Password = (EditText)findViewById(R.id.User_New_Password);
        Btn_Password_Reset = (Button)findViewById(R.id.User_Reset_Password);
        Btn_Back_to_Main = (Button)findViewById(R.id.User_Exit_To_Main);
        Btn_Delete_Myself = (Button)findViewById(R.id.User_Delete_Myself);
        db = new DBOpenHelper(this);
        intent = getIntent();
        you = new UserInfo(intent.getStringExtra("name"),intent.getStringExtra("password"));
        title.setText(you.getName()+you.getPassword());
        Btn_Back_to_Main.setOnClickListener(this);
        Btn_Delete_Myself.setOnClickListener(this);
        Btn_Password_Reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.User_Reset_Password:
                String old_password = Et_Old_Password.getText().toString();
                String new_password = Et_New_Password.getText().toString();
                // 数据库增删(改)查+返回主界面
                if(old_password.equals(you.getPassword())){
                    db.updatePassword(you.getName(),new_password);// 简单说一下为什么这里直接使用update了，intent是一层层传过来的，起点是登陆，不加修改，能确保当前用户是谁
                    intent = new Intent(User.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "不会真的有人记错自己原来的密码吧",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.User_Delete_Myself:
                // 数据库删+返回主界面
                db.delete(you.getName());// 理由同上
                intent = new Intent(User.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.User_Exit_To_Main:
                // 返回主界面
                Intent intent = new Intent(User.this, MainActivity.class);
                startActivity(intent);
        }
    }
}
