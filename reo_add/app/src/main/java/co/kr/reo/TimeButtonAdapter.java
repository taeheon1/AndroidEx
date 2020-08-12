package co.kr.reo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeButtonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<TimeData> buttonList = null;
    private ArrayList<Integer> blocknum = new ArrayList<>();

    LayoutInflater inflater;
    Activity activity;
    int month;
    int day;
    int check = 0;
    private int start = -1;
    private int end = 0;
    String startDate;
    String endDate = "0";
    ArrayList<Integer> blockstart;
    ArrayList<Integer> blockend;
    int sub = 0;

    HashMap<Integer, RecyclerView.ViewHolder> map = new HashMap<>();

    public TimeButtonAdapter(ArrayList<TimeData> buttonList, LayoutInflater l, Activity a, int month, int day, ArrayList<Integer> blockstart, ArrayList<Integer> blockend) {
        this.buttonList = buttonList;
        this.inflater = l;
        this.activity = a;
        this.month = month + 1;
        this.day = day;
        this.blockstart = blockstart;
        this.blockend = blockend;

    }

    public interface OnItemClickListener {
        void onItemClick(View v, int start, int end, int checking, String startDate, String endDate, int sub);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        Button time;
        TextView checkType;
        TextView checkText;

        ViewHolder1(final View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.btnTime);
            checkType = itemView.findViewById(R.id.checkType);
            checkText = itemView.findViewById(R.id.checkText);
            time.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sub = 0;
                            if (check == 0 || check == 2) {
                                if (check == 2) {
                                    clearing(start, end);
                                }
                                startDate = time.getText().toString();
                                start = Integer.valueOf(checkType.getText().toString());
                                check = 1;
                            } else if (check == 1) {
                                endDate = time.getText().toString();
                                end = Integer.valueOf(checkType.getText().toString());
                                if (end < start) {
                                    int change = end;
                                    end = start;
                                    start = change;
                                    String change2 = endDate;
                                    endDate = startDate;
                                    startDate = change2;
                                }
                                check = 2;
                                checking(start, end);
                            }
                            if(sub ==0){
                                view.setBackgroundColor(Color.rgb(255, 127, 0));
                            }

                            if (mListener != null) {
                                mListener.onItemClick(view, start, end, check, startDate, endDate, sub);
                            }
                        }
                    }
            );

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        if (viewType == Code.ViewType.tim1) {
            view = inflater.inflate(R.layout.buttonlist1, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim2) {
            view = inflater.inflate(R.layout.buttonlist2, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim3) {
            view = inflater.inflate(R.layout.buttonlist3, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim4) {
            view = inflater.inflate(R.layout.buttonlist4, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim5) {
            view = inflater.inflate(R.layout.buttonlist5, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim6) {
            view = inflater.inflate(R.layout.buttonlist6, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim7) {
            view = inflater.inflate(R.layout.buttonlist7, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim8) {
            view = inflater.inflate(R.layout.buttonlist8, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim9) {
            view = inflater.inflate(R.layout.buttonlist9, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim10) {
            view = inflater.inflate(R.layout.buttonlist10, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim11) {
            view = inflater.inflate(R.layout.buttonlist11, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim12) {
            view = inflater.inflate(R.layout.buttonlist12, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else if (viewType == Code.ViewType.tim13) {
            view = inflater.inflate(R.layout.buttonlist13, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        } else {
            view = inflater.inflate(R.layout.buttonlist14, parent, false);
            return new TimeButtonAdapter.ViewHolder1(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Calendar calendar = Calendar.getInstance();
        String res_time = buttonList.get(position).getTime();
        int viewtype = buttonList.get(position).getViewType();

        ((ViewHolder1) holder).time.setText(res_time);
        ((ViewHolder1) holder).checkType.setText(String.valueOf(viewtype));

        if ((month == calendar.getTime().getMonth() + 1) && (day == (calendar.getTime().getDate()))) {
            if ((calendar.getTime().getHours() >= Integer.valueOf(res_time.split(":")[0]))) {
                ((ViewHolder1) holder).time.setEnabled(false);
                ((ViewHolder1) holder).time.setBackgroundColor(Color.GRAY);
            }
        }
        for(int i = 0; i < blockstart.size(); i++){
            if (viewtype >= blockstart.get(i) && viewtype <= blockend.get(i)) {
                ((ViewHolder1) holder).time.setEnabled(false);
                ((ViewHolder1) holder).time.setBackgroundColor(Color.GRAY);
                blocknum.add(viewtype);
            }
        }

        map.put(viewtype, (ViewHolder1) holder);


    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return buttonList.get(position).getViewType();
    }

    public void checking(int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = 0; j < blocknum.size(); j++) {
                if (blocknum.get(j) == i) {
                    sub = 1;
                    return;
                }
            }

        }

        for (int i = start; i <= end; i++) {

            map.get(i).itemView.findViewById(R.id.btnTime).setBackgroundColor(Color.rgb(255, 127, 0));

        }


    }

    public void clearing(int start, int end) {
        for (int i = start; i <= end; i++) {
            map.get(i).itemView.findViewById(R.id.btnTime).setBackgroundColor(Color.TRANSPARENT);

        }
        for (int i = 0; i < blocknum.size(); i++) {
            map.get(blocknum.get(i)).itemView.findViewById(R.id.btnTime).setBackgroundColor(Color.GRAY);
        }

    }

}
