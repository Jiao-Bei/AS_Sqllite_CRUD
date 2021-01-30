package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private Button btnRegister;
    private Button btnForgetPwd;
    private EditText etName;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button)findViewById(R.id.Main_Login);
        btnRegister = (Button)findViewById(R.id.Main_Register);
        btnForgetPwd = (Button)findViewById(R.id.Main_Forget_Password);
        etName = (EditText)findViewById(R.id.Main_Name);
        etPassword = (EditText)findViewById(R.id.Main_Password);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnForgetPwd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch(v.getId()){
            // 对应登陆：
            case R.id.Main_Login:// 如果是root用户登陆则先进入管理界面然后返回(数据库查询)
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();
                DBOpenHelper db = new DBOpenHelper(this);
                UserInfo dbTarget = null; // 该变量指示DB当中的目标，初始化在find之后的迭代的时候使用
                ArrayList<UserInfo> list = db.find();
                Iterator iter = list.iterator();
                boolean haveTarget = false;
                while(iter.hasNext()){
                    dbTarget = (UserInfo) iter.next();
                    if(dbTarget.getName().equals(name)){
                        haveTarget = true;
                        break;
                    }
                }
                if(haveTarget && dbTarget.getPassword().equals(password) ){
                    if(name.equals("admin")){
                        intent = new Intent(MainActivity.this, Manager.class);
                        intent.putExtra("name",dbTarget.getName());
                        intent.putExtra("password",dbTarget.getPassword());
                        startActivity(intent);
                        break;
                    }else{
                        intent = new Intent(MainActivity.this,User.class);
                        intent.putExtra("name",dbTarget.getName());
                        intent.putExtra("password",dbTarget.getPassword());
                        startActivity(intent);
                        break;
                    }
                }
                if(haveTarget)
                    Toast.makeText(getApplicationContext(), "不会真的有人记错了自己的密码吧",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(getApplicationContext(), "不会真的有人不注册就想登陆吧",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Main_Register:
                intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                break;
            case R.id.Main_Forget_Password:
                intent = new Intent(MainActivity.this,Forget.class);
                startActivity(intent);
        }
    }
}
