package com.caomei.cvseller.eventbus;

public class EventMsg {

	private int flag;
	private Object data;
	
	public EventMsg(int flag,Object data){
		this.flag=flag;		
		this.data=data;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	} 
	
	
}
