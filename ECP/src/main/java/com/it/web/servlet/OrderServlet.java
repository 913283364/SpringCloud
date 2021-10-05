package com.it.web.servlet;

import com.it.domain.*;
import com.it.service.OrdersService;
import com.it.utils.BeanFactory;
import com.it.utils.PaymentUtil;
import com.it.utils.UUIDUtils;
import com.it.web.base.BaseServlet;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet {

    //bean工厂,获取orders接口实现类
    private OrdersService ordersService = BeanFactory.newInstance(OrdersService.class);

    /**
     *  submitOrder  添加订单的方法
     *
     *  实现步骤:
     *    取出session中的用户对象
     *      判断是否登录了,没有登录,提示登录
     *    取出session中的购物车对象
     *      判断是否有商品
     *
     *    购物车中的数据,转化成订单数据
     *    调用业务层方法写入
     */
    public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //取出session中的用户对象,因为登录的时候存储过session
        User user = (User)request.getSession().getAttribute("user");
        //判断用户对象
        if(user == null){
            //没有登录  则返回消息到前端页面
            Result re = new Result(Result.NOLOGIN,"尚未登录,请登录！");
            response.getWriter().print(JSONObject.fromObject(re));
            return;
        }
        //取出session中的购物车对象
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        //判断购物车中是否有商品
        //购物车中的购物项存储在Map集合,只需要判断Map的长度
        if(cart.getItemMap().size()==0){
            //购物车是空的
            Result re = new Result(Result.FAILS,"购物车没有商品,请添加商品到购物车！");
            response.getWriter().print(JSONObject.fromObject(re));
            return;
        }

        //数据转化,如果购物车有商品就把购物车数据,生成订单数据
        /*
         * 订单Orders需要的数据：
         *   主键使用uuid, 下单时间new Date()
         *   总金额,就是购物车总金额
         *   订单状态,没付钱
         *   用户主键 user
         */
        Orders orders = new Orders();
        String uuid = UUIDUtils.getId();
        //订单主键
        orders.setOid(uuid);
        //设置总金额
        orders.setTotal(cart.getTotal());
        //设置下单时间,并转化时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orders.setOrdertime(sdf.format( new Date()));
        //订单状态,未付款
        orders.setState(Constr.ORDER_WEIFUKUAN);
        //设置用户主键
        orders.setUid(user.getUid());


        //数据转化,把购物项CartItem转换成订单项OrderItem(中间表)
        /*
         * OrderItem订单项需要的数据:
         *   购买数量 购物项中数量
         *   商品小计 购物项中小计
         *   商品主键 CartItem.product.pid
         *   订单主键 uuid
         */

        //需要使用Collection集合存放Map的数据,容易遍历
        Collection<CartItem> cartItems = cart.getItemMap();
        //创建集合,保存订单项对象
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();

        //这里遍历一个cartItem(购物项)就会new一个orderItem()订单对象,
        //数据遍历一次就装入一次新的对象，最后数据只有一个,
        //所以用List集合把orderItem订单对象保存起来就不会丢失.

        //因为是多个购物项需要遍历集合,才能取出购物项对象
        for(CartItem cartItem : cartItems){
            //创建订单项对象
            OrderItem orderItem = new OrderItem();
            //设置订单主键
            orderItem.setOid(uuid);
            //设置商品主键
            orderItem.setPid( cartItem.getProduct().getPid() );
            //设置数量
            orderItem.setCount( cartItem.getCount() );
            //设置小计
            orderItem.setSubTotal(cartItem.getSubTotal());
            orderItemList.add(orderItem);
        }

        //调用业务层方法添加订单
        //ordersService.submitOrder(orders);
        //调用业务层方法添加订单项
        //ordersService.submitOrderItem(orderItemList);

        //调用业务层方法添加订单和订单项
        ordersService.addOrders(orders,orderItemList);
        //把生成的订单号,响应会浏览器
        Result re = new Result(Result.SUCCESS,"订单创建成功",uuid);
        response.getWriter().print(JSONObject.fromObject(re));
    }


    /**
     * 实现(查询)用户订单带分页的功能
     * 获取当前页数
     * session取出登录的User对象
     *
     *  判断: user对象有没有登录
     *   没有登录
     *       则响应message
     *
     *   有登录
     *      调用业务层,传递当前页,每页个数,用户主键
     *      获取返回的PageBean对象,转成JSON响应
     */
    public void UserOrderWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //session取出登录的User对象
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            //没有登录
            Result re = new Result(Result.NOLOGIN,"尚未登录");
            response.getWriter().print(JSONObject.fromObject(re));
            return;
        }
        // 有登录,则获取当前页数
        int  currentPage = Integer.parseInt(request.getParameter("currentPage"));
        // 定义每页显示的订单条数
        int pageSize = 3;
        // 调用业务层方法 ,获取PageBean对象,接收currentPage,pageSize,user.getUid
        PageBean<Orders> pb = ordersService.UserOrderWithPage(currentPage, pageSize, user.getUid());
        // 把pb转成JSON响应
        Result re = new Result(Result.SUCCESS,"查询成功",pb);
        response.getWriter().print(JSONObject.fromObject(re));
    }


    /**
     * 获取订单编号
     * 调用业务层传递订单号
     * 获取返回的订单对象
     * 转成JSON响应
     */
    /*info  实现订单详情*/
    public void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //是否进行登录验证
        //session取出登录的User对象
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            //没有登录
            Result re = new Result(Result.NOLOGIN,"尚未登录");
            response.getWriter().print(JSONObject.fromObject(re));
            return;
        }
        // 获取订单编号
        String oid = request.getParameter("oid");
        // 调用业务层info方法查询订单详情
        Orders orders = ordersService.info(oid);
        Result re = new Result(Result.SUCCESS,"查询成功",orders);
        response.getWriter().print(JSONObject.fromObject(re));
    }



    //支付,请求方法
    public void pay(HttpServletRequest request,HttpServletResponse response) throws Exception {
        // 接受参数
        String address = request.getParameter("address");
        String name = request.getParameter("name");//收货人
        String telephone = request.getParameter("telephone");
        String oid = request.getParameter("oid");

        // 调用业务层方法,更新订单信息
        ordersService.updateOrder(name, address, telephone, oid);


      // 组织发送支付公司需要哪些数据
        String pd_FrpId = request.getParameter("pd_FrpId");
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = oid;
        String p3_Amt = "0.01";
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
        // 传入回调地址
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        // 发送给第三方
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
        // Ajax跨域请求不能使用重定向,不能使用转发
        // respone.sendRedirect(sb.toString());

        //把sb的数据封装到JSON响应会页面
        Result re = new Result(Result.SUCCESS,"",sb.toString());
        response.getWriter().print(JSONObject.fromObject(re));

        // return null;
    }


    /*callBack 获取易宝数据,并做比较.数据是否有效,有效则支付成功,支付后的回调方法*/
    public String callback(HttpServletRequest request,HttpServletResponse response) throws Exception{
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

        //判断数据是否有效
        if (isValid) {
            // 响应数据有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向
                System.out.println("111");
                request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");

            } else if (r9_BType.equals("2")) {
                // 修改订单状态:
                // 服务器点对点 --- 支付公司通知你
                System.out.println("付款成功！222");
                // 修改订单状态 为已付款
                // 回复支付公司success,如果不回复,支付公司会一直通知
                response.getWriter().print("success");
            }
            //订单状态,更新订单状态为已经付款
            ordersService.updateOrderState(r6_Order);

        } else {
            // 数据无效
            System.out.println("数据被篡改！");
        }
        return "/jsp/msg.jsp";
    }
}
