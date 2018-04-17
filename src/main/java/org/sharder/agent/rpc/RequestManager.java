package org.sharder.agent.rpc;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.net.www.protocol.http.Handler;

import java.io.File;
import java.io.IOException;
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
    @Value("${agent.action_url}")
    private String ACTION_URL;
    private static volatile RequestManager mInstance;
    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;
    public static final int TYPE_POST_MULTIPART = 2;
    private OkHttpClient mOkHttpClient;
    private Handler okHttpHandler;

    /**
     * init RequestManager
     */
    public RequestManager() {
        //init OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(0, TimeUnit.MINUTES)
                .readTimeout(0, TimeUnit.MINUTES)
                .writeTimeout(0, TimeUnit.MINUTES)
                .build();
        okHttpHandler = new Handler();
    }

    /**
     * getInstance
     * @return RequestManager
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
     * @param requestType request type
     * @param paramsMap   request parameters
     */
    public Response requestSyn(int requestType, HashMap<String, String> paramsMap) throws IOException {
        Response response = null;
        switch (requestType) {
            case TYPE_GET:
                response = requestGetBySyn(paramsMap);
                break;
            case TYPE_POST:
                response = requestPostBySyn(paramsMap);
                break;
        }
        return response;
    }

    /**
     * okHttp sync get request
     * @param paramsMap request parameters
     * @return Response request response
     */
    private Response requestGetBySyn(HashMap<String, String> paramsMap) throws IOException {
        StringBuilder tempParams = new StringBuilder();
        Response response = null;
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            //URLEncoder for the params
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }
        String requestUrl = String.format("%s/%s?%s", BASE_URL, ACTION_URL, tempParams.toString());
        Request request = new Request.Builder().url(requestUrl).build();
        final Call call = mOkHttpClient.newCall(request);
        response = call.execute();
        return response;
    }

    /**
     * okHttp sync post request
     * @param paramsMap request parameters
     * @return Response request response
     */
    private Response requestPostBySyn(HashMap<String, String> paramsMap) throws IOException {
        Response response = null;
        FormBody.Builder formBody = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            formBody.add(key,paramsMap.get(key));
        }
        Request request = new Request.Builder()
                .url(BASE_URL+"/"+ACTION_URL)
                .post(formBody.build())
                .build();
        final Call call = mOkHttpClient.newCall(request);
        response = call.execute();
        return response;
    }

    /**
     * okHttp sync post request
     * @param paramsMap request parameters
     * @param file request file
     * @return Response request response
     * @throws IOException
     */
    public Response requestPostMultipartBySyn(HashMap<String, String> paramsMap, File file) throws IOException {
        Response response = null;
        MultipartBody.Builder requestBuilder = new MultipartBody.Builder();
        requestBuilder.setType(MultipartBody.FORM);
        if(file != null) {
            RequestBody fileBody = RequestBody.create(MediaType.parse(paramsMap.get("type")), file);
            requestBuilder.addPart(Headers.of(
                    "Content-Disposition",
                    "form-data; name=\"file\"; filename=\"" + file.getName() + "\"")
                    , fileBody);
        }
        for (String key : paramsMap.keySet()) {
            requestBuilder.addFormDataPart(key,paramsMap.get(key));
        }
        Request request = new Request.Builder()
                .url(BASE_URL+"/"+ACTION_URL)
                .post(requestBuilder.build())
                .build();
        logger.debug(request.toString());
        final Call call = mOkHttpClient.newCall(request);
        response = call.execute();
        Headers requestHeaders= response.networkResponse().request().headers();
        logger.debug(requestHeaders.toString());
        return response;
    }
}
