package com.atguigu.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.search.config.GulimallElasticSearchConfig;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @ToString
    @Data
    static class Account {
        private int account_number;

        private String firstname;

        private String address;

        private int balance;

        private String gender;

        private String city;

        private String employer;

        private String state;

        private int age;

        private String email;

        private String lastname;
    }

    @Test
    public void searchData() throws IOException {
        //1.??????????????????
        SearchRequest searchRequest = new SearchRequest();
        //???????????????????????????
        searchRequest.indices("bank");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //??????????????????
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
        System.out.println("address:" + searchSourceBuilder.toString());
        //????????????????????????????????????
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        searchSourceBuilder.aggregation(ageAgg);
        // ??????????????????
        AvgAggregationBuilder blanceAvg = AggregationBuilders.avg("blanceAgg").field("balance");
        searchSourceBuilder.aggregation(blanceAvg);
        //???
        System.out.println("???????????????" + searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);

        //2.????????????
        SearchResponse searchResponse = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        //3.?????????????????????searchResponse
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            String string = hit.getSourceAsString();
            Account account = JSON.parseObject(string, Account.class);
            System.out.println("account:" + account);
        }
        System.out.println(searchResponse.toString());
        Map map = JSON.parseObject(searchResponse.toString(), Map.class);
        //3.1???????????????????????????
        System.out.println("hits???" + map.get("hits"));
        //3.2 ????????????????????????????????????
        Aggregations aggregations = searchResponse.getAggregations();
        Terms ageAgg1 = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("?????????" + keyAsString);
        }
        Avg blanceAgg = aggregations.get("blanceAgg");
        System.out.println("???????????????" + blanceAgg.getValue());

    }

    @Test
    public void contextLoads() {
        System.out.println(client);
    }

}
