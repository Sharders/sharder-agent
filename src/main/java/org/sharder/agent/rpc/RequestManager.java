package org.sharder.agent.rpc;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.net.www.protocol.http.Handler;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * RequestManager
 *
 * @author bubai
 * @date 2018/3/24
 */
@Component
public class RequestManager {
    private static final Logger logger = LoggerFactory.getLogger(RequestManager.class);
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain;charset=utf-8");
    private static final String TAG = RequestManager.class.getSimpleName();
    @Value("${agent.rpc_url}")
    private String BASE_URL;
    private static volatile RequestManager mInstance;
    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;
    private OkHttpClient mOkHttpClient;
    private Handler okHttpHandler;

    /**
     * 初始化RequestManager
     */
    public RequestManager() {
        //init OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        okHttpHandler = new Handler();
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static RequestManager getInstance() {
        RequestManager inst = mInstance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new RequestManager();
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     *  okHttp request entrance
     * @param actionUrl  request action
     * @param requestType request type
     * @param paramsMap   request parameters
     */
    public Response requestSyn(String actionUrl, int requestType, HashMap<String, String> paramsMap) {
        Response response = null;
        switch (requestType) {
            case TYPE_GET:
                response = requestGetBySyn(actionUrl, paramsMap);
                break;
            case TYPE_POST:
                response = requestPostBySyn(actionUrl, paramsMap);
                break;
        }
        return response;
    }

    /**
     * okHttp sync get request
     * @param actionUrl request action
     * @param paramsMap request parameters
     * @return Response request response
     */
    private Response requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        Response response = null;
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //URLEncoder for the params
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, tempParams.toString());
            Request request = new Request.Builder().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            response = call.execute();
        } catch (Exception e) {
            logger.debug(TAG, e.toString());
        }
        return response;
    }

    /**
     * okHttp sync post request
     * @param actionUrl request action
     * @param paramsMap request parameters
     * @return Response request response
     */
    private Response requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        Response response = null;
        try {
            FormBody.Builder formBody = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                formBody.add(key,paramsMap.get(key));
            }
            Request request = new Request.Builder()
                    .url(BASE_URL+"/"+actionUrl)
                    .post(formBody.build())
                    .build();
            final Call call = mOkHttpClient.newCall(request);
            response = call.execute();
            logger.debug("response :{}",response.toString());
        } catch (Exception e) {
            logger.debug(TAG, e.toString());
        }
        return response;
    }
}
