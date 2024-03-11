package com.team.leaf.shopping.search.service;

import com.team.leaf.shopping.search.dto.AutoCompleteRequest;
import com.team.leaf.shopping.search.dto.UtilInitDto;
import com.team.leaf.shopping.search.util.Trie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutoCompleteService {

    Trie trie = new Trie();

    @Transactional
    public void addSearchWord(AutoCompleteRequest request) {
        trie.insert(request.getWord());
    }

    public List<UtilInitDto> findSearchComplete(AutoCompleteRequest request) {
        return trie.searchComplete(request.getWord());
    }
}
