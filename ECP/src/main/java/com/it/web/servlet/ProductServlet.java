package com.it.web.servlet;

import com.it.domain.PageBean;
import com.it.domain.Product;
import com.it.domain.Result;
import com.it.service.ProductService;
import com.it.utils.BeanFactory;
import com.it.web.base.BaseServlet;
import com.sun.xml.internal.ws.handler.ServerSOAPHandlerTube;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/ProductServlet")
public class ProductServlet extends BaseServlet {

    //bean工厂,获取业务层接口实现类对象
    ProductService productService = BeanFactory.newInstance(ProductService.class);


    /**
     * 调用最热和最新的业务层方法,分别获取查询到的最新和最热商品
     */

    /*findHotAndNews  查询最热和最新商品*/
    public void  findHotAndNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //调用业务层,获取最热门商品
        List<Product> isHotList = productService.findIsHot();
        //调用业务层,获取最新商品
        List<Product> newList = productService.findNew();

        //把最新最热的的list集合放入Map集合(集合嵌套),区分最新和最热方法
        Map<String,List<Product>> map = new HashMap<String, List<Product>>();
        //创建一个键来获取它对应的值
        map.put("news",newList);
        map.put("ishot",isHotList);
        Result re = new Result(Result.SUCCESS,"查询成功",map);
        response.getWriter().print(JSONObject.fromObject(re));
    }


    /**
     * findById  查询商品详情
     * 接收页面请求发送商品主键,专递业务层,获取返回值JavaBean
     * 封装到Result结果对象,转成JSON响应
     */
    public void  findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String pid = request.getParameter("pid");
        Product p =  productService.findById(pid);
        Result re = new Result(Result.SUCCESS,"查询成功",p);
        response.getWriter().print(JSONObject.fromObject(re));
        System.out.println(pid);
    }


    /**
     * findByPage  分页查询商品数据
     * 页面获取分类的主键
     * 获取当前页数
     * 调用业务层方法,返回PageBean对象
     * 封装到结果对象Result,转成JSON响应
     */
   public void  findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 获取当前页
        String currentPage = request.getParameter("currentPage");
        // 如果当前页为null和双引号的null(page函数问题),就设置当前页为1
        if(currentPage==null || "null".equals(currentPage))
            currentPage="1";

        // 定义变量保存每页个数
        int pageSize = 12;
        // 获取分类外键
        String cid = request.getParameter("cid");
        // 调用业务层方法,获取PageBean对象
        PageBean<Product> pb = productService.findByPage(Integer.parseInt(currentPage), pageSize, cid);
        // 把PageBean对象封装到JSON
        Result re = new Result(Result.SUCCESS,"查询成功",pb);
        response.getWriter().print(JSONObject.fromObject(re));
    }
}
