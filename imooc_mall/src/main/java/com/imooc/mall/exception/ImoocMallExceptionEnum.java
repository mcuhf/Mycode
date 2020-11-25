

package com.imooc.mall.exception;

/**
 * 异常枚举
 */
public enum ImoocMallExceptionEnum {
    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    PASSWORD_TOO_SHORT(10003,"密码小于八位"),
    NAME_EXISTED(10004,"不允许重名"),
    INSERT_FAILED(10005,"数据库插入数据失败"),
    WRONG_PASSWORD(10006,"密码错误"),
    NEED_LOGIN(10007,"用户未登录"),
    LOGIN_FAILED(10008,"签名更新失败"),
    NEED_ADMIN(10009,"非管理员禁止登陆"),
    PARA_NOT_NULL(10010,"参数不能为空"),
    CREATE_FAILED(10011,"商品目录新增失败"),
    REQS_PARA_ERROR(10012,"参数不符合要求"),
    UPDATE_FAILED(10013,"更新失败"),
    DELETE_FAILED(10014,"删除失败"),
    PRODUCT_ADD_FAILED(10015,"商品添加失败"),
    DIR_CREATE_FAILED(10016,"文件夹创建失败"),
    PHOTO_UPLOAD_FAILED(10017,"图片上传失败"),
    PRODUCT_NOT_EXISTED(10018,"商品不存在查询失败"),
    PRODUCT_STATE_ERROR(10019,"商品状态异常，无法加入购物车"),
    PRODUCT_NOT_ENOUGH(10020,"商品的库存不足,加入购物车失败"),
    CATEGORY_NAME_EXISTED(9999,"请勿添加重复的种类名目"),
    SYSTEM_ERROR(20000,"系统异常");
    Integer code;
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
