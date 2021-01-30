package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class Manager extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout BtnSetGroup = null;
    private Button[] button = new Button[100];// 100个给我们小型数据库估计差不多了
    private Intent intent = null;
    private TextView target = null;
    private Button BtnDelete = null;
    private Button BtnNext = null;
    private UserInfo you;
    private DBOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        init();
        BtnNext.setOnClickListener(this);
        BtnDelete.setOnClickListener(this);
    }
    private void init(){
        db = new DBOpenHelper(this);
        ArrayList<UserInfo> list = db.find();
        Iterator iter = list.iterator();
        BtnSetGroup = (LinearLayout) findViewById(R.id.Manager_BtnGroup);
        target = (TextView)findViewById(R.id.Manager_Target);
        BtnDelete = (Button)findViewById(R.id.Manager_Delete_It);
        BtnNext = (Button)findViewById(R.id.Manager_To_User);
        intent = getIntent();
        int i=0;
        // 这步主要是传到后面的User界面备用的
        you = new UserInfo(intent.getStringExtra("name"),intent.getStringExtra("password"));
        while(iter.hasNext()){// 在这里增加Button项
            button[i]=new Button(this);
            button[i].setTextSize(30);
            button[i].setText(((UserInfo)iter.next()).getName());
            button[i].setPadding(10,10,10,10);
            BtnSetGroup.addView(button[i]);
            button[i].setOnClickListener(this);
            i++;
        }
        i=0;
        target.setText("");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Manager_Delete_It:
                // 数据库的删除操作还有刷新一次
                String name = target.getText().toString();
                if(name.equals("")){
                    Toast.makeText(getApplicationContext(), "还没选对象就想着删除呢！", Toast.LENGTH_SHORT).show();
                }else{
                    if(db.delete(name)){
                        Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "不能删除admin！", Toast.LENGTH_SHORT).show();
                    }
                }
                BtnSetGroup.removeAllViews();
                init();
                break;
            case R.id.Manager_To_User:
                Intent intent =new Intent(Manager.this, User.class);
                intent.putExtra("name",you.getName());
                intent.putExtra("password",you.getPassword());
                startActivity(intent);
                break;
            default:
                Button temp=(Button)v;
                target.setText(temp.getText());
                break;
        }
    }
}
