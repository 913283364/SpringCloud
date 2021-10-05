package com.it.web.servlet;

import com.it.domain.Category;
import com.it.domain.PageBean;
import com.it.domain.Product;
import com.it.domain.Result;
import com.it.exception.CanNotDeleteException;
import com.it.service.CategoryService;
import com.it.service.ProductService;
import com.it.utils.BeanFactory;
import com.it.web.base.BaseServlet;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/AdminServlet")
public class AdminServlet extends BaseServlet {

    //bean工厂,获取业务层CategoryService接口实现类
    private CategoryService categoryService = BeanFactory.newInstance(CategoryService.class);
    //bean工厂,获取业务层ProductService接口实现类
    private ProductService productService = BeanFactory.newInstance(ProductService.class);


    /**
     * findAll  后台系统查询所有的商品分类数据
     * 直接调用Category的业务层查询,返回集合,转成JSON响应
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
        Result re = new Result(Result.SUCCESS, "查询成功", categoryList);
        response.getWriter().print(JSONObject.fromObject(re));
    }

    /**
     * addCategory  后台添加商品分类名称功能
     * 获取新分类名称
     * 调用业务层传递名称
     * 响应
     */
    public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("cname");
        categoryService.addCategory(cname);
        Result re = new Result(Result.SUCCESS, "添加成功");
        response.getWriter().print(JSONObject.fromObject(re));
    }

    /**
     * findById  根据后台商品分类主键查询分类数据
     * 获取页面提交的分类信息主键
     * 传递业务层,返回结果是一个分类信息的对象  Category对象
     * 响应,Category对象转成JSON
     */
    public void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        Category category = categoryService.findById(cid);
        Result re = new Result(Result.SUCCESS, "查询成功", category);
        response.getWriter().print(JSONObject.fromObject(re));
    }


    /**
     * updateCategory  后台系统更新商品分类名称方法
     * 获取页面提交的分类名称和主键
     * 传递到业务层
     * 响应
     */
    public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        categoryService.updateCategory(cid, cname);
        Result re = new Result(Result.SUCCESS, "更新成功");
        response.getWriter().print(JSONObject.fromObject(re));
    }


    /**
     * delCategory  商城删除分类数据
     * 获取页面提交的分类主键
     * 调用业务层删除
     * 响应
     */
    public void delCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //取出页面提交的分类主键
        String cid = request.getParameter("cid");
        //调用业务层方法,如果没有异常,删除了
        //如果业务层有异常,不能删除
        try {
            categoryService.delCategory(cid);
            Result re = new Result(Result.SUCCESS, "删除成功");
            response.getWriter().print(JSONObject.fromObject(re));
        } catch (CanNotDeleteException ex) {
            System.out.println(ex);
            Result re = new Result(Result.FAILS, "有数据不能删除");
            response.getWriter().print(JSONObject.fromObject(re));
        }
    }


    /**
     * findByPage  后台系统处理所有的商品分页查询功能
     * 获取当前页数
     * 调用业务层传递数据,返回PageBean对象
     * 转成JSON响应
     */
    public void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前页数(request.getParameter返回的是String所以要转成Integer)
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        //定义每页的显示条数
        int pageSize = 10;
        //调用业务层传递数据,返回PageBean对象
        PageBean<Product> pb = productService.findByPage(currentPage, pageSize);
        Result re = new Result(Result.SUCCESS,"分页查询成功",pb);
        response.getWriter().print(JSONObject.fromObject(re));
    }

}