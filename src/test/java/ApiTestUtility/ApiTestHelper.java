package ApiTestUtility;


import com.test.utility.*;
import org.testng.Reporter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Api测试帮助类
 * */
public class ApiTestHelper{
    // 测试模块名称
    public static String TestFunctionName = null;
    // 测试用例名称
    public static String TestCaseName = null;
    //从配置中获取测试环境hosts
    private static  String  ApiHost = PropertiesUtil.getValue("TestConfig.properties","EvnHost");
    //请求数据
    public  static  ApiRequestData ApiRequestData = null;
    //测试日志系统写入信息
    private static TestCaseTestLogInfo TestCaseTestLogInfo = new TestCaseTestLogInfo();
    // 是否记录日志到日志系统
    private static String IsToLogSystem = PropertiesUtil.getValue("TestConfig.properties","IsToLogSystem");
    // 被测系统编号
    private static String TestSystemSysNo = PropertiesUtil.getValue("TestConfig.properties","TestSystemSysNo");
    // 测试版本号
    private static String TestVersionNo = PropertiesUtil.getValue("TestConfig.properties","TestVersionNo");
    // 测试日志系统host
    private static String TestLogSystemHost = PropertiesUtil.getValue("TestConfig.properties","TestLogSystemHost");
    // 被测项目名称
    private static String TestProjectName = PropertiesUtil.getValue("TestConfig.properties","TestProjectName");
    // 测试日志详情叠加器
    private static StringBuilder stringbuilder = null;

    /// <summary>
    /// 执行API测试
    /// </summary>
    /// <returns>测试结果</returns>
    public static ApiBizResponseData ExcuteApiTset() {
        //初始化测试日志详情叠加器
        stringbuilder = new StringBuilder();
        TestCaseTestLogInfo.SetTestBeginTime(new Date());
        BuildTestCaseTestLogDetail(String.format("%s%n测试开始...%n", CommonTool.GetCurrentTimeMs()));
        ApiBizResponseData TestResult = null;
        try {
            //1.请求签名
            if(!Sign()){
                TestResult = new ApiBizResponseData();
                TestResult.SetCode(-20);
                TestResult.SetMessage("签名错误!");
                TestResult.SetResult("");
                return TestResult;
            }
            //2.签名成功请求业务得到测试结果
            String BizResult =ApiTest(BuildBizRequestString());
            TestResult = JsonHelper.JsonDeserialize(BizResult,ApiBizResponseData.class);
            BuildTestCaseTestLogDetail(String.format("%s%n测试结果:[{%s}].%n", CommonTool.GetCurrentTimeMs(), BizResult));
        }
        catch (Exception ex) {
            TestResult = new ApiBizResponseData();
            TestResult.SetCode(-9999);
            TestResult.SetMessage("测试过程发生异常！");
            TestResult.SetResult(ex.getMessage());
            BuildTestCaseTestLogDetail(String.format("%s%n测试结果:[{%s}].%n", CommonTool.GetCurrentTimeMs(), ex.getMessage()));
        }
        return TestResult;
    }

