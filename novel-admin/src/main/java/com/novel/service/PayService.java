package com.novel.service;

import com.novel.entity.OrderPay;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支付
 */
public interface PayService {
    OrderPay get(Long id);

    List<OrderPay> list();

    long count();

    int save(OrderPay pay);

    int update(OrderPay pay);

    int remove(Long id);

    int batchRemove(Long[] ids);

    Map<Object, Object> tableSta(Date minDate);
}
