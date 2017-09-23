package com.lametric.kvb;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.lametric.kvb.aws.AwsRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Application{

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args){
        AwsRequestHandler awsAwsRequestHandler = new AwsRequestHandler();

        Map<String,String> queryStringParam = new HashMap<>();
        queryStringParam.put("stationId","255");
        queryStringParam.put("minTime","4");
        queryStringParam.put("maxTime","120");
        queryStringParam.put("maxTime","120");
        queryStringParam.put("limit","5");
        queryStringParam.put("frames","disruption, allDepartures, singleDeparture");

        APIGatewayProxyRequestEvent gatewayRequest = new APIGatewayProxyRequestEvent();
        gatewayRequest.setQueryStringParameters(queryStringParam);

        awsAwsRequestHandler.handleRequest(gatewayRequest, null);
        return;
    }
}
