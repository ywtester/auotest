package utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtils {
    private static CloseableHttpClient client;
    private static RequestConfig config;
    static{
        config = RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).build();
        CloseableHttpClient client = HttpClients.custom().build();
    }
    public static String doGet(String url, Map<String,String> params,Map<String,String> headers) {
        if (url != null) {
            try {
                HttpGet httpGet = null;
                CloseableHttpResponse response = null;
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                if(params.size()>0){
                    for (Map.Entry<String, String> entry : params.entrySet()
                    ) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }
                    url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs), "UTF-8");
                }
                httpGet = new HttpGet(url);
                if(headers.size()>0){
                    for (Map.Entry<String, String> entry :
                            headers.entrySet()) {
                        httpGet.setHeader(entry.getKey(), entry.getValue());
                    }
                }
                response = client.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                String res = null;
                if (statusCode != 200) {
                    httpGet.abort();
                    throw new RuntimeException("HttpClient发生错误:" + statusCode);
                } else {
                    if (entity != null) {
                        res = EntityUtils.toString(entity, "UTF-8");
                    }
                }
                return res;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
