package co.kr.reo;

import java.util.ArrayList;
import java.util.Arrays;

public class TimeData {
    private String time;
    private boolean check;
    private int viewType;

    public TimeData(String time, boolean check, int viewType) {
        this.time = time;
        this.check = check;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
