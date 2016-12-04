package com.lametric;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.lametric.kvb.KvbAppEndpoint;
import com.lametric.kvb.KvbAppRequest;
import com.lametric.utils.LametricApp;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args){
        KvbAppRequest request = new KvbAppRequest();
        lambdaHandler(request, null);
    }

    @SuppressWarnings("unused")
    public static LametricApp lambdaHandler(KvbAppRequest kvbAppRequest, Context context) {
        logger.debug("Received request ...");
        KvbAppEndpoint kvbEndpoint = new KvbAppEndpoint();
        LametricApp lametricApp = null;
        try {
            lametricApp = kvbEndpoint.getResponse(kvbAppRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String responseBody = gson.toJson(lametricApp);

        System.out.println(responseBody);

        logger.debug("Finish processing ...");
        return lametricApp;
    }
}
