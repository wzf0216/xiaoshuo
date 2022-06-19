package com.novel.service.impl;


import com.novel.entity.OrderPay;
import com.novel.mapper.OrderPayDynamicSqlSupport;
import com.novel.mapper.OrderPayMapper;
import com.novel.service.OrderService;
import com.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.select.SelectDSL.select;


@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

    private final OrderPayMapper orderPayMapper;

    private  final UserService userService;

    /**
     * 创建充值订单
     *
     * @param payChannel 支付渠道
     * @param payAmount 支付金额
     * @param userId 用户ID
     * @return 商户订单号
     * */
    @SneakyThrows
    @Override
    public Long createPayOrder(Byte payChannel, Integer payAmount, Long userId) {
        Date currentDate = new Date();
        Long outTradeNo = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate)+new Random().nextInt(10));
        OrderPay orderPay = new OrderPay();
        orderPay.setOutTradeNo(outTradeNo);
        orderPay.setPayChannel(payChannel);
        orderPay.setTotalAmount(payAmount);
        orderPay.setUserId(userId);
        orderPay.setCreateTime(currentDate);
        orderPay.setUpdateTime(currentDate);
        orderPayMapper.insertSelective(orderPay);
        return outTradeNo;
    }
    /**
     * 更新订单状态
     * @param outTradeNo 商户订单号
     * @param tradeNo      支付宝/微信 订单号
     * @param tradeStatus 支付状态
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePayOrder(Long outTradeNo, String tradeNo, String tradeStatus) {
        SelectStatementProvider selectStatement = select(OrderPayDynamicSqlSupport.id,OrderPayDynamicSqlSupport.payStatus,OrderPayDynamicSqlSupport.totalAmount,OrderPayDynamicSqlSupport.userId)
                .from(OrderPayDynamicSqlSupport.orderPay)
                .where(OrderPayDynamicSqlSupport.outTradeNo, isEqualTo(outTradeNo))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        OrderPay orderPay = orderPayMapper.selectMany(selectStatement).get(0);

        if(orderPay.getPayStatus()!=1) {
            //此订单还未处理过

            if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
                //支付成功
                //1.更新订单状态为成功
                orderPay.setPayStatus((byte) 1);
                orderPay.setUpdateTime(new Date());
                orderPayMapper.updateByPrimaryKeySelective(orderPay);

                //2.增加用户余额
                userService.addAmount(orderPay.getUserId(),orderPay.getTotalAmount()*100);






            }
        }



    }
}
