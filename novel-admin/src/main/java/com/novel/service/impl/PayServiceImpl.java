package com.novel.service.impl;

import com.github.pagehelper.PageHelper;
import com.novel.entity.OrderPay;
import com.novel.mapper.OrderPayDynamicSqlSupport;
import com.novel.mapper.OrderPayMapper;
import com.novel.service.PayService;
import lombok.RequiredArgsConstructor;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;
import static  com.novel.mapper.OrderPayDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    private final OrderPayMapper orderPayMapper;
    @Override
    public OrderPay get(Long id) {
        SelectStatementProvider selectStatement= select(OrderPayDynamicSqlSupport.id,outTradeNo,tradeNo,payChannel,totalAmount,userId,payStatus,createTime,updateTime)
        .from(orderPay)
                .where(OrderPayDynamicSqlSupport.id,isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return orderPayMapper.selectMany(selectStatement).get(0);
    }

    @Override
    public List<OrderPay> list() {

        SelectStatementProvider selectStatement= select(OrderPayDynamicSqlSupport.id,outTradeNo,tradeNo,payChannel,totalAmount,userId,payStatus,createTime,updateTime)
                .from(orderPay)
                .where(OrderPayDynamicSqlSupport.id,isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return orderPayMapper.selectMany(selectStatement);
    }


    @Override
    public long count() {
        SelectStatementProvider selectStatement= select(SqlBuilder.count())
                .from(orderPay)
                .where(id,isNotNull())
                .orderBy(createTime.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return orderPayMapper.count(selectStatement);
    }

    @Override
    public int save(OrderPay pay) {
        return orderPayMapper.insert(pay);
    }

    @Override
    public int update(OrderPay pay) {
        return orderPayMapper.updateByPrimaryKeySelective(pay);
    }

    @Override
    public int remove(Long id) {
        return orderPayMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        int num=0;
        for (int i = 0; i <ids.length ; i++) {
            orderPayMapper.deleteByPrimaryKey(ids[i]);
            num++;

        }
        return num;
    }

    @Override
    public Map<Object, Object> tableSta(Date minDate) {
        return null;
    }
}
