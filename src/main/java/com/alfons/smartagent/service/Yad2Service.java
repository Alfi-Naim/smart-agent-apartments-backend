package com.alfons.smartagent.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class Yad2Service {

    public String searchApartments(String keyword) {
        System.out.println(URLEncoder.encode(keyword, StandardCharsets.UTF_8));
        return "Searched for:" + keyword;
    }

    private String getProductHtml(String city, String type, String maxPrice) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.yad2.co.il/realestate/rent?city=" + city + "&propertyGroup=" + type + "&property=1&price=-1-" + maxPrice)
                .method("GET", body)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "__uzma=9d0bd687-68cc-453f-b7c4-869ec7a4c889; __uzmb=1672252991; __uzme=7239; _vwo_uuid_v2=D9AEC50AC165ADE48F5B14A83A139EE0F|3651339a26099d5460dbfbd088805b91; __ssds=3; __ssuzjsr3=a9be0cd8e; __uzmaj3=f6bb0ecd-7b8f-4e7f-9fd1-673665cf8fee; __uzmbj3=1672252992; y2018-2-cohort=100; leadSaleRentFree=65; use_elastic_search=1; __gads=ID=a4a3867a4e8aab72:T=1672252995:S=ALNI_MYtWLCvPlP4RmRItG3uIqLSbCBrfw; guest_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXlsb2FkIjp7InV1aWQiOiJhZWEyMjBmZS1lZmI5LTQ2MWQtOThiOC0xMDMyYTNiNTEzMDQifSwiaWF0IjoxNjcyMjUyOTk2LCJleHAiOjE3MDU0NjEyNDkzNzF9.JP67-Q841Awa_rerjxyPcJP8pQxomrqQf3Hzbiptm5A; _fbp=fb.2.1672252995957.876608532; bc.visitor_token=7013937439028084736; abTestKey=32; canary=never; _gid=GA1.3.577010160.1673859182; server_env=production; y2_cohort_2020=48; __gpi=UID=00000bb5b20e2e4c:T=1672252995:RT=1673859192:S=ALNI_MYn0SAjHXVKs3TudZXWMjZBB5Epdg; _hjSessionUser_266550=eyJpZCI6ImNmZGFlNWVkLTRjYWUtNTY0MC1iNGFkLWIxMDMzZTA3YjAxNCIsImNyZWF0ZWQiOjE2NzIyNTI5OTU4OTksImV4aXN0aW5nIjp0cnVlfQ==; recommendations-searched-2=2; recommendations-home-category={\"categoryId\":2,\"subCategoryId\":2}; _hjAbsoluteSessionInProgress=0; __uzmhj=9dd98470a179d1e8a2c0b19f648492c38dfbab3bf90f7e267bc8169b22b099ba; _hjSession_266550=eyJpZCI6IjI4N2M4NDc1LTUxMmMtNDUxNS04ODkyLTM2ZTkwMTU4NGVmNSIsImNyZWF0ZWQiOjE2NzM4Njc2NDY0NjMsImluU2FtcGxlIjpmYWxzZX0=; _hjIncludedInSessionSample=0; __uzmcj3=621653410346; __uzmdj3=1673869660; _ga=GA1.3.280480591.1672252992; favorites_userid=dhd1457615389; __uzmc=8848828055219; __uzmd=1673869911; __uzmf=7f600019209704-1819-4842-b074-65c6a44982c716722529911991616920790-f016d338e49c3d9a280; _gat_UA-708051-1=1; _ga_GQ385NHRG1=GS1.1.1673866677.3.1.1673870884.60.0.0; ; canary=never; __uzmc=7046128343758; __uzmd=1673871017; __uzmf=7f600019209704-1819-4842-b074-65c6a44982c716722529911991618025846-bd88a5ad4e5e581c283")
                .addHeader("Sec-Fetch-Dest", "document")
                .addHeader("Sec-Fetch-Mode", "navigate")
                .addHeader("Sec-Fetch-Site", "none")
                .addHeader("Sec-Fetch-User", "?1")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .addHeader("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}