    /// <summary>
    /// 请求签名
    /// </summary>
    /// <returns>签名是否成功</returns>
    private  static boolean Sign(){
        String signdata = BuildSignRequestString();
        try {
            //设置header
            Map<String, String> Headers = new HashMap<String, String>();
            Headers.put("Content-Type", HttpClient.CONTENT_TYPE_FORM_URL);
            ApiSignResponseData apiSignResponseData = JsonHelper.JsonDeserialize(HttpClient.Post(String.format("%s/Home/AjaxSign", ApiHost),Headers, signdata),ApiSignResponseData.class);
            if(apiSignResponseData.GetSuccess()){
                ApiRequestData.SetSign(apiSignResponseData.GetSign());
                return true;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  false;
    }

    /// <summary>
    /// 生成签名字符串
    /// </summary>
    /// <param name="ApiRequesData"></param>
    /// <returns></returns>
    private static String BuildSignRequestString() {
        String SignData = "";
        SignData = "method=" + ApiRequestData.GetMethod();
        SignData += "&sign_type=" + ApiRequestData.GetSign_type();
        SignData += "&appid=" + ApiRequestData.GetAppid();
        SignData += "&appsecret=" + ApiRequestData.GetAppsecret();
        SignData += "&version=" + ApiRequestData.GetVersion();
        SignData += "&data=" + ApiRequestData.GetBizdata();
        return SignData;
    }

    /// <summary>
    /// 生成业务请求字符串
    /// </summary>
    /// <param name="ApiRequesData"></param>
    /// <returns></returns>
    private static String BuildBizRequestString() {
        String BizData = "";
        BizData = "method=" + ApiRequestData.GetMethod();
        BizData += "&sign_type=" + ApiRequestData.GetSign_type();
        BizData += "&appid=" + ApiRequestData.GetAppid();
        BizData += "&sign=" + ApiRequestData.GetSign();
        BizData += "&version=" + ApiRequestData.GetVersion();
        BizData += "&data=" + ApiRequestData.GetBizdata();
        return BizData;
    }

    /// <summary>
    /// 发起HTTP请求
    /// </summary>
    /// <returns></returns>
    private static String ApiTest(String requestdata) {
        BuildTestCaseTestLogDetail(String.format("%s%n测试数据:[%s,%s].\r\n", CommonTool.GetCurrentTimeMs(),ApiRequestData.GetMethod(),requestdata));
        //设置header
        Map<String, String> Headers = new HashMap<String, String>();
        Headers.put("Content-Type", HttpClient.CONTENT_TYPE_FORM_URL);
        if(ApiRequestData.GetRequestType() == RequestType.Post){
            return HttpClient.Post(String.format("%s/api", ApiHost),Headers, requestdata);
        }else {
            return HttpClient.Get(String.format("%s/api?%s", ApiHost, requestdata),Headers);
        }
    }

    /// <summary>
    /// 记录测试测试结果
    /// </summary>
    /// <param name="ResultType"></param>
    /// <param name="TestResultDetail"></param>
    public static void RecordTestResult(TestResultType ResultType, String TestResultDetail) {
        //是否记录日志到日志系统
        boolean IswritelogtoLogSystem = false;
        if (!CommonTool.IsNullOrEmpty(IsToLogSystem) && IsToLogSystem.equals("true"))
        {
            //记录日志详情
            IswritelogtoLogSystem = true;
        }

        if (ResultType == TestResultType.Pass)
        {
            if (IswritelogtoLogSystem)
            {
                BuildTestCaseTestLogDetail(String.format("%s%n测试通过,Pass..", CommonTool.GetCurrentTimeMs()));
                TestCaseTestLogInfo.SetTestStatus(1);
                TestCaseTestLogInfo.SetTestEndTime(new Date());
                //调用写入日志系统
                WriteLogToLogSystem();
            }
        }
        else
        {
            if (IswritelogtoLogSystem)
            {
                BuildTestCaseTestLogDetail(String.format("%s%n测试不通过,Failed,%n失败详情：[{%s}]", CommonTool.GetCurrentTimeMs(), TestResultDetail));
                TestCaseTestLogInfo.SetTestEndTime(new Date());
                TestCaseTestLogInfo.SetTestStatus(0);
                //调用写入日志系统
                System.out.println(WriteLogToLogSystem());
            }
            CommonTool.ThrowNewException(TestResultDetail);
        }
    }

    /// <summary>
    /// 测试日志写入测试日志系统
    /// </summary>
    /// <param name="ProjectName">被测项目名称</param>
    /// <returns></returns>
    private static String WriteLogToLogSystem() {
        //记录测试日志到测试日志系统Precheck
        ToLogSystemPrecheck();
        //调用异步Post请求写入日志系统
        Map<String, String> Headers = new HashMap<String, String>();
        Headers.put("Content-Type", HttpClient.CONTENT_TYPE_JSON_URL);
        //获取测试日志系统账号&密码配置
        String TestSystemAppId_Key = PropertiesUtil.getValue("TestConfig.properties","TestSystemAppId");
        if (CommonTool.IsNullOrEmpty(TestSystemAppId_Key))
        {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestSystemAppId&Key");
        }
        else
        {
            if (!TestSystemAppId_Key.contains("|")) {
                CommonTool.ThrowNewException("TestSystemAppId配置格式错误,AppId&Key应以\"|\"分隔");
            }
            String[] Id_Key = TestSystemAppId_Key.split("\\|");
            if (Id_Key != null && Id_Key.length != 2) {
                CommonTool.ThrowNewException("TestSystemAppId配置不正确！");
            }
            //将日志系统Id_key设置请求报文头
            Headers.put(Id_Key[0], Id_Key[1]);
        }
        String LogSystemUrl = String.format("%s/api/TestCaseTestLog", TestLogSystemHost);
        try
        {
            return Log4TestLogSystemHelper.WriteLogAsync(LogSystemUrl,Headers, TestCaseTestLogInfo);
        }
        catch (Exception ex)
        {
            return ex.getMessage();
        }

    }

    /// <summary>
    /// 写入日志系统前检查
    /// </summary>
    private static void ToLogSystemPrecheck() {
        if (CommonTool.IsNullOrEmpty(TestLogSystemHost)){
            CommonTool.ThrowNewException("请在测试配置文件中配置TestLogSystemHost");
        }
        if (CommonTool.IsNullOrEmpty(TestSystemSysNo)) {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestSystemSysNo");
        }
        if (CommonTool.IsNullOrEmpty(TestVersionNo)) {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestVersionNo");
        }
        if (CommonTool.IsNullOrEmpty(TestProjectName)) {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestProjectName");
        }

        TestCaseTestLogInfo.SetProjectName(TestProjectName);
        TestCaseTestLogInfo.SetTestSystemSysno(Integer.parseInt(TestSystemSysNo));
        TestCaseTestLogInfo.SetTestVersion(TestVersionNo);
        TestCaseTestLogInfo.SetUserSysNo(1);

        TestCaseTestLogInfo.SetFunctionName(TestFunctionName);
        TestCaseTestLogInfo.SetTestCaseName(TestCaseName);
    }

    /// <summary>
    /// 将测试详情追加写入到日志详情
    /// </summary>
    /// <param name="steptestlog"></param>
    private static void BuildTestCaseTestLogDetail(String steptestlog) {
        //1.TestNG 打印日志
        Reporter.log(steptestlog);
        //2.控制控制台打印日志
        System.out.println(steptestlog);
        //3.构建测试日志系统日志
        TestCaseTestLogInfo.SetLogDetail(stringbuilder.append(steptestlog).toString());
    }
}

