package com.fly.shiro.pojo;

import java.util.HashMap;
import java.util.Map;

public class Result {
	
	private boolean flag ;
	
	private int code ;
	
	private String massage ;
	
	private Map<String, Object> data ;
	
	public Result() {
		
	}
	
	public Result(boolean flag, int code) {
		this.flag = flag ;
		this.code = code ;
	}
	
	public Result(boolean flag, int code, String massage) {
		this.flag = flag ;
		this.code = code ;
		this.massage = massage ;
	}
	
	public Result(boolean flag, int code, String massage, Map<String, Object> data) {
		this.flag = flag ;
		this.code = code ;
		this.massage = massage ;
		this.data = data ;
	}
	
	public Result(boolean flag, int code, String massage, Object e) {
		this.flag = flag ;
		this.code = code ;
		this.massage = massage ;
		addData(e) ;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void addData(Object object) {
		this.data = new HashMap<String, Object>();
		this.data.put(object.getClass().getSimpleName().toLowerCase(), object);
	}
	
	@Override
	public String toString() {
		return "Result [flag=" + flag + ", code=" + code + ", massage=" + massage + ", data=" + data + "]";
	}
	
	
}
