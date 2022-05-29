package edu.skku.cs.todocalendar.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Presenter.ItemTouchHelperListener;
import edu.skku.cs.todocalendar.R;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ItemViewHolder> implements ItemTouchHelperListener {

    private Context mContext;
    private ArrayList<Plan> items = new ArrayList<>();

    public ListViewAdapter(Context mContext, ArrayList<Plan> items){
        this.mContext = mContext;
        this.items = items;
    }

    public ListViewAdapter(CalendarActivity mContext) {
        this.mContext = mContext;
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
        //이동할 객체 저장
        Plan plan = items.get(from_position);
        //이동할 객체 삭제
        items.remove(from_position);
        //이동하고 싶은 position에 추가
        items.add(to_position, plan);

        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    // Change Item 'done' status
    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

    // Delete Item
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

    /*
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listviewlayout, viewGroup, false);
        }

        TextView obj = view.findViewById(R.id.title);
        obj.setText(plans.get(i).getTitle());

        return view;
    }
    */

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        public void onBind(Plan todo) {
            title.setText(todo.getTitle());
        }
    }
}
