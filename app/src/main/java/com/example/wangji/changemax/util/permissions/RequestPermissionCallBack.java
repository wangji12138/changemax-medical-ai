package com.example.wangji.changemax.util.permissions;

/**
 * 权限请求结果回调接口
 * Created by WangJi.
 */

public interface RequestPermissionCallBack {
    /**
     * 同意授权
     */
    void granted();

    /**
     * 取消授权
     */
    void denied();
}