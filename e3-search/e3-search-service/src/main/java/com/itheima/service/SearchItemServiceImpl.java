package com.itheima.service;

import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void importIndex() throws Exception{
       List<SearchItem> searchItemList = itemMapper.findSearchItem();
        for (SearchItem searchItem:searchItemList
             ) {
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.addField("id",searchItem.getId());
            solrInputDocument.addField("item_title",searchItem.getTitle());
            solrInputDocument.addField("item_sell_point",searchItem.getSell_point());
            solrInputDocument.addField("item_price",searchItem.getPrice());
            solrInputDocument.addField("item_image",searchItem.getImage());
            solrInputDocument.addField("item_category_name",searchItem.getCategory_name());
            solrServer.add(solrInputDocument);
        }
        solrServer.commit();
    }

    @Override
    public List<SearchItem> findItemByKeyword(String keyword, Integer page, Integer row) throws Exception {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(keyword);
        solrQuery.set("df","item_keywords");
        solrQuery.setStart((page - 1) * row);
        solrQuery.setRows(row);
        //设置高亮显示
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style='color:red'>");
        solrQuery.setHighlightSimplePost("</em>");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        List<SearchItem> searchItemList = new ArrayList<SearchItem>();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument solrDocument:solrDocumentList
             ) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId(Long.parseLong(solrDocument.get("id")+""));
            searchItem.setSell_point(solrDocument.get("item_sell_point")+"");
            searchItem.setPrice(Long.parseLong(solrDocument.get("item_price")+""));
            searchItem.setImage(solrDocument.get("item_image")+"");
            searchItem.setCategory_name(solrDocument.get("item_category_name")+"");
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            if(list!=null&&list.size()>0){
                searchItem.setTitle(list.get(0));
            }else {
                searchItem.setTitle(solrDocument.get("item_title")+"");
            }
            searchItemList.add(searchItem);
        }
        return searchItemList;
    }
}
