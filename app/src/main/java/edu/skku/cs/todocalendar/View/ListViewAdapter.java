package edu.skku.cs.todocalendar.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.R;

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Plan> plans;

    public ListViewAdapter(Context mContext, ArrayList<Plan> plans){
        this.mContext = mContext;
        this.plans = plans;
    }

    @Override
    public int getCount() {
        return plans.size();
    }

    @Override
    public Object getItem(int i) {
        return plans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listviewlayout, viewGroup, false);
        }

        CheckBox obj = view.findViewById(R.id.todo_object);
        obj.setText(plans.get(i).getTitle());
        obj.setChecked(plans.get(i).getDone());

        return view;
    }
}
