package com.yanxuemeng.web;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


//生成4位随机验证码的servlet

@WebServlet("/checkCode")
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1:生成一个4位的字符串
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  //36    0 - 35
         //1.1 创建Random对象
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        //1.2 产生4个随机数,作为字符串的字符索引
        for (int i=0; i<4; i++){
            int index =  rd.nextInt(str.length());
            //1.3 根据索引，获取字符
            char ch = str.charAt(index);
             sb.append(ch);
        }
        String checkCodeStr = sb.toString();
        //将随机生成的字符串存入session中
        request.getSession().setAttribute("checkCode", checkCodeStr);

        //2:将生成的字符串画在画布上，生成一张图片（工具类）
        BufferedImage image = getImage(checkCodeStr);

        //3:将生成的图片响应给页面

        /*
           参1：表示生成的图片
           参2：表示图片的类型
           参3：表示响应流
         */
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

    private BufferedImage getImage(String checkCodeStr){
        Random rd = new Random();

        //创建一个画布
        BufferedImage image = new BufferedImage(75, 28, BufferedImage.TYPE_INT_RGB);

        //画笔
        Graphics g = image.getGraphics();
        //给画笔设置颜色
        g.setColor(new Color(240,240,240));  //#00000   FFFFFF
        //设置验证码的 背景色
        g.fillRect(0, 0, 75, 28);

        g.setFont(new Font("宋体",Font.BOLD,16));

        g.setColor(new Color(0,0,0));  //#00000   FFFFFF
//        g.drawString(checkCodeStr, 20, 20);
        for (int i = 0; i <4 ; i++) {
            g.setColor(new Color(rd.nextInt(120),rd.nextInt(120),rd.nextInt(120)));
            g.drawString(checkCodeStr.charAt(i)+"", 16*i + rd.nextInt(16), 15 + rd.nextInt(10) );
            if(i % 2 == 0) {
                g.setColor(new Color(rd.nextInt(120), rd.nextInt(120), rd.nextInt(120)));
                g.drawLine(rd.nextInt(75), rd.nextInt(28), rd.nextInt(75), rd.nextInt(28));
            }
        }
        return image;
    }
}
