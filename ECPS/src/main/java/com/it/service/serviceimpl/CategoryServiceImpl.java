package com.it.service.serviceimpl;

import com.it.dao.CategoryDao;
import com.it.dao.ProductDao;
import com.it.domain.Category;
import com.it.exception.CanNotDeleteException;
import com.it.service.CategoryService;
import com.it.utils.BeanFactory;
import com.it.utils.JedisUtils;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    //bean工厂,获取dao层接口实现类
    private CategoryDao categoryDao = BeanFactory.newInstance(CategoryDao.class);
    //bean工厂,获取dao层接ProductDao口实现类
    private ProductDao productDao = BeanFactory.newInstance(ProductDao.class);

    /**
     *  调用dao层,查询所有的商品分类数据,返回集合List
     */
    /*public List<Category> findAll() {
        List<Category> categoryList = null;
        try {
            categoryList = categoryDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }*/

    /**
     *  从redis数据库中查询导航条商品分类
     *  不存在数据
     *    查询MySQL数据,取出category表数据
     *    数据转成JSON字符串,存储redis
     *
     *  数据存储在
     *    redis数据库存储的是字符串
     *    字符串转成集合返回
     */
    public List<Category> findAll(){
        List<Category> categoryList = null;
        //获取Redis数据库连接对象
        Jedis jedis = JedisUtils.getJedis();
        //获取字符串
        String category =  jedis.get("category");
        try {
            //判断是否有数据
            //redis内存没有数据
            if (category == null) {
                // 查询MySQL数据库,取出category表数据放入集合
                categoryList = categoryDao.findAll();
                // 把集合中的数据,转成JSON存到redis
                // JSONArray.fromObject(categoryList).toString();//只存到JSON
                jedis.set("category", JSONArray.fromObject(categoryList).toString());
            } else {
                //redis内存有数据
                //category转成集合返回
                /**
                 * toList:把JSON格式字符串转成集合List
                 * 参数1:把集合数据放入JSONArray对,
                 * 参数2:被转换后的集合(category)的泛型的class对象
                 */
                // JSONArray.fromObject(category);
                categoryList = JSONArray.toList( JSONArray.fromObject(category),Category.class );
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            // 一定要归还连接池,否则不够连接会报错
            JedisUtils.close(jedis);
        }
        return  categoryList;
    }

    /**
     *  后台系统添加商品分类方法,传递分类名
     *  更新redis数据库
     */
    public void addCategory(String cname) {
        try {
            categoryDao.addCategory(cname);
            //redis与前台同步,删除旧数据
            clearRedis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空redis数据库
     */
    private void clearRedis(){
        //使用jedis连接redis删除键为category对应的数据
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("category");
        JedisUtils.close(jedis);
    }


    /**
     * Category 后台系统传递商品分类的主键到dao层
     * 把结果返回Category对象
     */
    public Category findById(String cid) {
        Category category = null;
        try {
            category = categoryDao.findById(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }


    /**
     *  updateCategory  更新分类数据
     *  web传递分类主键和分类名称
     *  调用dao层,传递参数,分类更新
     *  更新redis数据库
     */
    public void updateCategory(String cid, String cname) {
        try {
            categoryDao.updateCategory(cid,cname);
            //清空redis
            clearRedis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * delCategory 接收web层传递分类主键,调用dao层删除
     * 注意:更新redis数据库
     *
     *   注意: 删除分类数据功能
     *     该分类下,无数据,直接删除
     *     该分类下,有数据,不能删除
     *
     *   能否删除的结果,通知web层,异常
     *     删除之前,查询一次是否在分类下有数据
     */
    public void delCategory(String cid)throws CanNotDeleteException {
        try {
            //查询,分类下是否有数据
            long count = productDao.getProductCount(cid);
            //判断返回值,>0有数据,不能删除
            if (count > 0) {
                //查询有数据,分类不能删除
                //通知Web层不能删除,利用异常(抛出一个不能删除的异常,自定义)
                throw new CanNotDeleteException("分类有数据,不能删除");
            } else {
                //没有数据删除
                categoryDao.delCategory(cid);
                clearRedis();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
