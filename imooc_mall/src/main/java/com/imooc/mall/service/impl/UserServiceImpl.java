package com.imooc.mall.service.impl;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import com.imooc.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser() {
        User user = userMapper.selectByPrimaryKey(1);
        return user;
    }

    @Override
    public void register(String userName, String password) throws ImoocMallException {
        //查询用户名是否存在
        User result = userMapper.selectByName(userName);
        if (result!=null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //允许注册
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count==0){
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }


    @Override
    public User login(String userName, String password) throws ImoocMallException {
        String md5Password=null;
        try {
            md5Password=MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if (user==null){
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }
    //更新用户签名
    @Override
    public void updateInformation(User user) throws ImoocMallException {
        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count>1){
            throw new ImoocMallException(ImoocMallExceptionEnum.LOGIN_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        //1是默认普通用户  2是管理员
        return user.getRole().equals(2);
    }
}
