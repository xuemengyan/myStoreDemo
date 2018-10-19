package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanxuemeng.domain.*;
import com.yanxuemeng.service.OrderService;
import com.yanxuemeng.utils.PaymentUtil;
import com.yanxuemeng.utils.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.ResourceBundle;

@WebServlet(name = "OrderServlet",urlPatterns = "/order")
public class OrderServlet extends BaseServlet {
    private OrderService service = new OrderService();

    protected void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        //判断用户是否登陆
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            info = new ResultInfo(MyFlag.USER_UNLOGIN,null,null); //1
        }else{
            //判断购物车是否为空
            Cart cart = (Cart)request.getSession().getAttribute("cart");
            Collection<CartItem> cartList = cart.getItemsMap();
            if (cartList.size()==0){
                //购物车没有数据
                info = new ResultInfo(MyFlag.CART_EMPTY,null,null);//2
            }else{
                //购物车有数据
                //将购物车数据转为订单数据
                //操作数据库
                //生成订单ID
                String oid = UUIDUtil.getId();
                boolean bl  = service.submitOrder(cart,user,oid);
                if(bl){
                    info = new ResultInfo(MyFlag.SUBMIT_ORDER_SUCCESS,oid,null); //3
                }
            }


        }
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);

    }


    //订单列表分页
    protected void getOrderPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        //获取参数
        String curPageStr = request.getParameter("curPage");
        int curPage = Integer.parseInt(curPageStr);
        //判断用户是否登录
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            //用户没有登录
            info = new ResultInfo(MyFlag.USER_UNLOGIN,null,null);

        }else{
            //用户登录了,调用service层查询数据库
            PageBean<Orders> pageBean =  service.getOrderPage(curPage,user.getUid());
            info = new ResultInfo(MyFlag.SUCCESS,pageBean,null);//4
        }
        //响应回浏览器
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        System.out.println(jsonStr);
        response.getWriter().write(jsonStr);


    }
    //获取订单详情
    protected void getOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           ResultInfo info = null;
           try {
               String oid = request.getParameter("oid");
               //调用service层
               Orders order = service.getOrderDetail(oid);
               info = new ResultInfo(MyFlag.SUCCESS,order,null);//4

           }catch (Exception e){
               e.printStackTrace();
               info = new ResultInfo(MyFlag.FAILED,null,"服务器正在维护");//4
           }
            //响应回浏览器
            String jsonStr = new ObjectMapper().writeValueAsString(info);
            response.getWriter().write(jsonStr);


    }
    //订单支付
    protected void topay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        //更新数据库中订单的收货人地址,电话，名字
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String oid = request.getParameter("oid");
        boolean bl = service.updateInfo(address,name,telephone,oid);



        //--------------------------------------------------------------------------------
        //拼接跳转地址了
        // 组织发送支付公司需要哪些数据
        String pd_FrpId = request.getParameter("pd_FrpId"); //哪一家银行
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = oid;
        String p3_Amt = "0.01";//order.getTotal();//真实开发order.getTotal()

        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
        // 第三方支付可以访问网址
        String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        // 加密hmac 需要密钥
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        //发送给第三方
        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);

        info = new ResultInfo(MyFlag.SUCCESS, sb.toString(),null);
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(jsonStr);


    }
    protected void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //验证数据的合法性
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");
        // 身份校验 --- 判断是不是支付公司通知你
        String hmac = request.getParameter("hmac");
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                "keyValue");

        // 自己对上面数据进行加密 --- 比较支付公司发过来hamc
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);

        if(isValid){

            //判断 那种方式
            if(r9_BType.equals("1")){
                //浏览器重定向
                ////更新订单的状态
                boolean bl = service.updateState(r6_Order);

                //在页面给用户提示
                request.getRequestDispatcher("/success.jsp").forward(request, response);

            }else{//2
                //服务器点对点
                ////更新订单的状态
                boolean bl = service.updateState(r6_Order);
                //回写success的字符串
                response.getWriter().print("success");
            }
        }else{
            response.getWriter().print("非法访问!!!");
        }

    }



    }
