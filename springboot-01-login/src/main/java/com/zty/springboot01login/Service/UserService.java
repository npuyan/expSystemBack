package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = mapper.selectByUserName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.err.println(user);
        return user;
    }
    public void addUser(User record){
        BCryptPasswordEncoder encode=new BCryptPasswordEncoder(10);
        record.setPassword(encode.encode(record.getPassword()));
        mapper.insert(record);
    }
}