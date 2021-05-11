package com.bdu.laborder.service;

import com.bdu.laborder.entity.Labs;
import com.bdu.laborder.entity.LabsTop;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;


import java.util.List;

/**
 * @Author Qi
 * @data 2020/12/26 22:02
 */
public interface LabsService {

    PageInfo<Labs> getLabs(PageQuery pageQuery);
    Labs addLab(Labs labs);
    Labs getLabById(Integer id);
    Labs updataLab(Labs labs);
    int deleteLab(Integer id);
    List<Labs> getAllLab();
    List<LabsTop> getLabsTop();
}
