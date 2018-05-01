package ApiTestUtility;

/// <summary>
/// 签名结果实体
/// </summary>
public class ApiSignResponseData{
    //处理结果成功失败
    private  boolean  Success;
    public  void  SetSuccess(boolean Success){
        this.Success = Success;
    }
    public  boolean GetSuccess(){
        return  this.Success;
    }
    //签名字符串
    private  String  Sign;
    public  void  SetSign(String Sign){
        this.Sign = Sign;
    }
    public  String GetSign(){
        return  this.Sign;
    }
    //接口处理完毕返回的数据
    private  String  SignData;
    public  void  SetSignData(String SignData){
        this.SignData = SignData;
    }
    public  String GetSignData(){
        return  this.SignData;
    }
}
