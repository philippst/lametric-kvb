package com.lametric.kvb;

import com.lametric.kvb.aws.AwsRequestHandler;
import com.lametric.kvb.aws.GatewayRequest;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Application{

    private static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args){
        AwsRequestHandler awsAwsRequestHandler = new AwsRequestHandler();

        Map<String,String> queryStringParam = new HashMap<>();
        queryStringParam.put("stationId","255");
        queryStringParam.put("minTime","4");
        queryStringParam.put("maxTime","120");
        queryStringParam.put("maxTime","120");
        queryStringParam.put("limit","5");
        queryStringParam.put("frames","disruption, allDepartures, singleDeparture");

        GatewayRequest gatewayRequest = new GatewayRequest();
        gatewayRequest.setQueryStringParameters(queryStringParam);

        awsAwsRequestHandler.handleRequest(gatewayRequest, null);
        return;
    }
}
