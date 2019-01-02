package com.centersoft.controller;

import com.alibaba.fastjson.JSON;
import com.centersoft.entity.BaseEntity;
import com.centersoft.entity.Result;
import com.centersoft.entity.UserEntity;
import com.centersoft.enums.EResultType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class BaseController<T extends BaseEntity> {


    @Autowired
    HttpSession session;


    public UserEntity getSessionUser(){
        return (UserEntity) session.getAttribute("username");
    }

    protected Logger logger = LogManager.getLogger(getClass().getName());


    protected String retResultData(EResultType type) {
        return JSON.toJSONString(new Result(type));
    }

    protected String retResultData(EResultType type, Object data) {
        return JSON.toJSONString(new Result(type, data));
    }

    protected String retResultData(Integer code, String message) {
        return JSON.toJSONString(new Result(code, message));
    }

    protected String retResultData(Integer code, String message, Object data) {
        return JSON.toJSONString(new Result(code, message, data));
    }





}
