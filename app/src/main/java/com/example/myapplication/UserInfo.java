package com.example.myapplication;

public class UserInfo {
	private String name;
	private String password;
	private String question;
	private String answer;
	public UserInfo(String name, String password){
		this.name = name;
		this.password = password;
	}
	public UserInfo(String name,String User_question,String User_answer){
		this.name = name;
		this.question = User_question;
		this.answer = User_answer;
	}
	public UserInfo(String name, String password, String User_question, String User_answer){
		this.name = name;
		this.password = password;
		this.question = User_question;
		this.answer = User_answer;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAnswer(){
		return this.answer;
	}
	public void setAnswer(String answer){
		this.answer = answer;
	}
	public String getQuestion(){
		return this.question;
	}
	public void setQuestion(String question){
		this.question = question;
	}
}
