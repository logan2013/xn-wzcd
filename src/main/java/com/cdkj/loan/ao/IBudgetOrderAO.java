package com.cdkj.loan.ao;

import org.springframework.stereotype.Component;

import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.BudgetOrder;
import com.cdkj.loan.dto.req.XN632120Req;

@Component
public interface IBudgetOrderAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    // 填写准入申请单
    public String addBudgetOrder(XN632120Req req);

    // 风控专员审核
    public void riskApprove(String code, String approveResult,
            String approveNote, String operator);

    // 风控主管审核
    public void riskChargeApprove(String code, String operator,
            String approveResult, String approveNote);

    public Paginable<BudgetOrder> queryBudgetOrderPage(int start, int limit,
            BudgetOrder condition);

    public BudgetOrder getBudgetOrder(String code);

}
