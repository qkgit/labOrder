package com.bdu.laborder.common.core.domain.vo;

/**
 * @Title
 * @Author Qi
 * @data 2022/3/14 20:39
 *  示例
 *  meta : {
 *     title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
 *     icon: 'svg-name'/'el-icon-x' // 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
 *     breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
 *     noCache: true                // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
 *     activeMenu: '/example/list'  // 如果设置路径，侧边栏将高亮显示您设置的路径
 *   }
 */
public class MetaVo {

    /** 设置该路由在侧边栏和面包屑中展示的名字 */
    private String title;
    /** 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon */
    private String icon;
    /** 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true) */
    private boolean breadcrumd;
    /** 设置为true，则不会被 <keep-alive>缓存 */
    private boolean noCache;

    public MetaVo() {
    }

    public MetaVo(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaVo(String title, String icon, boolean breadcrumd, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.breadcrumd = breadcrumd;
        this.noCache = noCache;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isBreadcrumd() {
        return breadcrumd;
    }

    public void setBreadcrumd(boolean breadcrumd) {
        this.breadcrumd = breadcrumd;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }
}
