package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.SDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/9/27 14:18
 */
@Mapper
@Repository
public interface SDictMapper {

    /**
     * 根据条件查询字典类型
     * @param sDict
     * @return
     */
    public List<SDict> selectSDictTypeList(SDict sDict);

    /**
     * 根据条件分页查询字典数据
     * @param sDict 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SDict> selectSDictList(SDict sDict);

    /**
     * 根据字典类型查询字典项（父查子）
     * @param tableType 类型id （父id）
     * @return  字典数据集合
     */
    public List<SDict> selectSDictByType(String tableType);

    public SDict selectSDictTypeById(String id);

    /**
     * 根据id查询字典信息
     * @param id  id
     * @return 字典数据
     */
    public SDict selectSDictById(String id);

    /**
     * 校验字典类型称是否唯一
     *
     * @param code 字典类型
     * @return 结果
     */
    public SDict checkDictTypeUnique(String code);

    /**
     *  根据字典类型和字典编码查询字典信息
     * @param tableType  字典类型 （父code）
     * @param code 字典编码（子code）
     * @return 字典值
     */
    public String selectSDitName(@Param("tableType") String tableType,@Param("code") String code);



    /**
     *  添加字典类型
     * @param sDict
     * @return
     */
    public int insertSDictType(SDict sDict);

    /**
     *  新增字典数据信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int insertSDict(SDict sDict);

    public int updateSDictType(SDict sDict);

    /**
     *  修改字典数据信息
     * @param sDict 字典数据信息
     * @return 结果
     */
    public int updateSDict(SDict sDict);

    /**
     *  同步修改子 字典类型 （修改父字典）
     * @param oldTableType
     * @param newTableType
     * @param newTableName
     * @return
     */
    public int updateSDictDataType(@Param("oldTableType") String oldTableType,@Param("newTableType") String newTableType,@Param("newTableName") String newTableName);

    /**
     *  查询子集字典数量
     * @param tableType
     * @return
     */
    public int countSDictByType(String tableType);

    /**
     *  删除字典类型
     * @param id
     * @return
     */
    public int deleteSDictTypeById(String id);

    /**
     *  删除字典
     * @param id id
     * @return
     */
    public int deleteSDictById(String id);

    /**
     *  批量删除字典
     * @param ids
     * @return
     */
    public int deleteSDictByIds(int[] ids);

}
