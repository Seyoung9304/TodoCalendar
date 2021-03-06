package edu.skku.cs.todocalendar.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Presenter.CalendarContract;
import edu.skku.cs.todocalendar.Presenter.TodoPresenter;
import edu.skku.cs.todocalendar.R;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ItemViewHolder> implements CalendarContract.ItemTouchHelperListener {

    private Context mContext;
    private ArrayList<Plan> items = new ArrayList<>();
    CalendarContract.TodoPresenter todoPresenter;

    public ListViewAdapter(Context mContext, ArrayList<Plan> items){
        this.mContext = mContext;
        this.items = items;
        todoPresenter = new TodoPresenter(this);
    }

    public ListViewAdapter(CalendarActivity mContext) {
        this.mContext = mContext;
        todoPresenter = new TodoPresenter(this);
    }

    @NonNull
    @Override
    public ListViewAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listviewlayout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ItemViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Plan> items){
        this.items = items;
        notifyDataSetChanged();
        Log.e("listviewadapter", "setItems");
        Log.e("listviewadapter", String.valueOf(this.items.size()));
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //????????? ?????? ??????
        Plan plan = items.get(from_position);
        //????????? ?????? ??????
        items.remove(from_position);
        //???????????? ?????? position??? ??????
        items.add(to_position, plan);

        //Adapter??? ????????? ????????????
        // notifyItemMoved(from_position,to_position);
        notifyDataSetChanged();
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        return;
    }

    //public class DoneClickThread extends Thread{}

    // Change Item 'done' status
    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {
        Plan obj = items.get(position);
        int obj_id = obj.getId();
        Boolean obj_done = obj.getDone();
        new Thread() {
            public void run(){
                Log.e("object current done", obj_done.toString());
                todoPresenter.onDoneClick(obj_id, !obj_done);
            }
        }.start();
        obj.changeStatus();
        notifyDataSetChanged();
        Toast.makeText(mContext, "Update Status Success!", Toast.LENGTH_SHORT).show();
    }

    // Delete Item
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        Plan obj = items.get(position);
        new Thread(){
            public void run(){
                todoPresenter.onDeleteClick(obj.getId());
            }
        }.start();
        items.remove(position);
        //notifyItemRemoved(position);
        notifyDataSetChanged();
        Toast.makeText(mContext, "Delete Success!", Toast.LENGTH_SHORT).show();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Plan plan = items.get(pos);

                    Intent intent = new Intent(mContext, TodoActivity.class);
                    intent.putExtra("menu", "update");
                    intent.putExtra("id", plan.getId());
                    intent.putExtra("title", plan.getTitle());
                    intent.putExtra("memo", plan.getMemo());
                    intent.putExtra("y", plan.getYear());
                    intent.putExtra("m", plan.getMonth());
                    intent.putExtra("d", plan.getDay());

                    if (mContext instanceof CalendarActivity) {
                        ((CalendarActivity)mContext).runTodoActivity(intent);
                    }
                }
            });
        }

        public void onBind(Plan todo) {
            title.setText(todo.getTitle());
            if (todo.getDone()==true){
                title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
