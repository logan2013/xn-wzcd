package com.cdkj.loan.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 录发保合
 * @author: jiafr 
 * @since: 2018年6月30日 下午1:44:29 
 * @history:
 */
public class XN632220Req {

    // 预算单编号
    @NotBlank
    private String code;

    // 车辆颜色
    @NotBlank
    private String carColor;

    // 车架号码
    private String frameNo;

    // 提车日期
    @NotBlank
    private String deliveryDatetime;

    // 发票是否正确
    private String isRightInvoice;

    // 现发票价
    @NotBlank
    private String currentInvoicePrice;

    // 发票
    @NotBlank
    private String invoice;

    // 合格证
    @NotBlank
    private String certification;

    // 交强险
    @NotBlank
    private String forceInsurance;

    // 交强险
    @NotBlank
    private String forceInsurancePdf;

    // 商业险
    @NotBlank
    private String businessInsurance;

    // 机动车登记证书
    private String motorRegCertification;

    // 批单
    private String pdPdf;

    // 发保合备注
    private String fbhRemark;

    // 操作人
    @NotBlank
    private String operator;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMotorRegCertification() {
        return motorRegCertification;
    }

    public void setMotorRegCertification(String motorRegCertification) {
        this.motorRegCertification = motorRegCertification;
    }

    public String getForceInsurancePdf() {
        return forceInsurancePdf;
    }

    public void setForceInsurancePdf(String forceInsurancePdf) {
        this.forceInsurancePdf = forceInsurancePdf;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(String frameNo) {
        this.frameNo = frameNo;
    }

    public String getPdPdf() {
        return pdPdf;
    }

    public void setPdPdf(String pdPdf) {
        this.pdPdf = pdPdf;
    }

    public String getFbhRemark() {
        return fbhRemark;
    }

    public void setFbhRemark(String fbhRemark) {
        this.fbhRemark = fbhRemark;
    }

    public String getDeliveryDatetime() {
        return deliveryDatetime;
    }

    public void setDeliveryDatetime(String deliveryDatetime) {
        this.deliveryDatetime = deliveryDatetime;
    }

    public String getIsRightInvoice() {
        return isRightInvoice;
    }

    public void setIsRightInvoice(String isRightInvoice) {
        this.isRightInvoice = isRightInvoice;
    }

    public String getCurrentInvoicePrice() {
        return currentInvoicePrice;
    }

    public void setCurrentInvoicePrice(String currentInvoicePrice) {
        this.currentInvoicePrice = currentInvoicePrice;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getForceInsurance() {
        return forceInsurance;
    }

    public void setForceInsurance(String forceInsurance) {
        this.forceInsurance = forceInsurance;
    }

    public String getBusinessInsurance() {
        return businessInsurance;
    }

    public void setBusinessInsurance(String businessInsurance) {
        this.businessInsurance = businessInsurance;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
