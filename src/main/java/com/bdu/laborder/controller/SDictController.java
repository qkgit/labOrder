package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.entity.SDict;
import com.bdu.laborder.service.SDictService;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author Qi
 * @data 2021/10/2 16:52
 */
@RestController
@RequestMapping("/dict")
public class SDictController extends BaseController {

    @Autowired
    SDictService sDictService;

    /* ============ sDictType START ============ */

    @PostMapping("/type/list")
    public Result getTypeList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        SDict sDict = getParam(pageQuery, SDict.class);
        List<SDict> sDictPageInfo = sDictService.selectSDictTypeList(sDict);
        return getPageInfo(sDictPageInfo);
    }

    @GetMapping("/type/{dictId}")
    public Result getTypeInfo(@PathVariable String dictId){
        SDict sDict = sDictService.selectSDictById(dictId, Constant.DICT_TYPE);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,sDict);
    }

    @PostMapping("/type")
    public Result addDict(@RequestBody SDict sDict){
        // 判断code是否唯一
        if (sDictService.checkDictTypeUnique(sDict)){
            return error("新增字典 '"+sDict.getName()+"' 失败，字典类型已存在");
        }
        sDict.setCreateBy(getUserName());
        return toResult(sDictService.insertSDictType(sDict));
    }

    @DeleteMapping("/type/{dictIds}")
    public Result removeType(@PathVariable String[] dictIds){
        return toResult(sDictService.deleteSDictTypeByIds(dictIds));
    }

    @PutMapping("/type")
    public Result updateType(@RequestBody SDict sDict){
        if (sDictService.checkDictTypeUnique(sDict)){
            return error("修改字典 '"+sDict.getName()+"' 失败，字典类型已存在");
        }
        sDict.setUpdateBy(getUserName());
        return toResult(sDictService.updateSDictType(sDict));
    }

    /* ============ sDictType END ============ */

    /* ============ sDictData START ============ */

    @PostMapping("/data/list")
    public Result getDataList(@RequestBody PageQuery pageQuery) {
        startPage(pageQuery);
        SDict sDict = getParam(pageQuery, SDict.class);
        List<SDict> dictPageInfo = sDictService.selectSDictList(sDict);
        return getPageInfo(dictPageInfo);
    }

    @GetMapping("/data/type/{sDictType}")
    public Result getDictByType(@PathVariable String sDictType){
        List<SDict> sDicts = sDictService.selectSDictByType(sDictType);
        if (StringUtils.isNull(sDicts)){
            sDicts = new ArrayList<SDict>();
        }
        return success(sDicts);
    }

    @GetMapping("/data/{dictId}")
    public Result getDataInfo(@PathVariable String dictId){
        SDict sDict = sDictService.selectSDictById(dictId,Constant.DICT_DATA);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,sDict);
    }

    @PostMapping("/data")
    public Result addDictData(@RequestBody SDict sDict){
        if (StringUtils.isEmpty(sDict.getCode())){
            return error("字典编码不能为空！");
        }
        // 判断code是否唯一
        String sDitName = sDictService.selectSDictName(sDict.getTableType(), sDict.getCode());
        if (sDitName!=null){
            return error("新增字典 '"+sDict.getName()+"' 失败，字典编码重复！");
        }
        sDict.setCreateBy(getUserName());
       return toResult(sDictService.insertSDict(sDict));
    }

    @DeleteMapping("/data/{dictIds}")
    public Result removeDict(@PathVariable String[] dictIds){
        return toResult(sDictService.deleteDictByIds(dictIds));
    }

    @PutMapping("/data")
    public Result updateDict(@RequestBody SDict sDict){
        if (StringUtils.isEmpty(sDict.getCode())){
            return error("字典编码不能为空！");
        }
        // 判断code是否唯一
        if (sDictService.checkDictUnique(sDict)){
            return error("修改字典 '"+sDict.getName()+"' 失败，字典编码重复！");
        }
        sDict.setUpdateBy(getUserName());
        return toResult(sDictService.updateSDict(sDict));
    }


}
