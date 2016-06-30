package com.caomei.cvseller.Enum;


import java.io.Serializable;

/**
 * 访问网络的结果状态枚举
 * 
 * @author A Shuai
 * 
 */
public enum AccessNetState implements Serializable {

	/**
	 * 访问网络初始化
	 */
	Initialize,

	/**
	 * 访问网络得到正确的结果
	 */
	Success,

	/**
	 * 主机有响应但是不是正确的响应
	 */
	ErrorResponse,

	/**
	 * 连接超时异常
	 */
	ConnectTimeoutException,

	/**
	 * 主机协议异常（一般是无网络情况下导致DNS无法解析所致）
	 */
	ClientProtocolException,

	/**
	 * 网络未连接
	 * */

	NetNotConnectException,

	/**
	 * IO异常
	 */
	IOException,

	AccessNetState, /**
	 * 其他未知异常
	 */
	Exception

}