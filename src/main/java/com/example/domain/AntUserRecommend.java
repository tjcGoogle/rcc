package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户推荐表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ant_user_recommend")
public class AntUserRecommend {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 老会员id
     */
    @TableField(value = "`uid`")
    private Integer uid;

    /**
     * 新会员id
     */
    @TableField(value = "new_user_id")
    private Integer newUserId;

    /**
     * 老会员手机号
     */
    @TableField(value = "referrer_mobile")
    private String referrerMobile;

    /**
     * 新会员手机号
     */
    @TableField(value = "new_user_mobile")
    private String newUserMobile;

    /**
     * 实名状态:0=未实名,1=已实名
     */
    @TableField(value = "auth_status")
    private Boolean authStatus;

    /**
     * 被推荐人注册时间
     */
    @TableField(value = "sign_up_time")
    private Date signUpTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 父级代理id
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 父代理id集合
     */
    @TableField(value = "pids")
    private String pids;
}