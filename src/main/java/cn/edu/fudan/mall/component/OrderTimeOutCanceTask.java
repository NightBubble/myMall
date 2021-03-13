package cn.edu.fudan.mall.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单超时就取消的定时器
 */
//@Component
public class OrderTimeOutCanceTask {
    private Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCanceTask.class);

    @Scheduled(cron = "0/10 * * ? * ?")
    private void cancelTimeOrder(){
        LOGGER.info("取消订单，并根据sku编号释放锁定订单");
        System.out.println("send by task");
    }

}
