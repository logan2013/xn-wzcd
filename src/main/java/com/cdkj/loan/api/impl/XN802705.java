package com.cdkj.loan.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.loan.ao.IChargeAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.DateUtil;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.core.StringValidater;
import com.cdkj.loan.domain.Charge;
import com.cdkj.loan.dto.req.XN802705Req;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 充值订单分页查询
 * @author: xieyj 
 * @since: 2017年5月13日 下午7:27:55 
 * @history:
 */
public class XN802705 extends AProcessor {
    private IChargeAO chargeAO = SpringContextHolder.getBean(IChargeAO.class);

    private XN802705Req req = null;

    @Override
    public Object doBusiness() throws BizException {

        Charge condition = new Charge();

        condition.setCodeForQuery(req.getCode());
        condition.setPayGroup(req.getPayGroup());
        condition.setRefNo(req.getRefNo());
        condition.setAccountNumber(req.getAccountNumber());
        condition.setAccountName(req.getAccountName());

        condition.setType(req.getType());
        condition.setCurrency(req.getCurrency());
        condition.setBizType(req.getBizType());
        condition.setPayCardNo(req.getPayCardNo());
        condition.setStatus(req.getStatus());

        condition.setApplyUser(req.getApplyUser());
        condition.setApplyDatetimeStart(
            DateUtil.getFrontDate(req.getApplyDateStart(), false));
        condition.setApplyDatetimeEnd(
            DateUtil.getFrontDate(req.getApplyDateEnd(), true));
        condition.setPayUser(req.getPayUser());
        condition.setPayDatetimeStart(
            DateUtil.getFrontDate(req.getPayDateStart(), false));

        condition.setPayDatetimeEnd(
            DateUtil.getFrontDate(req.getPayDateEnd(), true));
        condition.setChannelType(req.getChannelType());

        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IChargeAO.DEFAULT_ORDER_COLUMN;
        }

        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());

        return chargeAO.queryChargePage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802705Req.class);
        ObjValidater.validateReq(req);
    }
}
