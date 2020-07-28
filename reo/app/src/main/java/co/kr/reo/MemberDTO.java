package co.kr.reo;

import androidx.room.Entity;

import java.io.Serializable;

@Entity
public class MemberDTO implements Serializable {

    private String mem_email;
    private String mem_name;

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_email() {
        return mem_email;
    }

    public void setMem_email(String mem_email) {
        this.mem_email = mem_email;
    }

}
