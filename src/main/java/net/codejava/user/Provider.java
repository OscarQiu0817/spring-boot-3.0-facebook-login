package net.codejava.user;

public enum Provider {

	LOCAL("Local"),
	FACEBOOK("Facebook"),
	GOOGLE("Google"),
	GITHUB("Github");

	private String name;

	Provider(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}


}
