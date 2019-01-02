package com.centersoft.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centersoft.entity.Employee;
import com.centersoft.entity.SUser;
import com.centersoft.entity.UserEntity;
import com.centersoft.service.UserSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Auther: lh
 * @Date: 2018/12/20 09:37
 * @Description:
 */


@Component
public class RedisSubscriber extends MessageListenerAdapter {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    UserSerivice userSerivice;

    @Override
    public void onMessage(Message message, byte[] bytes) {

        byte[] body = message.getBody();
        String msg = redisTemplate.getStringSerializer().deserialize(body);
        System.out.println(msg);
        byte[] channel = message.getChannel();
        String topic = redisTemplate.getStringSerializer().deserialize(channel);
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(msg)) {
            return;
        }
        JSONObject object = JSON.parseObject(msg);
        Employee employee = JSON.parseObject(object.getString("emp"), Employee.class);
        SUser user = JSON.parseObject(object.getString("user"), SUser.class);

        UserEntity userEntity = userSerivice.findByEmpid(employee.getId());
        switch (topic) {
            case "update_pwd":
                if (userEntity != null) {
                    userEntity.setPassword(user.getPassword());
                    userSerivice.mergeEntity(userEntity);
                }
                break;
            case "update_emp_info": //此时 user 是空的 要注意
                if (userEntity != null) {
                    userEntity.setUsername(employee.getPsnname());
                    userEntity.setAvatar(employee.getImgUrl());
                    userSerivice.mergeEntity(userEntity);
                }
                break;
            // 删除用户
            case "updata_frozen":
                if (userEntity != null) {
                    userSerivice.delEntity(userEntity);
                }
                break;
            case "updata_normal":
            case "add_update":
                if (userEntity == null) {
                    userSerivice.register(user, employee);
                } else {
                    userEntity.setUsername(employee.getPsnname());
                    userEntity.setAvatar(employee.getImgUrl());
                    userEntity.setPassword(user.getPassword());
                    userSerivice.mergeEntity(userEntity);
                }
                break;
        }
    }

}
