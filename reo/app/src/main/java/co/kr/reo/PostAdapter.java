package co.kr.reo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<OfficeDTO> boardList = null ;
    LayoutInflater inflater;
    RequestManager glide;
    Activity activity;
    Context context;
    ArrayList<String> unFilteredList;
    ArrayList<String> filteredList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView) ;
            if(boardList == null){
                // 뷰 객체에 대한 참조. (hold strong reference)
                textView1 = itemView.findViewById(R.id.research_map);
                itemView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(activity,MapSearchActivity.class);
                                activity.startActivity(intent);
                            }
                        }
                );
            } else {
                textView1 = itemView.findViewById(R.id.office_name);
                textView2 = itemView.findViewById(R.id.office_rent);
                textView3 = itemView.findViewById(R.id.office_type);
                textView4 = itemView.findViewById(R.id.office_stdaddr);
                imageView = itemView.findViewById(R.id.office_img);

                itemView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(activity,OfficeDetailActivity.class);
                                activity.startActivity(intent);
                            }
                        }
                );

            }

        }
    }

    public PostAdapter(ArrayList<OfficeDTO> boardList ,LayoutInflater l, RequestManager r, Activity a){
        this.boardList = boardList;
        this.inflater = l;
        this.glide = r;
        this.activity = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        if(boardList == null){
            view = inflater.inflate(R.layout.boardlist, parent, false);
        } else {
            view = inflater.inflate(R.layout.officelist, parent, false);

        }
        ViewHolder vh=new PostAdapter.ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        URL url = null;
        String rent;
        String type;
        if (boardList == null){
            holder.textView1.setText("map");

        } else {
            holder.textView1.setText(boardList.get(position).getOff_name());
            switch (boardList.get(position).getOff_type().split(",")[0]){
                case "공유오피스" :
                    rent = String.valueOf(boardList.get(position).getOff_rent());
                    break;
                case "회의실":
                    rent = String.valueOf(boardList.get(position).getOff_rentConfer());
                    break;
                case "세미나실":
                    rent = String.valueOf(boardList.get(position).getOff_rentSemi());
                    break;
                case "다목적홀":
                    rent = String.valueOf(boardList.get(position).getOff_rentHall());
                    break;
                default:
                    rent = String.valueOf(boardList.get(position).getOff_rentStudy());
            }
            holder.textView2.setText(rent + "원/ " + boardList.get(position).getOff_unit());

            type = boardList.get(position).getOff_type();
            type = type.replace(",", " #");
            holder.textView3.setText("#"+type);

            holder.textView4.setText(boardList.get(position).getOff_stdAddr());
            glide.load("http://192.168.219.107:9090/reo/resources/upload/"+boardList.get(position).getOff_image()).into(holder.imageView);
        }
    }
    @Override
    public int getItemCount() {
        if(boardList == null){
            return 1;
        } else {
            return boardList.size();
        }
    }
}
