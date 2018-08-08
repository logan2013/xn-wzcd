package com.cdkj.loan.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.loan.ao.IBudgetOrderAO;
import com.cdkj.loan.ao.IRepointDetailAO;
import com.cdkj.loan.bo.IAccountBO;
import com.cdkj.loan.bo.IAdvanceFundBO;
import com.cdkj.loan.bo.IBankBO;
import com.cdkj.loan.bo.IBankSubbranchBO;
import com.cdkj.loan.bo.IBankcardBO;
import com.cdkj.loan.bo.IBudgetOrderBO;
import com.cdkj.loan.bo.IBudgetOrderFeeBO;
import com.cdkj.loan.bo.IBudgetOrderGpsBO;
import com.cdkj.loan.bo.ICarDealerBO;
import com.cdkj.loan.bo.ICarDealerProtocolBO;
import com.cdkj.loan.bo.ICollectBankcardBO;
import com.cdkj.loan.bo.ICreditBO;
import com.cdkj.loan.bo.ICreditUserBO;
import com.cdkj.loan.bo.IDepartmentBO;
import com.cdkj.loan.bo.IInsuranceCompanyBO;
import com.cdkj.loan.bo.ILoanCsBO;
import com.cdkj.loan.bo.ILogisticsBO;
import com.cdkj.loan.bo.INodeFlowBO;
import com.cdkj.loan.bo.IRepayBizBO;
import com.cdkj.loan.bo.IRepayPlanBO;
import com.cdkj.loan.bo.IRepointDetailBO;
import com.cdkj.loan.bo.ISYSBizLogBO;
import com.cdkj.loan.bo.ISYSConfigBO;
import com.cdkj.loan.bo.ISYSUserBO;
import com.cdkj.loan.bo.ISmsOutBO;
import com.cdkj.loan.bo.ISupplementReasonBO;
import com.cdkj.loan.bo.ITotalAdvanceFundBO;
import com.cdkj.loan.bo.IUserBO;
import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.common.AmountUtil;
import com.cdkj.loan.common.DateUtil;
import com.cdkj.loan.common.PhoneUtil;
import com.cdkj.loan.common.SysConstants;
import com.cdkj.loan.core.StringValidater;
import com.cdkj.loan.domain.AdvanceFund;
import com.cdkj.loan.domain.Bank;
import com.cdkj.loan.domain.BankSubbranch;
import com.cdkj.loan.domain.BudgetOrder;
import com.cdkj.loan.domain.BudgetOrderFee;
import com.cdkj.loan.domain.BudgetOrderGps;
import com.cdkj.loan.domain.CarDealer;
import com.cdkj.loan.domain.CarDealerProtocol;
import com.cdkj.loan.domain.CollectBankcard;
import com.cdkj.loan.domain.Credit;
import com.cdkj.loan.domain.CreditUser;
import com.cdkj.loan.domain.Department;
import com.cdkj.loan.domain.InsuranceCompany;
import com.cdkj.loan.domain.LoanCs;
import com.cdkj.loan.domain.Logistics;
import com.cdkj.loan.domain.NodeFlow;
import com.cdkj.loan.domain.RepayBiz;
import com.cdkj.loan.domain.RepayPlan;
import com.cdkj.loan.domain.RepointDetail;
import com.cdkj.loan.domain.SYSConfig;
import com.cdkj.loan.domain.SYSUser;
import com.cdkj.loan.domain.SupplementReason;
import com.cdkj.loan.domain.TotalAdvanceFund;
import com.cdkj.loan.domain.User;
import com.cdkj.loan.dto.req.XN632120Req;
import com.cdkj.loan.dto.req.XN632120ReqRepointDetail;
import com.cdkj.loan.dto.req.XN632141Req;
import com.cdkj.loan.dto.req.XN632142Req;
import com.cdkj.loan.dto.req.XN632143Req;
import com.cdkj.loan.dto.req.XN632144Req;
import com.cdkj.loan.dto.req.XN632191Req;
import com.cdkj.loan.dto.req.XN632192Req;
import com.cdkj.loan.dto.req.XN632193Req;
import com.cdkj.loan.dto.req.XN632194Req;
import com.cdkj.loan.dto.req.XN632200Req;
import com.cdkj.loan.dto.req.XN632220Req;
import com.cdkj.loan.dto.req.XN632230Req;
import com.cdkj.loan.dto.req.XN632270Req;
import com.cdkj.loan.dto.req.XN632271Req;
import com.cdkj.loan.dto.req.XN632272Req;
import com.cdkj.loan.dto.req.XN632280Req;
import com.cdkj.loan.dto.req.XN632281Req;
import com.cdkj.loan.dto.req.XN632292Req;
import com.cdkj.loan.dto.req.XN632341Req;
import com.cdkj.loan.dto.res.XN632234Res;
import com.cdkj.loan.dto.res.XN632290Res;
import com.cdkj.loan.dto.res.XN632291Res;
import com.cdkj.loan.dto.res.XN632690Res;
import com.cdkj.loan.enums.EAccountType;
import com.cdkj.loan.enums.EAdvanceFundNode;
import com.cdkj.loan.enums.EAdvanceType;
import com.cdkj.loan.enums.EApproveResult;
import com.cdkj.loan.enums.EAssureType;
import com.cdkj.loan.enums.EBackAdvanceFundType;
import com.cdkj.loan.enums.EBankRepointStatus;
import com.cdkj.loan.enums.EBankType;
import com.cdkj.loan.enums.EBizErrorCode;
import com.cdkj.loan.enums.EBizLogType;
import com.cdkj.loan.enums.EBocFeeWay;
import com.cdkj.loan.enums.EBoolean;
import com.cdkj.loan.enums.EBudgetFrozenStatus;
import com.cdkj.loan.enums.EBudgetOrderFeeWay;
import com.cdkj.loan.enums.EBudgetOrderNode;
import com.cdkj.loan.enums.EBudgetType;
import com.cdkj.loan.enums.EButtonCode;
import com.cdkj.loan.enums.ECity;
import com.cdkj.loan.enums.ECollectBankcardType;
import com.cdkj.loan.enums.ECurrency;
import com.cdkj.loan.enums.EEnterFileStatus;
import com.cdkj.loan.enums.EFbhStatus;
import com.cdkj.loan.enums.EGpsFeeWay;
import com.cdkj.loan.enums.EGpsTypeProtocol;
import com.cdkj.loan.enums.EIsAdvanceFund;
import com.cdkj.loan.enums.ELoanPeriod;
import com.cdkj.loan.enums.ELoanRole;
import com.cdkj.loan.enums.ELogisticsStatus;
import com.cdkj.loan.enums.ELogisticsType;
import com.cdkj.loan.enums.ELyAmountType;
import com.cdkj.loan.enums.EMakeCardStatus;
import com.cdkj.loan.enums.EOtherType;
import com.cdkj.loan.enums.ERateType;
import com.cdkj.loan.enums.ERepointDetailStatus;
import com.cdkj.loan.enums.ERepointDetailType;
import com.cdkj.loan.enums.ERepointDetailUseMoneyPurpose;
import com.cdkj.loan.enums.EServiceChargeWay;
import com.cdkj.loan.enums.ESettleWay;
import com.cdkj.loan.enums.EShouldBackStatus;
import com.cdkj.loan.enums.ETotalAdvanceFundType;
import com.cdkj.loan.enums.EUseMoneyPurpose;
import com.cdkj.loan.enums.EUserKind;
import com.cdkj.loan.exception.BizException;

@Service
public class BudgetOrderAOImpl implements IBudgetOrderAO {

    @Autowired
    private IBudgetOrderBO budgetOrderBO;

    @Autowired
    private ICreditUserBO creditUserBO;

    @Autowired
    private INodeFlowBO nodeFlowBO;

    @Autowired
    private ISYSBizLogBO sysBizLogBO;

    @Autowired
    private IBankBO bankBO;

    @Autowired
    private IBankSubbranchBO bankSubbranchBO;

    @Autowired
    private ICarDealerBO carDealerBO;

    @Autowired
    private IInsuranceCompanyBO insuranceCompanyBO;

    @Autowired
    private IDepartmentBO departmentBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IBudgetOrderGpsBO budgetOrderGpsBO;

    @Autowired
    private ILogisticsBO logisticsBO;

    @Autowired
    private IAdvanceFundBO advanceFundBO;

    @Autowired
    private ICollectBankcardBO collectBankcardBO;

    @Autowired
    private IRepointDetailBO repointDetailBO;

    @Autowired
    private ICarDealerProtocolBO carDealerProtocolBO;

    @Autowired
    private IBankcardBO bankcardBO;

    @Autowired
    private IRepayBizBO repayBizBO;

    @Autowired
    private IRepayPlanBO repayPlanBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private IBudgetOrderFeeBO budgetOrderFeeBO;

    @Autowired
    private ILoanCsBO loanCsBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IRepointDetailAO repointDetailAO;

    @Autowired
    private ICreditBO creditBO;

    @Autowired
    private ISupplementReasonBO supplementReasonBO;

    @Autowired
    private ITotalAdvanceFundBO totalAdvanceFundBO;

