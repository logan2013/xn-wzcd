package com.cdkj.loan.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * @author: xieyj 
 * @since: 2015年8月19日 下午7:41:26 
 * @history:
 */
public class XN805161Req {
    // 收件编号（必填）
    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
