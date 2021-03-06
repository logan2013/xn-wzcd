package com.cdkj.loan.ao;

import java.math.BigDecimal;
import java.util.List;

import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.Withdraw;
import com.cdkj.loan.spring.ServiceModule;

@ServiceModule
public interface IWithdrawAO {
    String DEFAULT_ORDER_COLUMN = "code";

    // 提现申请
    public String applyOrderTradePwd(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote, String tradePwd, String googleCaptcha);

    // 待申请
    public String applyOrder(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote);

    // 取现审核
    public void approveOrder(String code, String approveUser,
            String approveResult, String approveNote);

    // 支付订单
    public void payOrder(String code, String payUser, String payResult,
            String payNote, String channelOrder);

    public Paginable<Withdraw> queryWithdrawPage(int start, int limit,
            Withdraw condition);

    public List<Withdraw> queryWithdrawList(Withdraw condition);

    public Withdraw getWithdraw(String code);
}
