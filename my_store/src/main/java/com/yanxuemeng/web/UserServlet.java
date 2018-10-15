package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yanxuemeng.domain.ResultInfo;
import com.yanxuemeng.domain.User;
import com.yanxuemeng.exception.PasswordErrorException;
import com.yanxuemeng.exception.UsernameExistsException;
import com.yanxuemeng.exception.UsernameNotExistsException;
import com.yanxuemeng.service.userService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "UserServlet",urlPatterns = "/user")
public class UserServlet extends BaseServlet {
    private userService service = new userService();


    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //生成返回信息对象
        ResultInfo info = null;
        //验证码校验
        //从session中获取验证码
        String webCheckCode = (String)request.getSession().getAttribute("checkCode");
        //获取表单中的验证码
        String chenkCode = request.getParameter("check");
        //比较
        if (chenkCode.equalsIgnoreCase(webCheckCode)){
            //获取表单数据
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            //调用service层进行验证登录
            try {
                User user = service.login(username,password);
                //将返回的user存到session中用作登录状态
                if (user !=null){
                    request.getSession().setAttribute("user",user);
                    info = new ResultInfo(ResultInfo.SUCCESS,null,null);
                }
            } catch (UsernameNotExistsException e) {
                e.printStackTrace();
                info = new ResultInfo(ResultInfo.FAILED,null,e.getMessage());
            }catch (PasswordErrorException e) {
                e.printStackTrace();
                info = new ResultInfo(ResultInfo.FAILED,null,e.getMessage());
            }catch (Exception e) {
                e.printStackTrace();
                info = new ResultInfo(ResultInfo.FAILED,null,"服务器出错");
            }

        }else{
            info = new ResultInfo(ResultInfo.FAILED,null,"验证码错误");
        }
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);

    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //生成返回信息对象
        ResultInfo info = null;
        //验证码校验
        //从session中获取验证码
        String webCheckCode = (String)request.getSession().getAttribute("checkCode");
        //获取表单中的验证码
        String chenkCode = request.getParameter("check");
        //比较
        if (chenkCode.equalsIgnoreCase(webCheckCode)){
            //获取表单数据
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                //将表单数据封装到user对象中
                BeanUtils.populate(user,map);
                //调用service层方法

                boolean b1 =  service.register(user);
                if (b1){
                    info = new ResultInfo(ResultInfo.SUCCESS,null,null);

                }

            } catch (UsernameExistsException e) {
                e.printStackTrace();
                info   =   new ResultInfo(ResultInfo.FAILED,null,e.getMessage());

            } catch (Exception e) {
                e.printStackTrace();
                info   =   new ResultInfo(ResultInfo.FAILED,null,"服务器正在维护");
            }
        }else {
            info   =   new ResultInfo(ResultInfo.FAILED,null,"验证码错误");
        }

        //将resultinfo转为json串
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);
    }
}
