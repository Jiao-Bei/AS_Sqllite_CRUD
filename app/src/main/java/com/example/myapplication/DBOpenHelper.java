package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Iterator;


public class DBOpenHelper extends SQLiteOpenHelper {

	private SQLiteDatabase db;
	public DBOpenHelper(Context context){
		super(context, "db_test.db",null,1);
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {// 在这里准备建表，该方法来自于父类的抽象方法
		db.execSQL("CREATE TABLE IF NOT EXISTS user("+
				"user_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"name TEXT UNIQUE NOT NULL,"+
				"password TEXT NOT NULL,"+
				"user_question TEXT NOT NULL,"+
				"user_answer TEXT NOT NULL)");// 好像阿里的规范说要用到update和create_time好了,但是之前写的时候出了莫名其妙的bug，就先删掉
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// 升级的这个我暂时还用不到，就先放着吧

	}

	// 接下来开始对本次项目的几项需求写出相应的函数，注册（add）,注销和删除（delete），更改密码（update）,登陆和查找其他用户（find）
	// 另注：find操作将会多次的使用
	public ArrayList<UserInfo> find(){
		SQLiteDatabase db = getReadableDatabase();
		ArrayList<UserInfo> list = new ArrayList<UserInfo>();
		Cursor cursor = db.query("user", null, null, null,null, null, "name DESC");

		/** 稍微解释一下每个变量的含义 最前的cursor是一个游标，指向db返回的query的首位。
		 * columns指向行，由于我们这边的所有需求都只需要name和password，我就不加最后两位为了符合规范而上的了（更改密码那一步需要这个函数也是因为规范）
		 * selection 选择那些比方说"password = ? AND price < ?"，selectionArgs中的值就会指向?(写法同columns项，这里因为不需要的原因就不多做描述了
		 * groupBy 和orderBy 还有having 就比较像SQL Server中的语句了，往String里加个SQL语句就好
		 *
		 * @return
		 */
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String password = cursor.getString(cursor.getColumnIndex("password"));
			String question = cursor.getString(cursor.getColumnIndex("user_question"));
			String answer = cursor.getString(cursor.getColumnIndex("user_answer"));
			list.add(new UserInfo(name,password,question,answer));
		}
		cursor.close();
		return list;
	}
	public boolean add(String name, String password, String question, String answer){
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<UserInfo> list = find();
		Iterator iter = list.iterator();
		Boolean haveAddTarget = false;
		if(list.size() != 0){
			while(iter.hasNext() && !haveAddTarget){
				if(((UserInfo)iter.next()).getName().equals(name)){// 大意就是如果我的表里面如果有一个东西的名字是否有我想加的name
				haveAddTarget = true;
				}
			}
		}
		if(haveAddTarget){
			return false;
		}else{
			// Date d=new Date();
			// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM0dd HH:mm:ss"); 这些是应对阿里规范要求的update等项的准备。
			db.execSQL("INSERT INTO user (name,password,user_question,user_answer) VALUES(?,?,?,?)",
					new Object[]{name,password,question,answer});
			return true;
		}
	}
	public boolean delete(String name){// 因为我的删除是在用户界面和管理界面的，所以目标都是必然存在的，就不用再查询一次了
		SQLiteDatabase db = getWritableDatabase();
		if(name.equals("admin")){
			return false;
		}
		db.execSQL("DELETE FROM user WHERE name = ?",new Object[]{name});
		return true;
	}
	public void updatePassword(String name, String password){// 因为我的更新只有在用户界面才可以更新，所以默认知道我们的对象是存在的
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE user SET password=? WhERE name=?",new Object[]{password,name});
	}
	public void updateQuestionAndAnswer(String name, String question, String answer){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE user SET user_question=?,user_answer=? WhERE name=?",new Object[]{question,answer,name});
	}
	public void deleteTable(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS user");
	}
}
