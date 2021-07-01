package com.louisblogs.louismall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.louisblogs.louismall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2021000117681955";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC0T5J/kiPY6ci2K2+At1+o9cIFA+Y8ullpOgfSqcs5SIW8ut+PTuMS2hVO74s5RgOXh7zNbesPbLSQULOqIrRMRmmj1TS4LJNVxsUut04UudiH3x0w4WgRUTbia57sKfZGyRvZ550ARn5NxHFRwhfmH6RRu4ri0ssEpIq+f5wf68eiJ4RoE1ZfYAhD/txBLbpZ+WYZk3yreo8rQF298EBX5OX2yyiRybPmOOJZvPQ/37mGc2qWvARtWS4sr5uxJVnSjE7d/CJgBU8T9kJhwEVV0dWElEb8oI5idVeXMhapB4Ynq6Zgk87efB+yMCH+kT/bt0hB+NEZaMrUvyRoheiHAgMBAAECggEBALQvLFOlY4M5JGS/BR8YoFSEuk0ut16KTh00a2reWII28cZG7G8WQx6spkyKXpNuXtqHP2n+ZL62P75Tlyb2pTvvxVN+FL/Tc6xtBNEJRtK5YSNuF4qoRcnAYdtj9p1D1aKpB9b6eE81IrRuEVvFXQKPLjKq+kBuq4hJMYjqlsbhtthVsRiBom/Kb95ASje2IlzbmtXIjY+kqAMC/2MgYkMrNsR/7ut4XfxWXn49gsxrf3zPKMdvaq+QRa2wlvsYrVMkxS/jKwVnNXNw64IkMdp7nBjwLIZBSXYa/jRHNaZfGYY6CFO1d+GAtiIoGmzMJ573Junt9ATzMF3YvnGnaSkCgYEA2nvMNpJWUYV94eIuOyRfbbcNDSQL15YXXE9sCQoCS1B7jSoD3SvsYqs7TisvwLWO1MUhqZkeVXhjowsV2gvTBcqdfqgg7n4BNmvVoaIYWuYdI37yLPvRBEHlKySnQ+BDM70DtHCYSdLA9XXLlMlTTveat94WSbcqkTJwQkEl1b0CgYEA00XCSYHHh8F3/A+wCOoNBpaeN4hEDNtuyz3ykZ6Da+nBqGMOAmihhENBrVJcffB2jXerKR5TPL/nQmDZmzLgjZw7yWkA1pcLsCMa38ms+VSlecQyU+SOwwYsFIsRuJGT3wEK0sPC84MtkbW2pA5bhqxR++azqDIbjjkkSWAfMZMCgYBzWB40xUcK3MXbhdRQZuB2hMhAIsKZu2Rz2H45u24AounHWeIWVsUCRlIV75jTTUatr7EY33xzNQ6tGgIcTlfG2/QP1OUl46HyQDXVkkT3YDsL7U4fT4ZGkB9TipM/s1rnKqeVEEhl2P0D2X9z+mGf8/pbjcUIf8naQ92eLNSzpQKBgF49IqHJOuhqOwv7L2l6hR7oNmDM7pXRl4Ue98FOtI6BMXnb3VbjtxAZNAWM1Gb0yNzngTiRJ03Ux+IK82XP9uwTwXNg3BfcPkFuF0G1YuvAfyNmiZB0Rd3mNelf8yKiBRSXEW3cFmyqmbwCExrwsJDM8rDrQj4HQ9VX1cDJGIDzAoGASUEGoVav26N84nxQbh4x8Mb6OdJazdrWoeYe1tgu7sL14RFfM3cSjHiAiUOX1QRhkphfpyCXDbl+fP3/WrMab/U6DZHbXZWci5WjLWQba1iBHh+mz+KyfQFZYlbpWQ86BZk3IKtQ9az2/QqAwRDW326QmiP7HRIKCszy7RniVS0=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAirrr5fXYKFWO9UzWS8F48EP2rAuoV+B/tF72+24pYU/lNXaGR7S/B1vIRlVE5P3NVOaPXFcuhq0CPFLa225sLW7q7DL08xbvRXy7byWwqognI16IHn2Xyayz3gFKtlQbjxXr7GUe2MiA4OVBcgeSN0yF5IjXRBFLuygXS+/uff2y1kuSAL6bdJ1HlA6fkRR123uHvPzXIPlBnrDIo+Qp8SugDZ6EuaSV1eC97BJiiUcInMo6L9GPXGmABtxkjTQqY0qsdbe88pa44MwKG+ec02S10iMz222oNT1zHq95dOq3JyTKSBqZj8Px7bEciCKkpANXSG8EfRW3DgXWo3kRJwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = "http://e2495j3537.zicp.vip/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://member.louismall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    private String timeout = "30m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
		        + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
