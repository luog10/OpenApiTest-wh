package TestCase.Common;

import ApiTestUtility.ApiBizResponseData;
import ApiTestUtility.ApiRequestData;
import ApiTestUtility.ApiTestHelper;
import com.test.utility.FileOpHelper;
import com.test.utility.RequestType;
import com.test.utility.TestResultType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCommonData {
    @Test
    //测试获取支付方式字典
    public  void  TestGetPayTypeDic(){
        //1.获取测试模块名称、测试用例名称
        ApiTestHelper.TestFunctionName = this.getClass().getName();
        ApiTestHelper.TestCaseName = Thread.currentThread() .getStackTrace()[1].getMethodName();
        //2.设置测试数据
        ApiRequestData apirequestdata  = new ApiRequestData();
        //2.1 测试方法
        apirequestdata.SetMethod("PayTypeDic");
        //2.2 接口请求方式
        apirequestdata.SetRequestType(RequestType.Get);
        //2.3 Appid
        apirequestdata.SetAppid("1014");
        //2.4 Appsecret
        apirequestdata.SetAppsecret("999badd4d7764846a9fea4777bc8c66f");
        ApiTestHelper.ApiRequestData=apirequestdata;
        //3.执行接口测试
        ApiBizResponseData responseData = ApiTestHelper.ExcuteApiTset();
        //4.断言测试结果
        try {
            Assert.assertEquals(responseData.GetCode(),"0");
            ApiTestHelper.RecordTestResult(TestResultType.Pass, "测试通过");
        }
        catch (Exception ex)
        {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, "测试未通过"+ ex.getMessage());
        }
    }
}
