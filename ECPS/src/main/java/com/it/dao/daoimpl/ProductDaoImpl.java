package com.it.dao.daoimpl;

import com.it.dao.ProductDao;
import com.it.domain.Constr;
import com.it.domain.Product;
import com.it.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

    /**
     * findIsHot 查询所有热门商品,is_hot=1 热门,pflag=0 商品上架
     * 把热门和上架的参数做成变量便于修改
     */
    public List<Product> findIsHot() throws SQLException {
        //拼写查询热门SQL语句
        String sql = "select * from product where is_hot =? and pflag=? limit ?,?";
        return qr.query(sql,new BeanListHandler<Product>(Product.class), Constr.ISHOT,Constr.PFLAG,0,9);
    }


    /**
     * findNew 查询最新商品,按照发布日期,进行倒叙 desc
     */
    public List<Product> findNew() throws SQLException {
        //拼写查询最新商品SQL语句
        String sql = "select * from product where pflag=? order by pdate desc limit ?,?";
        return qr.query(sql,new BeanListHandler<Product>(Product.class), Constr.PFLAG,0,9);
    }


    /**
     * findById  商品图片链接传递主键ID,查询商品的详细信息,返回JavaBean
     */
    public Product findById(String pid) throws SQLException {
        //拼写商品主键ID查询SQL语句
        String sql = "select * from product where pid= ?";
        return qr.query(sql,new BeanHandler<Product>(Product.class),pid);
    }


    /**
     * getTotalCount 根据商品分类查询的商品总数量,传递商品分类外键
     */
    public long getTotalCount(String cid) throws SQLException {
        //拼写分类查询的SQL
        String sql = "select count(*) from product where cid=?";
        //平均数,总数
        return qr.query(sql,new ScalarHandler<Long>(),cid);
    }

    /**
     *  findByPage 根据商品分类查询商品分页数据,
     *  传递当前页,每页条数,商品分类外键
     */
    public List<Product> findByPage(int currentPage, int pageSize, String cid) throws SQLException {
        //拼写分页查询SQL语句
         String sql = "select * from product where cid=? limit ?,?";
         return qr.query(sql,new BeanListHandler<Product>(Product.class),cid,(currentPage-1)*pageSize,pageSize);
    }

    /**
     *  getProductCount 传递商品分类表外键,查询是否有数据
     */
    public long getProductCount(String cid) throws SQLException {
        String sql = "select count(*) from product where cid=?";
        return qr.query(sql,new ScalarHandler<Long>(),cid);
    }


    /**
     * findByPage  查询所有商品数据,不需要查询商品分类cid(重载)
     * 传递当前页,每页条数
     */
    public List<Product> findByPage(int currentPage, int pageSize) throws SQLException {
        //拼写分页查询SQL语句
        String sql = "select * from product  limit ?,?";
        return qr.query(sql,new BeanListHandler<Product>(Product.class), (currentPage-1)*pageSize,pageSize  );

    }


    /**
     * getTotalCount   查询所有商品的总数量(重载)
     */
    public long getTotalCount() throws SQLException {
        //拼写分类查询的SQL
        String sql = "select count(*) from product";
        //平均数,总数
        return qr.query(sql,new ScalarHandler<Long>());
    }

    /**
     *  addProduct  后台系统添加商品数据
     *  取出JavaBean对象中的数据
     *  写入数据表product
     *  private String pid;//id
     private String pname;//名字
     private Double market_price;//市场价
     private Double shop_price;//商城价
     private String pimage;//图片
     private String pdate;//日期
     private Integer is_hot;  //是否热门  1:热门    0:不热门
     private String pdesc;//描述
     private Integer pflag;	//是否下架    1:下架	0:未下架
     private String cid;
     */
    public void addProduct(Product p) throws SQLException {
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),
                p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),
                p.getPflag(),p.getCid()
        };
        qr.update(sql,params);
    }

}
