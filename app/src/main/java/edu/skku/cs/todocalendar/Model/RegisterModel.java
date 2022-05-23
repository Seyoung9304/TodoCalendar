package edu.skku.cs.todocalendar.Model;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.skku.cs.todocalendar.Classes.User;
import edu.skku.cs.todocalendar.Presenter.RegisterContract;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterModel {
    RegisterContract.Presenter presenter;

    public RegisterModel(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }
    public Boolean checkID(String id){
        // POST request
        Boolean success = false;
        try {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ofihg9ck5b.execute-api.ap-northeast-2.amazonaws.com/dev/checkid").newBuilder();
            urlBuilder.addQueryParameter("uid", id);
            String url = urlBuilder.build().toString();
            Request req = new Request.Builder().url(url).build();
            Response response = client.newCall(req).execute();
            final String Response = response.body().string();
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(Response);
            success = jsonObject.getBoolean("success");
            Log.e("checkIDResponse", success.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        if (success){
            return true;
        }else{
            return false;
        }
    }
    public Boolean registerUser(String id, String pw, String pw_verify){
        Boolean success = false;
        if (pw.compareTo(pw_verify)==0){
            try {
                // make JSON
                User user = new User(id, pw);
                Gson gson = new Gson();
                String json = gson.toJson(user, User.class);
                OkHttpClient client = new OkHttpClient();
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ofihg9ck5b.execute-api.ap-northeast-2.amazonaws.com/dev/adduser").newBuilder();
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
                Log.e("registerUSERResponse", success.toString());
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                Log.e("registerUSER", "HTTPErr");
            }
        }else{
            Log.e("registerUSER", pw);
            Log.e("registerUSER", pw_verify);
            Log.e("registerUSER", "pw not verified");
            return false;
        }

        if (success){
            return true;
        }else{
            return false;
        }
    }
}
