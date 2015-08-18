package com.sinowel.netflow.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * 对微信开放的http接口，80端口模式
 * 
 * @author linyaoyao
 * @version 1.0 </pre> Created on :下午3:16:26 LastModified: History: </pre>
 */
public class WechatServlet extends HttpServlet {

	/**
	 * 开发者通过检验signature对请求进行校验。若确认此次Get请求来自微信服务器， 请远样返回echostr参数内容,则接入生效，否则接入失败。
	 * 
	 * 加密\校验流程： 1.将token,timestamp,nonce 三个参数进行字典排序 2.将三个参数字符串拼成一个字符串进行SHA1加密
	 * 3.开发者获得加密后的字符串课余signature对比，标识该请求来源于微信
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 6163951690712926575L;

	/**
	 * 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

//		System.out.println("signature:"+signature);
//		System.out.println("timestamp:"+timestamp);
//		System.out.println("nonce:"+nonce);
//		System.out.println("echostr:"+echostr);
//		System.out.println("token:"+ConfigManager.getValue("token"));
		
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (WechatHttpUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 处理微信服务器发来的消息 * </br> request中封装了请求相关的所有内容，可以从request中取出微信服务器发来的消息； </br>
	 * response 对接收到的消息进行响应，即发送消息。</br>
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String respMessage = "";
		PrintWriter out;
		try {
			// 调用核心业务类接收消息、处理消息
//			respMessage = WechatProcessUtil.processRequest(request);
			WechatService.processRequest(request);
			
			// 响应消息 目前返回一个空字符串
			// 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次
			// 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试。
			out = response.getWriter();
			out.print(respMessage);
			out.close();
			out = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
//		AccessToken accessToken = AccessTokenManager.getAccessToken(
//				"wx8fb303f69dcc8575", "abaa6394ab29bf22193b6426fcd23616");

		
 
	}

}
