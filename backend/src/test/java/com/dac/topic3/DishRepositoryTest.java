package com.dac.topic3;
import java.io.IOException;
import com.dac.topic3.entity.Dish;
import com.dac.topic3.entity.Restaurant;
import com.dac.topic3.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class DishRepositoryTest {

    @Autowired
    DishRepository disrepo;

    @Test
    public void createDish() throws IOException, JSONException {
        int shop_id = 6524;

        HttpGet httpGet = new HttpGet("https://gappapi.deliverynow.vn/api/dish/get_delivery_dishes?id_type=2&request_id="+shop_id);

        httpGet.addHeader("accept","application/json, text/plain, */*");
        httpGet.addHeader("accept-language","en-US,en;q=0.9,ja;q=0.8,vi;q=0.7");
        httpGet.addHeader("sec-ch-ua","\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
        httpGet.addHeader("sec-ch-ua-mobile","?0");
        httpGet.addHeader("sec-ch-ua-platform","\"Windows\"");
        httpGet.addHeader("sec-fetch-dest", "empty");
        httpGet.addHeader("sec-fetch-mode", "cors");
        httpGet.addHeader("sec-fetch-site", "cross-site");
        httpGet.addHeader("x-foody-access-token", "");
        httpGet.addHeader("x-foody-api-version", "1");
        httpGet.addHeader("x-foody-access-token", "");
        httpGet.addHeader("x-foody-app-type", "1004");
        httpGet.addHeader("x-foody-client-id", "");
        httpGet.addHeader("x-foody-client-language", "vi");
        httpGet.addHeader("x-foody-client-type", "1");
        httpGet.addHeader("x-foody-client-version", "3.0.0");
        httpGet.addHeader("referrer", "https://shopeefood.vn/");
        httpGet.addHeader("referrerPolicy", "strict-origin-when-cross-origin");
        httpGet.addHeader("x-foody-access-token", "");
        httpGet.addHeader("body", null);
        httpGet.addHeader("method", "GET");
        httpGet.addHeader("mode", "cors");
        httpGet.addHeader("credentials",  "omit");

        HttpClient client = HttpClients.createDefault();
        HttpResponse httpResponse = client.execute(httpGet);

        System.out.println("Content:");
        String content = IOUtils.toString(httpResponse.getEntity().getContent(), "UTF-8");
//		System.out.println(content);

        JSONObject json = new JSONObject(content);
        String image_link, name, price;

        JSONArray menu_infos = json.getJSONObject("reply").getJSONArray("menu_infos");
        for (int i = 0; i < menu_infos.length(); i++) {
            JSONObject first_menu = menu_infos.getJSONObject(i);
            JSONArray dishes = first_menu.getJSONArray("dishes");
            for (int j = 0; j < dishes.length(); j++) {
                JSONObject dish = dishes.getJSONObject(j);
                if(dish.has("discount_price")) {
                    Dish dish1 = new Dish(new Restaurant(), dish.getString("name"),
                            dish.getJSONArray("photos").getJSONObject(0).getString("value"),
                            dish.getJSONObject("discount_price").getString("text"));
//                    System.out.println(dish.getString("name") + " " + dish.getJSONObject("discount_price").get("value")
//                            + " " + dish.getJSONArray("photos").getJSONObject(0).get("value"));
                    disrepo.save(dish1);
                } else {
                    Dish dish1 = new Dish(new Restaurant(),
                            dish.getJSONArray("photos").getJSONObject(0).getString("value"),
                            dish.getString("name"),
                            dish.getJSONObject("price").getString("text"));
//                    System.out.println(dish.getString("name") + " " + dish.getJSONObject("price").get("value"));
                    disrepo.save(dish1);
                }
            }
        }
    }
}
