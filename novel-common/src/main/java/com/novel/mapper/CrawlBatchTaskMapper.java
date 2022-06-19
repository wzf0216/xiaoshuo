package com.novel.mapper;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.novel.entity.CrawlBatchTask;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface CrawlBatchTaskMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(CrawlBatchTaskDynamicSqlSupport.id, CrawlBatchTaskDynamicSqlSupport.sourceId, CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess, CrawlBatchTaskDynamicSqlSupport.crawlCountTarget, CrawlBatchTaskDynamicSqlSupport.taskStatus, CrawlBatchTaskDynamicSqlSupport.startTime, CrawlBatchTaskDynamicSqlSupport.endTime);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<CrawlBatchTask> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<CrawlBatchTask> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("CrawlBatchTaskResult")
    Optional<CrawlBatchTask> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="CrawlBatchTaskResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="source_id", property="sourceId", jdbcType=JdbcType.INTEGER),
        @Result(column="crawl_count_success", property="crawlCountSuccess", jdbcType=JdbcType.INTEGER),
        @Result(column="crawl_count_target", property="crawlCountTarget", jdbcType=JdbcType.INTEGER),
        @Result(column="task_status", property="taskStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<CrawlBatchTask> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(CrawlBatchTaskDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(CrawlBatchTask record) {
        return MyBatis3Utils.insert(this::insert, record, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, c ->
            c.map(CrawlBatchTaskDynamicSqlSupport.id).toProperty("id")
            .map(CrawlBatchTaskDynamicSqlSupport.sourceId).toProperty("sourceId")
            .map(CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess).toProperty("crawlCountSuccess")
            .map(CrawlBatchTaskDynamicSqlSupport.crawlCountTarget).toProperty("crawlCountTarget")
            .map(CrawlBatchTaskDynamicSqlSupport.taskStatus).toProperty("taskStatus")
            .map(CrawlBatchTaskDynamicSqlSupport.startTime).toProperty("startTime")
            .map(CrawlBatchTaskDynamicSqlSupport.endTime).toProperty("endTime")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<CrawlBatchTask> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, c ->
            c.map(CrawlBatchTaskDynamicSqlSupport.id).toProperty("id")
            .map(CrawlBatchTaskDynamicSqlSupport.sourceId).toProperty("sourceId")
            .map(CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess).toProperty("crawlCountSuccess")
            .map(CrawlBatchTaskDynamicSqlSupport.crawlCountTarget).toProperty("crawlCountTarget")
            .map(CrawlBatchTaskDynamicSqlSupport.taskStatus).toProperty("taskStatus")
            .map(CrawlBatchTaskDynamicSqlSupport.startTime).toProperty("startTime")
            .map(CrawlBatchTaskDynamicSqlSupport.endTime).toProperty("endTime")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(CrawlBatchTask record) {
        return MyBatis3Utils.insert(this::insert, record, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, c ->
            c.map(CrawlBatchTaskDynamicSqlSupport.id).toPropertyWhenPresent("id", record::getId)
            .map(CrawlBatchTaskDynamicSqlSupport.sourceId).toPropertyWhenPresent("sourceId", record::getSourceId)
            .map(CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess).toPropertyWhenPresent("crawlCountSuccess", record::getCrawlCountSuccess)
            .map(CrawlBatchTaskDynamicSqlSupport.crawlCountTarget).toPropertyWhenPresent("crawlCountTarget", record::getCrawlCountTarget)
            .map(CrawlBatchTaskDynamicSqlSupport.taskStatus).toPropertyWhenPresent("taskStatus", record::getTaskStatus)
            .map(CrawlBatchTaskDynamicSqlSupport.startTime).toPropertyWhenPresent("startTime", record::getStartTime)
            .map(CrawlBatchTaskDynamicSqlSupport.endTime).toPropertyWhenPresent("endTime", record::getEndTime)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<CrawlBatchTask> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<CrawlBatchTask> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<CrawlBatchTask> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<CrawlBatchTask> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(CrawlBatchTaskDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, CrawlBatchTaskDynamicSqlSupport.crawlBatchTask, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(CrawlBatchTask record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(CrawlBatchTaskDynamicSqlSupport.id).equalTo(record::getId)
                .set(CrawlBatchTaskDynamicSqlSupport.sourceId).equalTo(record::getSourceId)
                .set(CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess).equalTo(record::getCrawlCountSuccess)
                .set(CrawlBatchTaskDynamicSqlSupport.crawlCountTarget).equalTo(record::getCrawlCountTarget)
                .set(CrawlBatchTaskDynamicSqlSupport.taskStatus).equalTo(record::getTaskStatus)
                .set(CrawlBatchTaskDynamicSqlSupport.startTime).equalTo(record::getStartTime)
                .set(CrawlBatchTaskDynamicSqlSupport.endTime).equalTo(record::getEndTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(CrawlBatchTask record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(CrawlBatchTaskDynamicSqlSupport.id).equalToWhenPresent(record::getId)
                .set(CrawlBatchTaskDynamicSqlSupport.sourceId).equalToWhenPresent(record::getSourceId)
                .set(CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess).equalToWhenPresent(record::getCrawlCountSuccess)
                .set(CrawlBatchTaskDynamicSqlSupport.crawlCountTarget).equalToWhenPresent(record::getCrawlCountTarget)
                .set(CrawlBatchTaskDynamicSqlSupport.taskStatus).equalToWhenPresent(record::getTaskStatus)
                .set(CrawlBatchTaskDynamicSqlSupport.startTime).equalToWhenPresent(record::getStartTime)
                .set(CrawlBatchTaskDynamicSqlSupport.endTime).equalToWhenPresent(record::getEndTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(CrawlBatchTask record) {
        return update(c ->
            c.set(CrawlBatchTaskDynamicSqlSupport.sourceId).equalTo(record::getSourceId)
            .set(CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess).equalTo(record::getCrawlCountSuccess)
            .set(CrawlBatchTaskDynamicSqlSupport.crawlCountTarget).equalTo(record::getCrawlCountTarget)
            .set(CrawlBatchTaskDynamicSqlSupport.taskStatus).equalTo(record::getTaskStatus)
            .set(CrawlBatchTaskDynamicSqlSupport.startTime).equalTo(record::getStartTime)
            .set(CrawlBatchTaskDynamicSqlSupport.endTime).equalTo(record::getEndTime)
            .where(CrawlBatchTaskDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(CrawlBatchTask record) {
        return update(c ->
            c.set(CrawlBatchTaskDynamicSqlSupport.sourceId).equalToWhenPresent(record::getSourceId)
            .set(CrawlBatchTaskDynamicSqlSupport.crawlCountSuccess).equalToWhenPresent(record::getCrawlCountSuccess)
            .set(CrawlBatchTaskDynamicSqlSupport.crawlCountTarget).equalToWhenPresent(record::getCrawlCountTarget)
            .set(CrawlBatchTaskDynamicSqlSupport.taskStatus).equalToWhenPresent(record::getTaskStatus)
            .set(CrawlBatchTaskDynamicSqlSupport.startTime).equalToWhenPresent(record::getStartTime)
            .set(CrawlBatchTaskDynamicSqlSupport.endTime).equalToWhenPresent(record::getEndTime)
            .where(CrawlBatchTaskDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }
}