package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.UserService;
import com.mmall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by Administrator on 2018/9/12.
 */

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String userName, String password) {
        //首先要检查用户名是否存在
        if(userMapper.checkUserName(userName) == 0)
        {
            return ServerResponse.createByErrorMessage("用户名不存在,请重新输入");
        }
        //密码需要MD5加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.login(userName,md5Password);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功",user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        //检查用户名
        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!(validResponse.isSuccess()))
        {
            return validResponse;
        }
        //校验邮箱
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!(validResponse.isSuccess()))
        {
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);//默认注册为普通用户
        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword());//MD5加密
        user.setPassword(md5Password);

        int resultCount = userMapper.insert(user);
        if(resultCount == 0 )
        {
            return ServerResponse.createByErrorMessage("用户注册失败");
        }
        return ServerResponse.createBySuccessMessage("用户注册成功");
    }

    /**
     * 检查用户名是否有效
     * @param str
     * @param type
     * @return
     */
    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type))
        {
            if(Const.USERNAME.equals(type))//判断验证用户名
            {
                int resultCount = userMapper.checkUserName(str);
                if(resultCount > 0)
                {
                    return ServerResponse.createByErrorMessage("用户名已注册，请重新输入");
                }
            }
            if(Const.EMAIL.equals(type))
            {
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0)
                {
                    return ServerResponse.createByErrorMessage("邮箱已经注册，请重新输入");
                }
            }

        }else
        {
            return ServerResponse.createBySuccessMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @Override
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null)
        {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息");
        }
        currentUser.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(currentUser);
    }

    /**
     * 退出登陆
     * @param session
     * @return
     */
    @Override
    public ServerResponse<String> logout(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser != null)
        {
            session.removeAttribute(Const.CURRENT_USER);
            return ServerResponse.createBySuccessMessage("退出成功");
        }
        return ServerResponse.createByErrorMessage("服务器端异常");
    }


    @Override
    public ServerResponse<String> updateUserInfo(String email, String phone, String question, String answer, HttpSession session)
    {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
       if(currentUser != null)
       {
           ServerResponse validResponse = this.checkValid(email,Const.EMAIL);
           //校验email是否注册
           if(!(validResponse.isSuccess()))
           {
               return  validResponse;
           }

           currentUser.setEmail(email);
           currentUser.setPhone(phone);
           currentUser.setQuestion(question);
           currentUser.setAnswer(answer);
           int resultCount = userMapper.updateByPrimaryKeySelective(currentUser);
           if(resultCount > 0)
           {
               return ServerResponse.createBySuccessMessage("更新个人信息成功");
           }else
           {
               return ServerResponse.createByErrorMessage("更新个人信息失败");
           }
       }else
       {
           return ServerResponse.createByErrorMessage("用户未登录");
       }
    }

    @Override
    public ServerResponse<String> forgetGetQuestion(String userName) {
        ServerResponse response = this.checkValid(userName,Const.USERNAME);
        if(response.isSuccess())
        {
            return ServerResponse.createByErrorMessage("用户名未注册");
        }
        String question = userMapper.selectQuestionByUserName(userName);
        if(StringUtils.isNotBlank(question))
        {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("该用户未设置找回密码问题");
    }

    @Override
    public ServerResponse<String> forgetCheckAnswer(String userName, String question, String answer) {
        int resultCount = userMapper.selectCheckAnswerByUserName(userName, question, answer);
        if(resultCount == 0)
        {
            return ServerResponse.createByErrorMessage("问题答案错误");
        }
        String forgetToken = UUID.randomUUID().toString();
        TokenCache.setKey(TokenCache.TOKEN_PREFIX + userName,forgetToken);
        return ServerResponse.createBySuccess(forgetToken);
    }

    @Override
    public ServerResponse<String> forgetRestPassword(String userName, String newPassword, String forgetToken) {
        if(StringUtils.isBlank(forgetToken))
        {
            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
        }
        ServerResponse response = this.checkValid(userName,Const.USERNAME);
        if(response.isSuccess())
        {
            return ServerResponse.createByErrorMessage("用户名未注册");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + userName);
        if(StringUtils.isBlank(token))
        {
            return ServerResponse.createByErrorMessage("token无效或过期");
        }

        if(StringUtils.equals(token,forgetToken))
        {
            String md5Password = MD5Util.MD5EncodeUtf8(newPassword);
            int resultCount = userMapper.updateForgetResetPassword(userName, md5Password);
            if(resultCount > 0)
            {
                return ServerResponse.createBySuccessMessage("重置密码成功");
            }else
            {
                return ServerResponse.createByErrorMessage("token错误,请重新输入token");
            }
        }
            return ServerResponse.createByErrorMessage("重置密码失败");
    }


    @Override
    public ServerResponse<String> restPassword(String oldPassword, String newPassword, HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
       int resultCount = userMapper.selectCheckPassword(MD5Util.MD5EncodeUtf8(oldPassword),user.getId());
        if(resultCount == 0)
        {
            return ServerResponse.createByErrorMessage("密码输入有误，请重新输入");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount == 0)
        {
            return ServerResponse.createByErrorMessage("重置密码失败");
        }
        return ServerResponse.createBySuccessMessage("重置密码成功");
    }

    @Override
    public ServerResponse<User> updateInformation(HttpSession session, User user) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null)
        {
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        //email需要校验，校验新的email是否存在，并且不能是当前用户的email
        ServerResponse response =this.checkValid(user.getEmail(),Const.EMAIL);
        if(!(response.isSuccess()))
        {
            return ServerResponse.createByErrorMessage("邮箱已经被注册，请重新输入");
        }
        currentUser.setEmail(user.getEmail());
        currentUser.setPhone(user.getPhone());
        currentUser.setQuestion(user.getQuestion());
        currentUser.setAnswer(user.getAnswer());
        int updateCount = userMapper.updateByPrimaryKeySelective(currentUser);
        if(updateCount == 0)
        {
            return  ServerResponse.createByErrorMessage("更新资料失败");
        }
        return ServerResponse.createBySuccess("更新资料成功",currentUser);
    }

    /**
     * 获取登陆用户信息
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<User> getInformation(Integer userId) {

        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null)
        {
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }
}
