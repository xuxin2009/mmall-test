package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVO;
import com.mmall.vo.ProductListVO;

import java.util.List;


/**
 * Created by Administrator on 2018/9/14.
 */


public interface ProductService {

    /**
     * 新增OR更新产品
     * @param product
     * @return
     */
    ServerResponse<String> productSaveOrUpdate(Product product);


    /**
     * 产品上下架
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setProductSaleStatus(Integer productId,Integer status);

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVO> getProductDetails(Integer productId);

    /**
     * 获取商品list
     * @return
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);


    /**
     * 搜索商品
     * @param pageNum
     * @param pageSize
     * @param productName
     * @param productId
     * @return
     */
    ServerResponse<PageInfo> getSearchProduct(int pageNum,int pageSize,String productName,Integer productId);


    /*********************************前台产品接口************************************/
    /**
     * 获取商品详情根据productId
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVO> productDetailByProductId(Integer productId);

    /**
     * 获取产品list
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param categoryId
     * @param orderby
     * @return
     */
    ServerResponse<PageInfo> getProductList(int pageNum,int pageSize,String keyword,Integer categoryId,String orderby);


}
