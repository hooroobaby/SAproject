package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.json.*;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;

/**
 * Servlet implementation class checkLoginController
 */
//@WebServlet("/api/checklogin.do")
public class checkLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private MemberHelper mh =  MemberHelper.getHelper();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String email = jso.getString("email");
        
        JSONObject query = mh.getByEmail(email);
        JSONArray data = query.getJSONArray("data");
        if(!data.isEmpty()) {
        	/* 若帳號存在，比對密碼 */
        	JSONObject resp = new JSONObject();
            JSONObject m = data.getJSONObject(0);
            resp.put("status", "200");
            resp.put("message", "登入認證成功");
            resp.put("response", m);
            
            jsr.response(resp, response);
        }else {
			/** 以字串組出JSON格式之資料 */
			String resp = "{\"status\": \'400\', \'response\': \'\'}";
			/** 透過JsonReader物件回傳到前端（以字串方式） */
			jsr.response(resp, response);
        }
	}

}
