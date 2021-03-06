package com.cdkj.loan.dto.req;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 车辆抵押开始
 * @author: jiafr 
 * @since: 2018年7月14日 下午7:33:42 
 * @history:
 */
public class XN632194Req {

    // 预算单编号列表
    @NotEmpty
    private List<String> list;

    // 操作人
    @NotBlank
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

}
