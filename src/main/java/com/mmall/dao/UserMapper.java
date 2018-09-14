package com.mmall.dao;

import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(@Param("username") String userName,@Param("password") String password);

    int checkUserName(String userName);

    int checkEmail(String email);

    String selectQuestionByUserName(String userName);

    int selectCheckAnswerByUserName(@Param("username") String userName,@Param("question") String question,@Param("answer") String answer);

    int updateForgetResetPassword(@Param("username") String userName,@Param("newPassword") String newPassword);

    int selectCheckPassword(@Param("password") String password,@Param("userId") int userId);



}