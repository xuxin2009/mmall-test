package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.CategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/9/13.
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 添加节点
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public ServerResponse<String> addCategory(String categoryName, Integer parentId) {

        if(parentId == null || StringUtils.isBlank(categoryName))
        {
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);

        int resultCount = categoryMapper.insert(category);
        if(resultCount > 0)
        {
            return  ServerResponse.createBySuccessMessage("添加品类成功");
        }

        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    /**
     * 修改品类名字
     * @param categoryName
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<String> setCategoryName(String categoryName, Integer categoryId) {
        if(categoryId == null || StringUtils.isBlank(categoryName))
        {
            return ServerResponse.createByErrorMessage("商品参数有误");
        }
//使用下面的代码也可以，但是这不是优化的代码，会操作访问两次数据库
//        Category category = categoryMapper.selectByPrimaryKey(categoryId);
//        if(category == null)
//        {
//            return  ServerResponse.createByErrorMessage("商品品类id错误");
//        }

        Category category = new Category();
        category.setId(categoryId);

        category.setName(categoryName);
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(resultCount == 0)
        {
            return ServerResponse.createByErrorMessage("修改品类名称失败");
        }
        return ServerResponse.createBySuccessMessage("修改品类名称成功");
    }

    /**
     * 获取品类子节点
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectChildCategoryList(categoryId);
        if(CollectionUtils.isEmpty(categoryList))//使用collectionUtils,不仅对null进行判断还对""进行判断，categoryList == null
        {
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 获取当前分类id及递归子节点categoryId
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Integer>> selectCategoryAndChildById(Integer categoryId)
    {
        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategory(categorySet,categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();

        if(categorySet != null)
        {
            for(Category categoryItem : categorySet)
            {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }


    //递归算法，获取子节点数据,使用set集合时需要重写Category类的equals和hashcode方法
    private Set<Category> findChildrenCategory(Set<Category> categorySet,Integer categoryId)
    {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);

      if(category != null)
      {
          categorySet.add(category);
      }
        //查找子节点
        List<Category> categoryList = categoryMapper.selectChildCategoryList(categoryId);
        for(Category categoryItem : categoryList)
        {
            findChildrenCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }


}
