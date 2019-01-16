package com.mmall.controller.protal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.ProductService;
import com.mmall.vo.ProductDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/9/18.
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 根据产品id查看产品详情
     * @param productId
     * @return
     */
    @RequestMapping("/detail.do")
    public ServerResponse<ProductDetailVO> productDetail(int productId)
    {
        return productService.productDetailByProductId(productId);
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param keyword
     * @param orderby
     * @return
     */
    @RequestMapping("/list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                         @RequestParam(value = "keyword",required = false) String keyword,
                                         @RequestParam(value = "orderby",defaultValue = "") String orderby)
    {
        return productService.getProductList(pageNum, pageSize,keyword,categoryId,orderby);
    }

}
