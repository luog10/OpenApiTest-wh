package ApiTestUtility;

import com.test.utility.RequestType;
import java.util.Map;

/// <summary>
/// Api请求实体
/// </summary>
public class ApiRequestData {
    //API接口名称
    private  String method;
    public  void  SetMethod(String method){
        this.method = method;
    }
    public  String  GetMethod(){
        return  this.method;
    }
    //签名类型(md5)
    private  String sign_type = "md5";
    public  String  GetSign_type(){
        return  this.sign_type;
    }
    //分配给应用的AppId
    private String appid;
    public  void  SetAppid(String  appid){
        this.appid = appid;
    }
    public  String  GetAppid(){
        return  this.appid;
    }
    //分配给应用的AppId对应的appsecret
    private String appsecret;
    public  void  SetAppsecret(String  appsecret){
        this.appsecret = appsecret;
    }
    public  String  GetAppsecret(){
        return  this.appsecret;
    }
    //版本号
    private  String version = "1.0";
    public  void  SetVersion(String version){
        this.version = version;
    }
    public  String  GetVersion(){
        return  this.version;
    }
    //签名
    private  String sign;
    public  void  SetSign(String sign){
        this.sign = sign;
    }
    public  String  GetSign(){
        return  this.sign;
    }
    //API输入参数(json格式)
    private  String bizdata;
    public  void  SetBizdata(String bizdata){
        this.bizdata = bizdata;
    }
    public  String  GetBizdata(){
        return  this.bizdata;
    }
    //请求类型
    private  RequestType RequestType;
    public void  SetRequestType(RequestType RequestType){
        this.RequestType = RequestType;
    }
    public RequestType  GetRequestType(){
        return this.RequestType;
    }
    //请求Headers
    private  Map<String, String> headers;
    public  void  SetHeaders(Map<String, String> headers){
        this.headers = headers;
    }
    public Map<String, String> GetHeaders(){
        return  this.headers;
    }
}
