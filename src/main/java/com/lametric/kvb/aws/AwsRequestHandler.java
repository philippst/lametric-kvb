package com.lametric.kvb.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.lametric.kvb.KvbAppEndpoint;
import com.lametric.kvb.KvbAppRequest;
import com.lametric.kvb.exception.KvbAppConfigurationException;
import com.lametric.kvb.utils.LametricApp;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AwsRequestHandler implements RequestHandler<GatewayRequest,GatewayResponse> {

    private static final Logger logger = Logger.getLogger(AwsRequestHandler.class);

    @Override
    public GatewayResponse handleRequest(GatewayRequest request, Context context) {
        LametricApp lametricApp = null;
        logger.debug(request);

        KvbAppRequest kvbAppRequest = new KvbAppRequest(request.getQueryStringParameters());
        logger.info(kvbAppRequest);

        Integer statuscode = 200;
        KvbAppEndpoint kvbEndpoint = new KvbAppEndpoint();
        try {
            lametricApp = kvbEndpoint.getResponse(kvbAppRequest);
        } catch (IOException e) {
            logger.error(e);
            statuscode = 400;
        } catch (KvbAppConfigurationException e) {
            logger.warn(e);
            statuscode = 400;
        }

        Gson gson = new Gson();
        String responseBody = gson.toJson(lametricApp);

        GatewayResponse gatewayResponse = new GatewayResponse(
                responseBody,null,statuscode,false);
        logger.info(gatewayResponse);
        return gatewayResponse;
    }
}
