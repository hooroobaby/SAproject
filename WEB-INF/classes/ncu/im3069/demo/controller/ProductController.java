package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Product;
import ncu.im3069.demo.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

//@WebServlet("/api/product.do")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductHelper ph =  ProductHelper.getHelper();

    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("id");
        String s = jsr.getParameter("s");

        JSONObject resp = new JSONObject();
//        /** 判斷該字串是否存在，若存在代表要取回購物車內產品之資料，否則代表要取回全部資料庫內產品之資料 */
//        if (!id_list.isEmpty()) {
//          JSONObject query = ph.getByIdList(id_list);
//          resp.put("status", "200");
//          resp.put("message", "所有購物車之商品資料取得成功");
//          resp.put("response", query);
//        }
//        else {
//          JSONObject query = ph.getAll();
//
//          resp.put("status", "200");
//          resp.put("message", "所有商品資料取得成功");
//          resp.put("response", query);
//        }
        /** 判斷該字串是否為detail，是則為商品詳細頁面，否則代表要取回全部資料庫內產品之資料 */
        if (s.equals("detail")) {
          Product p = ph.getById(id);
          JSONObject jso = new JSONObject();
          jso.put("data", p.getData());
          resp.put("status", "200");
          resp.put("message", "商品詳細資料取得成功");
          resp.put("response", jso);
        }
        else {
          JSONObject query = ph.getAll();

          resp.put("status", "200");
          resp.put("message", "所有商品資料取得成功");
          resp.put("response", query);
        }
      
        jsr.response(resp, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
