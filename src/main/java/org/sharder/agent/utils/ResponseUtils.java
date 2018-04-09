package org.sharder.agent.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.sharder.agent.domain.ErrorDescription;
import org.sharder.agent.exception.SharderAgentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ResponseUtils
 *
 * @author bubai
 * @date 2018/4/9
 */
public class ResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    public static <T> T convert(Response response, Class<T> clazz) throws Exception {
        return convert(response, clazz, 0);
    }

    public static <T> T convert(Response response, Class<T> clazz, int trimIndex) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String responseStr = null;
        if(response.isSuccessful()){
            responseStr = response.body().string();
            for (int i=0; i<trimIndex; i++) {
                responseStr = responseStr.substring(responseStr.indexOf("["),responseStr.lastIndexOf("]")+1);
            }
            logger.debug("response success:{}",responseStr);
            ErrorDescription ed = mapper.readValue(responseStr,ErrorDescription.class);
            if (ed.getErrorDescription() != null) {
                throw new SharderAgentException(ed);
            }
        }
        return clazz == Void.class? null : mapper.readValue(responseStr, clazz);
    }
}
