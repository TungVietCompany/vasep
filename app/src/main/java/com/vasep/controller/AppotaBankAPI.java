package com.vasep.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;


import com.vasep.api.ServiceGeneratorAppota;
import com.vasep.api.ServiceInterface;
import com.vasep.api.ServiceInterfaceAppota;
import com.vasep.models.BankingAppotaAPI;
import com.vasep.models.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;

/**
 * Created by Administrator on 23/12/2016.
 */

public class AppotaBankAPI {
    public static final String BANK_PAYMENT_URL = "https://api.appotapay.com/v1/sandbox/services/ibanking?api_key=A180290-E5I9CK-A695AA44C9449619";
    public static  final  String URL_SUCCESS="http://103.237.147.54/rest_vasep/rest/appota_success";
    public static  final  String URL_FAIL="http://103.237.147.54/rest_vasep/rest/appota_fail";
    /*public AppotaBankAPI() {
    }

    public  String makeBankPayment(String developer_trans_id,int amount,int bank_id,String success_url, String error_url,
                                  String state, String target,String language_code) throws Exception {

        String url = BANK_PAYMENT_URL+"&lang="+language_code;;
        URL obj = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
        httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
        httpConnection.setRequestMethod("POST");
        String urlParameters = "developer_trans_id=%developer_trans_id%&amount=%amount%&bank_id=%bank_id%"
                +((success_url == null ||success_url.isEmpty())?"":"&success_url=%success_url%")
                +((error_url == null ||error_url.isEmpty())?"":"&error_url=%error_url%")
                +((state == null ||state.isEmpty())?"":"&state=%state%")
                +((target == null ||target.isEmpty())?"":"&target=%target%");
        urlParameters = urlParameters.replaceFirst("%amount%", String.valueOf(amount));
        urlParameters = urlParameters.replaceFirst("%bank_id%", String.valueOf(bank_id));
        if(success_url != null && !success_url.isEmpty()){
            //success_url is optional
            urlParameters = urlParameters.replaceFirst("%success_url%", success_url);
        }
        if(error_url != null && !error_url.isEmpty()){
            //error_url is optional
            urlParameters = urlParameters.replaceFirst("%error_url%", error_url);
        }
        if(target != null && !target.isEmpty()){
            //target is optional
            urlParameters = urlParameters.replaceFirst("%state%", state);
        }
        if(target != null && !target.isEmpty()){
            //target is optional
            urlParameters = urlParameters.replaceFirst("%target%", target);
        }

        httpConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        return onReceiveResponse(url, httpConnection, urlParameters);
    }

    private String  onReceiveResponse(String url, HttpURLConnection httpConnection, String urlParameters)
            throws IOException, UnsupportedEncodingException {
        int responseCode = httpConnection.getResponseCode();
        System.out.println("Url : " + url);
        System.out.println("Parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String json = response.toString();
        return  json;
    }*/

    private ServiceInterfaceAppota service;
    String language;
    Context context;
    public AppotaBankAPI(Context ct,String language){
        this.context=ct;
        this.language=language;
        service =  ServiceGeneratorAppota.GetInstance();
    }

    public BankingAppotaAPI CallAPI(int select_type,String developer_trans_id, String amount,String state){
        Call<BankingAppotaAPI> contact;
        if(select_type==1) {
            if (language.equals("vi")) {
                contact = service.callAPI_VI(developer_trans_id, amount, state, URL_SUCCESS, URL_FAIL);
            } else {
                contact = service.callAPI_EN(developer_trans_id, amount, state, URL_SUCCESS, URL_FAIL);
            }
        }else{
            if (language.equals("vi")) {
                contact = service.visaCallAPI_VI(developer_trans_id, amount, state, URL_SUCCESS, URL_FAIL);
            } else {
                contact = service.visacallAPI_EN(developer_trans_id, amount, state, URL_SUCCESS, URL_FAIL);
            }
        }
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            BankingAppotaAPI str = contact.execute().body();
            return str;
        } catch (Exception ex) {
            String err= ex.getMessage();
        }
        return null;
    }

    public void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {

                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
