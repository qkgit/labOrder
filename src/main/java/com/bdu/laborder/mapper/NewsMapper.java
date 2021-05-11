package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.News;
import com.bdu.laborder.entity.NewsRequest;
import org.apache.ibatis.annotations.Mapper;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/16 11:37
 */
@Mapper
@Repository
public interface NewsMapper {
    List<News> getAllNews(NewsRequest newsRequest);

    List<News> getHomeInform();

    News getNewsById(Integer newsId);

    int updateNews(News news);

    int addNews(News news);

    int releaseNews(Integer newsId);

    int recallNews(Integer newsId);

    int deleteNews(Integer newsId);
}
