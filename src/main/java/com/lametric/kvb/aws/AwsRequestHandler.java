package com.lametric.kvb.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.lametric.kvb.KvbAppEndpoint;
import com.lametric.kvb.KvbAppRequest;
import com.lametric.kvb.exception.KvbAppConfigurationException;
import com.lametric.kvb.utils.LametricApp;
import com.lametric.kvb.utils.LametricAppFrameName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AwsRequestHandler implements RequestHandler<GatewayRequest,GatewayResponse> {

    private static Logger logger = LoggerFactory.getLogger(AwsRequestHandler.class);

    @Override
    public GatewayResponse handleRequest(GatewayRequest request, Context context) {
        LametricApp lametricApp = new LametricApp();
        logger.debug("GatewayRequest: {}",request);

        KvbAppRequest kvbAppRequest = new KvbAppRequest(request.getQueryStringParameters());
        logger.info("KvbAppRequest: {}",kvbAppRequest);

        Integer statuscode = 200;
        KvbAppEndpoint kvbEndpoint = new KvbAppEndpoint();
        try {
            lametricApp = kvbEndpoint.getResponse(kvbAppRequest);
            logger.info("LametricApp Response: {}",lametricApp);
        } catch (IOException e) {
            logger.error("unkown error",e);
            statuscode = 400;
        } catch (KvbAppConfigurationException e) {
            logger.warn("user configuration error",e);
            statuscode = 400;
            lametricApp.addFrame(new LametricAppFrameName(e.getMessage()));
        }

        Gson gson = new Gson();
        String responseBody = gson.toJson(lametricApp);

        GatewayResponse gatewayResponse = new GatewayResponse(
                responseBody,null,statuscode,false);
        logger.info("GatewayResponse: {}",gatewayResponse);
        return gatewayResponse;
    }
}
