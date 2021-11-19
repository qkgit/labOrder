package com.bdu.laborder.service;

import com.bdu.laborder.common.core.domain.entity.SysDict;
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
    public SysDict selectSDictById(String id, String type);



    /**
     * 根据条件查询字典类型数据
     * @param sDict
     * @return
     */
    public List<SysDict> selectSDictTypeList(SysDict sDict);

    /**
     * 校验字典类型是否唯一
     *
     * @param sDict 字典类型
     * @return 结果
     */
    public boolean checkDictTypeUnique(SysDict sDict);

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
    public int insertSDictType(SysDict sDict);

    /**
     *  修改字典类型 信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int updateSDictType(SysDict sDict);

    /**
     * 根据字典类型查询字典项（父查子）
     * @param tableType 类型id （父id）
     * @return  字典数据集合 (ORDER BY order_num,create_time)
     */
    public List<SysDict> selectSDictByType(String tableType);


    /** ============================================================================================*/

    /**
     * 根据条件分页查询字典数据
     * @param sDict 字典数据查询信息
     * @return 字典数据集合信息
     */
    public List<SysDict> selectSDictList(SysDict sDict);

    /**
     * 校验字典类型是否唯一
     * @param sDict
     * @return
     */
    public boolean checkDictUnique(SysDict sDict);

    /**
     *  根据字典类型和字典编码查询字典值
     * @param tableType  字典类型 （父code）
     * @param code 字典编码（子code）
     * @return 字典值
     */
    public String selectSDictName(@Param("tableType") String tableType, @Param("code") String code);

    /**
     *  新增字典数据信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int insertSDict(SysDict sDict);

    /**
     *  修改字典数据信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int updateSDict(SysDict sDict);

    public int deleteDictByIds(String[] ids);

}
