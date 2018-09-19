package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.CategoryServie;
import com.mmall.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/9/13.
 */
@RestController
@RequestMapping("/manager/category")
public class CategoryManageController {



    @Autowired
    private CategoryServie categoryServie;

    /**
     * 添加品类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do",method = RequestMethod.GET)
    public ServerResponse<String> addCategory(HttpSession session, String categoryName, @RequestParam(value ="parentId",defaultValue = "0") int parentId)
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
        return categoryServie.addCategory(categoryName,parentId);
    }


    /**
     * 修改品类名字
     * @param session
     * @param newCategoryName
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "set_category_name.do",method = RequestMethod.GET)
    public ServerResponse<String> setCategoryName(HttpSession session,String newCategoryName,int categoryId)
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
        return categoryServie.setCategoryName(newCategoryName,categoryId);
    }

    /**
     * 获取品类子节点(不递归，平行子节点)
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/get_category.do",method = RequestMethod.GET)
    public ServerResponse getChildParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId)
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

        return categoryServie.getChildParallelCategory(categoryId);
    }

    /**
     * 获取当前分类id及递归子节点categoryId
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/get_deep_category.do",method = RequestMethod.GET)
    public  ServerResponse getDeepCategory(HttpSession session , Integer categoryId)
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
        return categoryServie.selectCategoryAndChildById(categoryId);
    }

}
