package com.cdkj.loan.api.impl;

import com.cdkj.loan.ao.IProvinceAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.core.StringValidater;
import com.cdkj.loan.dto.req.XN632022Req;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 修改省份编号
 * @author: silver 
 * @since: 2018年5月27日 上午11:32:09 
 * @history:
 */
public class XN632022 extends AProcessor {

    private IProvinceAO provinceAO = SpringContextHolder
        .getBean(IProvinceAO.class);

    private XN632022Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return provinceAO.editProvince(StringValidater.toInteger(req.getId()),
            req.getProvinceNo(), req.getName());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN632022Req.class);
        ObjValidater.validateReq(req);
    }

}
