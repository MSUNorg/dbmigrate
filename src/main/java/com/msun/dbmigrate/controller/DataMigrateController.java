/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.msun.dbmigrate.support.JsonResult;

/**
 * 数据迁移
 * 
 * @author zxc Sep 23, 2016 5:58:38 PM
 */
@Controller
@RequestMapping(value = "/migrate")
public class DataMigrateController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("migrate")//
        .addObject("list", dbconf());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResult domigrate() {
        return ok("迁移成功");
    }
}
