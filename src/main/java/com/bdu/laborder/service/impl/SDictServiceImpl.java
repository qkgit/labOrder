package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.entity.SDict;
import com.bdu.laborder.mapper.SDictMapper;
import com.bdu.laborder.service.SDictService;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/10/2 11:36
 */
@Service
public class SDictServiceImpl implements SDictService {

    @Autowired
    private SDictMapper sDictMapper;

    @Override
    public List<SDict> selectSDictTypeList(SDict sDict) {
        List<SDict> dictList = sDictMapper.selectSDictTypeList(sDict);
        return dictList;
    }

    @Override
    public List<SDict> selectSDictList(SDict sDict) {
        List<SDict> dictList = sDictMapper.selectSDictList(sDict);
        return dictList;
    }

    @Override
    public List<SDict> selectSDictByType(String tableType) {
        return null;
    }

    @Override
    public String selectSDitName(String tableType, String code) {
        String s = sDictMapper.selectSDitName(tableType, code);
        if (s!=null){

        }
        return null;
    }

    @Override
    public SDict selectSDictById(String id) {
        SDict sDict = sDictMapper.selectSDictById(id);
        return sDict;
    }

    @Override
    public int countSDictByType(String tableType) {
        return 0;
    }

    @Override
    public int deleteSDictById(int id) {
        return 0;
    }

    @Override
    public int deleteSDictByIds(int[] ids) {
        return 0;
    }

    @Override
    public int insertSDict(SDict sDict) {
        return 0;
    }

    @Override
    public int updateSDict(SDict sDict) {
        return 0;
    }

    @Override
    public int updateSDictType(String oldTableType, String newTableType, String newTableName) {
        return 0;
    }

    @Override
    public boolean checkDictTypeUnique(SDict sDict) {
        SDict dict = sDictMapper.checkDictTypeUnique(sDict.getCode());
        if (StringUtils.isNotNull(dict) && dict.getUuid().equals(sDict.getUuid())){
            return Constant.FALSE;
        }
        return Constant.TRUE;
    }
}
