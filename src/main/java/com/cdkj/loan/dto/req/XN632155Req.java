package com.cdkj.loan.dto.req;

import java.util.List;

/**
 * 分页查询资料传递
 * @author: jiafr 
 * @since: 2018年6月15日 下午10:21:09 
 * @history:
 */
public class XN632155Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 4012969865163344214L;

    // 业务编号
    private String bizCode;

    // 用户编号
    private String userId;

    // 节点编号
    private String bizNodeCode;

    // 状态
    private String status;

    // 状态List
    private List<String> statusList;

    // 类型
    private String type;

    // 类型List
    private List<String> typeList;

    // 客户姓名
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBizNodeCode() {
        return bizNodeCode;
    }

    public void setBizNodeCode(String bizNodeCode) {
        this.bizNodeCode = bizNodeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
