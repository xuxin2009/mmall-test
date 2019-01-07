package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2018/9/13.
 */
public interface CategoryService {
    /**
     * 添加品类
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse<String> addCategory(String categoryName,Integer parentId);

    /**
     * 修改品类名字
     * @param categoryName
     * @param categoryId
     * @return
     */
    ServerResponse<String> setCategoryName(String categoryName,Integer categoryId);

    /**
     *获取品类子节点
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId);

    /**
     * 获取当前分类id及递归子节点categoryId
     * @param categoryId
     * @return
     */
    ServerResponse<List<Integer>> selectCategoryAndChildById(Integer categoryId);

}