    @Override
    @Transactional
    public void editBudgetOrder(XN632120Req req) {
        BudgetOrder data = budgetOrderBO.getBudgetOrder(req
            .getBudgetOrderCode());
        if (!EBudgetOrderNode.START_NODE.getCode()
            .equals(data.getCurNodeCode())
                && !EBudgetOrderNode.FILL_AGAIN.getCode().equals(
                    data.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是预算单申请节点，不能操作");
        }

        data.setApplyUserCompany(req.getApplyUserCompany());
        data.setApplyUserDuty(req.getApplyUserDuty());
        data.setMarryState(req.getMarryState());
        data.setCustomerType(req.getCustomerType());

        if (EBudgetType.OUT.getCode().equals(req.getType())) {// 外单
            data.setOutCarDealerName(req.getOutCarDealerName());
        } else {// 正常
            data.setCarDealerCode(req.getCarDealerCode());
        }

        data.setOriginalPrice(StringValidater.toLong(req.getOriginalPrice()));

        data.setCarModel(req.getCarModel());
        data.setFrameNo(req.getFrameNo());
        data.setLoanPeriods(StringValidater.toInteger(req.getLoanPeriods()));
        data.setInvoicePrice(StringValidater.toLong(req.getInvoicePrice()));
        data.setRateType(req.getRateType());

        data.setBankRate(StringValidater.toDouble(req.getBankRate()));
        Long loanAmount = 0L;
        if (null == req.getLoanAmount()) {
            loanAmount = data.getLoanAmount();
        } else {
            loanAmount = StringValidater.toLong(req.getLoanAmount());
            data.setLoanAmount(loanAmount);
        }
        Long invoicePrice = StringValidater.toLong(req.getInvoicePrice());
        // 我司贷款成数=贷款金额/发票价格
        data.setCompanyLoanCs(AmountUtil.div(loanAmount, invoicePrice));

        // 获取我司贷款成数区间 标准
        LoanCs loanCsCondition = new LoanCs();
        loanCsCondition.setType(data.getShopWay());
        List<LoanCs> loanCsList = loanCsBO.queryLoanCsList(loanCsCondition);
        LoanCs resultCs = null;
        if (CollectionUtils.isNotEmpty(loanCsList)) {
            resultCs = loanCsList.get(0);
        }
        if (null != resultCs) {
            // 判断新我司贷款成数是否在标准内
            if (data.getCompanyLoanCs() > resultCs.getMaxCs()
                    || data.getCompanyLoanCs() < resultCs.getMinCs()) {
                // 不在我司准入贷款成数标准内
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前贷款成数不在我司贷款成数有效范围内");
            }
        }

        data.setIsAdvanceFund(req.getIsAdvanceFund());
        Long fee = StringValidater.toLong(req.getFee());// 服务费
        data.setFee(fee);
        double feeRate = AmountUtil.div(fee, loanAmount);
        data.setGlobalRate(feeRate
                + StringValidater.toDouble(req.getBankRate()));// 综合利率=服务费/贷款金额+银行利率
        data.setCarDealerSubsidy(StringValidater.toLong(req
            .getCarDealerSubsidy()));// 厂家贴息
        Long totalAmount = loanAmount + fee;// 贷款总额=贷款额+服务费
        data.setBankLoanCs(AmountUtil.div(totalAmount, invoicePrice));// 银行贷款成数=(贷款金额+服务费)/发票价格

        data.setApplyUserMonthIncome(StringValidater.toLong(req
            .getApplyUserMonthIncome()));
        data.setApplyUserSettleInterest(StringValidater.toLong(req
            .getApplyUserSettleInterest()));
        data.setApplyUserBalance(StringValidater.toLong(req
            .getApplyUserBalance()));
        data.setApplyUserJourShowIncome(req.getApplyUserJourShowIncome());

        data.setApplyUserIsPrint(req.getApplyUserIsPrint());
        data.setGhMonthIncome(StringValidater.toLong(req.getGhMonthIncome()));
        data.setGhSettleInterest(StringValidater.toLong(req
            .getGhSettleInterest()));
        data.setGhBalance(StringValidater.toLong(req.getGhBalance()));
        data.setGhJourShowIncome(req.getGhJourShowIncome());
        data.setGhIsPrint(req.getGhIsPrint());

        data.setEmergencyName1(req.getEmergencyName1());
        data.setEmergencyRelation1(req.getEmergencyRelation1());
        data.setEmergencyMobile1(req.getEmergencyMobile1());

        data.setEmergencyName2(req.getEmergencyName2());
        data.setEmergencyRelation2(req.getEmergencyRelation2());
        data.setEmergencyMobile2(req.getEmergencyMobile2());

        data.setGuarantor1MonthIncome(StringValidater.toLong(req
            .getGuarantor1MonthIncome()));
        data.setGuarantor1SettleInterest(StringValidater.toLong(req
            .getGuarantor1SettleInterest()));
        data.setGuarantor1Balance(StringValidater.toLong(req
            .getGuarantor1Balance()));
        data.setGuarantor1JourShowIncome(req.getGuarantor1JourShowIncome());

        data.setGuarantor1IsPrint(req.getGuarantor1IsPrint());
        data.setGuarantor2MonthIncome(StringValidater.toLong(req
            .getGuarantor2MonthIncome()));
        data.setGuarantor2SettleInterest(StringValidater.toLong(req
            .getGuarantor2SettleInterest()));
        data.setGuarantor2Balance(StringValidater.toLong(req
            .getGuarantor2Balance()));
        data.setGuarantor2JourShowIncome(req.getGuarantor2JourShowIncome());

        data.setGuarantor2IsPrint(req.getGuarantor2IsPrint());
        data.setIsHouseProperty(req.getIsHouseProperty());
        data.setHouseProperty(req.getHouseProperty());
        data.setIsHouseContract(req.getIsHouseContract());
        data.setHouseInvoice(req.getHouseInvoice());

        data.setIsLicense(req.getIsLicense());
        data.setLicense(req.getLicense());
        data.setIsSiteProve(req.getIsSiteProve());
        data.setSiteProve(req.getSiteProve());
        data.setSiteArea(req.getSiteArea());

        data.setCarType(req.getCarType());
        data.setIsDriceLicense(req.getIsDriceLicense());
        data.setDriceLicense(req.getDriceLicense());
        data.setOtherPropertyNote(req.getOtherPropertyNote());
        data.setApplyBirthAddress(req.getApplyBirthAddress());

        data.setApplyNowAddress(req.getApplyNowAddress());
        data.setHouseType(req.getHouseType());
        data.setGhBirthAddress(req.getGhBirthAddress());
        data.setGuarantor1BirthAddress(req.getGuarantor1BirthAddress());
        data.setGuarantor2BirthAddress(req.getGuarantor2BirthAddress());

        data.setOtherNote(req.getOtherNote());
        data.setOilSubsidyKil(StringValidater.toDouble(req.getOilSubsidyKil()));
        data.setIsPlatInsure(req.getIsPlatInsure());

        Map<String, Long> map = carDealerProtocolBO
            .calculateOilSubsidyGpsDeduct(loanAmount);
        data.setOilSubsidy(map.get("oilSubsidy"));// 油补
        data.setGpsDeduct(map.get("gpsDeduct"));// GPS提成

        if (EBudgetType.OUT.getCode().equals(req.getType())) {// 外单
            // 外单填写手续费
            data.setGpsFee(StringValidater.toLong(req.getGpsFee()));
            data.setLyAmount(StringValidater.toLong(req.getLyAmount()));
            data.setFxAmount(StringValidater.toLong(req.getFxAmount()));
            data.setOtherFee(StringValidater.toLong(req.getOtherFee()));
        } else {
            // 正常单 根据协议计算手续费
            Bank bank = bankBO.getBankBySubbranch(data.getLoanBankCode());
            XN632291Res xn632291Res = carDealerProtocolBO.calProtocolFee(
                loanAmount, bank.getBankCode(), data.getCarDealerCode());
            data.setGpsFee(StringValidater.toLong(xn632291Res.getGpsFee()));// GPS收费
            data.setLyAmount(StringValidater.toLong(xn632291Res.getLyAmount()));// 履约保证金
            data.setFxAmount(StringValidater.toLong(xn632291Res.getFxAmount()));// 风险担保金
            data.setOtherFee(StringValidater.toLong(xn632291Res.getOtherFee()));// 杂费
        }
        Long serviceCharge = data.getLyAmount() + data.getFxAmount()
                + data.getGpsFee() + data.getOtherFee();
        // 收客户手续费合计：履约保证金+担保风险金+GPS收费+杂费
        data.setServiceCharge(serviceCharge);
        data.setServiceChargeWay(req.getServiceChargeWay());// 手续费收取方式
        data.setGpsFeeWay(req.getGpsFeeWay());// gps费收取方式

        data.setMarryDivorce(req.getMarryDivorce());
        data.setApplyUserHkb(req.getApplyUserHkb());
        data.setBankBillPdf(req.getBankBillPdf());
        data.setSingleProvePdf(req.getSingleProvePdf());
        data.setIncomeProvePdf(req.getIncomeProvePdf());

        data.setLiveProvePdf(req.getLiveProvePdf());
        data.setBuildProvePdf(req.getBuildProvePdf());
        data.setHkbFirstPage(req.getHkbFirstPage());
        data.setHkbMainPage(req.getHkbMainPage());
        data.setGhHkb(req.getGhHkb());

        data.setGuarantor1Hkb(req.getGuarantor1Hkb());
        data.setGuarantor2Hkb(req.getGuarantor2Hkb());
        data.setHousePic(req.getHousePic());
        data.setHouseUnitPic(req.getHouseUnitPic());
        data.setHouseDoorPic(req.getHouseDoorPic());

        data.setHouseRoomPic(req.getHouseRoomPic());
        data.setHouseCustomerPic(req.getHouseCustomerPic());
        data.setHouseSaleCustomerPic(req.getHouseSaleCustomerPic());
        data.setCompanyNamePic(req.getCompanyNamePic());
        data.setCompanyPlacePic(req.getCompanyPlacePic());

        data.setCompanyWorkshopPic(req.getCompanyWorkshopPic());
        data.setCompanySaleCustomerPic(req.getCompanySaleCustomerPic());
        data.setSecondHgz(req.getSecondHgz());
        data.setSecondOdometer(req.getSecondOdometer());
        data.setSecondCarFrontPic(req.getSecondCarFrontPic());

        data.setSecondConsolePic(req.getSecondConsolePic());
        data.setSecond300Pdf(req.getSecond300Pdf());
        data.setSecondQxbPic(req.getSecondQxbPic());
        data.setSecondCarInPic(req.getSecondCarInPic());
        data.setSecondNumber(req.getSecondNumber());

        data.setOtherFilePdf(req.getOtherFilePdf());
        data.setOtherApplyNote(req.getOtherApplyNote());
        data.setApplyDatetime(new Date());

        calculateShouldBackMorgage(data);// 计算应退按揭款
        if (EIsAdvanceFund.YES.getCode().equals(data.getIsAdvanceFund())) {
            data.setShouldBackStatus(EShouldBackStatus.NO_NEED_REFUND.getCode());// 应退按揭款状态
        }

        String preNodeCode = data.getCurNodeCode();// 当前节点
        if (EButtonCode.SEND.getCode().equals(req.getDealType())) {
            // 发送
            String curNodeCode = nodeFlowBO.getNodeFlowByCurrentNode(
                preNodeCode).getNextNode();
            data.setCurNodeCode(curNodeCode);
            // 日志记录
            sysBizLogBO.saveNewAndPreEndSYSBizLog(data.getCode(),
                EBizLogType.BUDGET_ORDER, data.getCode(), preNodeCode,
                curNodeCode, null, req.getOperator());
        } else {
            // 保存
            // 日志记录
            sysBizLogBO.saveNewAndPreEndSYSBizLog(data.getCode(),
                EBizLogType.BUDGET_ORDER, data.getCode(), preNodeCode,
                preNodeCode, null, req.getOperator());
        }

        // 如果预算单对应的返点明细已经存在 先删除 再计算新的返点明细
        repointDetailBO.deleteRepointDetailByBudgetOrderCode(data.getCode());

        Department company = departmentBO.getDepartment(data.getCompanyCode());
        CreditUser user = creditUserBO.getCreditUserByCreditCode(
            data.getCreditCode(), ELoanRole.APPLY_USER);
        CarDealer carDealer = carDealerBO.getCarDealer(data.getCarDealerCode());
        // 协议外返点 和 应退按揭款（不垫资客户个人收款信息）
        List<XN632120ReqRepointDetail> repointDetailList = req
            .getRepointDetailList();
        for (XN632120ReqRepointDetail xn632120ReqRepointDetail : repointDetailList) {
            if (EUseMoneyPurpose.MORTGAGE.getCode().equals(
                xn632120ReqRepointDetail.getUseMoneyPurpose())) {// 应退按揭款
                if (EIsAdvanceFund.NO.equals(data.getIsAdvanceFund())) {// 不垫资填写退给客户个人的收款信息
                    data.setShouldBackUserName(xn632120ReqRepointDetail
                        .getCarDealerName());
                    data.setShouldBackAccountNo(xn632120ReqRepointDetail
                        .getAccountNo());
                    data.setShouldBackOpenBankName(xn632120ReqRepointDetail
                        .getOpenBankName());
                    data.setShouldBackAccountName(xn632120ReqRepointDetail
                        .getAccountName());
                }
            }
            if (EUseMoneyPurpose.PROTOCOL_OUTER.getCode().equals(
                xn632120ReqRepointDetail.getUseMoneyPurpose())) {// 协议外返点
                RepointDetail repointDetail = new RepointDetail();
                repointDetail.setType(ERepointDetailType.NORMAL.getCode());
                repointDetail.setCompanyCode(data.getCompanyCode());
                repointDetail.setCompanyName(company.getName());
                repointDetail.setBudgetCode(data.getCode());
                repointDetail.setUserName(data.getCustomerName());
                repointDetail.setIdNo(user.getIdNo());
                repointDetail.setCarDealerName(xn632120ReqRepointDetail
                    .getCarDealerName());// 单位名称（汽车经销商）
                repointDetail.setCarType(data.getCarType());
                repointDetail.setLoanAmount(data.getLoanAmount());
                repointDetail.setBankRate(data.getBankRate());
                Double benchmarkRate = benchmarkRate(data);
                repointDetail.setBenchmarkRate(benchmarkRate);
                repointDetail.setFee(fee);
                repointDetail
                    .setUseMoneyPurpose(EUseMoneyPurpose.PROTOCOL_OUTER
                        .getCode());
                repointDetail.setRepointAmount(StringValidater
                    .toLong(xn632120ReqRepointDetail.getRepointAmount()));// 返点金额
                repointDetail.setAccountNo(xn632120ReqRepointDetail
                    .getAccountNo());// 账号
                repointDetail.setOpenBankName(xn632120ReqRepointDetail
                    .getOpenBankName());// 开户行
                repointDetail.setAccountName(xn632120ReqRepointDetail
                    .getAccountName());// 户名
                repointDetail.setCurNodeCode(ERepointDetailStatus.GENERATE
                    .getCode());
                repointDetailBO.saveRepointDetail(repointDetail);
            }
        }
        // 协议内返点（计算返回的返点明细对象里有 ：返点用款用途 汽车经销商收款账号编号 和这个账号对应比例的返点金额 基准利率 四个字段）
        if (EBudgetType.NORMAL.getCode().equals(req.getType())) {// 正常单
            Bank bank = bankBO.getBankBySubbranch(data.getLoanBankCode());
            List<CollectBankcard> carDealerCollectBankcard = collectBankcardBO
                .queryCollectBankcardByCompanyCodeAndTypeAndBankCode(
                    data.getCarDealerCode(),
                    ECollectBankcardType.DEALER_REBATE.getCode(),
                    bank.getBankCode());// 经销商与本单业务银行类型对应的返点账号集合
            if (null != carDealerCollectBankcard) {
                List<RepointDetail> innerRepointDetailList = repointDetailAO
                    .calculateRepointDetail(data);
                for (RepointDetail innerRepointDetail : innerRepointDetailList) {
                    innerRepointDetail.setType(ERepointDetailType.NORMAL
                        .getCode());
                    innerRepointDetail.setCompanyCode(data.getCompanyCode());
                    innerRepointDetail.setCompanyName(company.getName());
                    innerRepointDetail.setBudgetCode(data.getCode());
                    innerRepointDetail.setUserName(data.getCustomerName());

                    innerRepointDetail.setIdNo(user.getIdNo());
                    innerRepointDetail
                        .setCarDealerCode(data.getCarDealerCode());
                    innerRepointDetail
                        .setCarDealerName(carDealer.getFullName());
                    innerRepointDetail.setCarType(data.getCarModel());
                    innerRepointDetail.setLoanAmount(data.getLoanAmount());

                    innerRepointDetail.setBankRate(data.getGlobalRate());
                    innerRepointDetail.setFee(fee);
                    CollectBankcard collectBankcard = collectBankcardBO
                        .getCollectBankcard(innerRepointDetail.getAccountCode());
                    innerRepointDetail.setAccountNo(collectBankcard
                        .getBankcardNumber());
                    innerRepointDetail.setOpenBankName(collectBankcard
                        .getSubbranch());
                    innerRepointDetail.setAccountName(collectBankcard
                        .getRealName());
                    innerRepointDetail
                        .setCurNodeCode(ERepointDetailStatus.GENERATE.getCode());
                    repointDetailBO.saveRepointDetail(innerRepointDetail);
                }
            }
        }

        // 删除
        budgetOrderGpsBO.removeBudgetOrderGpsList(data.getCode());
        // 添加
        budgetOrderGpsBO.saveBudgetOrderGpsList(data.getCode(),
            req.getGpsList());

        budgetOrderBO.refresh(data);
    }

    @Override
    @Transactional
    public void approveAreaManager(String code, String operator,
            String approveResult, String approveNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.AREA_AUDIT.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是区域总经理准入审核节点，不能操作");
        }
        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                EBudgetOrderNode.AREA_AUDIT.getCode()).getNextNode());
        } else {
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                EBudgetOrderNode.AREA_AUDIT.getCode()).getBackNode());
        }
        budgetOrderBO.refreshAreaManagerApprove(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
            budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), approveNote, operator);
    }

    @Override
    @Transactional
    public void approveBranchCompany(String code, String operator,
            String approveResult, String approveNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.COMPANY_AUDIT.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是准入审查省分公司总经理审核节点，不能操作");
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();// 之前节点
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                EBudgetOrderNode.COMPANY_AUDIT.getCode()).getNextNode());
        } else {
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                EBudgetOrderNode.COMPANY_AUDIT.getCode()).getBackNode());
        }
        budgetOrderBO.refreshBranchCompanyApprove(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
            budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), approveNote, operator);
    }

    @Override
    @Transactional
    public void approveGlobalManager(String code, String operator,
            String approveResult, String approveNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.SECOND_AUDIT.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是准入审查二审节点，不能操作");
        }
        if (EBoolean.YES.getCode().equals(budgetOrder.getIsLogistics())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点处于物流传递中，不能操作");
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();// 当前节点 准入审核二审
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            // 审核通过
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                preCurrentNode).getNextNode());
            // 预算单日志记录 收尾准入审核二审日志 预算单节点更新为垫资审核中但不生成日志
            sysBizLogBO.refreshPreSYSBizLog(EBizLogType.BUDGET_ORDER,
                budgetOrder.getCode(), preCurrentNode, approveNote, operator);
            // 产生手续费
            budgetOrderFeeBO.saveBudgetOrderFee(budgetOrder, operator);
            // 判断预算单是否垫资
            if (EIsAdvanceFund.NO.getCode().equals(
                budgetOrder.getIsAdvanceFund())) {
                // 不垫资 进入银行放款流程第一步
                EBudgetOrderNode bankLoanNode = null;
                Department company = departmentBO.getDepartment(budgetOrder
                    .getCompanyCode());
                if (ECity.WENZHOU.getValue().equals(company.getCityNo())) {
                    // 本地
                    bankLoanNode = EBudgetOrderNode.SALESMAN_SEND_LOGISTICS;
                } else {
                    // 外地
                    bankLoanNode = EBudgetOrderNode.BRANCH_SEND_LOGISTICS;
                }
                budgetOrder.setCurNodeCode(bankLoanNode.getCode());
                sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
                    EBizLogType.BANK_LOAN_COMMIT, budgetOrder.getCode(),
                    budgetOrder.getCurNodeCode());
                budgetOrderBO.bankLoanConfirmSubmitBank(budgetOrder);
                // 当前节点
                String curNodeCode = budgetOrder.getCurNodeCode();
                String nextNodeCode = nodeFlowBO.getNodeFlowByCurrentNode(
                    curNodeCode).getNextNode();
                // 生成资料传递
                logisticsBO.saveLogistics(ELogisticsType.BUDGET.getCode(),
                    budgetOrder.getCode(), budgetOrder.getSaleUserId(),
                    curNodeCode, nextNodeCode);
                // 产生物流单后改变状态为物流传递中
                budgetOrder.setIsLogistics(EBoolean.YES.getCode());
                budgetOrderBO.updateIsLogistics(budgetOrder);
            } else {
                // 垫资
                // 预算单节点改为垫资审核（进入垫资审核流程）
                budgetOrder.setCurNodeCode(EBudgetOrderNode.ADVANCE_FUND_AUDIT
                    .getCode());
                // 生成垫资单判断是本地公司业务还是外地公司业务
                Department company = departmentBO.getDepartment(budgetOrder
                    .getCompanyCode());
                if (ECity.WENZHOU.getValue().equals(company.getCityNo())) {
                    // 本地业务 打款给汽车经销商
                    AdvanceFund data = new AdvanceFund();
                    data.setBudgetCode(budgetOrder.getCode());
                    data.setType(EAdvanceType.PARENT_BIZ.getCode());
                    data.setCustomerName(budgetOrder.getCustomerName());
                    data.setCompanyCode(budgetOrder.getCompanyCode());
                    data.setCarDealerCode(budgetOrder.getCarDealerCode());
                    data.setUseAmount(budgetOrder.getShouldBackAmount());// 应退按揭款
                    data.setLoanAmount(budgetOrder.getLoanAmount());
                    data.setServiceCharge(budgetOrder.getServiceCharge());
                    data.setServiceChargeWay(budgetOrder.getServiceChargeWay());
                    data.setGpsFee(budgetOrder.getGpsFee());
                    data.setGpsFeeWay(budgetOrder.getGpsFeeWay());
                    data.setLoanBankCode(budgetOrder.getLoanBankCode());
                    data.setIsAdvanceFund(budgetOrder.getIsAdvanceFund());
                    CollectBankcard condition = new CollectBankcard();
                    condition.setCompanyCode(budgetOrder.getCarDealerCode());
                    List<CollectBankcard> list = collectBankcardBO
                        .queryCollectBankcardList(condition);
                    String collectBankcardCode = null;
                    for (CollectBankcard collectBankcard : list) {
                        if (ECollectBankcardType.DEALER_COLLECT.getCode()
                            .equals(collectBankcard.getType())) {
                            // 经销商的收款账号
                            collectBankcardCode = collectBankcard.getCode();
                        }
                    }
                    // 汽车经销商的账号
                    data.setCollectBankcardCode(collectBankcardCode);
                    data.setUpdater(operator);
                    data.setUpdateDatetime(new Date());
                    data.setCurNodeCode(EAdvanceFundNode.PARENT_CONFIRM
                        .getCode());

                    String advanceFundCode = advanceFundBO
                        .saveAdvanceFund(data);
                    sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
                        EBizLogType.ADVANCE_FUND_PARENT, advanceFundCode,
                        data.getCurNodeCode());

                } else {
                    // 外地业务 打款给分公司
                    AdvanceFund data = new AdvanceFund();
                    data.setBudgetCode(budgetOrder.getCode());
                    data.setType(EAdvanceType.BRANCH_BIZ.getCode());
                    data.setCustomerName(budgetOrder.getCustomerName());
                    data.setCompanyCode(budgetOrder.getCompanyCode());
                    data.setCarDealerCode(budgetOrder.getCarDealerCode());
                    data.setUseAmount(budgetOrder.getShouldBackAmount());
                    data.setLoanAmount(budgetOrder.getLoanAmount());
                    data.setServiceCharge(budgetOrder.getServiceCharge());
                    data.setServiceChargeWay(budgetOrder.getServiceChargeWay());
                    data.setGpsFee(budgetOrder.getGpsFee());
                    data.setGpsFeeWay(budgetOrder.getGpsFeeWay());
                    data.setLoanBankCode(budgetOrder.getLoanBankCode());
                    data.setIsAdvanceFund(budgetOrder.getIsAdvanceFund());
                    CollectBankcard condition = new CollectBankcard();
                    condition.setCompanyCode(budgetOrder.getCompanyCode());
                    List<CollectBankcard> list = collectBankcardBO
                        .queryCollectBankcardList(condition);
                    String collectBankcardCode = null;
                    for (CollectBankcard collectBankcard : list) {
                        if (ECollectBankcardType.PLATFORM.getCode().equals(
                            collectBankcard.getType())) {
                            // 公司普通账户
                            collectBankcardCode = collectBankcard.getCode();
                        }
                    }
                    // 分公司的账号
                    data.setCollectBankcardCode(collectBankcardCode);
                    data.setUpdater(operator);
                    data.setUpdateDatetime(new Date());
                    data.setCurNodeCode(EAdvanceFundNode.BRANCH_CONFIRM
                        .getCode());

                    String advanceFundCode = advanceFundBO
                        .saveAdvanceFund(data);

                    sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
                        EBizLogType.ADVANCE_FUND_BRANCH, advanceFundCode,
                        data.getCurNodeCode());
                }
            }
        } else {
            // 审核不通过
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                preCurrentNode).getBackNode());
            // 预算单日志记录
            sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
                EBizLogType.BUDGET_ORDER, budgetOrder.getCode(),
                preCurrentNode, budgetOrder.getCurNodeCode(), approveNote,
                operator);

        }
        budgetOrderBO.refreshGlobalManagerApprove(budgetOrder);

    }

    @Override
    @Transactional
    public void canceOrder(String code, String operator, String cancelNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder.setCurNodeCode(EBudgetOrderNode.START_NODE.getCode());
        budgetOrderBO.canceOrder(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
            budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void bankLoanCommit(String code, Date bankCommitDatetime,
            String bankCommitNote, String operator) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.BANK_LOAN_COMMIT.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是确认提交银行节点，不能操作");
        }
        // 当前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        String nextNodeCode = getNextNodeCode(budgetOrder.getCurNodeCode(),
            EBoolean.YES.getCode());

        budgetOrder.setCurNodeCode(nextNodeCode);
        budgetOrder.setCode(code);
        budgetOrder.setBankCommitDatetime(bankCommitDatetime);
        budgetOrder.setBankCommitNote(bankCommitNote);
        budgetOrder.setOperator(operator);
        budgetOrder.setOperateDatetime(new Date());
        budgetOrderBO.refreshBankLoanCommit(budgetOrder);

        // 日志记录

        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
            budgetOrder.getCurNodeCode());

        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void bankPointPushHasLoanList(XN632144Req req) {
        List<String> codeList = req.getCodeList();
        for (String code : codeList) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            if (!EBudgetOrderNode.BANK_POINT_PUSH_LOAN_LIST.getCode().equals(
                budgetOrder.getCurNodeCode())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前节点不是银行驻点推送已放款名单节点，不能操作");
            }
        }
        for (String code : codeList) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            budgetOrder.setHasLoanListPic(req.getHasLoanListPic());
            String preCurNodeCode = budgetOrder.getCurNodeCode();// 当前节点
            NodeFlow nodeFlow = nodeFlowBO
                .getNodeFlowByCurrentNode(preCurNodeCode);
            budgetOrder.setCurNodeCode(nodeFlow.getNextNode());// 当前节点的下一个节点
            budgetOrderBO.bankPointPushHasLoanList(budgetOrder);
            // 日志记录
            EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
                budgetOrder.getCurNodeCode());
            sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
                EBizLogType.BUDGET_ORDER, budgetOrder.getCode(),
                preCurNodeCode, nodeFlow.getNextNode(), currentNode.getValue(),
                req.getOperator());
        }

    }

    @Override
    @Transactional
    public void bankLoanConfirm(XN632141Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        if (!EBudgetOrderNode.CONFIRM_RECEIVABLES.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是银行放款确认收款节点，不能操作");
        }
        String curNodeCode = budgetOrder.getCurNodeCode();// 当前节点
        budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
            curNodeCode).getNextNode());
        budgetOrder.setCode(req.getCode());
        budgetOrder.setBankFkAmount(StringValidater.toLong(req
            .getBankFkAmount()));
        budgetOrder.setBankFkDatetime(DateUtil.strToDate(
            req.getBankFkDatetime(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setBankReceiptCode(req.getBankReceiptCode());

        budgetOrder.setBankReceiptNumber(req.getBankReceiptNumber());
        budgetOrder.setBankReceiptPdf(req.getBankReceiptPdf());
        budgetOrder.setBankReceiptNote(req.getBankReceiptNote());
        budgetOrder.setOperator(req.getOperator());
        budgetOrder.setOperateDatetime(new Date());

        BankSubbranch data = bankSubbranchBO.getBankSubbranch(budgetOrder
            .getLoanBankCode());
        if (EBankType.GH.getCode().equals(data.getBankType())) {
            budgetOrder.setMakeCardStatus(EMakeCardStatus.PENDING_CARD
                .getCode());
        } else {
            budgetOrder.setMakeCardStatus(EMakeCardStatus.PENDING_RECORD
                .getCode());
        }
        budgetOrderBO.refreshBankLoanConfirm(budgetOrder);

        /****** 生成还款业务 ******/
        // 检查用户是否已经注册过
        User user = userBO.getUser(budgetOrder.getMobile(),
            EUserKind.Customer.getCode());
        String userId = null;
        if (user == null) {
            // 用户代注册并实名认证
            userId = userBO.doRegisterAndIdentify(budgetOrder.getMobile(),
                budgetOrder.getIdKind(), budgetOrder.getCustomerName(),
                budgetOrder.getIdNo());
            distributeAccount(userId, budgetOrder.getMobile(),
                EUserKind.Customer.getCode());
        } else {
            userId = user.getUserId();
        }

        // 生成还款业务时银行卡号不能为空
        if (StringUtils.isBlank(budgetOrder.getBankCardNumber())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "预算单银行卡号为空，请先导入合同！");
        }

        // 绑定用户银行卡
        String bankcardCode = bankcardBO.bind(userId,
            budgetOrder.getCustomerName(), budgetOrder.getBankCardNumber(),
            budgetOrder.getLoanBankCode(), budgetOrder.getLoanBankName());

        // 自动生成还款业务
        RepayBiz repayBiz = repayBizBO.generateCarLoanRepayBiz(budgetOrder,
            userId, bankcardCode, req.getOperator());

        budgetOrderBO.updateRepayBizCode(budgetOrder.getCode(),
            repayBiz.getCode(), userId);

        // 自动生成还款计划
        repayPlanBO.genereateNewRepayPlan(repayBiz);
        /****** 生成还款业务 ******/

        // 日志记录
        sysBizLogBO.refreshPreSYSBizLog(EBizLogType.BUDGET_ORDER,
            budgetOrder.getCode(), curNodeCode, req.getBankReceiptNote(),
            req.getOperator());

        // 月结的返点在银行放款后产生
        CarDealer carDealer = carDealerBO.getCarDealer(budgetOrder
            .getCarDealerCode());
        if (carDealer.getSettleWay().equals(ESettleWay.MONTH.getCode())) {
            RepointDetail condition = new RepointDetail();
            condition.setBudgetCode(budgetOrder.getCode());
            List<RepointDetail> list = repointDetailBO
                .queryRepointDetailList(condition);
            for (RepointDetail repointDetail : list) {
                repointDetail.setCurNodeCode(ERepointDetailStatus.TODO_PAY
                    .getCode());
                repointDetailBO.updateCurNodeCode(repointDetail);
            }
        }
    }

    @Override
    @Transactional
    public void carPledgeCommit(String code, Date pledgeCommitDatetime,
            String pledgeCommitNote, String operator) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);

        if (!EBudgetOrderNode.LOCAL_SUBMIT_BANK.getCode().equals(
            budgetOrder.getPledgeCurNodeCode())
                && !EBudgetOrderNode.OUT_SUBMIT_BANK.getCode().equals(
                    budgetOrder.getPledgeCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是车辆抵押确认提交银行节点，不能操作");
        }

        // 当前节点
        String preCurrentNode = budgetOrder.getPledgeCurNodeCode();
        String nextNodeCode = getNextNodeCode(preCurrentNode,
            EBoolean.YES.getCode());

        budgetOrder.setPledgeCurNodeCode(nextNodeCode);
        budgetOrder.setCode(code);
        budgetOrder.setPledgeCommitDatetime(pledgeCommitDatetime);
        budgetOrder.setPledgeCommitNote(pledgeCommitNote);
        budgetOrder.setOperator(operator);
        budgetOrder.setOperateDatetime(new Date());
        budgetOrderBO.refreshCarPledgeCommit(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
            budgetOrder.getPledgeCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void carPledgeConfirm(XN632191Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        String preCurrentNode = budgetOrder.getPledgeCurNodeCode();// 当前抵押流程节点

        if (!EBudgetOrderNode.TODO_LOCAL_PLEDGE_ACHIEVE.getCode().equals(
            preCurrentNode)
                && !EBudgetOrderNode.TODO_OUT_PLEDGE_ACHIEVE.getCode().equals(
                    preCurrentNode)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是车辆抵押流程待提交抵押完成节点，不能操作");
        }
        if (EBoolean.YES.getCode().equals(budgetOrder.getIsLogistics())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点处于物流传递中，不能操作");
        }

        if (EBudgetOrderNode.TODO_LOCAL_PLEDGE_ACHIEVE.getCode().equals(
            preCurrentNode)) {
            // 温州本地 抵押完成后预算单入档状态改为待入档
            budgetOrder.setEnterFileStatus(EEnterFileStatus.TODO.getCode());
            budgetOrderBO.updateEnterFileStatus(budgetOrder);
        }

        if (EBudgetOrderNode.TODO_OUT_PLEDGE_ACHIEVE.getCode().equals(
            preCurrentNode)) {
            // 外地 生成资料传递（分公司寄送抵押材料给总公司）
            NodeFlow nodeFlow = nodeFlowBO
                .getNodeFlowByCurrentNode(preCurrentNode);
            NodeFlow flow = nodeFlowBO.getNodeFlowByCurrentNode(nodeFlow
                .getNextNode());
            logisticsBO.saveLogistics(ELogisticsType.BUDGET.getCode(),
                budgetOrder.getCode(), budgetOrder.getSaleUserId(),
                nodeFlow.getNextNode(), flow.getNextNode());
            // 产生物流单后改变状态为物流传递中
            budgetOrder.setIsLogistics(EBoolean.YES.getCode());
            budgetOrderBO.updateIsLogistics(budgetOrder);
        }
        // 更改预算单车辆抵押流程节点
        String nextNodeCode = getNextNodeCode(preCurrentNode,
            EBoolean.YES.getCode());
        budgetOrder.setPledgeCurNodeCode(nextNodeCode);
        budgetOrder.setCode(req.getCode());
        budgetOrder.setOperator(req.getOperator());
        budgetOrder.setOperateDatetime(new Date());
        budgetOrder.setGreenBigCode(req.getGreenBigCode());
        budgetOrder.setGreenBigSmj(req.getGreenBigSmj());
        SYSUser user = sysUserBO.getUser(req.getOperator());
        budgetOrder.setOperateDepartment(user.getDepartmentCode());
        budgetOrderBO.refreshCarPledgeConfirm(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
            budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());
    }

    // 逻辑
    // 1、前提条件判断
    // 2、没完善可再次补充，完善则入档完成
    @Override
    @Transactional
    public void carLoanArchive(XN632200Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        if (!EEnterFileStatus.TODO.getCode().equals(
            budgetOrder.getEnterFileStatus())
                && !EEnterFileStatus.TODO_MAKEUP.getCode().equals(
                    budgetOrder.getEnterFileStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "预算单入档状态不是待入档或待补录，不能操作");
        }

        if (EBoolean.YES.getCode().equals(req.getIsComplete())
                && null == req.getStorePlace()) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "存放位置不能为空！");
        }

        if (StringUtils.isNotBlank(req.getEmergencyMobile1())) {
            PhoneUtil.checkMobile(req.getEmergencyMobile1());
        }
        if (StringUtils.isNotBlank(req.getEmergencyMobile2())) {
            PhoneUtil.checkMobile(req.getEmergencyMobile2());
        }
        if (StringUtils.isNotBlank(req.getGuarantor1Mobile())) {
            PhoneUtil.checkMobile(req.getGuarantor1Mobile());
        }
        if (StringUtils.isNotBlank(req.getGuarantor2Mobile())) {
            PhoneUtil.checkMobile(req.getGuarantor2Mobile());
        }
        budgetOrder.setInsuranceCompanyCode(req.getInsuranceCompanyCode());
        budgetOrder.setCarColor(req.getCarColor());
        budgetOrder.setCarBrand(req.getCarBrand());

        budgetOrder.setEngineNo(req.getEngineNo());

        budgetOrder.setForceInsurance(req.getForceInsurance());

        budgetOrder.setCommerceInsurance(req.getCommerceInsurance());
        budgetOrder
            .setInsuranceEffectDatetime(DateUtil.strToDate(
                req.getInsuranceEffectDatetime(),
                DateUtil.FRONT_DATE_FORMAT_STRING));

        budgetOrder.setInsuranceBank(req.getInsuranceBank());
        budgetOrder.setRegCertificateCode(req.getRegCertificateCode());

        budgetOrder.setGuarantor1Name(req.getGuarantor1Name());
        budgetOrder.setGuarantor1Mobile(req.getGuarantor1Mobile());
        budgetOrder.setGuarantor2Name(req.getGuarantor2Name());
        budgetOrder.setGuarantor2Mobile(req.getGuarantor2Mobile());
        budgetOrder.setBankCardNumber(req.getBankCardNumber());
        budgetOrder.setBillDatetime(StringValidater.toInteger(req
            .getBillDatetime()));

        if (EBoolean.YES.getCode().equals(req.getIsComplete())) {
            budgetOrder.setEnterFileStatus(EEnterFileStatus.ACHIEVE.getCode());
        } else {
            budgetOrder.setEnterFileStatus(EEnterFileStatus.TODO_MAKEUP
                .getCode());
        }
        budgetOrder.setMonthAmount(StringValidater.toLong(req
            .getRepayMonthAmount()));
        budgetOrder.setRepayBankDate(StringValidater.toInteger(req
            .getRepayBankDate()));
        budgetOrder.setRepayFirstMonthAmount(StringValidater.toLong(req
            .getRepayFirstMonthAmount()));
        budgetOrder
            .setRepayFirstMonthDatetime(DateUtil.strToDate(
                req.getRepayFirstMonthDatetime(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setRepayMonthAmount(StringValidater.toLong(req
            .getRepayMonthAmount()));

        budgetOrder.setIsComplete(req.getIsComplete());
        budgetOrder.setStorePlace(req.getStorePlace());
        budgetOrder.setFileRemark(req.getFileRemark());

        budgetOrder.setOperator(req.getOperator());
        budgetOrder.setOperateDatetime(new Date());
        SYSUser sysUser = sysUserBO.getUser(req.getOperator());
        budgetOrder.setOperateDepartment(sysUser.getDepartmentCode());

        if (CollectionUtils.isNotEmpty(req.getFileList())) {
            StringBuilder fileListBuilder = new StringBuilder();
            for (String file : req.getFileList()) {
                fileListBuilder.append(file).append(",");
            }
            String string = fileListBuilder.toString();
            String fileList = string.substring(0, string.length() - 1);
            budgetOrder.setFileList(fileList);
        }

        budgetOrderBO.refreshCarLoanArchive(budgetOrder);
        // 更新还款业务
        repayBizBO.refreshRepayBiz(budgetOrder);
        // 更新还款计划
        repayPlanBO.refreshRepayPlan(budgetOrder);
    }

    // 分配账号
    private void distributeAccount(String userId, String mobile, String kind) {
        List<String> currencyList = new ArrayList<String>();
        currencyList.add(ECurrency.CNY.getCode());
        currencyList.add(ECurrency.JF.getCode());

        for (String currency : currencyList) {
            accountBO.distributeAccount(userId, mobile,
                EAccountType.getAccountType(kind), currency);
        }
    }

    // 获取下一个节点
    public String getNextNodeCode(String curNodeCode, String approveResult) {
        NodeFlow nodeFolw = nodeFlowBO.getNodeFlowByCurrentNode(curNodeCode);
        String nextNodeCode = null;
        if (EBoolean.YES.getCode().equals(approveResult)) {
            nextNodeCode = nodeFolw.getNextNode();
        } else if (EBoolean.NO.getCode().equals(approveResult)) {
            nextNodeCode = nodeFolw.getBackNode();
        }
        return nextNodeCode;
    }

    @Override
    public Paginable<BudgetOrder> queryBudgetOrderPage(int start, int limit,
            BudgetOrder condition) {
        Paginable<BudgetOrder> page = budgetOrderBO.getPaginable(start, limit,
            condition);
        List<BudgetOrder> list = page.getList();
        for (BudgetOrder budgetOrder : list) {
            initBudget(budgetOrder);
        }
        return page;
    }

    @Override
    public List<BudgetOrder> queryBudgetOrderList(BudgetOrder condition) {
        List<BudgetOrder> list = budgetOrderBO.queryBudgetOrderList(condition);

        for (BudgetOrder budgetOrder : list) {
            initBudget(budgetOrder);
        }

        return list;
    }

    @Override
    public BudgetOrder getBudgetOrder(String code) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        initBudget(budgetOrder);

        // 获取我司贷款成数区间
        LoanCs loanCsCondition = new LoanCs();
        loanCsCondition.setType(budgetOrder.getShopWay());
        List<LoanCs> loanCsList = loanCsBO.queryLoanCsList(loanCsCondition);
        if (CollectionUtils.isNotEmpty(loanCsList)) {
            LoanCs resultCs = loanCsList.get(0);
            budgetOrder.setCompanyLoanCsSection(resultCs.getMinCs() + "-"
                    + resultCs.getMaxCs());
        }
        return budgetOrder;
    }

    @Override
    public List<BudgetOrder> queryBudgetOrderByList(List<String> list) {
        List<BudgetOrder> budgetOrderList = new ArrayList<BudgetOrder>();
        for (String code : list) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            initBudget(budgetOrder);

            // 获取我司贷款成数区间
            LoanCs loanCsCondition = new LoanCs();
            loanCsCondition.setType(budgetOrder.getShopWay());
            List<LoanCs> loanCsList = loanCsBO.queryLoanCsList(loanCsCondition);
            if (CollectionUtils.isNotEmpty(loanCsList)) {
                LoanCs resultCs = loanCsList.get(0);
                budgetOrder.setCompanyLoanCsSection(resultCs.getMinCs() + "-"
                        + resultCs.getMaxCs());
            }
            budgetOrderList.add(budgetOrder);
        }
        return budgetOrderList;
    }

    private void initBudget(BudgetOrder budgetOrder) {
        String file = budgetOrder.getFileList();
        if (StringUtils.isNotBlank(file)) {
            String[] fileArray = file.split(",");
            List<String> fileList = new ArrayList<String>();
            for (String data : fileArray) {
                fileList.add(data);
            }
            budgetOrder.setFileListArray(fileList);
        }

        if (StringUtils.isNotBlank(budgetOrder.getBankReceiptCode())) {
            CollectBankcard receiptBank = collectBankcardBO
                .getCollectBankcard(budgetOrder.getBankReceiptCode());
            if (null != receiptBank) {
                budgetOrder.setBankReceiptName(receiptBank.getBankName());
            }
        }

        if (StringUtils.isNotBlank(budgetOrder.getCarDealerCode())) {
            CarDealer carDealer = carDealerBO.getCarDealer(budgetOrder
                .getCarDealerCode());

            budgetOrder.setCarDealerName(carDealer.getFullName());
            budgetOrder.setCarDealerPhone(carDealer.getContactPhone());
        }

        if (StringUtils.isNotBlank(budgetOrder.getInsuranceCompanyCode())) {
            InsuranceCompany insuranceCompany = insuranceCompanyBO
                .getInsuranceCompany(budgetOrder.getInsuranceCompanyCode());
            budgetOrder.setInsuranceCompanyName(insuranceCompany.getName());
        }

        if (StringUtils.isNotBlank(budgetOrder.getLoanBankCode())) {
            Bank loanBank = bankBO.getBankBySubbranch(budgetOrder
                .getLoanBankCode());
            budgetOrder.setLoanBankName(loanBank.getBankName());
        }

        if (StringUtils.isNotBlank(budgetOrder.getLoanBankCode())) {
            BankSubbranch subbranch = bankSubbranchBO
                .getBankSubbranch(budgetOrder.getLoanBankCode());

            budgetOrder.setBankSubbranch(subbranch);
        }

        if (StringUtils.isNotBlank(budgetOrder.getOperateDepartment())) {
            Department department = departmentBO.getDepartment(budgetOrder
                .getOperateDepartment());

            budgetOrder.setOperateDepartmentName(department.getName());
        }

        // 业务公司名称
        if (StringUtils.isNotBlank(budgetOrder.getCompanyCode())) {
            Department company = departmentBO.getDepartment(budgetOrder
                .getCompanyCode());
            budgetOrder.setCompanyName(company.getName());
        }

        if (StringUtils.isNotBlank(budgetOrder.getOperator())) {
            SYSUser user = sysUserBO.getUser(budgetOrder.getOperator());
            budgetOrder.setOperatorName(user.getRealName());
        }

        if (StringUtils.isNotBlank(budgetOrder.getSaleUserId())) {
            SYSUser saleUser = sysUserBO.getUser(budgetOrder.getSaleUserId());
            budgetOrder.setSaleUserName(saleUser.getRealName());
        }
        List<BudgetOrderGps> budgetOrderGpsList = budgetOrderGpsBO
            .queryBudgetOrderGpsList(budgetOrder.getCode());
        if (CollectionUtils.isNotEmpty(budgetOrderGpsList)) {
            budgetOrder.setBudgetOrderGpsList(budgetOrderGpsList);
        }

        if (StringUtils.isNotBlank(budgetOrder.getCreditCode())) {
            Credit credit = creditBO.getCredit(budgetOrder.getCreditCode());
            budgetOrder.setCredit(credit);
        }

        if (StringUtils.isNotBlank(budgetOrder.getGuarantor1IdNo())) {
            if (budgetOrder.getGuarantor1IdNo().length() != 18) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "担保人1身份证号不合法！");
            }
            String sex = getSexByIdNo(budgetOrder.getGuarantor1IdNo());
            budgetOrder.setGuarantor1Sex(sex);
        }

        if (StringUtils.isNotBlank(budgetOrder.getGuarantor2IdNo())) {
            if (budgetOrder.getGuarantor1IdNo().length() != 18) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "担保人2身份证号不合法！");
            }
            String sex = getSexByIdNo(budgetOrder.getGuarantor2IdNo());
            budgetOrder.setGuarantor2Sex(sex);
        }

        if (StringUtils.isNotBlank(budgetOrder.getRepayBizCode())) {
            List<RepayPlan> planList = repayPlanBO
                .queryRepayPlanListByRepayBizCode(budgetOrder.getRepayBizCode());
            budgetOrder.setRepayPlansList(planList);
        }
        // 担保打印人
        if (StringUtils.isNotBlank(budgetOrder.getGuarantPrintUser())) {
            SYSUser user = sysUserBO.getUser(budgetOrder.getGuarantPrintUser());
            budgetOrder.setGuarantPrintName(user.getRealName());
        }

        // 垫资表
        AdvanceFund advanceFund = advanceFundBO
            .getAdvanceFundPageByBudgetOrder(budgetOrder.getCode());
        if (advanceFund != null) {
            budgetOrder.setAdvanceFund(advanceFund);
        }

        // 获取返点列表
        /*
         * List<RepointDetail> shouldBackRepointList = repointDetailBO
         * .queryRepointDetailList(budgetOrder.getCode(),
         * ERepointDetailUseMoneyPurpose.SHOULD_BACK.getCode());
         */
        ArrayList<RepointDetail> shouldBackRepointList = new ArrayList<RepointDetail>();
        RepointDetail mortgageRepointDetail = new RepointDetail();
        mortgageRepointDetail.setUseMoneyPurpose(EUseMoneyPurpose.MORTGAGE
            .getCode());
        mortgageRepointDetail.setRepointAmount(budgetOrder
            .getShouldBackAmount());
        if (EIsAdvanceFund.YES.getCode().equals(budgetOrder.getIsAdvanceFund())) {
            // 垫资
            CollectBankcard condition = new CollectBankcard();
            condition.setCompanyCode(budgetOrder.getCarDealerCode());
            condition.setType(ECollectBankcardType.DEALER_COLLECT.getCode());
            List<CollectBankcard> list = collectBankcardBO
                .queryCollectBankcardByCompanyCodeAndType(condition);
            if (CollectionUtils.isNotEmpty(list)) {
                CollectBankcard collectBankcard = list.get(0);
                mortgageRepointDetail.setAccountName(collectBankcard
                    .getRealName());
                mortgageRepointDetail.setAccountNo(collectBankcard
                    .getBankcardNumber());
                mortgageRepointDetail.setOpenBankName(collectBankcard
                    .getSubbranch());
            }
            CarDealer carDealer = carDealerBO.getCarDealer(budgetOrder
                .getCarDealerCode());
            mortgageRepointDetail.setCarDealerName(carDealer.getFullName());
        } else {
            // 不垫资
            mortgageRepointDetail.setCarDealerName(budgetOrder
                .getShouldBackUserName());
            mortgageRepointDetail.setAccountName(budgetOrder
                .getShouldBackAccountName());
            mortgageRepointDetail.setAccountNo(budgetOrder
                .getShouldBackAccountNo());
            mortgageRepointDetail.setOpenBankName(budgetOrder
                .getShouldBackOpenBankName());
        }
        shouldBackRepointList.add(mortgageRepointDetail);
        budgetOrder.setRepointDetailList1(shouldBackRepointList);

        List<RepointDetail> proInRepointList = repointDetailBO
            .queryRepointDetailList(budgetOrder.getCode(),
                ERepointDetailUseMoneyPurpose.PROIN_REPOINT.getCode());
        budgetOrder.setRepointDetailList2(proInRepointList);

        List<RepointDetail> proOutRepointList = repointDetailBO
            .queryRepointDetailList(budgetOrder.getCode(),
                ERepointDetailUseMoneyPurpose.PROOUT_REPOINT.getCode());
        budgetOrder.setRepointDetailList3(proOutRepointList);
    }

    private String getSexByIdNo(String idNo) {
        /**
         * 根据身份编号获取性别
         * @param idCard 身份编号
         * @return 性别(M-男，F-女，N-未知)
         */
        String sGender = null;

        String sCardNum = idNo.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "男";
        } else {
            sGender = "女";
        }
        return sGender;
    }

    @Override
    public Paginable<BudgetOrder> queryBudgetOrderPageByRoleCode(int start,
            int limit, BudgetOrder condition) {
        Paginable<BudgetOrder> page = budgetOrderBO.getPaginableByRoleCode(
            start, limit, condition);
        List<BudgetOrder> list = page.getList();
        for (BudgetOrder budgetOrder : list) {
            initBudget(budgetOrder);
        }
        return page;
    }

    @Override
    @Transactional
    public void approveMakeCard(String code, String makeCardRemark,
            String operator) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EMakeCardStatus.PENDING_CARD.getCode().equals(
            budgetOrder.getMakeCardStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前状态不是待制卡状态，不能操作！");
        }
        BankSubbranch data = bankSubbranchBO.getBankSubbranch(budgetOrder
            .getLoanBankCode());
        if (EBankType.GH.getCode().equals(data.getBankType())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "银行行别不是工行，不能操作！");
        }
        budgetOrder.setMakeCardStatus(EMakeCardStatus.ALREADY_MADE_CARD
            .getCode());
        budgetOrder.setMakeCardRemark(makeCardRemark);
        budgetOrder.setMakeCardOperator(operator);
        budgetOrderBO.approveMakeCard(budgetOrder);
    }

    @Override
    @Transactional
    public void cardMaking(String code, String bankCardNumber,
            String makeCardRemark) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EMakeCardStatus.PENDING_RECORD.getCode().equals(
            budgetOrder.getMakeCardStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前状态不是待回录状态，不能操作！");
        }

        BankSubbranch data = bankSubbranchBO.getBankSubbranch(budgetOrder
            .getLoanBankCode());
        if (EBankType.GH.getCode().equals(data.getBankType())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "银行行别是工行，不能操作！");
        }
        budgetOrder.setMakeCardStatus(EMakeCardStatus.BACK_RECORD.getCode());
        budgetOrder.setBankCardNumber(bankCardNumber);
        budgetOrder.setMakeCardRemark(makeCardRemark);
        budgetOrderBO.refreshCardMaking(budgetOrder);
    }

    @Override
    @Transactional
    public void entryPreservation(XN632220Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        if (!EFbhStatus.PENDING_ENTRY.getCode().equals(
            budgetOrder.getFbhStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前不是待录入发保合状态，不能操作！");
        }
        budgetOrder.setDeliveryDatetime(DateUtil.strToDate(
            req.getDeliveryDatetime(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setFbhStatus(EFbhStatus.TO_PENDING_ENTRY.getCode());// 已录入
        Long loanAmount = budgetOrder.getLoanAmount();
        Long currentInvoicePrice = StringValidater.toLong(req
            .getCurrentInvoicePrice());
        // 新我司贷款成数
        double companyLoanCs = AmountUtil.div(loanAmount, currentInvoicePrice);
        // 判断现发票价格和发票价格是否匹配
        if (currentInvoicePrice.longValue() == budgetOrder.getInvoicePrice()
            .longValue()) {
            // 匹配
            budgetOrder.setIsRightInvoice(EBoolean.YES.getCode());
        } else {
            // 不匹配
            budgetOrder.setIsRightInvoice(EBoolean.NO.getCode());
            // 获取我司贷款成数区间 标准
            LoanCs loanCsCondition = new LoanCs();
            loanCsCondition.setType(budgetOrder.getShopWay());
            List<LoanCs> loanCsList = loanCsBO.queryLoanCsList(loanCsCondition);
            LoanCs resultCs = null;
            if (CollectionUtils.isNotEmpty(loanCsList)) {
                resultCs = loanCsList.get(0);
            }
            if (null != resultCs) {
                // 判断新我司贷款成数是否在标准内
                if (companyLoanCs >= resultCs.getMaxCs()
                        || companyLoanCs <= resultCs.getMinCs()) {
                    // 不在我司准入贷款成数标准内 进入发票不匹配流程
                    budgetOrder.setCancelNodeCode(budgetOrder.getCurNodeCode());
                    budgetOrder
                        .setCurNodeCode(EBudgetOrderNode.INVOICE_MISMATCH_APPLY
                            .getCode());
                    budgetOrder.setFrozenStatus(EBudgetFrozenStatus.FROZEN
                        .getCode());
                    budgetOrderBO.invoiceMismatchApply(budgetOrder);
                    // 记录日志
                    sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
                        EBizLogType.INVOICE_MISMATCH, budgetOrder.getCode(),
                        budgetOrder.getCurNodeCode());
                    budgetOrder.setFbhStatus(EFbhStatus.INVOICE_MISMATCH_TODO
                        .getCode());// 发票不匹配处理中
                }
            }
        }
        budgetOrder.setCurrentInvoicePrice(StringValidater.toLong(req
            .getCurrentInvoicePrice()));
        // 原贷款成数
        budgetOrder.setPreCompanyLoanCs(budgetOrder.getCompanyLoanCs());
        // 新贷款成数
        budgetOrder.setCompanyLoanCs(companyLoanCs);
        budgetOrder.setInvoice(req.getInvoice());
        budgetOrder.setCertification(req.getCertification());

        budgetOrder.setForceInsurance(req.getForceInsurance());
        budgetOrder.setBusinessInsurance(req.getBusinessInsurance());
        budgetOrder.setMotorRegCertification(req.getMotorRegCertification());
        budgetOrder.setPdPdf(req.getPdPdf());
        budgetOrder.setFbhRemark(req.getFbhRemark());
        budgetOrderBO.entryPreservation(budgetOrder);
        // 已录入发保合 把返点明细状态改为待支付
        if (EFbhStatus.TO_PENDING_ENTRY.getCode().equals(
            budgetOrder.getFbhStatus())) {
            CarDealer carDealer = carDealerBO.getCarDealer(budgetOrder
                .getCarDealerCode());
            if (!carDealer.getSettleWay().equals(ESettleWay.MONTH.getCode())) {// 不是月结的在垫资后并且录完发保合可以返点
                RepointDetail condition = new RepointDetail();
                condition.setBudgetCode(budgetOrder.getCode());
                List<RepointDetail> list = repointDetailBO
                    .queryRepointDetailList(condition);
                for (RepointDetail repointDetail : list) {
                    repointDetail.setCurNodeCode(ERepointDetailStatus.TODO_PAY
                        .getCode());
                    repointDetailBO.updateCurNodeCode(repointDetail);
                }
            }
        }
    }

    @Override
    @Transactional
    public void invoiceMismatchApply(XN632230Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        if (!EBudgetOrderNode.INVOICE_MISMATCH_APPLY.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是发票不匹配申请节点，不能操作！");
        }
        XN632234Res res = modifyLoanAmountCalculateData(req.getCode(),
            req.getLoanAmount());

        // 1个贷款金额 3个贷款成数 6个费用1个应退按揭款
        budgetOrder.setPreLoanAmount(budgetOrder.getLoanAmount());
        budgetOrder.setLoanAmount(StringValidater.toLong(req.getLoanAmount()));
        budgetOrder.setPreCompanyLoanCs(budgetOrder.getCompanyLoanCs());
        budgetOrder.setCompanyLoanCs(StringValidater.toDouble(res
            .getCompanyLoanCs()));
        budgetOrder.setPreBankLoanCs(budgetOrder.getBankLoanCs());
        budgetOrder
            .setBankLoanCs(StringValidater.toDouble(res.getBankLoanCs()));
        budgetOrder.setPreGlobalRate(budgetOrder.getGlobalRate());
        budgetOrder
            .setGlobalRate(StringValidater.toDouble(res.getGlobalRate()));
        if (StringUtils.isNotBlank(res.getFxAmount())) {
            budgetOrder.setPreFxAmount(budgetOrder.getFxAmount());
            budgetOrder.setFxAmount(StringValidater.toLong(res.getFxAmount()));
        }
        if (StringUtils.isNotBlank(res.getLyAmount())) {
            budgetOrder.setPreLyAmount(budgetOrder.getLyAmount());
            budgetOrder.setLyAmount(StringValidater.toLong(res.getLyAmount()));
        }
        if (StringUtils.isNotBlank(res.getGpsFee())) {
            budgetOrder.setPreGpsFee(budgetOrder.getGpsFee());
            budgetOrder.setGpsFee(StringValidater.toLong(res.getGpsFee()));
        }
        if (StringUtils.isNotBlank(res.getOtherFee())) {
            budgetOrder.setPreOtherFee(budgetOrder.getOtherFee());
            budgetOrder.setOtherFee(StringValidater.toLong(res.getOtherFee()));
        }
        budgetOrder.setPreGpsDeduct(budgetOrder.getGpsDeduct());
        budgetOrder.setGpsDeduct(StringValidater.toLong(res.getGpsDeduct()));
        budgetOrder.setPreOilSubsidy(budgetOrder.getOilSubsidy());
        budgetOrder.setOilSubsidy(StringValidater.toLong(res.getOilSubsidy()));
        budgetOrder.setPreShouldBackAmount(budgetOrder.getShouldBackAmount());
        // 生成新返点明细数据
        List<XN632290Res> list = res.getList();
        for (XN632290Res xn632290Res : list) {
            if (EUseMoneyPurpose.MORTGAGE.getCode().equals(// 应退按揭款（ 垫资）
                xn632290Res.getUseMoneyPurpose())) {
                budgetOrder.setPreShouldBackAmount(budgetOrder
                    .getShouldBackAmount());// 原来的应退按揭款
                budgetOrder.setShouldBackAmount(StringValidater
                    .toLong(xn632290Res.getRepointAmount()));// 新应退按揭款
            }
            if (EUseMoneyPurpose.PROTOCOL_INNER.getCode().equals(
                xn632290Res.getUseMoneyPurpose())) {// 协议内返点
                Department company = departmentBO.getDepartment(budgetOrder
                    .getCompanyCode());
                CreditUser user = creditUserBO.getCreditUserByCreditCode(
                    budgetOrder.getCreditCode(), ELoanRole.APPLY_USER);
                CarDealer carDealer = carDealerBO.getCarDealer(budgetOrder
                    .getCarDealerCode());
                RepointDetail repointDetail = new RepointDetail();
                repointDetail.setType(ERepointDetailType.NEW.getCode());
                repointDetail.setCompanyCode(budgetOrder.getCompanyCode());
                repointDetail.setCompanyName(company.getName());
                repointDetail.setBudgetCode(budgetOrder.getCode());
                repointDetail.setUserName(budgetOrder.getCustomerName());

                repointDetail.setIdNo(user.getIdNo());
                repointDetail.setCarDealerCode(budgetOrder.getCarDealerCode());
                repointDetail.setCarDealerName(carDealer.getFullName());
                repointDetail.setCarType(budgetOrder.getCarModel());
                repointDetail.setLoanAmount(budgetOrder.getLoanAmount());

                repointDetail.setBankRate(budgetOrder.getGlobalRate());
                repointDetail.setBenchmarkRate(StringValidater
                    .toDouble(xn632290Res.getBenchmarkRate()));
                repointDetail.setFee(budgetOrder.getFee());
                repointDetail
                    .setUseMoneyPurpose(EUseMoneyPurpose.PROTOCOL_INNER
                        .getCode());
                repointDetail.setRepointAmount(StringValidater
                    .toLong(xn632290Res.getRepointAmount()));
                repointDetail.setAccountCode(xn632290Res.getAccountCode());
                repointDetail.setCurNodeCode(ERepointDetailStatus.GENERATE
                    .getCode());
                repointDetailBO.saveRepointDetail(repointDetail);
            }
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();// 当前节点
        // 下个节点
        budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
            preCurrentNode).getNextNode());
        // 日志记录
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.INVOICE_MISMATCH, budgetOrder.getCode(),
            preCurrentNode, budgetOrder.getCurNodeCode(), null,
            req.getOperator());
        budgetOrderBO.applyInvoiceMismatch(budgetOrder);
        // 协议外返点暂不处理
    }

    @Override
    @Transactional
    public void invoiceMismatchApprove(String code, String approveResult,
            String approveNote, String operator) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);

        if (!EBudgetOrderNode.APPROVE_APPLY.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是发票不匹配审核节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            // 审核通过
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                preCurrentNode).getNextNode());
        } else {
            // 审核不通过 重新填写预算单
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                preCurrentNode).getBackNode());
            // 1、还原1个贷款金额3个贷款成数6个费用的新数据 共还原10项原数据
            budgetOrder.setLoanAmount(budgetOrder.getPreLoanAmount());
            budgetOrder.setPreLoanAmount(null);

            budgetOrder.setCompanyLoanCs(budgetOrder.getPreCompanyLoanCs());
            budgetOrder.setPreCompanyLoanCs(0);
            budgetOrder.setBankLoanCs(budgetOrder.getPreBankLoanCs());
            budgetOrder.setPreBankLoanCs(0);
            budgetOrder.setGlobalRate(budgetOrder.getPreGlobalRate());
            budgetOrder.setPreGlobalRate(0);

            budgetOrder.setFxAmount(budgetOrder.getPreFxAmount());
            budgetOrder.setPreFxAmount(null);
            budgetOrder.setLyAmount(budgetOrder.getPreLyAmount());
            budgetOrder.setPreLyAmount(null);
            budgetOrder.setGpsFee(budgetOrder.getPreGpsFee());
            budgetOrder.setPreGpsFee(null);
            budgetOrder.setOtherFee(budgetOrder.getPreOtherFee());
            budgetOrder.setPreOtherFee(null);
            budgetOrder.setOilSubsidy(budgetOrder.getPreOilSubsidy());
            budgetOrder.setPreOilSubsidy(null);
            budgetOrder.setGpsDeduct(budgetOrder.getPreGpsDeduct());
            budgetOrder.setPreGpsDeduct(null);

            // 2.将原来的手续费设置为失效 （手续费明细没处理）
            budgetOrderFeeBO.refreshBudgetOrderNoEffect(budgetOrder.getCode());

            // 3.删除返点数据
            repointDetailBO.delete(budgetOrder.getCode());
        }

        budgetOrderBO.invoiceMismatchApprove(budgetOrder);

        // 日志记录
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            budgetOrder.getCurNodeCode(), approveNote, operator);
    }

    @Override
    @Transactional
    public void invoiceMismatchSecondApprove(String code, String approveResult,
            String approveNote, String operator) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.TWO_APPROVE_APPLY.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是发票不匹配二审节点，不能操作");
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();// 当前节点
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            // 二审通过
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                preCurrentNode).getNextNode());
            // 计算出新应收手续费总额 更新手续费单的应收总额 （履约保证金+担保风险金+GPS收费+杂费）
            if (EBudgetOrderFeeWay.TRANSFER.getCode().equals(
                budgetOrder.getServiceChargeWay())) {
                Long totalFee = budgetOrder.getFee()
                        + budgetOrder.getLyAmount() + budgetOrder.getFxAmount()
                        + budgetOrder.getGpsFee() + budgetOrder.getOtherFee();
                BudgetOrderFee budgetOrderFee = budgetOrderFeeBO
                    .getBudgetOrderFeeByBudgetOrder(code);
                if (totalFee > budgetOrderFee.getShouldAmount()) {
                    budgetOrderFee.setIsSettled(EBoolean.NO.getCode());// 未结清
                } else {
                    budgetOrderFee.setIsSettled(budgetOrderFee.getIsSettled());
                }
                budgetOrderFee.setShouldAmount(totalFee);
                budgetOrderFee.setUpdateDatetime(new Date());
                budgetOrderFeeBO.updateShouldAmountAndIsSettled(budgetOrderFee);
            }
            // TODO 手续费收取方式是按揭款扣 在退按揭款时使用新计算出的手续费
            // 删除原返点数据
            repointDetailBO.deletePreRepointDetail(budgetOrder.getCode(),
                ERepointDetailType.NORMAL.getCode());

            budgetOrderBO.updateCurNodeCode(budgetOrder);
        } else {
            // 二审不通过 重新填写预算单
            budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
                EBudgetOrderNode.TWO_APPROVE_APPLY.getCode()).getBackNode());
            // 1.删除1个贷款金额3个贷款成数和6个费用的新数据 还原共10项原数据
            budgetOrder.setLoanAmount(budgetOrder.getPreLoanAmount());
            budgetOrder.setPreLoanAmount(null);

            budgetOrder.setCompanyLoanCs(budgetOrder.getPreCompanyLoanCs());
            budgetOrder.setPreCompanyLoanCs(0);
            budgetOrder.setBankLoanCs(budgetOrder.getPreBankLoanCs());
            budgetOrder.setPreBankLoanCs(0);
            budgetOrder.setGlobalRate(budgetOrder.getPreGlobalRate());
            budgetOrder.setPreGlobalRate(0);

            budgetOrder.setFxAmount(budgetOrder.getPreFxAmount());
            budgetOrder.setPreFxAmount(null);
            budgetOrder.setLyAmount(budgetOrder.getPreLyAmount());
            budgetOrder.setPreLyAmount(null);
            budgetOrder.setGpsFee(budgetOrder.getPreGpsFee());
            budgetOrder.setPreGpsFee(null);
            budgetOrder.setOtherFee(budgetOrder.getPreOtherFee());
            budgetOrder.setPreOtherFee(null);
            budgetOrder.setOilSubsidy(budgetOrder.getPreOilSubsidy());
            budgetOrder.setPreOilSubsidy(null);
            budgetOrder.setGpsDeduct(budgetOrder.getPreGpsDeduct());
            budgetOrder.setPreGpsDeduct(null);
            budgetOrderBO.invoiceMismatchApprove(budgetOrder);// 更新数据和更新节点

            // 2.将原来的手续费设置为失效（手续费明细未处理）
            budgetOrderFeeBO.refreshBudgetOrderNoEffect(budgetOrder.getCode());

            // 3.删除返点数据
            repointDetailBO.delete(budgetOrder.getCode());
        }

        // 日志记录
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            budgetOrder.getCurNodeCode(), approveNote, operator);
    }

    @Override
    @Transactional
    public void mortgageRefund(String code, String shouldBackBankcardCode,
            String shouldBackDatetime, String shouldBackBillPdf) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        budgetOrder.setShouldBackBankcardCode(shouldBackBankcardCode);
        budgetOrder.setShouldBackDatetime(DateUtil.strToDate(
            shouldBackDatetime, DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setShouldBackBillPdf(shouldBackBillPdf);
        budgetOrderBO.mortgageRefund(budgetOrder);
    }

    @Override
    @Transactional
    public void applyCancel(XN632270Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        String preCurrentNode = budgetOrder.getCurNodeCode();// 当前主流程节点
        budgetOrder.setZfReason(req.getZfReason());
        budgetOrder.setFrozenStatus(EBudgetFrozenStatus.FROZEN.getCode());
        budgetOrder.setCancelNodeCode(preCurrentNode);
        budgetOrder.setCurNodeCode(EBudgetOrderNode.APPROVE_CANCEL.getCode()); // 作废流程节点
        budgetOrderBO.applyCancel(budgetOrder);
        // 日志 记录本次操作
        sysBizLogBO.recordCurrentSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_CANCEL, budgetOrder.getCode(),
            EBudgetOrderNode.APPLY_CANCEL.getCode(), req.getZfReason(),
            req.getOperator());
        // 生成下一步操作日志
        sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_CANCEL, budgetOrder.getCode(),
            EBudgetOrderNode.APPROVE_CANCEL.getCode());
    }

    @Override
    @Transactional
    public void cancelBizAudit(XN632271Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        if (!EBudgetOrderNode.APPROVE_CANCEL.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是审核节点，不能操作");
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(req.getApproveResult())) {
            // 判断是否已垫资 如果已经垫资 下一个节点是财务审核节点 未垫资 下一个节点时废流程结束节点
            if (EIsAdvanceFund.NO.getCode().equals(
                budgetOrder.getIsAdvanceFund())) {// 没垫资情况
                budgetOrder.setCurNodeCode(EBudgetOrderNode.CANCEL_APPLY_END
                    .getCode());
                budgetOrder.setFrozenStatus(EBudgetFrozenStatus.NORMAL
                    .getCode());
            } else {// 垫资情况
                String currentNode = nodeFlowBO.getNodeFlowByCurrentNode(
                    budgetOrder.getCurNodeCode()).getNextNode();
                budgetOrder.setCurNodeCode(currentNode);
            }

            // 更新gps使用状态为待使用
            budgetOrderGpsBO.removeBudgetOrderGpsList(budgetOrder.getCode());
        } else if (EApproveResult.NOT_PASS.getCode().equals(
            req.getApproveResult())) {
            budgetOrder.setCurNodeCode(budgetOrder.getCancelNodeCode());
            budgetOrder.setFrozenStatus(EBudgetFrozenStatus.NORMAL.getCode());
        }

        budgetOrderBO.cancelBizAudit(budgetOrder);
        // 写日志
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_CANCEL, budgetOrder.getCode(), preCurrentNode,
            budgetOrder.getCurNodeCode(), req.getApproveNote(),
            req.getOperator());
    }

    @Override
    @Transactional
    public void financeConfirm(XN632272Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        if (!EBudgetOrderNode.FINANCE_CONFIRM_RECEIVABLES.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是财务确认收款节点，不能操作");
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder.setZfSkAmount(StringValidater.toLong(req.getZfSkAmount()));
        budgetOrder.setZfSkBankcardCode(req.getZfSkBankcardCode());
        budgetOrder.setZfSkReceiptDatetime(DateUtil.strToDate(
            req.getZfSkReceiptDatetime(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setCurNodeCode(nodeFlowBO.getNodeFlowByCurrentNode(
            preCurrentNode).getNextNode());
        budgetOrderBO.financeConfirm(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
            budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());
    }

    @Override
    @Transactional
    public void receiptAndReturn(XN632280Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        budgetOrder.setBackAdvanceFundType(req.getType());// 收回垫资款类型（1客户作废2垫资款退回）
        String zfReason = null;
        if ("1".equals(req.getType())) {
            zfReason = req.getZfReason();
        }
        budgetOrder.setZfReason(zfReason);
        budgetOrder.setZfSkAmount(StringValidater.toLong(req.getZfSkAmount()));
        budgetOrder
            .setBackAdvanceFundNodeCode(EBudgetOrderNode.BACK_ADVANCE_FUND_FINANCE_AUDIT
                .getCode());
        budgetOrderBO.receiptAndReturn(budgetOrder);
        // 日志记录 记录本次操作
        sysBizLogBO.recordCurrentSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BACK_ADVANCE_FUND, budgetOrder.getCode(),
            EBudgetOrderNode.BACK_ADVANCE_FUND_START.getCode(),
            req.getZfReason(), req.getOperator());
        // 日志记录 生成下一步操作
        sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BACK_ADVANCE_FUND, budgetOrder.getCode(),
            EBudgetOrderNode.BACK_ADVANCE_FUND_FINANCE_AUDIT.getCode());
    }

    @Override
    @Transactional
    public void remindingProcess(XN632281Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        String preNodeCode = budgetOrder.getBackAdvanceFundNodeCode();// 当前节点
        if (!EBudgetOrderNode.BACK_ADVANCE_FUND_FINANCE_AUDIT.getCode().equals(
            preNodeCode)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前不是收回垫资款审核节点，不能操作！");
        }
        String curNodeCode = null;
        if (EApproveResult.PASS.getCode().equals(req.getApproveResult())) {// 审核通过
            if (EBackAdvanceFundType.CANCEL.getCode().equals(
                budgetOrder.getBackAdvanceFundType())) {// 1客户作废
                String preCurrentNode = budgetOrder.getCurNodeCode();// 当前主流程节点
                budgetOrder.setFrozenStatus(EBudgetFrozenStatus.FROZEN
                    .getCode());
                budgetOrder.setCancelNodeCode(preCurrentNode);
                budgetOrder.setCurNodeCode(EBudgetOrderNode.APPROVE_CANCEL
                    .getCode()); // 作废流程节点
                budgetOrderBO.applyCancel(budgetOrder);
                // 生成下一步日志 作废审核
                sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
                    EBizLogType.BUDGET_CANCEL, budgetOrder.getCode(),
                    EBudgetOrderNode.APPROVE_CANCEL.getCode());
                // 记录页面收款信息到预算单
                budgetOrder.setZfSkBankcardCode(req.getZfSkBankcardCode());
                budgetOrder.setZfSkReceiptDatetime(DateUtil.strToDate(
                    req.getZfSkReceiptDatetime(),
                    DateUtil.FRONT_DATE_FORMAT_STRING));
                budgetOrder.setZfFkBillPdf(req.getBillPdf());
            } else {// 2垫资款退回（仅限分公司业务）
                // 1 垫资单回到确认用款单节点
                AdvanceFund advanceFund = advanceFundBO
                    .getAdvanceFundByBudgetOrderCode(req.getCode());
                advanceFund.setCurNodeCode(EAdvanceFundNode.BRANCH_CONFIRM
                    .getCode());
                advanceFundBO.refreshAdvanceFund(advanceFund);
                // 生成日志
                sysBizLogBO.saveSYSBizLog(advanceFund.getBudgetCode(),
                    EBizLogType.ADVANCE_FUND_BRANCH, advanceFund.getCode(),
                    advanceFund.getCurNodeCode());
                // 2 垫资汇总单减去该笔业务的金额
                TotalAdvanceFund totalAdvanceFund = totalAdvanceFundBO
                    .getTotalAdvanceFund(advanceFund.getTotalAdvanceFundCode());
                totalAdvanceFund.setTotalAdvanceFund(getLong(totalAdvanceFund
                    .getTotalAdvanceFund())
                        - getLong(advanceFund.getUseAmount()));
                totalAdvanceFund.setPayAmount(getLong(totalAdvanceFund
                    .getPayAmount()) - getLong(advanceFund.getUseAmount()));
                totalAdvanceFundBO.refreshTotalAdvanceFund(totalAdvanceFund);
                // 3 记录页面填写的数据到垫资汇总表 类型是4收回垫资款
                TotalAdvanceFund unAdvanceCollect = new TotalAdvanceFund();
                unAdvanceCollect
                    .setType(ETotalAdvanceFundType.ADVANCE_FUND_BACK.getCode());
                unAdvanceCollect.setCompanyCode(advanceFund.getCompanyCode());
                unAdvanceCollect.setCollectionAmount(budgetOrder
                    .getZfSkAmount());
                budgetOrder.setZfSkAmount(null);
                unAdvanceCollect.setCollectionDatetime(DateUtil.strToDate(
                    req.getZfSkReceiptDatetime(),
                    DateUtil.FRONT_DATE_FORMAT_STRING));
                unAdvanceCollect.setCollectionBankcardCode(req
                    .getZfSkBankcardCode());
                unAdvanceCollect.setCollectionBillPdf(req.getBillPdf());
                unAdvanceCollect.setCollectionNote(req.getApproveNote());
                unAdvanceCollect.setUpdater(req.getOperator());
                unAdvanceCollect.setUpdateDatetime(new Date());
                totalAdvanceFundBO.saveTotalAdvanceFund(unAdvanceCollect);
            }
            curNodeCode = nodeFlowBO.getNodeFlowByCurrentNode(preNodeCode)
                .getNextNode();
            sysBizLogBO.refreshPreSYSBizLog(EBizLogType.BACK_ADVANCE_FUND,
                budgetOrder.getCode(), preNodeCode, req.getApproveNote(),
                req.getOperator());
        } else {
            curNodeCode = nodeFlowBO.getNodeFlowByCurrentNode(preNodeCode)
                .getBackNode();
            sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
                EBizLogType.BACK_ADVANCE_FUND, budgetOrder.getCode(),
                preNodeCode, curNodeCode, req.getApproveNote(),
                req.getOperator());
        }
        budgetOrder.setBackAdvanceFundNodeCode(curNodeCode);// 更新收回垫资款流程节点
        budgetOrderBO.backAdvanceFundFinanceAudit(budgetOrder);
    }

    @Override
    @Transactional
    public void renewInsuranceRemind(String code) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        int insuranceRemindCount = budgetOrder.getInsuranceRemindCount() + 1;
        budgetOrderBO.renewInsuranceRemind(code, insuranceRemindCount);

        String mobile = budgetOrder.getMobile();
        String content = "尊敬的" + PhoneUtil.hideMobile(mobile)
                + "用户，您的保险即将到期，请及时续保！";
        smsOutBO.sendSmsOut(mobile, content);
    }

    @Override
    public void renewInsurance(XN632341Req req) {
        BudgetOrder data = new BudgetOrder();
        data.setCode(req.getCode());
        data.setInsuranceCompanyCode(req.getInsuranceCompanyCode());
        data.setInsuranceApplyDatetime(DateUtil.strToDate(
            req.getInsuranceApplyDatetime(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setInsuranceEndDatetime(DateUtil.strToDate(
            req.getInsuranceEndDatetime(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setInsuranceForcePdf(req.getInsuranceForcePdf());

        data.setInsuranceBusinessPdf(req.getInsuranceBusinessPdf());
        data.setInsuranceNote(req.getInsuranceNote());
        data.setOperator(req.getOperator());
        data.setOperateDatetime(new Date());
        budgetOrderBO.renewInsurance(data);
    }

    @Override
    public Paginable<BudgetOrder> queryBudgetOrderPageByDz(int start,
            int limit, BudgetOrder condition) {
        return budgetOrderBO.getPaginable(start, limit, condition);
    }

    @Override
    @Transactional
    public XN632234Res modifyLoanAmountCalculateData(String code,
            String loanAmount) {
        XN632234Res res = new XN632234Res();
        BudgetOrder data = budgetOrderBO.getBudgetOrder(code);
        Long invoicePrice = data.getCurrentInvoicePrice();// 现发票价
        // 我司贷款成数：贷款金额 / 发票价格
        res.setCompanyLoanCs(String.valueOf(AmountUtil.div(
            StringValidater.toLong(loanAmount), invoicePrice)));
        double feeRate = AmountUtil.div(data.getFee(),
            StringValidater.toLong(loanAmount));
        // 综合利率： 服务费/贷款金额+银行利率
        res.setGlobalRate(String.valueOf(feeRate + data.getBankRate()));
        Long totalAmount = StringValidater.toLong(loanAmount) + data.getFee();
        // 银行贷款成数：(贷款金额+服务费) / 发票价格
        res.setBankLoanCs(String.valueOf(AmountUtil.div(totalAmount,
            invoicePrice)));
        // 手续费:履约保证金+担保风险金+GPS收费+杂费
        String carDealerCode = data.getCarDealerCode();
        Bank bank = bankBO.getBankBySubbranch(data.getLoanBankCode());
        EBankType eBankType = null;
        if (EBankType.GH.getCode().equals(bank.getBankCode())) {
            eBankType = EBankType.GH;
        } else if (EBankType.ZH.getCode().equals(bank.getBankCode())) {
            eBankType = EBankType.ZH;
        } else if (EBankType.JH.getCode().equals(bank.getBankCode())) {
            eBankType = EBankType.JH;
        }
        CarDealerProtocol carDealerProtocol = carDealerProtocolBO
            .getCarDealerProtocolByCarDealerCode(carDealerCode,
                eBankType.getCode());
        // 担保风险金
        if (EAssureType.PERCENT.getCode().equals(
            carDealerProtocol.getAssureType())) {
            res.setFxAmount(String.valueOf(AmountUtil.mul(
                StringValidater.toLong(loanAmount),
                carDealerProtocol.getAssureRate())));
        }
        // 履约保证金
        if (ELyAmountType.PERCENT.getCode().equals(
            carDealerProtocol.getLyAmountType())) {
            res.setLyAmount(String.valueOf(AmountUtil.mul(
                StringValidater.toLong(loanAmount),
                carDealerProtocol.getLyAmountRate())));
        }
        // gps收费
        if (EGpsTypeProtocol.PERCENT.getCode().equals(
            carDealerProtocol.getGpsType())) {
            res.setGpsFee(String.valueOf(AmountUtil.mul(
                StringValidater.toLong(loanAmount),
                carDealerProtocol.getGpsRate())));
        }
        // 杂费
        if (EOtherType.PERCENT.getCode().equals(
            carDealerProtocol.getOtherType())) {
            res.setOtherFee(String.valueOf(AmountUtil.mul(
                StringValidater.toLong(loanAmount),
                carDealerProtocol.getOtherRate())));
        }
        SYSConfig sysConfigoil = sysConfigBO
            .getSYSConfig(SysConstants.BUDGET_OIL_SUBSIDY_RATE);
        Double oilSubsidyBFB = StringValidater.toDouble(sysConfigoil
            .getCvalue());
        Long oilSubsidy = AmountUtil.mul(StringValidater.toLong(loanAmount),
            oilSubsidyBFB);
        // 油补
        res.setOilSubsidy(String.valueOf(oilSubsidy));
        SYSConfig sysConfig = sysConfigBO
            .getSYSConfig(SysConstants.BUDGET_GPS_DEDUCT_RATE);
        Double gpsBFB = StringValidater.toDouble((sysConfig.getCvalue()));
        Long gpsDeduct = AmountUtil.mul(StringValidater.toLong(loanAmount),
            gpsBFB);
        // gps提成
        res.setGpsDeduct(String.valueOf(gpsDeduct));
        ArrayList<XN632290Res> list = new ArrayList<XN632290Res>();
        // 协议内返点明细
        List<CollectBankcard> carDealerCollectBankcard = collectBankcardBO
            .queryCollectBankcardByCompanyCodeAndTypeAndBankCode(
                data.getCarDealerCode(),
                ECollectBankcardType.DEALER_REBATE.getCode(),
                bank.getBankCode());
        if (null != carDealerCollectBankcard) {
            List<RepointDetail> repointDetailList = repointDetailAO
                .calculateRepointDetail(data);
            for (RepointDetail repointDetail : repointDetailList) {
                CarDealer carDealer = carDealerBO.getCarDealer(data
                    .getCarDealerCode());
                XN632290Res innerRepointDetail = new XN632290Res();
                innerRepointDetail
                    .setUseMoneyPurpose(EUseMoneyPurpose.PROTOCOL_INNER
                        .getCode());
                innerRepointDetail.setRepointAmount(String
                    .valueOf(repointDetail.getRepointAmount()));
                innerRepointDetail.setAccountCode(repointDetail
                    .getAccountCode());
                innerRepointDetail.setCompanyName(carDealer.getFullName());
                CollectBankcard collectBankcard = collectBankcardBO
                    .getCollectBankcard(repointDetail.getAccountCode());
                innerRepointDetail.setBankcardNumber(collectBankcard
                    .getBankcardNumber());
                innerRepointDetail.setSubbranch(collectBankcard.getBankName());
                innerRepointDetail.setBenchmarkRate(String
                    .valueOf(repointDetail.getBenchmarkRate()));
                list.add(innerRepointDetail);
            }
        }
        // 应退按揭款
        calculateShouldBackMorgage(data);// 重新计算
        XN632290Res shouldBackMortgage = new XN632290Res();
        shouldBackMortgage.setUseMoneyPurpose(EUseMoneyPurpose.MORTGAGE
            .getCode());
        shouldBackMortgage.setRepointAmount(String.valueOf(data
            .getShouldBackAmount()));
        if (EIsAdvanceFund.NO.getCode().equals(data.getIsAdvanceFund())) {// 展示给调用接口的前端看的
            // 不垫资 应退按揭款 退给个人 有收款相关信息
            shouldBackMortgage.setCompanyName(data.getShouldBackUserName());
            shouldBackMortgage.setBankcardNumber(data.getShouldBackAccountNo());
            shouldBackMortgage.setSubbranch(data.getShouldBackOpenBankName());
            shouldBackMortgage.setAccountName(data.getShouldBackAccountName());
        }
        list.add(shouldBackMortgage);
        res.setList(list);
        return res;
    }

    @Override
    @Transactional
    public BudgetOrder loanContractPrint(XN632142Req req) {
        BudgetOrder budgetOrder = getBudgetOrder(req.getCode());
        if (!EBudgetOrderNode.LOAN_PRINT.getCode().equals(
            budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是打印岗打印节点，不能操作");
        }
        if (EBoolean.YES.getCode().equals(budgetOrder.getIsLogistics())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点处于物流传递中，不能操作");
        }

        if (EFbhStatus.PENDING_ENTRY.getCode().equals(
            budgetOrder.getFbhStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "未录入发保合，不能操作");
        }
        // 当前节点
        String curNodeCode = budgetOrder.getCurNodeCode();
        String nextNodeCode = getNextNodeCode(curNodeCode,
            EBoolean.YES.getCode());

        budgetOrder.setPostcode(req.getPostcode());
        budgetOrder.setFamilyPhone(req.getFamilyPhone());
        budgetOrder.setApplyUserCompanyPhone(req.getApplyUserCompanyPhone());
        budgetOrder.setGhMobile(req.getGhMobile());
        budgetOrder.setGhCompanyName(req.getGhCompanyName());
        budgetOrder.setCarBrand(req.getCarBrand());
        budgetOrder.setEngineNo(req.getEngineNo());
        budgetOrder.setGuarantorNowAddress(req.getGuarantorNowAddress());
        budgetOrder.setGuarantorFamilyPhone(req.getGuarantorFamilyPhone());
        budgetOrder.setGuarantorCompanyName(req.getGuarantorCompanyName());
        budgetOrder.setGuarantorCompanyPhone(req.getGuarantorFamilyPhone());
        budgetOrder
            .setGuarantorCompanyAddress(req.getGuarantorCompanyAddress());
        budgetOrder.setInsuranceCompany(req.getInsuranceCompany());
        budgetOrder.setGuarantApplyUserNote(req.getGuarantApplyUserNote());
        budgetOrder.setGuarantPrintTemplateId(req.getGuarantPrintTemplateId());
        budgetOrder.setGuarantPrintUser(req.getOperator());
        budgetOrder.setGuarantPrintDatetime(new Date());
        // budgetOrder.setCurNodeCode(nextNodeCode);
        budgetOrderBO.loanContractPrint(budgetOrder);

        // 生成资料传递
        logisticsBO.saveLogistics(ELogisticsType.BUDGET.getCode(),
            budgetOrder.getCode(), budgetOrder.getSaleUserId(), curNodeCode,
            nextNodeCode);
        // 产生物流单后改变状态为物流传递中
        budgetOrder.setIsLogistics(EBoolean.YES.getCode());
        budgetOrderBO.updateIsLogistics(budgetOrder);

        // 写日志
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), curNodeCode,
            nextNodeCode, EBoolean.YES.getCode(), req.getOperator());

        return budgetOrder;
    }

    @Override
    @Transactional
    public BudgetOrder pledgeContractPrint(XN632192Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        if (!EBudgetOrderNode.LOCAL_PRINTPOST_PRINT.getCode().equals(
            budgetOrder.getPledgeCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是车辆抵押（本地）打印岗打印节点，不能操作");
        }
        if (EBoolean.YES.getCode().equals(budgetOrder.getIsLogistics())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点处于物流传递中，不能操作");
        }
        // 当前抵押流程节点
        String preNodeCode = budgetOrder.getPledgeCurNodeCode();
        String nextNodeCode = getNextNodeCode(preNodeCode,
            EBoolean.YES.getCode());

        budgetOrder.setCarNumber(req.getCarNumber());
        budgetOrder.setFrameNo(req.getFrameNo());
        budgetOrder.setEngineNo(req.getEngineNo());
        budgetOrder.setPledgePrintTemplateId(req.getPledgePrintTemplateId());
        budgetOrder.setPledgePrintUser(req.getOperator());
        budgetOrder.setPledgePrintDatetime(new Date());
        // budgetOrder.setPledgeCurNodeCode(nextNodeCode);
        budgetOrderBO.pledgeContractPrint(budgetOrder);

        // 判断是否是银行驻点补件
        // 查补件单
        Logistics condition = new Logistics();
        condition.setBizCode(req.getCode());
        condition.setFromNodeCode(EBudgetOrderNode.LOCAL_PRINTPOST_PRINT
            .getCode());
        condition.setToNodeCode(EBudgetOrderNode.LOCAL_COLLATEPOST_COLLATE
            .getCode());
        condition.setStatus(ELogisticsStatus.TO_SEND_AGAIN.getCode());
        List<Logistics> logisticsList = logisticsBO
            .queryLogisticsList(condition);
        if (CollectionUtils.isEmpty(logisticsList)) {
            // 生成资料传递
            logisticsBO.saveLogistics(ELogisticsType.BUDGET.getCode(),
                budgetOrder.getCode(), budgetOrder.getSaleUserId(),
                preNodeCode, nextNodeCode);
        }
        // 产生物流单后改变状态为物流传递中
        budgetOrder.setIsLogistics(EBoolean.YES.getCode());
        budgetOrderBO.updateIsLogistics(budgetOrder);

        // 日志记录
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preNodeCode,
            nextNodeCode, null, req.getOperator());

        return budgetOrder;
    }

    private Double benchmarkRate(BudgetOrder data) {
        Bank bank = bankBO.getBankBySubbranch(data.getLoanBankCode());
        CarDealerProtocol protocol = carDealerProtocolBO
            .getCarDealerProtocolByCarDealerCode(data.getCarDealerCode(),
                bank.getBankCode());
        Double benchmarkRate = 0.0;

        if (ELoanPeriod.ONE_YEAER.getCode().equals(data.getLoanPeriods())) {
            if (ERateType.CT.getCode().equals(data.getRateType())) {
                benchmarkRate = protocol.getPlatCtRate12();
            }
            if (ERateType.ZK.getCode().equals(data.getRateType())) {
                benchmarkRate = protocol.getPlatZkRate12();
            }
        }
        if (ELoanPeriod.TWO_YEAR.getCode().equals(data.getLoanPeriods())) {

            if (ERateType.CT.getCode().equals(data.getRateType())) {
                benchmarkRate = protocol.getPlatCtRate24();
            }
            if (ERateType.ZK.getCode().equals(data.getRateType())) {
                benchmarkRate = protocol.getPlatZkRate24();
            }

        }
        if (ELoanPeriod.THREE_YEAR.getCode().equals(data.getLoanPeriods())) {
            if (ERateType.CT.getCode().equals(data.getRateType())) {
                benchmarkRate = protocol.getPlatCtRate36();
            }
            if (ERateType.ZK.getCode().equals(data.getRateType())) {
                benchmarkRate = protocol.getPlatZkRate36();
            }
        }
        return benchmarkRate;
    }

    @Override
    @Transactional
    public void collateAchieve(XN632193Req req) {
        List<String> list = req.getList();
        for (String code : list) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            if (!EBudgetOrderNode.LOCAL_COLLATEPOST_COLLATE.getCode().equals(
                budgetOrder.getPledgeCurNodeCode())
                    && !EBudgetOrderNode.OUT_COLLATEPOST_COLLATE.getCode()
                        .equals(budgetOrder.getPledgeCurNodeCode())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前节点不是车辆抵押理件岗理件节点，不能操作");
            }
            if (EBoolean.YES.getCode().equals(budgetOrder.getIsLogistics())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前节点处于物流传递中，不能操作");
            }
        }
        for (String code : list) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            String preCurNodeCode = budgetOrder.getPledgeCurNodeCode();
            // budgetOrder.setPledgeCurNodeCode(nodeFlowBO
            // .getNodeFlowByCurrentNode(preCurNodeCode).getNextNode());
            EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
                budgetOrder.getPledgeCurNodeCode());
            // budgetOrderBO.collateAchieve(budgetOrder);
            // 日志记录
            sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
                EBizLogType.BUDGET_ORDER, budgetOrder.getCode(),
                preCurNodeCode, currentNode.getCode(), currentNode.getValue(),
                req.getOperator());
            // 生成资料传递
            NodeFlow nodeFlow = nodeFlowBO.getNodeFlowByCurrentNode(budgetOrder
                .getPledgeCurNodeCode());
            logisticsBO.saveLogistics(ELogisticsType.BUDGET.getCode(),
                budgetOrder.getCode(), budgetOrder.getSaleUserId(),
                budgetOrder.getPledgeCurNodeCode(), nodeFlow.getNextNode());
            // 产生物流单后改变状态为物流传递中
            budgetOrder.setIsLogistics(EBoolean.YES.getCode());
            budgetOrderBO.updateIsLogistics(budgetOrder);
        }
    }

    @Override
    @Transactional
    public void pledgeBegin(XN632194Req req) {

        List<String> list = req.getList();
        for (String code : list) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            if (!EBudgetOrderNode.OUT_PLEDGE_BEGIN.getCode().equals(
                budgetOrder.getPledgeCurNodeCode())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前节点不是车辆抵押开始节点，不能操作");
            }
        }
        for (String code : list) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            String preCurNodeCode = budgetOrder.getPledgeCurNodeCode();
            budgetOrder.setPledgeCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(preCurNodeCode).getNextNode());
            EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
                budgetOrder.getPledgeCurNodeCode());
            budgetOrderBO.collateAchieve(budgetOrder);
            // 日志记录
            sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
                EBizLogType.BUDGET_ORDER, budgetOrder.getCode(),
                preCurNodeCode, currentNode.getCode(), currentNode.getValue(),
                req.getOperator());
        }
    }

    @Override
    @Transactional
    public void bankLoanCollateAchieve(XN632143Req req) {

        List<String> list = req.getList();
        for (String code : list) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            if (!EBudgetOrderNode.BANK_LOAN_COLLATEPOST_COLLATE.getCode()
                .equals(budgetOrder.getCurNodeCode())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前节点不是银行放款理件节点，不能操作");
            }
            if (EBoolean.YES.getCode().equals(budgetOrder.getIsLogistics())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前节点处于物流传递中，不能操作");
            }
        }
        for (String code : list) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            String preCurNodeCode = budgetOrder.getCurNodeCode();
            // budgetOrder.setCurNodeCode(nodeFlowBO
            // .getNodeFlowByCurrentNode(preCurNodeCode).getNextNode());
            EBudgetOrderNode currentNode = EBudgetOrderNode.getMap().get(
                budgetOrder.getCurNodeCode());
            budgetOrderBO.loanBankCollateAchieve(budgetOrder);

            // 生成资料传递
            NodeFlow nodeFlow = nodeFlowBO.getNodeFlowByCurrentNode(budgetOrder
                .getCurNodeCode());
            List<SupplementReason> supplementReason = supplementReasonBO
                .getSupplementReasonByLogisticsCode(code);
            String loCode = logisticsBO.saveLogistics(
                ELogisticsType.BUDGET.getCode(), budgetOrder.getCode(),
                budgetOrder.getSaleUserId(), budgetOrder.getCurNodeCode(),
                nodeFlow.getNextNode());
            // 传递补件原因
            if (CollectionUtils.isNotEmpty(supplementReason)) {
                Logistics logistics = logisticsBO.getLogistics(loCode);
                logistics
                    .setFromNodeCode(EBudgetOrderNode.HEADQUARTERS_SEND_PRINT
                        .getCode());
                logistics
                    .setToNodeCode(EBudgetOrderNode.BANK_LOAN_COLLATEPOST_COLLATE
                        .getCode());
                for (SupplementReason reason : supplementReason) {
                    supplementReasonBO.refreshLogisticsCode(reason.getId(),
                        loCode);
                }
            }
            // 产生物流单后改变状态为物流传递中
            budgetOrder.setIsLogistics(EBoolean.YES.getCode());
            budgetOrderBO.updateIsLogistics(budgetOrder);

            // 日志记录
            sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
                EBizLogType.BUDGET_ORDER, budgetOrder.getCode(),
                preCurNodeCode, currentNode.getCode(), currentNode.getValue(),
                req.getOperator());
        }
    }

    private BudgetOrder calculateShouldBackMorgage(BudgetOrder data) {
        // 计算应退按揭款 应退按揭款金额=贷款金额-收客户手续费（按揭款扣）-GPS收费（按揭款扣）-厂家贴息
        Long carDealerSubsidy = 0L;// 厂家贴息
        if (null != data.getCarDealerSubsidy()) {
            carDealerSubsidy = data.getCarDealerSubsidy();
        }
        Long shouldBackAmount = data.getLoanAmount() - carDealerSubsidy;
        Long sxFee = 0L;// 收客户手续费
        sxFee = getLong(data.getLyAmount()) + getLong(data.getFxAmount())
                + getLong(data.getOtherFee());
        if (EServiceChargeWay.MORTGAGE.getCode().equals(
            data.getServiceChargeWay())) {
            shouldBackAmount = shouldBackAmount - sxFee;
        }
        if (EGpsFeeWay.MORTGAGE.getCode().equals(data.getGpsFeeWay())) {
            shouldBackAmount = shouldBackAmount - data.getGpsFee();
        }
        data.setShouldBackAmount(shouldBackAmount);
        return data;
    }

    @Override
    public void bankRepoint(XN632292Req req) {
        List<String> codeList = req.getCodeList();
        for (String code : codeList) {
            BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
            if (!EBankRepointStatus.NO.getCode().equals(
                budgetOrder.getBankRepointStatus())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "当前业务不是待返点状态，不能操作！");
            }
            budgetOrder.setBankRepointStatus(EBankRepointStatus.YES.getCode());
            budgetOrderBO.bankRepoint(budgetOrder);
        }
    }

    private Long getLong(Object obj) {
        if (null == obj) {
            return 0L;
        } else {
            return (Long) obj;
        }
    }

    @Override
    public XN632690Res calculation(String carDealerCode, String loanBankCode,
            Integer loanPeriods, Long loanAmount, String rateType,
            String serviceChargeWay, double bankRate) {
        XN632690Res res = new XN632690Res();
        Bank bank = bankBO.getBank(loanBankCode);
        // 中行
        if (bank.getBankCode().equals("BOC")) {
            // 传统
            if (ERateType.CT.getCode().equals(rateType)) {
                // 1.首期本金 = 贷款额- (2) *（期数-1）
                // 2.每期本金=贷款额/期数
                Long annualPrincipal = (long) AmountUtil.div(loanAmount,
                    loanPeriods);// 每期本金
                Long initialPrincipal = (loanAmount - AmountUtil.mul(
                    annualPrincipal, (loanPeriods - 1)));// 首期本金

                // 手续费=贷款额*基准利率
                // 3.首期=手续费-（4）*（期数-1）
                // 4.每期=(2)*基准利率
                CarDealerProtocol carDealerProtocol = carDealerProtocolBO
                    .getCarDealerProtocolByCarDealerCode(carDealerCode,
                        bank.getBankCode());// 获取经销商协议
                double rate = 0;// 基准利率
                if (loanPeriods == 12) {
                    rate = carDealerProtocol.getPlatCtRate12();
                } else if (loanPeriods == 24) {
                    rate = carDealerProtocol.getPlatCtRate24();
                } else {
                    rate = carDealerProtocol.getPlatCtRate36();
                }
                Long poundage = AmountUtil.mul(loanAmount, rate);// 手续费
                Long annualPoundage = AmountUtil.mul(annualPrincipal, rate);// 每期手续费
                Long initialPoundage = poundage
                        - AmountUtil.mul(annualPoundage, (loanPeriods - 1));// 首期手续费

                // 高息金额=贷款额*（总利率-基准利率）
                // 5.高息金额首期=高息金额-（6）*（期数-1）
                // 6.高息金额每期=高息金额/期数
                // HighRate
                Long highRate = AmountUtil.mul(loanAmount, (bankRate - rate));// 高息金额
                Long annualHighRate = (long) AmountUtil.div(highRate,
                    loanPeriods);// 高息金额每期
                Long initialHighRate = highRate
                        - AmountUtil.mul(annualHighRate, (loanPeriods - 1));// 高息金额首期

                // 高息手续费=高息金额*基准利率
                // 7.高息手续费首期=（8）*（期数-1）
                // 8.高息手续费每期=(6）*基准利率
                Long highRatePoundage = AmountUtil.mul(highRate, rate);// 高息手续费
                Long annualHighRatePoundage = AmountUtil.mul(annualHighRate,
                    rate);// 高息手续费每期
                Long initialHighRatePoundage = AmountUtil.mul(
                    annualHighRatePoundage, (loanPeriods - 1));// 高息手续费首期

                // 首期月供=1+3+5+7
                Long initialAmount = initialPrincipal + initialPoundage
                        + initialHighRate + initialHighRatePoundage;
                // 每期月供=2+4+6+8
                Long annualAmount = annualPrincipal + annualPoundage
                        + annualHighRate + annualHighRatePoundage;

                res.setAnnualAmount(String.valueOf(annualAmount));
                res.setInitialAmount(String.valueOf(initialAmount));
            } else if (ERateType.ZK.getCode().equals(rateType)) {// 直客
                if (EBocFeeWay.STAGES.getCode().equals(serviceChargeWay)) {// 分期
                    // 本金：
                    // 1.首期=贷款额-（2）*（期数-1）
                    // 2.每期=贷款额/期数
                    Long annualPrincipal = (long) AmountUtil.div(loanAmount,
                        loanPeriods);// 每期本金
                    Long initialPrincipal = loanAmount
                            - AmountUtil
                                .mul(annualPrincipal, (loanPeriods - 1));// 首期本金

                    // 手续费=贷款额*利率
                    // 3.首期=手续费-（4）*（期数-1）
                    // 4.每期=（2）*利率
                    Long poundage = AmountUtil.mul(loanAmount, bankRate);// 手续费
                    Long annualPoundage = AmountUtil.mul(annualPrincipal,
                        bankRate);// 每期手续费
                    Long initialPoundage = poundage
                            - AmountUtil.mul(annualPoundage, (loanPeriods - 1));// 首期手续费

                    // 月供：
                    // 首期=1+3
                    Long initialAmount = initialPrincipal + initialPoundage;
                    // 每期=2+4
                    Long annualAmount = annualPrincipal + annualPoundage;

                    res.setAnnualAmount(String.valueOf(annualAmount));
                    res.setInitialAmount(String.valueOf(initialAmount));
                } else if (EBocFeeWay.DISPOSABLE.getCode().equals(
                    serviceChargeWay)) {// 一次性
                    // 本金：
                    // 1.首期=贷款额-（2）*（期数-1）
                    // 2.每期=贷款额/期数
                    Long annualPrincipal = (long) AmountUtil.div(loanAmount,
                        loanPeriods);// 每期本金
                    Long initialPrincipal = loanAmount
                            - AmountUtil
                                .mul(annualPrincipal, (loanPeriods - 1));// 首期本金

                    // 手续费=贷款额*利率
                    Long poundage = AmountUtil.mul(loanAmount, bankRate);// 手续费

                    // 月供：
                    // 首期=1+手续费
                    Long initialAmount = initialPrincipal + poundage;
                    // 每期=2
                    Long annualAmount = annualPrincipal;

                    res.setAnnualAmount(String.valueOf(annualAmount));
                    res.setInitialAmount(String.valueOf(initialAmount));
                } else {// 附加费
                    // 本金：
                    // 1.首期=贷款额-（2）*（期数-1）
                    // 2.每期=贷款额/期数
                    Long annualPrincipal = (long) AmountUtil.div(loanAmount,
                        loanPeriods);// 每期本金
                    Long initialPrincipal = loanAmount
                            - AmountUtil
                                .mul(annualPrincipal, (loanPeriods - 1));// 首期本金

                    // 手续费=贷款额*利率
                    // 3.首期=手续费-（4）*（期数-1）
                    // 4.每期=（2）*利率
                    Long poundage = AmountUtil.mul(loanAmount, bankRate);// 手续费
                    Long annualPoundage = AmountUtil.mul(annualPrincipal,
                        bankRate);// 每期手续费
                    Long initialPoundage = poundage
                            - AmountUtil.mul(annualPoundage, (loanPeriods - 1));// 首期手续费

                    // 附加费：
                    // 5.首期=附加费-（2）*（期数-1）
                    // 6.每期=附加费/期数

                    // 附加费手续费
                    // 7.首期=手续费-（4）*（期数-1）
                    // 8.每期=（2）*利率
                    Long annualSurchargePoundage = AmountUtil.mul(
                        annualPrincipal, bankRate);// 每期附加费手续费
                    Long initialSurchargePoundage = poundage
                            - AmountUtil.mul(annualPoundage, (loanPeriods - 1));// 首期附加费手续费

                    // 月供
                    // 首期=1+3+5+7
                    // 每期=2+4+6+8
                    // TODO

                }
            }
        } else if (bank.getBankCode().equals("ICBC")) {// 工行
            // a)服务费=(实际利率-基准利率)*贷款额
            // b)月供=((贷款额+服务费)*(1+基准利率))/贷款期数
            CarDealerProtocol carDealerProtocol = carDealerProtocolBO
                .getCarDealerProtocolByCarDealerCode(carDealerCode,
                    bank.getBankCode());// 获取经销商协议
            double rate = 0;// 基准利率
            if (loanPeriods == 12) {
                rate = carDealerProtocol.getPlatCtRate12();
            } else if (loanPeriods == 24) {
                rate = carDealerProtocol.getPlatCtRate24();
            } else {
                rate = carDealerProtocol.getPlatCtRate36();
            }
            Long poundage = AmountUtil.mul(loanAmount, (bankRate - rate));// 服务费
            Long amount = AmountUtil.mul((loanAmount + poundage), (rate + 1));
            Long monthAmount = (long) AmountUtil.div(amount, (loanPeriods - 1));// 月供

            res.setAnnualAmount(String.valueOf(monthAmount));
            res.setInitialAmount(String.valueOf(monthAmount));
        } else if (bank.getBankCode().equals("CCB")) {// 建行
            // a) 服务费=0
            // b) 月供=贷款额*（1+利率）/期数
            Long amount = AmountUtil.mul(loanAmount, (bankRate + 1));
            Long monthAmount = (long) AmountUtil.div(amount, loanPeriods);// 月供

            res.setAnnualAmount(String.valueOf(monthAmount));
            res.setInitialAmount(String.valueOf(monthAmount));
        }
        return res;
    }

    @Override
    public Object queryBudgetOrderPageForBalanceDetail(int start, int limit,
            BudgetOrder condition) {
        Paginable<BudgetOrder> page = budgetOrderBO.getPaginable(start, limit,
            condition);
        List<BudgetOrder> list = page.getList();
        for (BudgetOrder budgetOrder : list) {
            AdvanceFund advanceFund = advanceFundBO
                .getAdvanceFundByBudgetOrderCode(budgetOrder.getCode());
            budgetOrder.setAdvanceFundDatetime(advanceFund
                .getAdvanceFundDatetime());// 垫资日期
            RepayBiz repayBiz = repayBizBO.getRepayBizByRefCode(budgetOrder
                .getCode());
            budgetOrder.setRepayMonthDatetime(repayBiz.getMonthDatetime());// 月供还款日
            RepayPlan curMonth = repayPlanBO.getRepayPlanCurMonth(repayBiz
                .getCode());
            budgetOrder.setOverplusAmount(curMonth.getOverdueAmount());// 当期欠款
            budgetOrder.setDebtBalance(repayBiz.getRestAmount()
                    + repayBiz.getRestOverdueAmount());// 借款余额
            budgetOrder.setReplaceRealRepayAmount(repayBiz
                .getRestReplaceRepayAmount());// 代偿金额
        }
        return page;
    }

}
