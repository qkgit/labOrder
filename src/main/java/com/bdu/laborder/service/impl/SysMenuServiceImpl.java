package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.TreeSelect;
import com.bdu.laborder.common.core.domain.entity.SysMenu;
import com.bdu.laborder.common.core.domain.entity.SysRole;
import com.bdu.laborder.common.core.domain.service.UserService;
import com.bdu.laborder.common.core.domain.vo.MetaVo;
import com.bdu.laborder.common.core.domain.vo.RouterVo;
import com.bdu.laborder.mapper.SysMenuMapper;
import com.bdu.laborder.mapper.SysRoleMapper;
import com.bdu.laborder.mapper.SysRoleMenuMapper;
import com.bdu.laborder.service.SysMenuService;


import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/18 16:49
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper menuMapper;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(String userId) {
        return selectMenuList(new SysMenu(), userId);
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu   菜单信息查询条件
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, String userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (UserService.isAdmin(roleMapper.getUserRolesById(userId))) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<String> selectMenuListByRoleId(String roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return menuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(String userId) {
        List<SysMenu> menus = null;
        // 管理员显示所有菜单信息
        if (UserService.isAdmin(roleMapper.getUserRolesById(userId))) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, UserConstants.ROOT_MENU_ID);
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(String menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        LinkedList<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setName(getRouterName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getRouterComponent(menu));
            router.setHidden("1".equals(menu.getVisible()));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
            if (isDirHasChildren(menu)) {
                // 含有子路由的目录
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(menu.getChildren()));
            } else if (isMenuFrame(menu)) {
                // 一级菜单(非外链) 需要将自身加入到 router.children 中
                ArrayList<RouterVo> childrenList = new ArrayList<>();
                // 1.置空meta
                router.setMeta(null);
                // 2.设置children
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setComponent(menu.getComponent());
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
                // 3.将children放入 router.children 中
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 是否是含有子路由的目录
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isDirHasChildren(SysMenu menu) {
        return StringUtils.isNotEmpty(menu.getChildren())
                && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 是否为菜单内部跳转  一级菜单(非外链)
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenu menu) {
        return UserConstants.ROOT_MENU_ID.equals(menu.getParentId())
                && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame());
    }

    /**
     * 是否为目录内部跳转  一级目录(非外链)
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isDirFrame(SysMenu menu) {
        return UserConstants.ROOT_MENU_ID.equals(menu.getParentId())
                && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame());
    }

    /**
     * 是否为内链组件(系统内部打开外部链接)
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenu menu) {
        return UserConstants.NO_FRAME.equals(menu.getIsFrame())
                && StringUtils.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *  （非一级目录）
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu) {
        return !UserConstants.ROOT_MENU_ID.equals(menu.getParentId()) && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouterName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 一级菜单(非外链)
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();

        // 一级目录(非外链)
        if (isDirFrame(menu)) {
            routerPath = "/" + menu.getPath();
        }
        // 一级菜单(非外链)
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }

        // todo 内链打开外网方式
//        if (!UserConstants.ROOT_MENU_ID.equals(menu.getParentId()) && isInnerLink(menu)) {
//            routerPath = StringUtils.replaceEach(routerPath, new String[]{Constant.HTTP, Constant.HTTPS}, new String[]{"", ""});
//        }

        return routerPath;
    }

    /**
     * 获取路由组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getRouterComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        }  else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        //todo 内链打开外网方式
//        else if (StringUtils.isEmpty(menu.getComponent()) && !UserConstants.ROOT_MENU_ID.equals(menu.getParentId()) && isInnerLink(menu)) {
//            component = UserConstants.INNER_LINK;
//        }
        return component;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        List<String> tempList = new ArrayList<String>();
        for (SysMenu dept : menus) {
            tempList.add(dept.getMenuId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext(); ) {
            SysMenu menu = (SysMenu) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(String menuId) {
        return menuMapper.deleteMenuById(menuId);
    }


    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && !StringUtils.equals(info.getMenuId(), menu.getMenuId())) {
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean hasChildByMenuId(String menuId) {
        int i = menuMapper.hasChildByMenuId(menuId);
        return i > 0 ? true : false;
    }

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkMenuExistRole(String menuId) {
        int i = roleMenuMapper.checkMenuExistRole(menuId);
        return i > 0 ? true : false;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, String parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (StringUtils.equals(t.getParentId(), parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }


    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (StringUtils.equals(n.getParentId(), t.getMenuId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
