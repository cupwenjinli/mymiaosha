package cn.com.wenjin.miaosha.services;

import cn.com.wenjin.miaosha.dao.Userdao;
import cn.com.wenjin.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-24 15:51
 */
@Service
public class UserService {
    @Autowired
    Userdao userDao ;

    public User getByid(int id){
        return userDao.getById(id);
    }

    //@Transactional
    public boolean tx(){
        User u1 = new User();
        u1.setId(2);
        u1.setName("222");

        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("wenjin");

        userDao.insert(u2);
        return true;
    }
}