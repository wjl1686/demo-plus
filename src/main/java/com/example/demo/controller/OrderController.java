package com.example.demo.controller;



import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;

    /**
     * 基于Feign调用服务接口
     *
     * @param productId
     * @param userId
     * @param stockCount
     * @param creditCount
     * @return
     */
    @GetMapping(value = "/create")
    public String createOrder(
            @RequestParam("productId") Long productId,
            @RequestParam("userId") Long userId,
            @RequestParam("stockCount") Integer stockCount,
            @RequestParam("creditCount") Integer creditCount) {
        orderService.createOrder(productId, userId, stockCount, creditCount);
        return "success";
    }

    /**
     * 直接调用原生http接口
     *
     * @param productId
     * @param stockCount
     * @return
     */
    @GetMapping("/stock/deduct")
    public String deductStock(@RequestParam("productId") Long productId, @RequestParam("stockCount") Long stockCount) {
        return this.restTemplate.getForObject("http://localhost:9001/stock/deduct/" + productId + "/" + stockCount,
                String.class);
    }

    /**
     * 基于Ribbon调用服务接口
     *
     * @return
     */
    @GetMapping("/stock/getIpAndPort")
    public String getIpAndPort() {
        return this.restTemplate.getForObject("http://stock-feign/stock/getIpAndPort", String.class);
    }


}
