package com.dac.topic3.util;

import com.dac.topic3.entity.Dish;
import com.dac.topic3.entity.Restaurant;
import com.dac.topic3.repository.DishRepository;
import com.dac.topic3.repository.RestaurantRepository;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrawData {
    @Autowired
    RestaurantRepository resrepo;
    @Autowired
    DishRepository dishrepo;

    public void crawListRestaurant(List<Integer> res_id, String status){
        List<Restaurant> listRestaurant = getResById(res_id);
        for(Restaurant restaurant : listRestaurant){
            try {
                JSONArray infors =  getDishRaw(restaurant);
                refreshDish(infors, restaurant,status);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void crawRestaurant(Restaurant restaurant, String status){
        try {
            JSONArray infors = getDishRaw(restaurant);
            refreshDish(infors,restaurant, status);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Restaurant> getResById(List<Integer> res_id){
        List<Restaurant> listRestaurant = new ArrayList<>();
        for(Integer id: res_id){
            listRestaurant.add(resrepo.getById(id));
        }
        return listRestaurant;
    }

    public JSONArray getDishRaw(Restaurant restaurant) throws IOException {
        HttpGet httpGet = new HttpGet("https://gappapi.deliverynow.vn/api/dish/get_delivery_dishes?id_type=2&request_id="+restaurant.getShop_code());

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

        return  json.getJSONObject("reply").getJSONArray("menu_infos");
    }

    public void refreshDish(JSONArray menu_infors, Restaurant restaurant,String status){
        if(status.equals("update")) {
            dishrepo.deleteAll();
        }
        for (int i = 0; i < menu_infors.length(); i++) {
            JSONObject first_menu = menu_infors.getJSONObject(i);
            JSONArray dishes = first_menu.getJSONArray("dishes");
            for (int j = 0; j < dishes.length(); j++) {
                JSONObject dishRaw = dishes.getJSONObject(j);
                if(dishRaw.has("discount_price")) {
                    Dish dish = new Dish(restaurant, dishRaw.getString("name"),
                            dishRaw.getJSONArray("photos").getJSONObject(0).getString("value"),
                            dishRaw.getJSONObject("discount_price").getString("text").replace("đ",""));
                    dishrepo.save(dish);
                } else {
                    Dish dish = new Dish(restaurant,
                            dishRaw.getJSONArray("photos").getJSONObject(0).getString("value"),
                            dishRaw.getString("name"),
                            dishRaw.getJSONObject("price").getString("text").replace("đ",""));
                    dishrepo.save(dish);
                }
            }
        }
    }
}
