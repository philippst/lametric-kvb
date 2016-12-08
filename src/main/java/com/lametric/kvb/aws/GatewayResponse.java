package com.lametric.kvb.aws;

import com.google.common.base.MoreObjects;

import java.util.Map;

public class GatewayResponse {

    private String body;
    private Map<String, String> headers;
    private int statusCode;
    private boolean base64Encoded;

    public GatewayResponse(){}

    public GatewayResponse(String body, Map<String, String> headers,
                           int statusCode, boolean base64Encoded) {
        this.headers = headers;
        this.statusCode = statusCode;
        this.body = body;
        this.base64Encoded = base64Encoded;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isBase64Encoded() {
        return base64Encoded;
    }

    public void setBase64Encoded(boolean base64Encoded) {
        this.base64Encoded = base64Encoded;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    // APIGW expects the property to be called "isBase64Encoded"
    public boolean isIsBase64Encoded() {
        return base64Encoded;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("body", body)
                .add("headers", headers)
                .add("statusCode", statusCode)
                .add("base64Encoded", base64Encoded)
                .toString();
    }
}