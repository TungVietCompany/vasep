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
   // public static  final  String URL_SUCCESS="http://103.237.147.54/rest_vasep/rest/appota_success";
    //public static  final  String URL_FAIL="http://103.237.147.54/rest_vasep/rest/appota_fail";

    public static  final  String URL_SUCCESS="http://113.23.69.61:81/rest_vasep/rest/appota_success";
    public static  final  String URL_FAIL="http://113.23.69.61:81/rest_vasep/rest/appota_fail";

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
