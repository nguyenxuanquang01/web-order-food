package com.dac.topic3.controller.user;

import com.dac.topic3.entity.ListRestaurantToday;
import com.dac.topic3.entity.Order;
import com.dac.topic3.entity.Restaurant;
import com.dac.topic3.entity.User;
import com.dac.topic3.jwtutil.JwtTokenUtil;
import com.dac.topic3.model.DishOrder;
import com.dac.topic3.model.Paging;
import com.dac.topic3.repository.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    OrderRepository orderRepo;
    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    RestaurantRepository resrepo;
    @Autowired
    UserRepository userrepo;

    @Autowired
    ListResTodayRepository listResTodayRepo;

    @Autowired
    DishRepository dishrepo;

    @PostMapping("/order")
    public void order(@RequestBody List<DishOrder> dishOrdered, @RequestHeader("Authorization") String authorToken){
        String token = authorToken.substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String sub = (String) claims.get("sub");
        long userId = Long.parseLong(sub.split(",")[0]);
        for (DishOrder dish: dishOrdered){
            Order order = new Order();
            order.setDish_id(dish.getDish_id());
            order.setImage_link(dish.getImage_link());
            order.setDish(dish.getName());
            order.setPrice(Double.parseDouble(dish.getPrice().replace(",","")));
            order.setOrderDate(new Date(System.currentTimeMillis()));
            order.setOrderTime(new Time(System.currentTimeMillis()));
            order.setQuantity(Integer.parseInt(dish.getQuantity()));
            order.setStatus("-1");
            Restaurant restaurant = resrepo.getById(Integer.valueOf(dish.getId_store()));
            order.setRestaurant( restaurant);
            User user = userrepo.getById(userId);
            order.setUser(user);
            orderRepo.save(order);
        }
    }
    //Get all Dish
    @GetMapping("/listdish")
    public List<List<Object>> getAllDish(){
        List<ListRestaurantToday> listRestaurantTodays = listResTodayRepo.getByDate(new Date(System.currentTimeMillis()));
        List<Restaurant> restaurants = new ArrayList<>();
        for(ListRestaurantToday resToday: listRestaurantTodays){
            restaurants.add(resrepo.getReferenceById(resToday.getRestaurant().getId()));
        }
        List<List<Object>> listDishs = new ArrayList<>();
        for(Restaurant restaurant : restaurants){
            List<Object> dishs = dishrepo.findAllInforDish(restaurant.getId());
            listDishs.add(dishs);
        }
        return listDishs;
    }

    @GetMapping("/get-all-dish-ordered-by-employee")
    public List<Object> getAllDishOrderedByEmployee(@RequestHeader("Authorization") String authorToken){
        String token = authorToken.substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String sub = (String) claims.get("sub");
        long userId = Long.parseLong(sub.split(",")[0]);
        List<Object> orders = orderRepo.getAllDishOrderWithOrderDateAndUser(new Date(System.currentTimeMillis()),userId);
        return orders;
    }

    @PostMapping("/paid")
    public void paid(@RequestHeader("Authorization") String authorToken, @RequestBody Order reqOrder){
        if (reqOrder.getId() == null){
            String token = authorToken.substring(7);
            Claims claims = jwtUtil.parseClaims(token);
            String sub = (String) claims.get("sub");
            long userId = Long.parseLong(sub.split(",")[0]);
            User user = userrepo.getReferenceById(userId);
            List<Order> orders = orderRepo.getByUserAndOrderDate(user,new Date(System.currentTimeMillis()));
            for(Order order :orders){
                order.setStatus("0");
                orderRepo.save(order);
            }
        }else {
            Order order = orderRepo.getById(reqOrder.getId());
            order.setStatus("0");
            orderRepo.save(order);
        }
    }

    @PostMapping("/get-all-infor-order")
    public List<Object> getAllInforOrder(@RequestBody Paging page, @RequestHeader("Authorization") String authorToken){
        String token = authorToken.substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String sub = (String) claims.get("sub");
        long userId = Long.parseLong(sub.split(",")[0]);
        System.out.println(page.getStatus());
        if (page.getStatus().equals("")){
            return orderRepo.getAllInfoOrderByUser(userId);
        }else {
            return orderRepo.getAllInfoOrderByUserAndStatus(userId, page.getStatus());
        }
    }
}
