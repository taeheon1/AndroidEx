package co.kr.reo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;

public class OfficeAdapter extends RecyclerView.Adapter<OfficeAdapter.ViewHolder> {

    private ArrayList<OfficeDTO> boardList = null;
    LayoutInflater inflater;
    RequestManager glide;
    Activity activity;
    String image1;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView office_name;
        TextView office_rent;
        TextView office_type;
        TextView office_stdaddr;
        ImageView office_img;

        ViewHolder(View itemView) {
            super(itemView);

            office_name = itemView.findViewById(R.id.office_name);
            office_rent = itemView.findViewById(R.id.office_rent);
            office_type = itemView.findViewById(R.id.office_type);
            office_stdaddr = itemView.findViewById(R.id.office_stdaddr);
            office_img = itemView.findViewById(R.id.office_img);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = getAdapterPosition();
                            Intent intent;
                            if(!boardList.get(position).getOff_unit().equals("월")){
                               intent = new Intent(activity, OfficeDetailActivity.class);

                            } else {
                               intent = new Intent(activity, OfficeDetailMonthActivity.class);
                            }
                            intent.putExtra("off_no", boardList.get(position).getOff_no());
                            activity.startActivity(intent);

                        }
                    }
            );
        }
    }

    public OfficeAdapter(ArrayList<OfficeDTO> boardList, LayoutInflater l, RequestManager r, Activity a) {
        this.boardList = boardList;
        this.inflater = l;
        this.glide = r;
        this.activity = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        view = inflater.inflate(R.layout.officelist, parent, false);
        ViewHolder vh = new OfficeAdapter.ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        URL url = null;
        String rent;
        String type;

        holder.office_name.setText(boardList.get(position).getOff_name());
        switch (boardList.get(position).getOff_type().split(",")[0]) {
            case "공유오피스":
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
        holder.office_rent.setText(rent + "원/ " + boardList.get(position).getOff_unit());

        type = boardList.get(position).getOff_type();
        type = type.replace(",", " #");
        holder.office_type.setText("#" + type);

        holder.office_stdaddr.setText(boardList.get(position).getOff_stdAddr());
        image1 = boardList.get(position).getOff_image();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("images/" + image1);
        ref.getDownloadUrl().addOnCompleteListener(
                new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            glide.load(task.getResult()).placeholder(R.drawable.loading).into(holder.office_img);
                        } else {
                            // URL을 가져오지 못하면 토스트 메세지
                            Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }

    @Override
    public int getItemCount() {

        return boardList.size();

    }
}
