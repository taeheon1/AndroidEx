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

import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private ArrayList<ReservationDTO> reservList = null;
    LayoutInflater inflater;
    RequestManager glide;
    Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView office_name;
        TextView office_price;
        TextView office_start_time;
        TextView office_stdaddr;
        TextView office_end_time;

        ViewHolder(View itemView) {
            super(itemView);

            office_name = itemView.findViewById(R.id.office_name);
            office_price = itemView.findViewById(R.id.office_price);
            office_start_time = itemView.findViewById(R.id.office_start_time);
            office_stdaddr = itemView.findViewById(R.id.office_stdaddr);
            office_end_time = itemView.findViewById(R.id.office_end_time);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = getAdapterPosition();
                            Intent intent;
                            if (!reservList.get(position).getOff_unit().equals("월")) {
                                intent = new Intent(activity, OfficeDetailActivity.class);

                            } else {
                                intent = new Intent(activity, OfficeDetailMonthActivity.class);
                            }
                            intent.putExtra("off_no", reservList.get(position).getOff_no());
                            activity.startActivity(intent);

                        }
                    }
            );
        }
    }

    public ReservationAdapter(ArrayList<ReservationDTO> reservList, LayoutInflater l, RequestManager r, Activity a) {
        this.reservList = reservList;
        this.inflater = l;
        this.glide = r;
        this.activity = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view;
        view = inflater.inflate(R.layout.reservist, parent, false);
        ViewHolder vh = new ReservationAdapter.ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        URL url = null;

        String name = reservList.get(position).getOff_name();
        int no = reservList.get(position).getOff_no();
        int people = reservList.get(position).getRes_people();
        String price = reservList.get(position).getRoom_price();
        Timestamp startdatetime = reservList.get(position).getRes_startdatetime();
        Timestamp enddatetime = reservList.get(position).getRes_enddatetime();

        holder.office_name.setText(name);
        holder.office_price.setText(price);
        holder.office_stdaddr.setText(name);
        holder.office_name.setText(name);
        holder.office_name.setText(name);

    }

    @Override
    public int getItemCount() {

        return reservList.size();

    }
}
