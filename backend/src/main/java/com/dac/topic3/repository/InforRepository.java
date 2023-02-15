package com.dac.topic3.repository;

import com.dac.topic3.entity.InforPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InforRepository extends JpaRepository<InforPay, Integer> {
}
