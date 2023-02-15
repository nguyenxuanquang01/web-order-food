package com.dac.topic3;

import com.dac.topic3.entity.ListRestaurantToday;
import com.dac.topic3.entity.Restaurant;
import com.dac.topic3.repository.ListResTodayRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ListResTodayRepoTest {
    @Autowired
    ListResTodayRepository listResTodayRepository;
    @Test
    public void creatRestaurant(){
        ListRestaurantToday listRestaurantToday = new ListRestaurantToday();
        listRestaurantToday.setRestaurant(new Restaurant(1,"6524","Gimbab Hàn Quốc - Ngọc Khánh","https://shopeefood.vn/ha-noi/gimbab-han-quoc-ngoc-khanh"));
        listRestaurantToday.setDate(new Date(System.currentTimeMillis()));
        listResTodayRepository.save(listRestaurantToday);
    }
}
