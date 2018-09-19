package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.FileService;
import com.mmall.service.ProductService;
import com.mmall.utils.PropertiesUtil;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/14.
 */

@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;
    /**
     * 新增OR更新产品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping(value = "/save.do",method = RequestMethod.GET)
    public ServerResponse productSave(HttpSession session, Product product)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验是否是管理员admin
        if(user.getRole().intValue() != Const.Role.ROLE_ADMIN)
        {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
        return  productService.productSaveOrUpdate(product);
    }

    /**
     * 产品上下架
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "/set_sale_status.do",method = RequestMethod.GET)
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验是否是管理员admin
        if(user.getRole().intValue() != Const.Role.ROLE_ADMIN)
        {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
        //产品上下架逻辑
        return productService.setProductSaleStatus(productId, status);
    }


    /**
     * 获取商品详情
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "/detail.do",method = RequestMethod.GET)
    public  ServerResponse productDetail(HttpSession session,Integer productId)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验是否是管理员admin
        if(user.getRole().intValue() != Const.Role.ROLE_ADMIN)
        {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
        return productService.getProductDetails(productId);
    }

    /**
     * 产品list
     * @param session
     * @return
     */
    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10") int pageSize)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验是否是管理员admin
        if(user.getRole().intValue() != Const.Role.ROLE_ADMIN)
        {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
        return productService.getProductList(pageNum, pageSize);
    }

    /**
     * 搜索商品
     * @param session
     * @param pageNum
     * @param pageSize
     * @param productName
     * @param productId
     * @return
     */
    @RequestMapping(value = "/search.do",method = RequestMethod.GET)
    public ServerResponse productSearch(HttpSession session,String productName,int productId,
                                        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验是否是管理员admin
        if(user.getRole().intValue() != Const.Role.ROLE_ADMIN)
        {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
        return productService.getSearchProduct(pageNum, pageSize, productName, productId);

    }

    /**
     * 文件上传到FTP
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload.do",method = RequestMethod.POST)
    public ServerResponse uploadFile(HttpSession session,@RequestParam("file")MultipartFile file , HttpServletRequest request)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验是否是管理员admin
        if(user.getRole().intValue() != Const.Role.ROLE_ADMIN)
        {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }

        //获取上传文件的保存路径，会在webapp下面创建一个upload文件夹，存放上传的文件
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file,path);
       String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap = new HashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }


    /**
     * 富文本上传
     * @param session
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/richtext_img_upload.do",method = RequestMethod.POST)
    public Map richTextImgUpload(HttpSession session,
                                 @RequestParam("richtextFile")MultipartFile file ,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        Map resultMap = Maps.newHashMap();

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            resultMap.put("success",false);
            resultMap.put("msg","用户未登陆，请登陆");
            return resultMap;
        }
        //校验是否是管理员admin
        if(user.getRole().intValue() != Const.Role.ROLE_ADMIN)
        {
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作，需要管理员权限");
            return resultMap;
        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
       if(StringUtils.isBlank( targetFileName))
       {
           resultMap.put("success",false);
           resultMap.put("msg","上传失败");
           resultMap.put("url",url);
           return resultMap;
       }
        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("url",url);
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

}
