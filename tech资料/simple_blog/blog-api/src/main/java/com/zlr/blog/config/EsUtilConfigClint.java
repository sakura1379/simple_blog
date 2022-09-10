//package com.zlr.blog.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
///**
// * @author Zenglr
// * @program: simple_blog
// * @packagename: com.zlr.blog.config
// * @Description
// * @create 2022-08-19-下午8:33
// */
//@Configuration
//public class EsUtilConfigClint {
//    /**
//     * 客户端
//     * @return
//     * @throws IOException
//     */
//    public ElasticsearchClient configClint() throws IOException {
//        // Create the low-level client
//        RestClient restClient = RestClient.builder(
//                new HttpHost("127.0.0.1", 9200)).build();
//
//        // Create the transport with a Jackson mapper
//        ElasticsearchTransport transport = new RestClientTransport(
//                restClient, new JacksonJsonpMapper());
//
//        // 客户端
//        ElasticsearchClient client = new ElasticsearchClient(transport);
//
//        return client;
//    }
//}
