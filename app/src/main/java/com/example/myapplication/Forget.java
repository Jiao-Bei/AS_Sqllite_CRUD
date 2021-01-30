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

public class Forget extends AppCompatActivity implements View.OnClickListener{

	private Button btnInquiry;
	private Button btnReturn;
	private Button btnCommit;
	private EditText etYourName;
	private EditText etAnswer;
	private TextView tvQuestion;
	private TextView tvPassword;
	private DBOpenHelper db;
	private UserInfo target;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		btnInquiry = (Button)findViewById(R.id.Forget_Inquiry);
		btnReturn = (Button)findViewById(R.id.Forget_Return);
		btnCommit = (Button)findViewById(R.id.Forget_Commit);
		etYourName = (EditText)findViewById(R.id.Forget_Your_Name);
		etAnswer = (EditText)findViewById(R.id.Forget_Answer);
		tvQuestion = (TextView)findViewById(R.id.Forget_Question);
		tvPassword = (TextView)findViewById(R.id.Forget_Show_Password);
		db = new DBOpenHelper(this);
		btnReturn.setOnClickListener(this);
		btnCommit.setOnClickListener(this);
		btnInquiry.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.Forget_Inquiry:
				ArrayList<UserInfo> list = db.find();
				Iterator iter = list.iterator();
				boolean haveTarget = false;
				while(iter.hasNext()){
					target = ((UserInfo)iter.next());
					if(target.getName().equals(etYourName.getText().toString())){
						haveTarget = true;
						break;
					}
				}
				if(haveTarget){
					tvQuestion.setText(target.getQuestion());
				}else{
					Toast.makeText(getApplicationContext(), "这个名字不存在", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.Forget_Commit:
				if(etAnswer.getText().toString().equals(target.getAnswer())){
					tvPassword.setText(target.getPassword());
				}else{
					Toast.makeText(getApplicationContext(), "密保问题打错啦", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.Forget_Return:
				Intent intent = new Intent(Forget.this,MainActivity.class);
				startActivity(intent);
				break;
		}
	}
}
