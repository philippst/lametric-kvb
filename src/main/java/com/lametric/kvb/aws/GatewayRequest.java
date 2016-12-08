package com.lametric.kvb.aws;

import com.google.common.base.MoreObjects;

import java.util.Collections;
import java.util.Map;

public class GatewayRequest {

    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, String> headers = Collections.emptyMap();
    private Map<String, String> queryStringParameters = Collections.emptyMap();
    private Map<String, String> pathParameters = Collections.emptyMap();
    private Map<String, String> stageVariables = Collections.emptyMap();
    private GatewayRequestContext requestContext;
    private String body;

    public GatewayRequest() {}

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = toUnmodifiableMap(headers);
    }

    public Map<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public void setQueryStringParameters(Map<String, String> queryStringParameters) {
        this.queryStringParameters = toUnmodifiableMap(queryStringParameters);
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = toUnmodifiableMap(pathParameters);
    }

    public Map<String, String> getStageVariables() {
        return stageVariables;
    }

    public void setStageVariables(Map<String, String> stageVariables) {
        this.stageVariables = toUnmodifiableMap(stageVariables);
    }

    public GatewayRequestContext getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(GatewayRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private Map<String, String> toUnmodifiableMap(Map<String, String> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(map);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("resource", resource)
                .add("path", path)
                .add("httpMethod", httpMethod)
                .add("headers", headers)
                .add("queryStringParameters",queryStringParameters)
                .add("pathParameters",pathParameters)
                .add("body",body)
                .toString();
    }

}
