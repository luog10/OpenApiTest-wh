package TestCase.Order;

import ApiTestUtility.ApiBizResponseData;
import ApiTestUtility.ApiRequestData;
import ApiTestUtility.ApiTestHelper;
import com.test.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

public class TestOrder {
    @Test
    public  void  TestMockData(){
        String str = RandomHelper.GenerateCheckCode(5);
        String str1 = RandomHelper.GenerateCheckCodeNum(5);
        String Str2 = RandomHelper.GetRandomCodeOfCHS(5);
        String Str4 = RandomHelper.GetRandomCode(5);
    }
    @Test
    //测试单商品创建订单
    public void TestCreateOrderOfOneProduct(){
        //1.获取测试模块名称、测试用例名称
        ApiTestHelper.TestFunctionName = this.getClass().getName();
        ApiTestHelper.TestCaseName = Thread.currentThread() .getStackTrace()[1].getMethodName();
        //2.设置测试数据
        ApiRequestData apirequestdata  = new ApiRequestData();
        //2.1 测试方法
        apirequestdata.SetMethod("OrderCreate");
        //2.2 接口请求方式
        apirequestdata.SetRequestType(RequestType.Post);
        //2.3 Appid
        apirequestdata.SetAppid("1042");
        //2.4 Appsecret
        apirequestdata.SetAppsecret("36413522cfdc46d395ad41a476a68414");
        //2.5 创建订单业务数据
        String SoNo = CommonTool.GetCurrentTimeSec().replace("-","")
                                                    .replace(" ","")
                                                    .replace(":","");
        apirequestdata.SetBizdata(FileOpHelper.Read("/Order/CreateOrderOfOneProduct")
                                  .replace("#SONO#", SoNo)
                                  .replace("#PaySerialNumber#",SoNo));
        ApiTestHelper.ApiRequestData=apirequestdata;

        //3.执行接口测试
        ApiBizResponseData responseData = ApiTestHelper.ExcuteApiTset();
        //4.断言测试结果
        try {
            Assert.assertEquals(responseData.GetCode(),"0");
            ApiTestHelper.RecordTestResult(TestResultType.Pass, "测试通过");
        }
        catch (AssertionError ex) {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, "测试未通过"+ ex.getMessage());
            Assert.fail();
        }
    }
}
