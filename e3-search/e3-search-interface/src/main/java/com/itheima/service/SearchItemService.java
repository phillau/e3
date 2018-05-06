package com.itheima.service;

import com.itheima.pojo.SearchItem;
import java.util.List;

public interface SearchItemService {
    void importIndex() throws Exception;
    List<SearchItem> findItemByKeyword(String keyword, Integer page, Integer row) throws Exception;
}
