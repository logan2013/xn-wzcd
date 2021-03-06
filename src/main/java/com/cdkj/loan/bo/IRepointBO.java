package com.cdkj.loan.bo;

import java.util.List;

import com.cdkj.loan.bo.base.IPaginableBO;
import com.cdkj.loan.domain.Repoint;

public interface IRepointBO extends IPaginableBO<Repoint> {

    public boolean isRepointExist(String code);

    public String saveRepoint(Repoint data);

    public int removeRepoint(String code);

    public void refreshRepoint(Repoint data);

    public List<Repoint> queryRepointList(Repoint condition);

    public Repoint getRepoint(String code);

    // 确认打款
    public void confirmLoan(Repoint data);

    // 分公司总经理审批
    public void branchCompanyManagerApprove(Repoint repoint);

    // 财务确认
    public void financeConfirm(Repoint repoint);

}
