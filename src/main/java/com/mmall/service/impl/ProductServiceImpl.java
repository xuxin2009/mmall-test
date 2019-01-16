package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.CategoryService;
import com.mmall.service.ProductService;
import com.mmall.utils.DateTimeUtil;
import com.mmall.utils.PropertiesUtil;
import com.mmall.vo.ProductDetailVO;
import com.mmall.vo.ProductListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;
    /**
     * 新增OR更新产品
     * @param product
     * @return
     */
    @Override
    public ServerResponse<String> productSaveOrUpdate(Product product) {
        if(product != null)
        {
            //判断是否上传图片，如果上传有图片则把第一幅图设置成主图
            if(StringUtils.isNotBlank(product.getSubImages()))
            {
                String[] subImageArray = StringUtils.split(",");
                if(subImageArray.length > 0)
                {
                    product.setMainImage(subImageArray[0]);
                }
            }
            //如果是更新操作的话，product的Id一定不为空
            if(product.getId() != null)
            {
                int updateCount = productMapper.updateByPrimaryKeySelective(product);
                if(updateCount > 0)
                {
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }else
                {
                    return ServerResponse.createByErrorMessage("更新产品失败");
                }
            }else//新增操作
            {
                int insertCount = productMapper.insert(product);
                if(insertCount > 0)
                {
                    return ServerResponse.createBySuccessMessage("新增商品成功");
                }else
                {
                    return ServerResponse.createByErrorMessage("新增商品失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新商品失败");
    }

    /**
     * 产品上下架
     * @param productId
     * @param status
     * @return
     */
    @Override
    public ServerResponse<String> setProductSaleStatus(Integer productId, Integer status) {
        if(productId == null || status == null)
        {
            return ServerResponse.createByErrorMessage("产品上下架参数有误");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int updateCount = productMapper.updateByPrimaryKeySelective(product);

        //也可以使用下面的方法，但是需要去mapper中写sql语句
        //int updateCount = productMapper.updateSetProductStatus(productId, status);

        if(updateCount > 0)
        {
            return ServerResponse.createBySuccessMessage("修改产品销售状态成功！");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败！");
    }

    /**
     * 查看产品详情
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVO> getProductDetails(Integer productId) {
        if(productId == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null)
        {
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }
        //由于前台不需要全部查询的数据，因此需要使用VO对象，把需要的数据传到前台
        //VO对象--value object
        ProductDetailVO productDetailVO =assembleProductDetailVO(product) ;

        return ServerResponse.createBySuccess(productDetailVO);
    }

    /**
     * VO对象转换，从数据库读取的时间是一个时间毫秒数，需转换成标准格式
     * @param product
     * @return
     */
    private ProductDetailVO assembleProductDetailVO(Product product)
    {
        ProductDetailVO productDetailVO = new ProductDetailVO();

        productDetailVO.setId(product.getId());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setName(product.getName());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setSubImages(product.getSubImages());

        //imagehost
        productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        //parentCategoryId

        //parentCategoryId
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null)
        {
            productDetailVO.setParentCategoryId(0);
        }else
        {
            productDetailVO.setParentCategoryId(category.getParentId());
        }
        //updatetime
        productDetailVO.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        //creattime
        productDetailVO.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        return productDetailVO;
    }

    /**
     * 获取商品list
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize) {
        //1.pagehelper start
        PageHelper.startPage(pageNum, pageSize);

        //2.编写自己的SQL查询语句
        List<Product> productList =  productMapper.selectProductList();

        //3.pageHelper-收尾
        PageInfo pageRsult = new PageInfo(productList);

        List<ProductListVO> productListVOList = new ArrayList();
        for (Product ProductItem : productList )
        {
            ProductListVO productListVO = assembleProductListVO(ProductItem);
            productListVOList.add(productListVO);
        }

        pageRsult.setList(productListVOList);

        return ServerResponse.createBySuccess(pageRsult);
    }
    private ProductListVO assembleProductListVO(Product product)
    {
        ProductListVO productListVO = new ProductListVO();

        productListVO.setId(product.getId());
        productListVO.setName(product.getName());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVO.setPrice(product.getPrice());
        productListVO.setSubtitle(product.getSubtitle());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setStatus(product.getStatus());

        return productListVO;
    }

    @Override
    public ServerResponse<PageInfo> getSearchProduct(int pageNum, int pageSize, String productName, Integer productId) {
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(productName))
        {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        if(productId == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Product> productList = productMapper.selectSearchProductByIdOrName(productName, productId);

        PageInfo pageResult = new PageInfo(productList);

        //VO转换
        List<ProductListVO> productListVOList = new ArrayList();
        for(Product productItem : productList)
        {
            ProductListVO productListVO = assembleProductListVO(productItem);
            productListVOList.add(productListVO);
        }
        //分页
        pageResult.setList(productListVOList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /*********************************前台产品接口************************************/

    @Override
    public ServerResponse<ProductDetailVO> productDetailByProductId(Integer productId) {
        if(productId == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null)
        {
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }

        if(product.getStatus()!= Const.ProductStatus.ON_SALE.getCode())
        {
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }
        ProductDetailVO productDetailVO =assembleProductDetailVO(product) ;

        return ServerResponse.createBySuccess(productDetailVO);
    }

    /**
     * 获取产品列表
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param categoryId
     * @param orderby
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize, String keyword, Integer categoryId,String orderby) {

        if(StringUtils.isBlank(keyword) && categoryId == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList = new ArrayList<Integer>();
        //判断categoryId是否为空
        if(categoryId != null)
        {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword))
            {
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = categoryService.selectCategoryAndChildById(category.getId()).getData();
        }
        //判断keyword是否为空
        if(StringUtils.isNotBlank(keyword))
        {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum, pageSize);

        if(StringUtils.isNotBlank(orderby))
        {
            if(Const.productListOrderBy.PRICE_DESC_ASC.contains(orderby))
            {
                String[] orderByArray = orderby.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndCategoryId(
               StringUtils.isBlank(keyword) ? null : keyword,
               categoryIdList.size() == 0 ? null : categoryIdList );

        List<ProductListVO> productListVOList = Lists.newArrayList();
        for(Product product : productList)
        {
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
