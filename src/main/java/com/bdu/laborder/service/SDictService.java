package com.bdu.laborder.service;

import com.bdu.laborder.entity.SDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/9/30 23:04
 */
public interface SDictService {


    /**
     * 根据id查询字典信息
     * @param id  id
     * @return 字典数据
     */
    public SDict selectSDictById(String id,String type);



    /**
     * 根据条件查询字典类型数据
     * @param sDict
     * @return
     */
    public List<SDict> selectSDictTypeList(SDict sDict);

    /**
     * 校验字典类型是否唯一
     *
     * @param sDict 字典类型
     * @return 结果
     */
    public boolean checkDictTypeUnique(SDict sDict);

    /**
     *  批量删除字典类型
     * @param ids id
     * @return
     */
    public int deleteSDictTypeByIds(String[] ids);

    /**
     *  添加字典类型
     * @param sDict
     * @return
     */
    public int insertSDictType(SDict sDict);

    /**
     *  修改字典类型 信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int updateSDictType(SDict sDict);

    /**
     * 根据字典类型查询字典项（父查子）
     * @param tableType 类型id （父id）
     * @return  字典数据集合
     */
    public List<SDict> selectSDictByType(String tableType);


    /** ============================================================================================*/

    /**
     * 根据条件分页查询字典数据
     * @param sDict 字典数据查询信息
     * @return 字典数据集合信息
     */
    public List<SDict> selectSDictList(SDict sDict);

    /**
     *  根据字典类型和字典编码查询字典信息
     * @param tableType  字典类型 （父code）
     * @param code 字典编码（子code）
     * @return 字典值
     */
    public String selectSDitName(@Param("tableType") String tableType, @Param("code") String code);

    /**
     *  新增字典数据信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int insertSDict(SDict sDict);

    /**
     *  修改字典数据信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int updateSDict(SDict sDict);

    public int deleteDictByIds(String[] ids);

}
