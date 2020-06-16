package com.leyou.service;

import com.leyou.dao.UserMapper;
import com.leyou.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public Boolean check(String data, Integer type) {
        Boolean result = false;
        User user = new User();
        //1.判断校验的内容：type：用户名，手机
        if (type==1){
            user.setUsername(data);
        }else if (type==2){
            user.setPhone(data);
        }
        //2.根据校验内容去数据库查询
        User user1 = userMapper.selectOne(user);
        if (user1==null){
            return true;
        }
        //3.用户名存在返回false 手机号存在返回false
        return result;
    }

    public void insertUser(User user) {
        //salt
        String salt = UUID.randomUUID().toString().substring(0,32);
        String pwd = this.getPwd(user.getPassword(), salt);
        user.setPassword(pwd);
        user.setSalt(salt);
        user.setCreated(new Date());
        userMapper.insert(user);
    }
    //通过原始密码+盐值生成md5加密后的密码
    public String getPwd(String password,String salt){
        //MD5加密
        String md5Hex = DigestUtils.md5Hex(password + salt);
        return md5Hex;
    }

    public User findUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }
}
