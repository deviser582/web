package com.example.teat_web;

//import androidx.appcompat.app.AlertController;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private RecyclerView recyclerView;
    private List<RvData>list = new ArrayList<>();
    private RvAdapter rvAdapter = new RvAdapter(list);
    TextView responseText;
    String responsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.reponse_text);
        sendRequest.setOnClickListener(this);

        recyclerView = findViewById(R.id.re_demo);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvAdapter);

        list.add(new RvData("hhh","jjj","lll"));
        list.add(new RvData("qqq","www","eee"));

        rvAdapter.notifyDataSetChanged();
        jsonDecodeTest();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithHttpURLConnection("https://www.wanandroid.com/article/list/0/json");
        }
    }

    private void sendRequestWithHttpURLConnection(final String mUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mUrl);
                    HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    String responsData = StreamToString(in);
                    showResponse(responsData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        String oneline;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try{
            while ((oneline = reader.readLine()) != null){
                sb.append(oneline).append('\n');
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }

    /*private void jsonDecodeTest(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObjectDate = jsonObject.getJSONObject("data");
            String curPage = jsonObjectDate.getString("curPage");
            Toast.makeText(this,curPage,Toast.LENGTH_LONG).show();

            JSONArray jsonArraydatas = jsonObjectDate.getJSONArray("datas");
            for (int i=0;i < jsonArraydatas.length();i++) {
                JSONObject jsonObjectdata = jsonArraydatas.getJSONObject(i);
                String link = jsonObjectdata.getString("link");
                String shareUser = jsonObjectdata.getString("shareUser");
                String title = jsonObjectdata.getString("title");
                list.add(new RvData(title,shareUser,link));
            }
            rvAdapter.notifyDataSetChanged();
            String offset = jsonObjectDate.getString("offset");
            String over = jsonObjectDate.getString("over");
            String pageCount = jsonObjectDate.getString("pageCount");
            String size = jsonObjectDate.getString("size");
            String total = jsonObjectDate.getString("total");

            String errorCode = jsonObject.getString("errorCode");
            String errormsg = jsonObject.getString("errormsg");
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private void jsonDecodeTest(){
        String jsonData = "{\"school\":{\"name\":\"某中学\",\"presidnet\": {\"name\":\"WXZ\",\"salary\":100000000,\"age\":30},\"student\": [{\"name\":\"A\",\"age\":19},{\"name\":\"B\",\"age\":19}, {\"name\":\"C\",\"age\":18}]}}";
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONObject jsonObjectSchool = jsonObject.getJSONObject("school");
                    String schoolName = jsonObjectSchool.getString("name");
                    JSONObject jsonObjectPresident = jsonObjectSchool.getJSONObject("presidnet");
                    String presidentName = jsonObjectPresident.getString("name");
                    String presidentSalary = jsonObjectPresident.getString("salary");
                    String presidentAge = jsonObjectPresident.getString("age");
                    Toast.makeText(this, schoolName, Toast.LENGTH_LONG).show();
                    JSONArray jsonArrayStudents =
                            jsonObjectSchool.getJSONArray("student");
                    for (int i = 0; i < jsonArrayStudents.length(); i++) {
                        JSONObject jsonObjectStu = jsonArrayStudents.getJSONObject(i);
                        String stuNum = jsonObjectStu.getString("name");
                        Toast.makeText(this, stuNum, Toast.LENGTH_SHORT).show();
                    }
                    list.add(new RvData(presidentName,presidentSalary,presidentAge));
                    rvAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

    }
}
