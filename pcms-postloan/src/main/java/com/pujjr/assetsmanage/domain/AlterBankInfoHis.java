package com.pujjr.assetsmanage.domain;

public class AlterBankInfoHis {
    private String id;

    private String appId;

    private String repayAcctNo;

    private String repayBankId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRepayAcctNo() {
        return repayAcctNo;
    }

    public void setRepayAcctNo(String repayAcctNo) {
        this.repayAcctNo = repayAcctNo;
    }

    public String getRepayBankId() {
        return repayBankId;
    }

    public void setRepayBankId(String repayBankId) {
        this.repayBankId = repayBankId;
    }
}