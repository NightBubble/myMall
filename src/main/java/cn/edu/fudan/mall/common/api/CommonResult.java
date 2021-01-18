package cn.edu.fudan.mall.common.api;

/**
 * 通用返回对象
 */
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    protected CommonResult(){

    }

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功结果
     */

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 返回成功结果
     */

    public static <T> CommonResult<T> success(T data, String message){
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 返回失败结果
     * @param errorCode 错误码
     */

    public static <T> CommonResult<T> failed(IErrorCode errorCode){
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 返回失败结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message){
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 返回失败结果
     */
    public static <T> CommonResult<T> failed(Integer code, String message){
        return new CommonResult<T>(code, message,null);
    }

    /**
     * 返回认证失败结果
     */
    public static <T> CommonResult<T> validFailed(){
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 返回认证失败和提示信息
     */
    public static <T> CommonResult<T> validFailed(String message){
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 返回未登录结果
     */
    public static <T> CommonResult<T> unauthorized(){
        return failed(ResultCode.UNAUTHORIZED);
    }

    /**
     * 返回未登录结果和提示信息
     */
    public static <T> CommonResult<T> unauthorized(String message){
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), message, null);
    }

    /**
     * 返回未授权结果
     */
    public static <T> CommonResult<T> forbidden(){
        return failed(ResultCode.FORBIDDEN);
    }

    /**
     * 返回未授权结果和提示信息
     */
    public static <T> CommonResult<T> forbidden(String message){
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), message, null);
    }

    /**
     * 判断是否成功
     *
     */

    public boolean isSuccess(){
        if(this.code == ResultCode.SUCCESS.getCode()){
            return true;
        }else{
            return false;
        }
    }


    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
