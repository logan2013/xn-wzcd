package com.cdkj.loan.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.loan.ao.IBudgetOrderAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.DateUtil;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.domain.BudgetOrder;
import com.cdkj.loan.dto.req.XN630912Req;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 奖金提成
 * @author: jiafr 
 * @since: 2018年8月22日 下午5:59:52 
 * @history:
 */
public class XN630912 extends AProcessor {
    private IBudgetOrderAO budgetOrderAO = SpringContextHolder
        .getBean(IBudgetOrderAO.class);

    private XN630912Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        BudgetOrder condition = new BudgetOrder();
        condition.setCompanyCode(req.getCompanyCode());
        condition.setSaleUserId(req.getSaleUserId());
        if (StringUtils.isNotBlank(req.getFkMonthStart())) {
            condition.setBankFkDatetimeStart(DateUtil.getBeginTime(
                Integer.valueOf(req.getFkMonthStart().substring(0, 4)),
                Integer.valueOf(req.getFkMonthStart().substring(5, 7))));
        }
        if (StringUtils.isNotBlank(req.getFkMonthEnd())) {
            condition.setBankFkDatetimeEnd(DateUtil.getEndTime(
                Integer.valueOf(req.getFkMonthEnd().substring(0, 4)),
                Integer.valueOf(req.getFkMonthEnd().substring(5, 7))));
        }
        if (StringUtils.isBlank(req.getFkMonthStart())
                && StringUtils.isBlank(req.getFkMonthEnd())) {
            condition.setBankFkDatetimeForYear("2");
        }
        return budgetOrderAO.bonusDeduct(condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN630912Req.class);
        ObjValidater.validateReq(req);
    }

}
