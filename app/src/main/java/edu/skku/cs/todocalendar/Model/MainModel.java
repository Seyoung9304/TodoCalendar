package edu.skku.cs.todocalendar.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.skku.cs.todocalendar.Presenter.MainContract;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainModel {
    MainContract.Presenter presenter;
    public MainModel(MainContract.Presenter presenter){
        this.presenter = presenter;
    }
    public Boolean checkIDPW(String id, String pw) {
        // get request and check if id, pw is correct
        // API 통신
        Boolean success = false;
        try {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ofihg9ck5b.execute-api.ap-northeast-2.amazonaws.com/dev/login").newBuilder();
            urlBuilder.addQueryParameter("uid", id);
            urlBuilder.addQueryParameter("pw", pw);
            String url = urlBuilder.build().toString();
            Request req = new Request.Builder().url(url).build();
            Response response = client.newCall(req).execute();
            final String Response = response.body().string();
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(Response);
            success = jsonObject.getBoolean("success");
        } catch (JSONException | IOException e) {
            Log.e("checkidpw", "error");
            e.printStackTrace();
        }
        Log.e("uid", id);
        Log.e("success?", success.toString());
        if (success){
            return true;
        }else{
            return false;
        }
    }
}
