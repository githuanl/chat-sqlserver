package com.centersoft.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by liudong on 2018/6/20.
 */
@Entity
@Table(name = "c_group")
public class GroupEntity extends BaseEntity {

    private String id;          //群id
    private String groupname;   //群的名字
    private String user_id;     //创建的人 id
    private String user_name;   //创建人的名字，群主
    private String creat_date;  //创建时间
    private String avatar;      //群的头像

    @Id
    @GeneratedValue(generator = "_uuid")
    @Column(columnDefinition = "varchar(36)")
    @GenericGenerator(name = "_uuid", strategy = "org.hibernate.id.UUIDGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreat_date() {
        return creat_date;
    }

    public void setCreat_date(String creat_date) {
        this.creat_date = creat_date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
