package com.it.service.serviceimpl;
import com.it.dao.ProductDao;
import com.it.domain.PageBean;
import com.it.domain.Product;
import com.it.service.ProductService;
import com.it.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;
public class ProductServiceImpl implements ProductService {
    //bean工厂,获取dao层接口实现类对象
    private ProductDao productDao = BeanFactory.newInstance(ProductDao.class);

    /**
     *  查询热门商品,调用dao层查询最热商品方法,把结果返回给集合
     */
    public List<Product> findIsHot() {
        List<Product> isHotList = null;
        try {
            isHotList = productDao.findIsHot();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isHotList;
    }

    /**
     *  查询最新商品,调用dao层查询最新商品方法,把结果返回给集合
     */
    public List<Product> findNew() {
        List<Product> newList = null;
        try {
            newList = productDao.findNew();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newList;
    }



    /**
     *  根据商品图片链接主键ID查询商品详情
     *  主键Web层传递,调用dao层,传递主键,获取JavaBean
     */
    public Product findById(String pid) {
        Product p = null;
        try {
            p  = productDao.findById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  p;
    }


    /**
     * PageBean<Product> 查询商品的分页
     * 参数,当前页,每页显示条数,分类外键
     * 封装好PageBean对象,返回web层
     */
    public PageBean<Product> findByPage(int currentPage, int pageSize, String cid) {
        PageBean<Product> pb = new PageBean<Product>();
        try {
              // 设置当前页数
              pb.setCurrentPage(currentPage);
              // 设置每页显示条数
              pb.setPageSize(pageSize);
              // 设置显示商品数据,调用dao层方法从数据库中查询
              // 传递:当前页,每页显示条数,分类外键
              List<Product> productList = productDao.findByPage(currentPage, pageSize, cid);
              // 把商品数据放入PageBean
              pb.setList(productList);

              // 设置商品总数,调用dao层方法传递分类外键
              long totalCount = productDao.getTotalCount(cid);
              pb.setTotalCount(totalCount);

              // 设置总页数(商品总数*1.0 / 每页显示条数)
              // Math.ceil:返回大于或者等于指定表达式的最小整数
              int totalPage = (int)Math.ceil(totalCount*1.0/pageSize);
              pb.setTotalPage(totalPage);

           }catch (Exception ex){ex.printStackTrace();}
            return pb;
    }

    /**
     *  PageBean<Product>  查询所有商品的分页,不需查询商品分类(重载)
     *  方法接收：当前页,每页显示条数,总页数
     *  从数据表获取数据,封装PageBean对象
     */
    public PageBean<Product> findByPage(int currentPage, int pageSize) {
        PageBean<Product> pb = new PageBean<Product>();
        try {
            //设置当前页
            pb.setCurrentPage(currentPage);
            //设置每页显示条数
            pb.setPageSize(pageSize);
            //查询所有的商品数据,分页并存入商品集合
            List<Product> productList = productDao.findByPage(currentPage, pageSize);
            //把商品集合放入pb,显示的商品信息
            pb.setList(productList);
            //查询所有的商品数据总数量(总条数)
            long totalCount = productDao.getTotalCount();
            //设置商品总数量
            pb.setTotalCount(totalCount);
            //设置总页数
            pb.setTotalPage( (int)(Math.ceil(totalCount*1.0/pageSize))  );
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return pb;
    }

    public void addProduct(Product product) {
        try {
            productDao.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
