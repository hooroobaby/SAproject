package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import org.json.*;
import ncu.im3069.demo.app.Member;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;


/**
 * Servlet implementation class LoginController
 */
@WebServlet("/api/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MemberHelper mh =  MemberHelper.getHelper();

//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String email = jso.getString("email");
        String password = jso.getString("password");
        
        JSONObject query = mh.getByEmail(email);
        JSONArray data = query.getJSONArray("data");
        if(!data.isEmpty()) {
        	/* 若帳號存在，比對密碼 */
            JSONObject m = data.getJSONObject(0);
        	if(m.getString("password").equals(password)) {
        		JSONObject resp = new JSONObject();
                Cookie login_email = new Cookie("email", email);
                login_email.setMaxAge(7 * 24 * 60 * 60); // 一星期內有效
                login_email.setPath("/");
                response.addCookie(login_email);
                
                resp.put("status", "200");
                resp.put("message", "登入成功");
                resp.put("response", query);
                
                jsr.response(resp, response);
        	}else {
        		/** 以字串組出JSON格式之資料 */
    			String resp = "{\"status\": \'400\', \"message\": \'登入失敗，密碼！\', \'response\': \'\'}";
    			/** 透過JsonReader物件回傳到前端（以字串方式） */
    			jsr.response(resp, response);
        	}
        }else {
			/** 以字串組出JSON格式之資料 */
			String resp = "{\"status\": \'400\', \"message\": \'登入失敗，帳號不存在！\', \'response\': \'\'}";
			/** 透過JsonReader物件回傳到前端（以字串方式） */
			jsr.response(resp, response);
        }
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
