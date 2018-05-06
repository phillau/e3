package com.sinosoft.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrJTest {
    @Test
    public void addDocument() throws IOException, SolrServerException {
        // 第一步：把solrJ的jar包添加到工程中
        // 第二步：创建一个SolrServer，使用HttpSolrServer创建对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.203.152:8080/solr");
        // 第三步：创建一个文档对象SolrInputDocument对象
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        // 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义
        solrInputDocument.addField("id","01");
        solrInputDocument.addField("item_title","mix手机");
        solrInputDocument.addField("item_sell_point","质量非常好");
        solrInputDocument.addField("item_price","111");
        // 第五步：把文档添加到索引库中
        solrServer.add(solrInputDocument);
        // 第六步：提交
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.203.152:8080/solr");
        solrServer.deleteById("01");
        solrServer.commit();
    }

    @Test
    public void queryDocument() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.203.152:8080/solr");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        long numFound = solrDocumentList.getNumFound();
        System.out.println("查询到的总记录数为："+numFound);
        for (SolrDocument solrDocument:solrDocumentList
             ) {
            System.out.println(solrDocument.get("item_title"));
        }
    }

    @Test
    public void queryDocumentWithHighLighting() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.203.152:8080/solr");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("mix");
        solrQuery.set("df","item_keywords");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        long numFound = solrDocumentList.getNumFound();
        System.out.println("查询到的总记录数为："+numFound);
        for (SolrDocument solrDocument:solrDocumentList) {
            String item_title = "";
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            if (list!=null&&list.size()>0) {
                item_title = list.get(0);
            }else {
                item_title = (String) solrDocument.get("item_title");
            }
            System.out.println("item_title="+item_title);
            System.out.println("item_price="+solrDocument.get("item_price"));
        }
    }
}
