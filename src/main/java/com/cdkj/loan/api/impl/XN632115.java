package com.cdkj.loan.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.loan.ao.ICreditAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.DateUtil;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.domain.Credit;
import com.cdkj.loan.dto.req.XN632115Req;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 按角色编号分页查询征信列表
 * @author: jiafr 
 * @since: 2018年5月26日 上午10:57:21 
 * @history:
 */
public class XN632115 extends AProcessor {

    private ICreditAO creditAO = SpringContextHolder.getBean(ICreditAO.class);

    private XN632115Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        // 征信表分页查询结果
        Credit condition = new Credit();
        condition.setCode(req.getCreditCode());
        condition.setLoanBankCode(req.getLoanBankCode());
        condition.setSaleUserId(req.getSaleUserId());
        condition.setApplyDatetime(DateUtil.strToDate(req.getApplyDatetime(),
            DateUtil.FRONT_DATE_FORMAT_STRING));
        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = ICreditAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(column, req.getOrderDir());
        int start = Integer.valueOf(req.getStart());
        int limit = Integer.valueOf(req.getLimit());

        return creditAO.queryCreditPageByRole(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN632115Req.class);
        ObjValidater.validateReq(req);

    }

}