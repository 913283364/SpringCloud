package com.it.dao.daoimpl;

import com.it.dao.CategoryDao;
import com.it.domain.Category;
import com.it.utils.C3P0Utils;
import com.it.utils.UUIDUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

    /**
     * 查询商品分类表所有的分类信息,返回集合List
     */
    public List<Category> findAll() throws SQLException {

            //查询商品分类表所有数据的SQL
            String sql ="select * from category";
            return qr.query(sql,new BeanListHandler<Category>(Category.class));
    }

    /**
     *  addCategory  后台系统添加商品分类名称,传递分类名
     */
    public void addCategory(String cname) throws SQLException {
        String sql = "insert into category values(?,?)";
        qr.update(sql, UUIDUtils.getId(),cname);
    }

    /**
     *  Category  接收业务层传递商品分类的主键
     *  查询数据,返回Category对象
     */
    public Category findById(String cid) throws SQLException {
        String sql = "select * from category where cid=?";
        return qr.query(sql,new BeanHandler<Category>(Category.class),cid);
    }


    /**
     *  updateCategory  后台系统更新分类数据
     *  业务层传递分类主键和分类名称
     */
    public void updateCategory(String cid, String cname) throws SQLException {
        String sql = "update category set cname=? where cid = ?";
        qr.update(sql,cname,cid);
    }


    /**
     *  delCategory  删除指定的分类数据
     *  业务层传递分类主键
     */
    public void delCategory(String cid) throws SQLException {
        String sql = "delete from category where cid=?";
        qr.update(sql,cid);
    }
}
