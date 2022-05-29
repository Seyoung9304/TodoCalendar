package edu.skku.cs.todocalendar.View;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.skku.cs.todocalendar.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    int selected_position = -1;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (90);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        /*
        if (selected_position == position){
            holder.top.setVisibility(View.VISIBLE);
            holder.bottom.setVisibility(View.VISIBLE);
            holder.start.setVisibility(View.VISIBLE);
            holder.end.setVisibility(View.VISIBLE);
        }
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousItem = selected_position;
                selected_position = position;
                notifyItemChanged(previousItem);
                notifyItemChanged(position);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dayOfMonth;
        private final CalendarAdapter.OnItemListener onItemListener;
        public ConstraintLayout background;
        public View top, bottom, start, end;

        public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener)
        {
            super(itemView);
            background = itemView.findViewById(R.id.calendar_cell);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            top = itemView.findViewById(R.id.box_top);
            bottom = itemView.findViewById(R.id.box_bottom);
            start = itemView.findViewById(R.id.box_start);
            end = itemView.findViewById(R.id.box_end);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            // 클릭 시 배경색 지정
            // background.setBackgroundColor(Color.parseColor("#4DBB86FC"));
            onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
        }
    }

}
