package com.cdkj.loan.enums;

public enum ECollectionResult {
    ALL_REPAY("all_repay", "全额还款"), PART_REPAY("part_repay", "部分还款"), PROVIDE_DEPOSIT(
            "provide_deposit", "提供押金"), REJUST_REPAY("rejust_repay", "拒绝还款"), TAKE_CAR(
            "take_car", "拖车处理"), JUDGE("judge", "诉讼"), REPLACE_REPAY(
            "replace_repay", "代偿");

    ECollectionResult(String code, String value) {
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