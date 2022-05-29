package edu.skku.cs.todocalendar.View;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.skku.cs.todocalendar.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;
    public ConstraintLayout background;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener)
    {
        super(itemView);
        background = itemView.findViewById(R.id.calendar_cell);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
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
