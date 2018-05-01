package ApiTestUtility;

/// <summary>
/// Api业务响应结果实体
/// </summary>
public class ApiBizResponseData{
    //处理结果编码
    private  int  Code;
    public  void  SetCode(int Code){
        this.Code = Code;
    }
    public  int GetCode(){
        return  this.Code;
    }
    //处理结果消息
    private  String  Message;
    public  void  SetMessage(String Code){
        this.Message = Message;
    }
    public  String GetMessage(){
        return  this.Message;
    }
    //接口处理完毕返回的数据
    private  String  Result;
    public  void  SetResult(String Result){
        this.Result = Result;
    }
    public  String GetResult(){
        return  this.Result;
    }
}
