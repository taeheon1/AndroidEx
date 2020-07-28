package co.kr.test200722;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OfficeListAdapter extends RecyclerView.Adapter<OfficeListAdapter.ViewHolder> {
    List<OfficeDTO> officeDTO = new ArrayList<>();
    Context context;

    public OfficeListAdapter(List<OfficeDTO> officeDTO, Context context) {
        this.officeDTO = officeDTO;
        this.context = context;
    }

    @NonNull
    @Override
    public OfficeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.off_list_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfficeListAdapter.ViewHolder holder, int position) {
        OfficeDTO dto = officeDTO.get(position);
        holder.off_name.setText(dto.getOff_name());
        holder.off_rent.setText(dto.getOff_rent());
        holder.off_stdAddr.setText(dto.getOff_stdAddr());
    }

    @Override
    public int getItemCount() {
        return officeDTO.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView off_name;
        TextView off_rent;
        TextView off_stdAddr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            off_name = itemView.findViewById(R.id.off_name);
            off_rent = itemView.findViewById(R.id.off_rent);
            off_stdAddr = itemView.findViewById(R.id.off_stdAddr);
        }
    }
}
