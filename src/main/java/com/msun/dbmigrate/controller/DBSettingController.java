/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.lamfire.pandora.FireMap;
import com.msun.dbmigrate.support.JsonResult;
import com.msun.dbmigrate.support.utils.DbMeta;

/**
 * 数据库配置
 * 
 * @author zxc Sep 26, 2016 10:06:24 AM
 */
@Controller
@RequestMapping(value = "/setting")
public class DBSettingController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("setting");
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(value = "id") String id) {
        FireMap dao = pandora.getMap(DBCONF);
        return new ModelAndView("settingEdit");
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult del(@PathVariable(value = "id") String id) {
        return ok("删除成功");
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(DbMeta dbMeta) {
        return ok("成功");
    }
}
