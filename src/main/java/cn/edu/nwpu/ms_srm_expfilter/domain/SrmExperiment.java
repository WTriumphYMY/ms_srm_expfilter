package cn.edu.nwpu.ms_srm_expfilter.domain;

/**
 * @ClassName SrmExperiment
 * @Author: gd
 * @Date: 2019/4/26 17:35
 * @Version: v1.0
 * @Description:实体对应数据库的表
 */

public class SrmExperiment {

    private int pkId;
    private String expTime;
    private String expPressure;
    private String expForce;
    private String srmName;
    private String gmtCreate;

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getExpPressure() {
        return expPressure;
    }

    public void setExpPressure(String expPressure) {
        this.expPressure = expPressure;
    }

    public String getExpForce() {
        return expForce;
    }

    public void setExpForce(String expForce) {
        this.expForce = expForce;
    }

    public String getSrmName() {
        return srmName;
    }

    public void setSrmName(String srmName) {
        this.srmName = srmName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}