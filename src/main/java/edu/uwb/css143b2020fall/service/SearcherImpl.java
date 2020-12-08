package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
DO NOT CHANGE
 */

@Service
public class SearcherImpl implements Searcher {
    public List<Integer> search(String keyPhrase, Map<String, List<List<Integer>>> index) {
        List<Integer> result = new ArrayList<>();
        if (!index.containsKey(keyPhrase)) {
            return result;
        } else {
            List<List<Integer>> value = index.get(keyPhrase);
            for (int i = 0; i < value.size(); i++) {
                if (!value.get(i).isEmpty()) {
                    result.add(i);
                }
            }
        }
        return result;
    }
}