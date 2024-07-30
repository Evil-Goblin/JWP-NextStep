package org.example.webserver.service.variable;

import lombok.Getter;

@Getter
public enum ContentType {
    ALL_VALUE("*/*"),
    APPLICATION_ATOM_XML_VALUE("application/atom+xml"),
    APPLICATION_CBOR_VALUE("application/cbor"),
    APPLICATION_FORM_URLENCODED_VALUE("application/x-www-form-urlencoded"),
    APPLICATION_GRAPHQL_RESPONSE_VALUE("application/graphql-response+json"),
    APPLICATION_GRAPHQL_VALUE("application/graphql+json"),
    APPLICATION_JSON_UTF8_VALUE("application/json;charset=UTF-8"),
    APPLICATION_JSON_VALUE("application/json"),
    APPLICATION_NDJSON_VALUE("application/x-ndjson"),
    APPLICATION_OCTET_STREAM_VALUE("application/octet-stream"),
    APPLICATION_PDF_VALUE("application/pdf"),
    APPLICATION_PROBLEM_JSON_UTF8_VALUE("application/problem+json;charset=UTF-8"),
    APPLICATION_PROBLEM_JSON_VALUE("application/problem+json"),
    APPLICATION_PROBLEM_XML_VALUE("application/problem+xml"),
    APPLICATION_PROTOBUF_VALUE("application/x-protobuf"),
    APPLICATION_RSS_XML_VALUE("application/rss+xml"),
    APPLICATION_STREAM_JSON_VALUE("application/stream+json"),
    APPLICATION_XHTML_XML_VALUE("application/xhtml+xml"),
    APPLICATION_XML_VALUE("application/xml"),
    APPLICATION_JAVASCRIPT_VALUE("application/javascript"),
    IMAGE_GIF_VALUE("image/gif"),
    IMAGE_JPEG_VALUE("image/jpeg"),
    IMAGE_PNG_VALUE("image/png"),
    MULTIPART_FORM_DATA_VALUE("multipart/form-data"),
    MULTIPART_MIXED_VALUE("multipart/mixed"),
    MULTIPART_RELATED_VALUE("multipart/related"),
    TEXT_EVENT_STREAM_VALUE("text/event-stream"),
    TEXT_HTML_VALUE("text/html"),
    TEXT_MARKDOWN_VALUE("text/markdown"),
    TEXT_PLAIN_VALUE("text/plain"),
    TEXT_XML_VALUE("text/xml"),
    TEXT_CSS_VALUE("text/css");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public static ContentType from(String viewName) {
        String fileType = viewName.substring(viewName.lastIndexOf(".") + 1).toLowerCase();

        return switch (fileType) {
            case "html" -> TEXT_HTML_VALUE;
            case "css" -> TEXT_CSS_VALUE;
            case "js" -> APPLICATION_JAVASCRIPT_VALUE;
            default -> TEXT_PLAIN_VALUE;
        };
    }

    public static ContentType of(String contentType) {
        return switch (contentType) {
            case "*/*" -> ALL_VALUE;
            case "application/atom+xml" -> APPLICATION_ATOM_XML_VALUE;
            case "application/cbor" -> APPLICATION_CBOR_VALUE;
            case "application/x-www-form-urlencoded" -> APPLICATION_FORM_URLENCODED_VALUE;
            case "application/graphql-response+json" -> APPLICATION_GRAPHQL_RESPONSE_VALUE;
            case "application/graphql+json" -> APPLICATION_GRAPHQL_VALUE;
            case "application/json;charset=UTF-8" -> APPLICATION_JSON_UTF8_VALUE;
            case "application/json" -> APPLICATION_JSON_VALUE;
            case "application/x-ndjson" -> APPLICATION_NDJSON_VALUE;
            case "application/octet-stream" -> APPLICATION_OCTET_STREAM_VALUE;
            case "application/pdf" -> APPLICATION_PDF_VALUE;
            case "application/problem+json;charset=UTF-8" -> APPLICATION_PROBLEM_JSON_UTF8_VALUE;
            case "application/problem+json" -> APPLICATION_PROBLEM_JSON_VALUE;
            case "application/problem+xml" -> APPLICATION_PROBLEM_XML_VALUE;
            case "application/x-protobuf" -> APPLICATION_PROTOBUF_VALUE;
            case "application/rss+xml" -> APPLICATION_RSS_XML_VALUE;
            case "application/stream+json" -> APPLICATION_STREAM_JSON_VALUE;
            case "application/xhtml+xml" -> APPLICATION_XHTML_XML_VALUE;
            case "application/xml" -> APPLICATION_XML_VALUE;
            case "application/javascript" -> APPLICATION_JAVASCRIPT_VALUE;
            case "image/gif" -> IMAGE_GIF_VALUE;
            case "image/jpeg" -> IMAGE_JPEG_VALUE;
            case "image/png" -> IMAGE_PNG_VALUE;
            case "multipart/form-data" -> MULTIPART_FORM_DATA_VALUE;
            case "multipart/mixed" -> MULTIPART_MIXED_VALUE;
            case "multipart/related" -> MULTIPART_RELATED_VALUE;
            case "text/event-stream" -> TEXT_EVENT_STREAM_VALUE;
            case "text/html" -> TEXT_HTML_VALUE;
            case "text/markdown" -> TEXT_MARKDOWN_VALUE;
            case "text/xml" -> TEXT_XML_VALUE;
            case "text/css" -> TEXT_CSS_VALUE;
            default -> TEXT_PLAIN_VALUE;
        };
    }
}
