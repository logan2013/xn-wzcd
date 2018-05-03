package com.cdkj.loan.api.impl;

import com.cdkj.loan.ao.ILoanOrderAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.dto.req.XN630502Req;
import com.cdkj.loan.dto.res.BooleanRes;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 修改车贷订单（包含提交审核）
 * @author: CYL 
 * @since: 2018年4月30日 下午8:26:08 
 * @history:
 */
public class XN630502 extends AProcessor {
    private ILoanOrderAO loanOrderAO = SpringContextHolder
        .getBean(ILoanOrderAO.class);

    private XN630502Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        loanOrderAO.editLoanOrder(req);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN630502Req.class);
        ObjValidater.validateReq(req);
    }

}
