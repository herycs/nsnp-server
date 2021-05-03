package com.herycs.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.herycs.article.pojo.Column;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ColumnDao extends JpaRepository<Column,String>,JpaSpecificationExecutor<Column>{

    @Query(value = "select * from tb_column where id = ?", nativeQuery = true)
    Column findById();

    @Query(value = "select * from tb_column where userid=? and state=1 limit ?", nativeQuery = true)
    List<Column> findByUid(String uid, int limit);

    @Query(value = "select * from tb_column where userid=? and state=2 limit ?", nativeQuery = true)
    List<Column> findVisitedColumn(String uid, int limit);

    @Query(value = "select * from tb_column order by member desc", nativeQuery = true)
    List<Column> findHot();

    @Query(value = "select * from tb_column order by pro desc", nativeQuery = true)
    List<Column> findHotByPro();

    @Query(value = "select * from tb_column order by visit desc", nativeQuery = true)
    List<Column> findHotByVisit();

}
