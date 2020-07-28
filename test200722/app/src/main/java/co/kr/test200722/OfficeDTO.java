package co.kr.test200722;

import java.io.Serializable;

public class OfficeDTO {
    private String off_type;
    private String off_unit;
    private String off_name;
    private int off_rent;
    private String off_stdAddr;

    public String getOff_type() {
        return off_type;
    }

    public void setOff_type(String off_type) {
        this.off_type = off_type;
    }

    public String getOff_unit() {
        return off_unit;
    }

    public void setOff_unit(String off_unit) {
        this.off_unit = off_unit;
    }

    public String getOff_name() {
        return off_name;
    }

    public void setOff_name(String off_name) {
        this.off_name = off_name;
    }

    public int getOff_rent() {
        return off_rent;
    }

    public void setOff_rent(int off_rent) {
        this.off_rent = off_rent;
    }

    public String getOff_stdAddr() {
        return off_stdAddr;
    }

    public void setOff_stdAddr(String off_stdAddr) {
        this.off_stdAddr = off_stdAddr;
    }
}
