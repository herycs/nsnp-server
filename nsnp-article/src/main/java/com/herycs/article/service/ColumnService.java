package com.herycs.article.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.herycs.article.dao.ColumnDao;
import com.herycs.article.pojo.Column;
import com.herycs.common.util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ColumnService {

	private static Logger logger = LoggerFactory.getLogger(ColumnService.class);

	@Autowired
	private ColumnDao columnDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Column> findAll() {
		return columnDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Column> findSearch(Map whereMap, int page, int size) {
		Specification<Column> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return columnDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Column> findSearch(Map whereMap) {
		Specification<Column> specification = createSpecification(whereMap);
		return columnDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Column findById(String id) {
		return columnDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param column
	 */
	public void add(Column column) {
		column.setId( idWorker.nextId() + "" );
		columnDao.save(column);
	}

	/**
	 * 修改
	 * @param column
	 */
	public void update(Column column) {
		columnDao.save(column);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		columnDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Column> createSpecification(Map searchMap) {

		return new Specification<Column>() {

			@Override
			public Predicate toPredicate(Root<Column> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 专栏名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 专栏简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	public List<Column> findUserInColumnUnderLimit(String userid) {
		return columnDao.findByUid(userid, 4);
	}

	public List<Column> findUserInColumn(String userid) {
		return columnDao.findByUid(userid, 100);
	}

	public List<Column> findUserVisitedColumnUnderLimit(String userid) {
		return columnDao.findVisitedColumn(userid, 4);
	}

	public List<Column> findUserVisitedColumn(String userid) {

		logger.info("{}", columnDao.findVisitedColumn(userid, 100));

		return columnDao.findVisitedColumn(userid, 100);
	}

	public List<Column> findHot() {
		return columnDao.findHot();
	}

	public List<Column> findHotByPro() {
		return columnDao.findHotByPro();
	}

	public List<Column> findHotByVisit() {
		return columnDao.findHotByVisit();
	}

	public void addVisited(String columnId) {
		Column byId = findById(columnId);
		if (byId != null) {
			byId.setVisit(byId.getVisit() + 1);
		}
		columnDao.save(byId);
	}

	public void addMember(String columnId) {
		Column byId = findById(columnId);
		if (byId != null) {
			byId.setMember(byId.getMember() + 1);
		}
		columnDao.save(byId);
	}

	public void addPro(String columnId) {
		Column byId = findById(columnId);
		if (byId != null) {
			byId.setPro(byId.getPro() + 1);
		}
		columnDao.save(byId);
	}
}
