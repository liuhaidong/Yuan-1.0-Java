package ai.bitwill.xyz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import ai.bitwill.xyz.config.EngineType;
import ai.bitwill.xyz.config.User;
import ai.bitwill.xyz.config.Yuan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;
import org.springframework.util.DigestUtils;
import com.alibaba.fastjson.JSONObject;

@RestController
public class YuanController {
    private static final String template = "Hello, %application.properties!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private User user;

    @Autowired
    private Yuan yuan;

    @GetMapping("/dialog")
    public Answer dialog(@RequestParam(value = "ask", defaultValue = "") String ask) throws InterruptedException {
        String question = "对话：“%s”";
        question = String.format(question,ask);
       question = yuan.getDialogSample() + question;
        String requestId = doSubmitRequest(EngineType.DIALOG,question);
        Thread.sleep(yuan.getWaitingTime());
        String resData = doReplyRequest(requestId);
        return new Answer(resData);
    }

    @GetMapping("/translate")
    public Answer translate(@RequestParam(value = "ask", defaultValue = "") String ask) throws InterruptedException {
        String question = "将下列中文翻译成英文。中文：%s 英文：";
        String requestId = doSubmitRequest(EngineType.TRANSLATE,String.format(question,ask));
        Thread.sleep(yuan.getWaitingTime());
        String resData = doReplyRequest(requestId);
        return new Answer(resData);
    }

    @GetMapping("/poetry")
    public Answer poetry(@RequestParam(value = "ask", defaultValue = "") String ask) throws InterruptedException {
        String question = "以%s为题作一首诗：";
        String requestId = doSubmitRequest(EngineType.BASE10B,String.format(question,ask));
        Thread.sleep(yuan.getWaitingTime());
        String resData = doReplyRequest(requestId);
        return new Answer(resData);
    }

    @GetMapping("/any")
    public Answer any(@RequestParam(value = "ask", defaultValue = "") String ask) throws InterruptedException {
        String requestId = doSubmitRequest(EngineType.BASE10B,ask);
        Thread.sleep(yuan.getWaitingTime());
        String resData = doReplyRequest(requestId);
        return new Answer(resData);
    }

    //
    public static String getApiToken(String str) {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return getMD5(str+today);
    }

    public static String getMD5(String str) {
        String base = str;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public String doSubmitRequest(EngineType engineType,String question){
        RestTemplate restTemplate = getRestTemplate();

        String params = String.format("engine=%s&account=%s&data=%s&temperature=%s&topP=%s&topK=%s&tokensToGenerate=%d&type=%s",
                engineType.getEngineType(),user.getUserName(),question,yuan.getTemperature(),yuan.getTopP(),yuan.getTopK(), yuan.getMax_tokens(),"api");
        ResponseEntity<JSONObject> response
                = restTemplate.getForEntity(yuan.getSubmitUrl() + params, JSONObject.class);
        System.out.println(yuan.getSubmitUrl() + params);
        return response.getBody().getString("resData");
    }

    public String doReplyRequest(String requestId){
        RestTemplate restTemplate = getRestTemplate();

        String params = String.format("account=%s&requestId=%s",
                user.getUserName(),requestId);
        ResponseEntity<JSONObject> response
                = restTemplate.getForEntity(yuan.getReplyUrl() + params, JSONObject.class);
        System.out.println(response.getBody().toJSONString());
        return response.getBody().getString("resData");
    }

    private RestTemplate getRestTemplate() {
        String apiTokey = getApiToken(user.getUserName()+user.getMobile());
        RestTemplate restTemplate = new RestTemplate();
        //Add a ClientHttpRequestInterceptor to the RestTemplate
        restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor(){
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().set("token",apiTokey);//Set the header for each request
                return execution.execute(request, body);
            }
        });
        return restTemplate;
    }
}
