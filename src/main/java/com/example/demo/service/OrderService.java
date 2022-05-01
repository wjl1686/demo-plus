package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {



    @Transactional
	public String createOrder(Long productId, Long userId, Integer stockCount, Integer creditCount) {
        System.out.println("创建订单成功");
        /*stockService.deductStock(productId, stockCount);
        creditService.addCredit(userId, creditCount);
        wmsService.delivery(userId, productId);*/
		return "success";
	}

}