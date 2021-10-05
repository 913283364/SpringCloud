package com.it.web.servlet;

import com.it.domain.Cart;
import com.it.domain.CartItem;
import com.it.domain.Product;
import com.it.domain.Result;
import com.it.service.ProductService;
import com.it.utils.BeanFactory;
import com.it.web.base.BaseServlet;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {

    //bean工厂,获取商品业务层接口实现类
    private ProductService productService = BeanFactory.newInstance(ProductService.class);


    /**
     *  addCart  添加购物车方法
     *  获取客户端提交数据
     *  商品主键和购买的数量
     *  商品主键,查询商品的详情
     *  商品详情,购买数量,组成购物项对象
     *  调用购物车对象的方法,添加到购物车
     */

    public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //商品主键和购买的数量
        String pid = request.getParameter("pid");
        int count = Integer.parseInt( request.getParameter("count") );
        //调用Product业务层,传递商品主键,获取商品详情
        Product product = productService.findById(pid);
        //商品购买数量,存储到购物项对象
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);  //把商品存进去
        cartItem.setCount(count);
        //调用购物车对象的方法addCart
        Cart cart = getCart(request);
        cart.addCart(cartItem);
        Result re = new Result(Result.SUCCESS,"购物车添加成功",cart);
        response.getWriter().print(JSONObject.fromObject(re));
    }

    /**
     * 购物车对象存储到session域对象
     * 取出购物车,没有购物车,新建
     * 1.第一次调用getCart方法就是所有商品都是新的,从Session中拿一个cart发现是null
     * 2.cart=null就创建一个cart对象,然后存入Session这样cart就有值了
     * 3.第二次调用getCart,cart有值直接返回cart,上面的addCart把商品放入有值的Cart
     */
    /*getCart  获取购物车对象*/
    private Cart getCart(HttpServletRequest request){
        //session域中,取出购物车对象(必须强制,因为取出的都是obj)
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart==null){
            cart = new Cart();
            //购物车对象存储session
            request.getSession().setAttribute("cart",cart);
        }
        return  cart;
    }


    /**
     *  session域中取出购物车对象
     *  转成JSON响应
     */
    /*showCart  展示购物车的方法*/
    public void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session域中获取购物车对象
        Cart cart = getCart(request);
        Result re = new Result(Result.SUCCESS,"购物车查询成功",cart);
        response.getWriter().print(JSONObject.fromObject(re));
    }


    /**
     *  接收请求传递的商品主键
     *  获取出购物车对象session
     *  调用购物车对象的方法 removeItem(主键)
     */
    /* removeItem  移除购物项的方法*/
    public void removeItem (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收请求传递的商品主键
        String pid = request.getParameter("pid");
        //获取出购物车对象session
        Cart cart = getCart(request);
        cart.removeItem(pid);
        Result re = new Result(Result.SUCCESS,"移除成功");
        response.getWriter().print(JSONObject.fromObject(re));
    }


    /**
     * session中取出购物车对象
     * 调用购物车对象方法clearItem清空
     */
    /*clearItem 清空购物车的方法*/
    public void clearItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCart(request);
        //到cart类从调用清空购物车方法
        cart.clearItem();
        //把清除成功的消息返回到前端页面
        Result re = new Result(Result.SUCCESS,"清空购物车成功");
        response.getWriter().print(JSONObject.fromObject(re));
    }
}
