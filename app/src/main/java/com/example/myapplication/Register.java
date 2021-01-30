package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText etName;
    private EditText etPassword;
    private EditText etConfirmPwd;
    private EditText etQuestion;
    private EditText etAnswer;
    private Button btnCommit;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName=(EditText)findViewById(R.id.Register_Name);
        etPassword=(EditText)findViewById(R.id.Register_Password);
        etConfirmPwd=(EditText)findViewById(R.id.Register_Confirm_Password);
        etQuestion=(EditText)findViewById(R.id.Register_Question);
        etAnswer=(EditText)findViewById(R.id.Register_Answer);
        btnCommit=(Button)findViewById(R.id.Register_Accomplish);
        btnBack=(Button)findViewById(R.id.Register_Back_To_Main);
        btnCommit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Register_Accomplish:// 先数据库查询，在数据库添加
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();
                String confirm = etConfirmPwd.getText().toString();
                String question = etQuestion.getText().toString();
                String answer = etAnswer.getText().toString();
                if(confirm.equals(password)){
                    DBOpenHelper db = new DBOpenHelper(this);
                    if(db.add(name,password,question,answer))
                        Toast.makeText(getApplicationContext(), "注册成功",  Toast.LENGTH_SHORT).show();
                    else{
                        Toast.makeText(getApplicationContext(), "这个名字被抢注啦！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Register_Back_To_Main:
                Intent intent=new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
