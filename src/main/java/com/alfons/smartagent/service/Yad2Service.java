package com.alfons.smartagent.service;

import com.alfons.smartagent.model.ApartmentsFeed;
import com.alfons.smartagent.model.Neighborhood;
import com.alfons.smartagent.model.Root;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class Yad2Service {

    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Autowired
    ObjectMapper om;

    public ApartmentsFeed searchApartments(String city, String type, String minPrice, String maxPrice) throws IOException {
        String cityId = getCityId(city);
        return getApartments(cityId, type, minPrice, maxPrice);
    }

    private String getCityId(String city) throws IOException {
        Request request = new Request.Builder()
                .url("https://www.yad2.co.il/api/search/autocomplete/realestate?text=" + urlEncodeUTF8(city))
                .method("GET", null)
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "__uzma=9d0bd687-68cc-453f-b7c4-869ec7a4c889; __uzmb=1672252991; __uzme=7239; _vwo_uuid_v2=D9AEC50AC165ADE48F5B14A83A139EE0F|3651339a26099d5460dbfbd088805b91; __ssds=3; __ssuzjsr3=a9be0cd8e; __uzmaj3=f6bb0ecd-7b8f-4e7f-9fd1-673665cf8fee; __uzmbj3=1672252992; y2018-2-cohort=100; leadSaleRentFree=65; use_elastic_search=1; __gads=ID=a4a3867a4e8aab72:T=1672252995:S=ALNI_MYtWLCvPlP4RmRItG3uIqLSbCBrfw; guest_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXlsb2FkIjp7InV1aWQiOiJhZWEyMjBmZS1lZmI5LTQ2MWQtOThiOC0xMDMyYTNiNTEzMDQifSwiaWF0IjoxNjcyMjUyOTk2LCJleHAiOjE3MDU0NjEyNDkzNzF9.JP67-Q841Awa_rerjxyPcJP8pQxomrqQf3Hzbiptm5A; _fbp=fb.2.1672252995957.876608532; bc.visitor_token=7013937439028084736; abTestKey=32; canary=never; _gid=GA1.3.577010160.1673859182; server_env=production; y2_cohort_2020=48; __gpi=UID=00000bb5b20e2e4c:T=1672252995:RT=1673859192:S=ALNI_MYn0SAjHXVKs3TudZXWMjZBB5Epdg; _hjSessionUser_266550=eyJpZCI6ImNmZGFlNWVkLTRjYWUtNTY0MC1iNGFkLWIxMDMzZTA3YjAxNCIsImNyZWF0ZWQiOjE2NzIyNTI5OTU4OTksImV4aXN0aW5nIjp0cnVlfQ==; recommendations-searched-2=2; recommendations-home-category={\"categoryId\":2,\"subCategoryId\":2}; _hjAbsoluteSessionInProgress=0; __uzmhj=9dd98470a179d1e8a2c0b19f648492c38dfbab3bf90f7e267bc8169b22b099ba; __uzmcj3=596532877749; __uzmdj3=1673867646; y1-site-new=_ex_x3_org_88888_o_organic; supplier=1; favorites_userid=dhd1457615389; _hjSession_266550=eyJpZCI6IjI4N2M4NDc1LTUxMmMtNDUxNS04ODkyLTM2ZTkwMTU4NGVmNSIsImNyZWF0ZWQiOjE2NzM4Njc2NDY0NjMsImluU2FtcGxlIjpmYWxzZX0=; _ga=GA1.3.280480591.1672252992; _gat_UA-708051-1=1; _ga_GQ385NHRG1=GS1.1.1673866677.3.1.1673868183.58.0.0; __uzmc=5963024783776; __uzmd=1673868233; __uzmf=7f600019209704-1819-4842-b074-65c6a44982c716722529911991615242130-7f26cfe21a42a09f247; canary=never; __uzmc=3067225024084; __uzmd=1673944049; __uzmf=7f600019209704-1819-4842-b074-65c6a44982c716722529911991691058490-8a3808f3d2ccc506250")
                .addHeader("Referer", "https://www.yad2.co.il/realestate/rent?topArea=41&area=52&city=2660&propertyGroup=apartments&property=1&price=-1-5555")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .addHeader("mainsite_version_commit", "b37efa434ef5091747896be11dc1937304f933f5")
                .addHeader("mobile-app", "false")
                .addHeader("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        List<Neighborhood> neighborhoods = om.readValue(res, new TypeReference<>() {});
        return neighborhoods.get(0).getValue().getCity();
    }

    private ApartmentsFeed getApartments(String cityId, String type,String minPrice, String maxPrice) throws IOException {
        Request request = new Request.Builder()
                .url("https://gw.yad2.co.il/feed-search-legacy/realestate/" + type + "?city=" + cityId + "&price=" + minPrice + "-" + maxPrice + "&forceLdLoad=true")
                .method("GET", null)
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "_vwo_uuid_v2=D9AEC50AC165ADE48F5B14A83A139EE0F|3651339a26099d5460dbfbd088805b91; __ssds=3; __ssuzjsr3=a9be0cd8e; __uzmaj3=f6bb0ecd-7b8f-4e7f-9fd1-673665cf8fee; __uzmbj3=1672252992; y2018-2-cohort=100; leadSaleRentFree=65; use_elastic_search=1; __uzmb=1672252995; __gads=ID=a4a3867a4e8aab72:T=1672252995:S=ALNI_MYtWLCvPlP4RmRItG3uIqLSbCBrfw; __uzma=d3cad84b-6feb-4b3a-9c21-a2d7530078b7; __uzme=7275; guest_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXlsb2FkIjp7InV1aWQiOiJhZWEyMjBmZS1lZmI5LTQ2MWQtOThiOC0xMDMyYTNiNTEzMDQifSwiaWF0IjoxNjcyMjUyOTk2LCJleHAiOjE3MDU0NjEyNDkzNzF9.JP67-Q841Awa_rerjxyPcJP8pQxomrqQf3Hzbiptm5A; _fbp=fb.2.1672252995957.876608532; abTestKey=32; canary=never; _gid=GA1.3.577010160.1673859182; server_env=production; _hjSessionUser_266550=eyJpZCI6ImNmZGFlNWVkLTRjYWUtNTY0MC1iNGFkLWIxMDMzZTA3YjAxNCIsImNyZWF0ZWQiOjE2NzIyNTI5OTU4OTksImV4aXN0aW5nIjp0cnVlfQ==; _gcl_au=1.1.499818689.1673883605; __uzmcj3=342646493032; __uzmdj3=1673888498; _ga=GA1.3.280480591.1672252992; favorites_userid=dhd1457615389; _gat_UA-708051-1=1; _hjAbsoluteSessionInProgress=0; __uzmd=1673952261; __uzmc=4739492882378; __gpi=UID=00000bb5b20e2e4c:T=1672252995:RT=1673952261:S=ALNI_MYn0SAjHXVKs3TudZXWMjZBB5Epdg; y2_cohort_2020=70; _ga_GQ385NHRG1=GS1.1.1673952260.6.1.1673952278.42.0.0; canary=never; __uzma=82a2f74c-5215-4ac1-a6c4-4f66c99bd8df; __uzmb=1673952429; __uzmc=2534993132943; __uzmd=1673952799; __uzme=8348")
                .addHeader("Origin", "https://www.yad2.co.il")
                .addHeader("Referer", "https://www.yad2.co.il/")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-site")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .addHeader("mainsite_version_commit", "b37efa434ef5091747896be11dc1937304f933f5")
                .addHeader("mobile-app", "false")
                .addHeader("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        Root root = om.readValue(res, Root.class);
        return root.getData().getFeed();
    }

    public static String urlEncodeUTF8(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}