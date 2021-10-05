package com.it.web.servlet;

import com.it.domain.Category;
import com.it.domain.Result;
import com.it.service.CategoryService;
import com.it.utils.BeanFactory;
import com.it.web.base.BaseServlet;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    //bean工厂,获取业务层接口实现类
    private CategoryService categoryService = BeanFactory.newInstance(CategoryService.class);

    /**
     * findAll  查询所有的商品分类数据
     * 调用业务层方法,返回集合对象List
     * 封装到结果对象,转成JSON响应
     */
    public void findAll(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
        //封装到结果对象
        Result result = new Result(Result.SUCCESS,"查询成功",categoryList);
        response.getWriter().print(JSONObject.fromObject(result));
    }
}
