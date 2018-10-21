package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanxuemeng.domain.MyFlag;
import com.yanxuemeng.domain.Product;
import com.yanxuemeng.domain.ResultInfo;
import com.yanxuemeng.service.UploadService;
import com.yanxuemeng.utils.UUIDUtil;
import com.yanxuemeng.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "uploadServlet",urlPatterns = "/upload")
public class uploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = myGetParameterMap(request);

        //将map数据封装给product对象
        Product product = new Product();
        try {
            BeanUtils.populate(product,map);
            //调用Service层将数据保存数据库
            UploadService service = new UploadService();
            boolean b = service.addProduct(product);
            if (b){
                ResultInfo info = new ResultInfo(MyFlag.SUCCESS,null,null);
                String jsonStr = new ObjectMapper().writeValueAsString(info);
                response.getWriter().write(jsonStr);
            }

        }catch (Exception E){
            E.printStackTrace();
        }



    }


    public static Map<String, String[]> myGetParameterMap(HttpServletRequest request){
        HashMap<String, String[]> map = new HashMap<>();
        //获取所有表单项数据
        try {
            List<FileItem> fileItem = UploadUtils.getFileItem(request);
            //遍历集合,判断表单项的分类
            for (FileItem item : fileItem) {
                if (item.isFormField()){
                    //普通表单项
                    //获取表单的名字
                    String name = item.getFieldName();
                    //获取表单值
                    String value = item.getString("UTF-8");
                    map.put(name,new String[]{value});
                }else {
                    //附件项
                    //获取上传文件的名字
                    String filename = item.getName();
                    //将原始文件名转为UUID风格
                    filename = UploadUtils.getUUIDName(filename);
                    //实现文件的上传和复制
                    //获取输入流
                    InputStream is = item.getInputStream();
                    //获取输出流
                    String dir = ResourceBundle.getBundle("upload").getString("uploadDir");
                    FileOutputStream fos = new FileOutputStream(dir + filename);
                    //文件复制
                    IOUtils.copy(is,fos);
                    //释放资源
                    fos.close();
                    is.close();
                    item.delete();
                    //给map集合封装图片路径
                    String pimage = "resources\\products\\1\\"+filename;
                    map.put("pimage",new String[]{pimage});



                }
            }
            //封装其他数据
            map.put("pid", new String[]{UUIDUtil.getId()}); //pid
            map.put("pdate", new String[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date())}); //pdate
            map.put("pflag", new String[]{"0"});  //pflag

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
