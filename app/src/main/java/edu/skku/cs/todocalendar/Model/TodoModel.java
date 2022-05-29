package edu.skku.cs.todocalendar.Model;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Classes.User;
import edu.skku.cs.todocalendar.Presenter.CalendarContract;
import edu.skku.cs.todocalendar.Presenter.CalendarPresenter;
import edu.skku.cs.todocalendar.Presenter.TodoContract;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TodoModel {
    TodoContract.Presenter presenter;

    public TodoModel(TodoContract.Presenter presenter){
        this.presenter = presenter;
    }
    public Boolean addPlan(String uid, String title, String memo, int year, int month, int day){
        // POST addPlan()
        Boolean success = false;
        try {
            // make JSON
            Plan plan = new Plan();
            plan.setUid(uid);
            plan.setTitle(title);
            plan.setMemo(memo);
            plan.setYear(year);
            plan.setMonth(month);
            plan.setDay(day);
            Gson gson = new Gson();
            String json = gson.toJson(plan, Plan.class);
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ofihg9ck5b.execute-api.ap-northeast-2.amazonaws.com/dev/addplan").newBuilder();
            String url = urlBuilder.build().toString();
            // POST Request
            Request req = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();
            Log.e("json", json);
            Response response = client.newCall(req).execute();
            // Get Response
            final String Response = response.body().string();
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(Response);
            success = jsonObject.getBoolean("success");
            Log.e("addPlanResponse", success.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.e("addPlanResponse", "HTTPErr");
        }
        if (success){
            return true;
        }else{
            return false;
        }
    }
    public Boolean changePlan(int id, String title, String memo, int year, int month, int day){
        // POST changePlan()
        Boolean success = false;
        try {
            // make JSON
            Plan plan = new Plan();
            plan.setId(id);
            plan.setTitle(title);
            plan.setMemo(memo);
            plan.setYear(year);
            plan.setMonth(month);
            plan.setDay(day);
            Gson gson = new Gson();
            String json = gson.toJson(plan, Plan.class);
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ofihg9ck5b.execute-api.ap-northeast-2.amazonaws.com/dev/updateplan").newBuilder();
            String url = urlBuilder.build().toString();
            // POST Request
            Request req = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();
            Log.e("json", json);
            Response response = client.newCall(req).execute();
            // Get Response
            final String Response = response.body().string();
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(Response);
            success = jsonObject.getBoolean("success");
            Log.e("changePlanResponse", success.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.e("changePlanResponse", "HTTPErr");
        }
        if (success){
            return true;
        }else{
            return false;
        }
    }
}
