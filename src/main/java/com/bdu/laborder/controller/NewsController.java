package com.bdu.laborder.controller;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.common.Result;
import com.bdu.laborder.common.ResultGenerator;
import com.bdu.laborder.entity.News;
import com.bdu.laborder.service.NewsService;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/14 16:50
 */
@RestController
public class NewsController {

    @Autowired
    NewsService newsService;

    // 查询文章列表
    @PostMapping("/newsList")
    public Result getNewsList(@RequestBody PageQuery pageQuery){
        PageInfo<News> news = newsService.getNews(pageQuery);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,news);
    }
    // 首页显示文章列表
      // 公告
    @GetMapping("/homeNews")
    public Result getHomeInform(){
        List<News> homeInform = newsService.getHomeInform();
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,homeInform);
    }

    // 新建文章
    @PostMapping("/new")
    public Result addNews(@RequestBody News news){
        boolean b = newsService.addNews(news);
        if (b){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
    }
    // 发布/撤回 文章
    @PostMapping("/releaseNews/{id}")
    public Result releaseNews(@PathVariable Integer id){
        boolean b = newsService.releaseNews(id);
        if (b){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
    }
    // 获取文章详情
    @GetMapping("/news/{id}")
    public Result getNews(@PathVariable Integer id){
        News news = newsService.getNews(id);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,news);
    }
    // 修改文章
    @PutMapping("/news")
    public Result updateNews(@RequestBody News news){
        boolean b = newsService.updateNews(news);
        if (b){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
    }
    // 删除文章
    @DeleteMapping("/news/{id}")
    public Result deleteNews(@PathVariable Integer id){
        boolean b = newsService.deleteNews(id);
        if (b){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
    }

}
