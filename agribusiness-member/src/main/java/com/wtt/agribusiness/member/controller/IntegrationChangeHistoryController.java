package com.wtt.agribusiness.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wtt.agribusiness.member.entity.IntegrationChangeHistoryEntity;
import com.wtt.agribusiness.member.service.IntegrationChangeHistoryService;
import com.wtt.common.utils.PageUtils;
import com.wtt.common.utils.R;



/**
 * 积分变化历史记录
 *
 * @author Wang TianTian
 * @email 442301197@qq.com
 * @date 2021-02-17 13:28:55
 */
@RestController
@RequestMapping("member/integrationchangehistory")
public class IntegrationChangeHistoryController {
    @Autowired
    private IntegrationChangeHistoryService integrationChangeHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:integrationchangehistory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = integrationChangeHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:integrationchangehistory:info")
    public R info(@PathVariable("id") Long id){
		IntegrationChangeHistoryEntity integrationChangeHistory = integrationChangeHistoryService.getById(id);

        return R.ok().put("integrationChangeHistory", integrationChangeHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:integrationchangehistory:save")
    public R save(@RequestBody IntegrationChangeHistoryEntity integrationChangeHistory){
		integrationChangeHistoryService.save(integrationChangeHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:integrationchangehistory:update")
    public R update(@RequestBody IntegrationChangeHistoryEntity integrationChangeHistory){
		integrationChangeHistoryService.updateById(integrationChangeHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:integrationchangehistory:delete")
    public R delete(@RequestBody Long[] ids){
		integrationChangeHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}