package edu.skku.cs.todocalendar.Model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Presenter.CalendarContract;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalendarModel {
    String USER_ID;
    ArrayList<Plan> plans = new ArrayList<>();

    CalendarContract.CalendarPresenter presenter;
    CalendarContract.TodoPresenter todopresenter;
    public CalendarModel(CalendarContract.CalendarPresenter presenter){
        this.presenter = presenter;
    }

    public CalendarModel(CalendarContract.TodoPresenter presenter){
        this.todopresenter = presenter;
    }

    public ArrayList<Plan> getPlans(String uid) {
        try{
            USER_ID = uid;
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ofihg9ck5b.execute-api.ap-northeast-2.amazonaws.com/dev/getplan").newBuilder();
            urlBuilder.addQueryParameter("uid", uid);
            String url = urlBuilder.build().toString();
            Request req = new Request.Builder().url(url).build();
            Response response = client.newCall(req).execute();
            final String Response = response.body().string();
            JSONObject jsonObject = new JSONObject(Response);
            JSONArray jsonlist = jsonObject.getJSONArray("plans");
            for(int i=0; i<jsonlist.length(); i++){
                JSONObject tmp = (JSONObject) jsonlist.get(i);
                String tmp_str = tmp.toString();
                Gson gson = new GsonBuilder().create();
                Plan plan = gson.fromJson(tmp_str, Plan.class);
                plans.add(plan);
            }
        }catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return plans;
    }

    public ArrayList<Plan> getTodayPlans(int year, int date, int day) {
        ArrayList<Plan> todayplan = new ArrayList<>();
        ArrayList<Plan> plans = new ArrayList<>();
        try{
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ofihg9ck5b.execute-api.ap-northeast-2.amazonaws.com/dev/getplan").newBuilder();
            urlBuilder.addQueryParameter("uid", USER_ID);
            String url = urlBuilder.build().toString();
            Request req = new Request.Builder().url(url).build();
            Response response = client.newCall(req).execute();
            final String Response = response.body().string();
            JSONObject jsonObject = new JSONObject(Response);
            JSONArray jsonlist = jsonObject.getJSONArray("plans");
            for(int i=0; i<jsonlist.length(); i++){
                JSONObject tmp = (JSONObject) jsonlist.get(i);
                String tmp_str = tmp.toString();
                Gson gson = new GsonBuilder().create();
                Plan plan = gson.fromJson(tmp_str, Plan.class);
                plans.add(plan);
            }
        }catch(IOException | JSONException e){
            e.printStackTrace();
        }
        for(int i=0; i<plans.size(); i++){
            if (plans.get(i).checkDate(year, date, day)==true){
                todayplan.add(plans.get(i));
            }
        }
        return todayplan;
    }
}
