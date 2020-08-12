package co.kr.reo;

import androidx.room.Entity;

import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class MemberDTO implements Serializable {

    private int mem_no;
    private String mem_email;
    private String mem_name;
    private String mem_pw;
    private String mem_tel;
    private String mem_role;
    private String mem_zipcode;
    private String mem_roadaddress;
    private String mem_detailaddress;
    private String mem_sector;
    private String mem_agentName;
    private String mem_buisnessNo;
    private String searchCondition;
    private String searchKeyword;
    private String log_ip;

    public String getMem_sector() {
        return mem_sector;
    }

    public void setMem_sector(String mem_sector) {
        this.mem_sector = mem_sector;
    }

    public String getMem_agentName() {
        return mem_agentName;
    }

    public void setMem_agentName(String mem_agentName) {
        this.mem_agentName = mem_agentName;
    }

    public String getMem_zipcode() {
        return mem_zipcode;
    }

    public void setMem_zipcode(String mem_zipcode) {
        this.mem_zipcode = mem_zipcode;
    }

    public String getMem_buisnessNo() {
        return mem_buisnessNo;
    }

    public void setMem_buisnessNo(String mem_buisnessNo) {
        this.mem_buisnessNo = mem_buisnessNo;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public int getMem_no() {
        return mem_no;
    }

    public void setMem_no(int mem_no) {
        this.mem_no = mem_no;
    }

    public String getMem_email() {
        return mem_email;
    }

    public void setMem_email(String mem_email) {
        this.mem_email = mem_email;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_pw() {
        return mem_pw;
    }

    public void setMem_pw(String mem_pw) {
        this.mem_pw = mem_pw;
    }

    public String getMem_tel() {
        return mem_tel;
    }

    public void setMem_tel(String mem_tel) {
        this.mem_tel = mem_tel;
    }

    public String getMem_role() {
        return mem_role;
    }

    public void setMem_role(String mem_role) {
        this.mem_role = mem_role;
    }


    public String getMem_roadaddress() {
        return mem_roadaddress;
    }

    public void setMem_roadaddress(String mem_roadaddress) {
        this.mem_roadaddress = mem_roadaddress;
    }

    public String getMem_detailaddress() {
        return mem_detailaddress;
    }

    public void setMem_detailaddress(String mem_detailaddress) {
        this.mem_detailaddress = mem_detailaddress;
    }
    public String getLog_ip() {
        return log_ip;
    }

    public void setLog_ip(String log_ip) {
        this.log_ip = log_ip;
    }

}
