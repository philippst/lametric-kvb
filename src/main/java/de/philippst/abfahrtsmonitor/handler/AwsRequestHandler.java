package de.philippst.abfahrtsmonitor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.lametric.IndicatorApp;
import de.philippst.abfahrtsmonitor.KvbAppEndpoint;
import de.philippst.abfahrtsmonitor.KvbAppRequest;
import de.philippst.abfahrtsmonitor.exception.KvbAppConfigurationException;
import com.lametric.frame.FrameText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AwsRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

    private static Logger logger = LoggerFactory.getLogger(AwsRequestHandler.class);

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        IndicatorApp lametricApp = new IndicatorApp();
        logger.debug("GatewayRequest: {}",request);

        KvbAppRequest kvbAppRequest = new KvbAppRequest(request.getQueryStringParameters());
        logger.info("KvbAppRequest: {}",kvbAppRequest);

        Integer statuscode = 200;
        KvbAppEndpoint kvbEndpoint = new KvbAppEndpoint();
        try {
            lametricApp = kvbEndpoint.getResponse(kvbAppRequest);
        } catch (KvbAppConfigurationException e) {
            statuscode = 400;
            lametricApp.addFrame(new FrameText(e.getMessage()));
        }

        Gson gson = new Gson();
        String responseBody = gson.toJson(lametricApp);

        APIGatewayProxyResponseEvent gatewayProxyResponseEvent = new APIGatewayProxyResponseEvent()
                .withBody(responseBody)
                .withStatusCode(statuscode);

        logger.info("GatewayProxyResponseEvent: {}",gatewayProxyResponseEvent);
        return gatewayProxyResponseEvent;
    }
}
