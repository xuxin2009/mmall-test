package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.OrderService;
import com.mmall.service.UserService;
import com.mmall.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2019/1/17.
 */
@Controller
@RequestMapping("/manager/order/")
public class OrderManagerController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize)
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
        return orderService.manageList(pageNum, pageSize);
    }

    /**
     * 订单详情
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVO> orderDetail(HttpSession session, Long orderNo)
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
        return orderService.manageDetail(orderNo);
    }

    /**
     * 后台搜索
     * @param session
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo,
                                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize)
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
        return orderService.manageSearch(orderNo,pageNum,pageSize);
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> SendGoods(HttpSession session, Long orderNo)
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
        return orderService.manageSendGoods(orderNo);
    }

}
