package com.novel.mapper;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.novel.entity.AuthorCode;
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
public interface AuthorCodeMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(AuthorCodeDynamicSqlSupport.id, AuthorCodeDynamicSqlSupport.inviteCode, AuthorCodeDynamicSqlSupport.validityTime, AuthorCodeDynamicSqlSupport.isUse, AuthorCodeDynamicSqlSupport.createTime, AuthorCodeDynamicSqlSupport.createUserId);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<AuthorCode> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<AuthorCode> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("AuthorCodeResult")
    Optional<AuthorCode> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="AuthorCodeResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="invite_code", property="inviteCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="validity_time", property="validityTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_use", property="isUse", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT)
    })
    List<AuthorCode> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, AuthorCodeDynamicSqlSupport.authorCode, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, AuthorCodeDynamicSqlSupport.authorCode, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(AuthorCodeDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(AuthorCode record) {
        return MyBatis3Utils.insert(this::insert, record, AuthorCodeDynamicSqlSupport.authorCode, c ->
            c.map(AuthorCodeDynamicSqlSupport.id).toProperty("id")
            .map(AuthorCodeDynamicSqlSupport.inviteCode).toProperty("inviteCode")
            .map(AuthorCodeDynamicSqlSupport.validityTime).toProperty("validityTime")
            .map(AuthorCodeDynamicSqlSupport.isUse).toProperty("isUse")
            .map(AuthorCodeDynamicSqlSupport.createTime).toProperty("createTime")
            .map(AuthorCodeDynamicSqlSupport.createUserId).toProperty("createUserId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<AuthorCode> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, AuthorCodeDynamicSqlSupport.authorCode, c ->
            c.map(AuthorCodeDynamicSqlSupport.id).toProperty("id")
            .map(AuthorCodeDynamicSqlSupport.inviteCode).toProperty("inviteCode")
            .map(AuthorCodeDynamicSqlSupport.validityTime).toProperty("validityTime")
            .map(AuthorCodeDynamicSqlSupport.isUse).toProperty("isUse")
            .map(AuthorCodeDynamicSqlSupport.createTime).toProperty("createTime")
            .map(AuthorCodeDynamicSqlSupport.createUserId).toProperty("createUserId")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(AuthorCode record) {
        return MyBatis3Utils.insert(this::insert, record, AuthorCodeDynamicSqlSupport.authorCode, c ->
            c.map(AuthorCodeDynamicSqlSupport.id).toPropertyWhenPresent("id", record::getId)
            .map(AuthorCodeDynamicSqlSupport.inviteCode).toPropertyWhenPresent("inviteCode", record::getInviteCode)
            .map(AuthorCodeDynamicSqlSupport.validityTime).toPropertyWhenPresent("validityTime", record::getValidityTime)
            .map(AuthorCodeDynamicSqlSupport.isUse).toPropertyWhenPresent("isUse", record::getIsUse)
            .map(AuthorCodeDynamicSqlSupport.createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(AuthorCodeDynamicSqlSupport.createUserId).toPropertyWhenPresent("createUserId", record::getCreateUserId)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<AuthorCode> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, AuthorCodeDynamicSqlSupport.authorCode, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<AuthorCode> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, AuthorCodeDynamicSqlSupport.authorCode, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<AuthorCode> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, AuthorCodeDynamicSqlSupport.authorCode, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<AuthorCode> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(AuthorCodeDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, AuthorCodeDynamicSqlSupport.authorCode, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(AuthorCode record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(AuthorCodeDynamicSqlSupport.id).equalTo(record::getId)
                .set(AuthorCodeDynamicSqlSupport.inviteCode).equalTo(record::getInviteCode)
                .set(AuthorCodeDynamicSqlSupport.validityTime).equalTo(record::getValidityTime)
                .set(AuthorCodeDynamicSqlSupport.isUse).equalTo(record::getIsUse)
                .set(AuthorCodeDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
                .set(AuthorCodeDynamicSqlSupport.createUserId).equalTo(record::getCreateUserId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(AuthorCode record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(AuthorCodeDynamicSqlSupport.id).equalToWhenPresent(record::getId)
                .set(AuthorCodeDynamicSqlSupport.inviteCode).equalToWhenPresent(record::getInviteCode)
                .set(AuthorCodeDynamicSqlSupport.validityTime).equalToWhenPresent(record::getValidityTime)
                .set(AuthorCodeDynamicSqlSupport.isUse).equalToWhenPresent(record::getIsUse)
                .set(AuthorCodeDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
                .set(AuthorCodeDynamicSqlSupport.createUserId).equalToWhenPresent(record::getCreateUserId);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(AuthorCode record) {
        return update(c ->
            c.set(AuthorCodeDynamicSqlSupport.inviteCode).equalTo(record::getInviteCode)
            .set(AuthorCodeDynamicSqlSupport.validityTime).equalTo(record::getValidityTime)
            .set(AuthorCodeDynamicSqlSupport.isUse).equalTo(record::getIsUse)
            .set(AuthorCodeDynamicSqlSupport.createTime).equalTo(record::getCreateTime)
            .set(AuthorCodeDynamicSqlSupport.createUserId).equalTo(record::getCreateUserId)
            .where(AuthorCodeDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(AuthorCode record) {
        return update(c ->
            c.set(AuthorCodeDynamicSqlSupport.inviteCode).equalToWhenPresent(record::getInviteCode)
            .set(AuthorCodeDynamicSqlSupport.validityTime).equalToWhenPresent(record::getValidityTime)
            .set(AuthorCodeDynamicSqlSupport.isUse).equalToWhenPresent(record::getIsUse)
            .set(AuthorCodeDynamicSqlSupport.createTime).equalToWhenPresent(record::getCreateTime)
            .set(AuthorCodeDynamicSqlSupport.createUserId).equalToWhenPresent(record::getCreateUserId)
            .where(AuthorCodeDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }
}