package com.ansel.controller;


import com.alibaba.fastjson.JSON;
import com.ansel.model.RespResult;
import com.ansel.pojo.Choice;
import com.ansel.pojo.RespData;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;

@Controller
public class ChatBox {



    @Value(value = "${gpt.url}")
    private String url;

    @Value(value = "${gpt.modelName}")
    private String modelName;

    @Value(value = "${gpt.pk}")
    private String pk;

    @PostMapping("/ask")
    @ResponseBody
    public RespResult askGpt(String content) throws IOException {
        String answer = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String paramJson = "{\n" +
                "     \"model\": \""+modelName+"\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \""+content+"\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        Header[] allHeaders = post.getAllHeaders();
        for (Header allHeader : allHeaders) {
            System.out.println(allHeader);
        }

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
            RespData respData = JSON.parseObject(res, RespData.class);
            for (Choice choice : respData.getChoices()) {
                answer = choice.getMessage().getContent();
            }
            System.out.println(respData);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

        return RespResult.success(answer);

    }
    @PostMapping("/test")
    @ResponseBody
    public RespResult test(String content) throws IOException{
        System.out.println(content);
        return RespResult.success(content);
    }


    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
