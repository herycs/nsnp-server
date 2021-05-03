package com.herycs.article.controller;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.herycs.article.pojo.Column;
import com.herycs.article.service.ColumnService;
import com.herycs.common.entity.PageResult;
import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;

import java.util.*;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/column")
public class ColumnController {

	@Autowired
	private ColumnService columnService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",columnService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功", columnService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Column> pageList = columnService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Column>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",columnService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param column
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Column column  ){
		columnService.add(column);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param column
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Column column, @PathVariable String id ){
		column.setId(id);
		columnService.update(column);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		columnService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	@RequestMapping(value = "/in/{uid}", method = RequestMethod.GET)
	public Result findByUser(@PathVariable("uid") String uid) {
		return new Result(true,StatusCode.OK,"获取成功", columnService.findUserInColumn(uid));
	}

	@RequestMapping(value = "/in/limit/{uid}", method = RequestMethod.GET)
	public Result findByUserUnderLimit(@PathVariable("uid") String uid) {
		return new Result(true,StatusCode.OK,"获取成功", columnService.findUserInColumnUnderLimit(uid));
	}

	@RequestMapping(value = "/visit/{uid}", method = RequestMethod.GET)
	public Result findVisited(@PathVariable("uid") String uid) {
		return new Result(true,StatusCode.OK,"获取成功", columnService.findUserVisitedColumn(uid));
	}

	@RequestMapping(value = "/visit/limit/{uid}", method = RequestMethod.GET)
	public Result findVisitedUnderLimit(@PathVariable("uid") String uid) {
		return new Result(true,StatusCode.OK,"获取成功", columnService.findUserVisitedColumnUnderLimit(uid));
	}

	@RequestMapping(value = "/hot", method = RequestMethod.GET)
	public Result findHot() {

		HashMap<String, Object> hot1Map = new HashMap<>();
		hot1Map.put("name","最受欢迎榜");
		hot1Map.put("data", columnService.findHot());

		HashMap<String, Object> hot2Map = new HashMap<>();
		hot2Map.put("name","最热榜");
		hot2Map.put("data", columnService.findHotByPro());

		HashMap<String, Object> hot3Map = new HashMap<>();
		hot3Map.put("name","热门榜");
		hot3Map.put("data", columnService.findHotByVisit());

		List<Map> es = Arrays.asList(hot1Map, hot2Map, hot3Map);


		return new Result(true, StatusCode.OK, "获取成功", es);
	}
	
}
