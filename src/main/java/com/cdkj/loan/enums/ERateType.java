package com.cdkj.loan.enums;

/**
 * 利率类型
 * @author: jiafr 
 * @since: 2018年6月16日 下午10:06:00 
 * @history:
 */
public enum ERateType {

    CT("1", "传统"), ZK("2", "直客");

    ERateType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
