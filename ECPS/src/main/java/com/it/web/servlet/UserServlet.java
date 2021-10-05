package com.it.web.servlet;

import com.it.domain.Result;
import com.it.domain.User;
import com.it.service.UserService;
import com.it.utils.BeanFactory;
import com.it.utils.UUIDUtils;
import com.it.web.base.BaseServlet;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {

    //bean工厂,获取业务层接口,通过反射创建业务层的实现类对象
    private UserService userService = BeanFactory.newInstance(UserService.class);


    /**
     *  register  处理客户端的注册功能
     *  获取表单数据 Map
     *  数据封装到JavaBean对象
     *  调用业务层传递JavaBean
     *  响应回客户端
     */
    public void register(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {

        // 1.使用getParameterMap接受客户端发送的Ajax请求处理注册功能
        Map<String,String[]> map = request.getParameterMap();
        User user = new User();

        // 1.1使用BeanUtils自动填充数据
        try {
            BeanUtils.populate(user, map);
        }catch (Exception ex){ex.printStackTrace();}
        System.out.print("uesr");

        // 2.部分数据要通过程序来设置的:State,Uid,生成激活码
        user.setState(1);  //设置激活码,就是1
        user.setUid(UUIDUtils.getId()); //设置主键
        userService.register(user);

        // 3.创建结果对象,封装状态码(定义变量)和对应信息
        Result result = new Result(Result.SUCCESS,"注册成功");

        // 4.对象转JSON,print自动调用toString()
        //JSONObject.fromObject(result).toString();
        response.getWriter().print(JSONObject.fromObject(result));
    }


    /**
     * login 处理客户端登录
     * 获取用户名和密码
     * 传递业务层,接收返回值User
     * 判断User对象的值
     * user!=null 不是空,登录成功
     *   登录成功,User对象存储session域
     *   创建Cookie,保存用户名
     *   写回客户端浏览器
     *   进行响应JSON
     *
     * user==null 是空,登录失败
     *   进行响应json
     */
    public void login(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用业务层方法,传递用户名密码返回给User类
        User user = userService.login(username, password);
        //判断user对象
        if (user!=null){
            //数据不为空代表登录成功,把user对象存储到Session
            request.getSession().setAttribute("user",user);
            //创建cookie对象,保存用户名
            Cookie cookie = new Cookie("username",username);
            //设置cookie离开10分钟用户名无效，要重新登录
            cookie.setMaxAge(60*10);
            //设置携带路径
            cookie.setPath(request.getContextPath());
            //设置cookie携带的域名
            cookie.setDomain("ecps.com");
            response.addCookie(cookie);
            //设置响应结果集对象
            Result result = new Result(Result.SUCCESS,"登录成功");
            response.getWriter().print( JSONObject.fromObject(result));
        }else {
            Result result = new Result(Result.FAILS,"登录失败,请检查用户名或密码");
            response.getWriter().print( JSONObject.fromObject(result));
        }
    }

    /**
     * loginOut 客户端的退出登录
     * 销毁session对象
     * 销毁客户端cookie
     */
    public void loginOut(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        //销毁session对象
        request.getSession().invalidate();
        //销毁客户端cookie
        //创建cookie对象,保存用户名
        Cookie cookie = new Cookie("username",null);
        //设置cookie参数
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        //设置cookie携带的域名
        cookie.setDomain("ecps.com");
        response.addCookie(cookie);
        Result result = new Result(Result.SUCCESS,"退出成功");
        response.getWriter().print(JSONObject.fromObject(result));
    }
}
