package com.okhttp3;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2016/12/5.
 */
public class BaseUse {

    OkHttpClient client=new OkHttpClient();

    //发起http请求
    String run(String url) throws IOException {
        Request request=new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
