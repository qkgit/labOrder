package com.bdu.laborder.service;

import com.bdu.laborder.entity.News;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/16 11:36
 */
public interface NewsService {

    PageInfo<News> getNews(PageQuery pageQuery);

    List<News> getHomeInform();

    boolean addNews(News news);

    boolean releaseNews(Integer newsId);

    News getNews(Integer id);

    boolean updateNews(News news);

    boolean deleteNews(Integer newsId);
}
