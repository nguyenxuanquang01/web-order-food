package com.dac.topic3.controller.admin;


import com.dac.topic3.entity.*;
import com.dac.topic3.model.ExtraFee;
import com.dac.topic3.model.Paging;
import com.dac.topic3.model.ResToday;
import com.dac.topic3.repository.*;
import com.dac.topic3.repository.DishRepository;
import com.dac.topic3.util.CrawData;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    CrawData crawData;

    @Autowired
    UserRepository userrepo;
    @Autowired
    RestaurantRepository resrepo;
    @Autowired
    ListResTodayRepository listResTodayRepo;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    InforRepository inforRepo;


    //Get all Member
    @GetMapping("/listmember")
    @RolesAllowed("ROLE_ADMIN")
    public List<List<User>> getAllMember(){
        List<List<User>> members = new ArrayList<>();
        members.add(userrepo.getByStatus("inactive"));
        members.add(userrepo.getByStatus(("active")));
        return members;
    }

    @PostMapping("/update-member")
    @RolesAllowed("ROLE_ADMIN")
    public User updateUser(@RequestBody User user){
        userrepo.save(user);
        return user;
    }


    //Get all Restaurant
    @PostMapping("/listrestaurant")
    @RolesAllowed("ROLE_ADMIN")
    public List<Restaurant> getALlRestaurant(@RequestBody Paging paging){
        List<Restaurant> allRestaurant = resrepo.findRestaurantByStatus(1);
        return allRestaurant;
    }

    //Update Restaurant
    @PutMapping("/restaurant")
    @RolesAllowed("ROLE_ADMIN")
    public Restaurant updateRestaurant(@RequestBody Restaurant restaurant){
        resrepo.save(restaurant);
        return restaurant;
    }

    // Delete Restaurant
    @PostMapping(value = "/delerestaurant")
    public void deleteRestaurant(@RequestBody Restaurant restaurant){
        resrepo.save(restaurant);
    }

    //Add Restaurant
    @PostMapping("/addres")
    public JSONObject addRestaurant(@RequestBody Restaurant restaurant){
        resrepo.save(restaurant);
        JSONObject temp = new JSONObject();
        temp.put("code", 2000);
        temp.put("data", "success");
        return temp;
    }

    // Create List Restaurant Today
    @PostMapping(value = "/makelistrestoday")
    public void makeListResToday(@RequestBody List<ResToday> resTodays){
        for (ResToday resToday : resTodays){
            Restaurant restaurant = resrepo.getById(resToday.getRes_id());
            listResTodayRepo.save(new ListRestaurantToday(restaurant,resToday.getDate()));
            crawData.crawRestaurant(restaurant, "update");
        }
    }


    //Get List Restaurant Today
    @GetMapping(value = "/listrestoday")
    public List<Restaurant> getListResToday(){
        List<ListRestaurantToday> listRestaurantTodays = listResTodayRepo.getByDate(new Date(System.currentTimeMillis()));
        List<Restaurant> restaurants = new ArrayList<>();
        for(ListRestaurantToday resToday: listRestaurantTodays){
            restaurants.add(resrepo.getReferenceById(resToday.getRestaurant().getId()));
        }
        return restaurants;
    }

    //Get all Dish be group by

    @GetMapping(value = "/get-dish-groupby")
    public List<Object> getAllDishGroupBy(){
        return orderRepo.getAllGroupDish(new Date(System.currentTimeMillis()));
    }

    @GetMapping(value = "/get-all-dish-ordered-by-employees")
    public List<Object> getAllDishOrderedByEmpls(){
        return orderRepo.getAllOrderedByEmpls(new Date(System.currentTimeMillis()));
    }

    @PostMapping(value = "/set-price")
    public void setPrice(@RequestBody ExtraFee extraFee){
        double fee = (extraFee.getFeeShip()+ extraFee.getFee()) - extraFee.getVoucher();
        System.out.println(fee);
        double totalPrice = orderRepo.getTotalPrice(new Date(System.currentTimeMillis()));
        System.out.println(totalPrice);
        List<Order> listOrder = orderRepo.getAllByOrderDate(new Date(System.currentTimeMillis()));
        for(Order order: listOrder){
            double newPrice = round(order.getPrice()*(totalPrice+fee)/totalPrice);
            order.setFinalPrice(newPrice);
            orderRepo.save(order);
        }
    }

    @PostMapping(value = "/confirm-pay")
    public void confirmPay(@RequestBody Order orderReq){
        Order order = orderRepo.getById(orderReq.getId());
        order.setStatus("1");
        orderRepo.save(order);
    }

    @PostMapping(value = "/get-all-infor-order")
    public List<Object> getAllInfroOrder(@RequestBody Paging paging){
        if(paging.getName().equals("")&&paging.getStatus().equals("")){
            return orderRepo.getAllInfoOrder();
        }else if(paging.getName().equals("")&&!paging.getStatus().equals("")){
            return orderRepo.getAllInfoOrderByStatus(paging.getStatus());
        }else if(!paging.getName().equals("")&&paging.getStatus().equals("")){
            return orderRepo.getAllInfoOrderByName(paging.getName());
        }else{
            return orderRepo.getAllInfoOrderByNameAndStatus(paging.getName(), paging.getStatus());
        }
    }


    @GetMapping(value = "/get-infor")
    public List<InforPay> getInfor(){
        return inforRepo.findAll();
    }

    @PostMapping(value = "/update-infor")
    public InforPay updateInfor(@RequestBody InforPay inforPay){
        inforRepo.save(inforPay);
        return inforPay;
    }

}
