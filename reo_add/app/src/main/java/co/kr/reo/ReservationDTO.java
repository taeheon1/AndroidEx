package co.kr.reo;

import java.sql.Timestamp;

public class ReservationDTO {
    private int res_no;
    private String mem_email;
    private String mem_agentName;
    private String mem_agentTel;

//	private String res_datetime;
//	private String res_startdatetime;
//	private String res_enddatetime;

    private Timestamp res_datetime;
    private Timestamp res_startdatetime;
    private Timestamp res_enddatetime;

    private String res_done;
    private int res_grade;
    private String room_price;
    private String res_cancel;
    private String res_memo;
    private int res_people;
    private int off_no;
    private String off_name;
    private String off_unit;

    public int getRes_no() {
        return res_no;
    }

    public void setRes_no(int res_no) {
        this.res_no = res_no;
    }

    public String getMem_email() {
        return mem_email;
    }

    public void setMem_email(String mem_email) {
        this.mem_email = mem_email;
    }

    public String getMem_agentName() {
        return mem_agentName;
    }

    public void setMem_agentName(String mem_agentName) {
        this.mem_agentName = mem_agentName;
    }

    public String getMem_agentTel() {
        return mem_agentTel;
    }

    public void setMem_agentTel(String mem_agentTel) {
        this.mem_agentTel = mem_agentTel;
    }

    public Timestamp getRes_datetime() {
        return res_datetime;
    }

    public void setRes_datetime(Timestamp res_datetime) {
        this.res_datetime = res_datetime;
    }

    public Timestamp getRes_startdatetime() {
        return res_startdatetime;
    }

    public void setRes_startdatetime(Timestamp res_startdatetime) {
        this.res_startdatetime = res_startdatetime;
    }

    public Timestamp getRes_enddatetime() {
        return res_enddatetime;
    }

    public void setRes_enddatetime(Timestamp res_enddatetime) {
        this.res_enddatetime = res_enddatetime;
    }

    public String getRes_done() {
        return res_done;
    }

    public void setRes_done(String res_done) {
        this.res_done = res_done;
    }

    public int getRes_grade() {
        return res_grade;
    }

    public void setRes_grade(int res_grade) {
        this.res_grade = res_grade;
    }

    public String getRoom_price() {
        return room_price;
    }

    public void setRoom_price(String roomprice) {
        this.room_price = roomprice;
    }

    public String getRes_cancel() {
        return res_cancel;
    }

    public void setRes_cancel(String res_cancel) {
        this.res_cancel = res_cancel;
    }

    public String getRes_memo() {
        return res_memo;
    }

    public void setRes_memo(String res_memo) {
        this.res_memo = res_memo;
    }

    public int getRes_people() {
        return res_people;
    }

    public void setRes_people(int res_people) {
        this.res_people = res_people;
    }

    public int getOff_no() {
        return off_no;
    }

    public void setOff_no(int off_no) {
        this.off_no = off_no;
    }

    public String getOff_name() {
        return off_name;
    }

    public void setOff_name(String off_name) {
        this.off_name = off_name;
    }

    public String getOff_unit() {
        return off_unit;
    }

    public void setOff_unit(String off_unit) {
        this.off_unit = off_unit;
    }


}
