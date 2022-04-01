package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.entity.News;
import com.bdu.laborder.entity.NewsRequest;
import com.bdu.laborder.mapper.NewsMapper;
import com.bdu.laborder.service.NewsService;
import com.bdu.laborder.utils.CreateGson;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/16 11:37
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsMapper newsMapper;

    @Override
    public PageInfo<News> getNews(PageQuery pageQuery) {
        Gson gson = CreateGson.createGson();
        NewsRequest item = gson.fromJson(gson.toJson(pageQuery.getItem()), NewsRequest.class);
        PageInfo page = pageQuery.getPage();
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<News> allNews = newsMapper.getAllNews(item);
        PageInfo<News> newsPageInfo = new PageInfo<>(allNews);
        return newsPageInfo;
    }



    @Override
    public List<News> getHomeInform() {
        List<News> homeInform = newsMapper.getHomeInform();
        return homeInform;
    }

    @Override
    public boolean addNews(News news) {
        int i = newsMapper.addNews(news);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean releaseNews(Integer newsId) {
        int i = 0;
        News news = newsMapper.getNewsById(newsId);
        String oldStatus = news.getReleaseStatus();
        if (oldStatus.equals(Constant.NEWS_RELEASE_FALSE)){
             i = newsMapper.releaseNews(newsId);
        }else if (oldStatus.equals(Constant.NEWS_RELEASE_TRUE)){
             i = newsMapper.recallNews(newsId);
        }else {
            return false;
        }

        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public News getNews(Integer id) {
        News news = newsMapper.getNewsById(id);
        return news;
    }

    @Override
    public boolean updateNews(News news) {
        int i = newsMapper.updateNews(news);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteNews(Integer newsId) {
        int i = newsMapper.deleteNews(newsId);
        if (i == 1){
            return true;
        }
        return false;
    }
}
