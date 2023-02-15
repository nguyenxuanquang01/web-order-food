package com.dac.topic3;

import com.dac.topic3.entity.Restaurant;
import com.dac.topic3.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository resrepo;

    @Test
    public void creatRestaurant(){
        resrepo.deleteAll();
//        Restaurant restaurant = new Restaurant("6524","Gimbab Hàn Quốc - Ngọc Khánh","https://shopeefood.vn/ha-noi/gimbab-han-quoc-ngoc-khanh",1);
//        resrepo.save(restaurant);
    }
}
