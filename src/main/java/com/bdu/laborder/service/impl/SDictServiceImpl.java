package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.common.core.domain.entity.SysDict;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.SDictMapper;
import com.bdu.laborder.service.SDictService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
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
    public SysDict selectSDictById(String id, String type) {
        SysDict sDict = null;
        switch (type){
            case Constant.DICT_TYPE:
                sDict = sDictMapper.selectSDictTypeById(id);
                break;
            case Constant.DICT_DATA:
                sDict = sDictMapper.selectSDictById(id);
                break;
            default:
                return null;
        }
        return sDict;
    }


    @Override
    public List<SysDict> selectSDictTypeList(SysDict sDict) {
        List<SysDict> dictList = sDictMapper.selectSDictTypeList(sDict);
        return dictList;
    }

    @Override
    public boolean checkDictTypeUnique(SysDict sDict) {
        SysDict dict = sDictMapper.checkDictTypeUnique(sDict.getCode());
        if (StringUtils.isNotNull(dict) && !dict.getUuid().equals(sDict.getUuid())){
            return Constant.TRUE;
        }
        return Constant.FALSE;
    }

    @Override
    public int insertSDictType(SysDict sDict) {
        sDict.setUuid(UuidUtil.getUuid());
        return sDictMapper.insertSDictType(sDict);
    }

    @Override
    public int updateSDictType(SysDict sDict) {

        SysDict oldDict = sDictMapper.selectSDictTypeById(sDict.getUuid());
        try {
            sDictMapper.updateSDictDataType(oldDict.getCode(), sDict.getCode(), sDict.getName());
            return sDictMapper.updateSDictType(sDict);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteSDictTypeByIds(String[] ids) {
        int i =0;
        for (String id : ids) {
            SysDict sDict = sDictMapper.selectSDictTypeById(id);
            if (sDictMapper.countSDictByType(sDict.getCode())>0) {
                throw new LabOrderException(String.format("%1$s已分配字典项,不能删除",sDict.getName()));
            }
            try {
                i += sDictMapper.deleteSDictTypeById(id);
            }catch (Exception e){
                i =0;
                throw new LabOrderException();
            }

        }
        return i;
    }

    @Override
    public List<SysDict> selectSDictByType(String tableType) {
        List<SysDict> sDicts = sDictMapper.selectSDictByType(tableType);
        return sDicts;
    }


    /** ####################################################################################*/


    @Override
    public List<SysDict> selectSDictList(SysDict sDict) {
        List<SysDict> dictList = sDictMapper.selectSDictList(sDict);
        return dictList;
    }

    @Override
    public boolean checkDictUnique(SysDict sDict) {
        SysDict dict = sDictMapper.selectSDictByCode(sDict.getTableType(), sDict.getCode());
        if (StringUtils.isNotNull(dict) && !dict.getUuid().equals(sDict.getUuid())){
            return Constant.TRUE;
        }
        return Constant.FALSE;
    }

    @Override
    public String selectSDictName(String tableType, String code) {
        SysDict dict = sDictMapper.selectSDictByCode(tableType, code);
        return dict == null ? null : dict.getName();
    }

    @Override
    public int insertSDict(SysDict sDict) {
        sDict.setUuid(UuidUtil.getUuid());
        SysDict sDictType = sDictMapper.checkDictTypeUnique(sDict.getTableType());
        sDict.setTableName(sDictType.getName());
        return sDictMapper.insertSDict(sDict);
    }

    @Override
    public int updateSDict(SysDict sDict) {
        return sDictMapper.updateSDict(sDict);
    }

    @Override
    public int deleteDictByIds(String[] ids) {
        int i =0;
        for (String id : ids) {
            try {
                i += sDictMapper.deleteSDictById(id);
            }catch (Exception e){
                i =0;
                throw new LabOrderException();
            }

        }
        return i;
    }


}
