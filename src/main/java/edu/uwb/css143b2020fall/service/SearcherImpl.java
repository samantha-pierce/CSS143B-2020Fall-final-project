package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.*;

/*
DO NOT CHANGE
 */

@Service
public class SearcherImpl implements Searcher {
    public List<Integer> search(String keyPhrase, Map<String, List<List<Integer>>> index) {
        List<Integer> result = new ArrayList<>();
        String[] sepPhrase = keyPhrase.split(" ");
        for (String wordInPhrase : sepPhrase) {
            if (!index.containsKey(wordInPhrase)) {
                return result;
            } else if (sepPhrase.length > 1) {
                List<Integer> commonDocs = getCommonDocs(sepPhrase, index);
            } else {
                List<List<Integer>> value = index.get(wordInPhrase);
                for (int i = 0; i < value.size(); i++) {
                    if (!value.get(i).isEmpty()) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

    private List<Integer> getCommonDocs(String[] wordList, Map<String, List<List<Integer>>> index) {
        // combines list of doc indexes for each word in the search phrase into hashmap
        Map<String, Set<Integer>> getSearchPhrase = new HashMap<>();
        Set<Integer> oneDocList;
        for (int eaWord = 0; eaWord < wordList.length; eaWord++) {
            oneDocList = new HashSet<>();
            List<List<Integer>> value = index.get(wordList[eaWord]);
            for (int docIndex = 0; docIndex < value.size(); docIndex++) {
                if (!value.get(docIndex).isEmpty()) {
                    oneDocList.add(docIndex);
                }
            }
            getSearchPhrase.put(wordList[eaWord], oneDocList);
        }
        // get the largest list
        List<Set<Integer>> searchPhraseValues = new ArrayList<>();
        for (String key : getSearchPhrase.keySet()) {
            searchPhraseValues.add(getSearchPhrase.get(key));
        }
        int largeListSize = 0;
        Set<Integer> largeDoc = new HashSet<>();
        for (Set<Integer> eaDocList : searchPhraseValues) {
            if (eaDocList.size() > largeListSize) {
                largeListSize = eaDocList.size();
                largeDoc = eaDocList;
            }
        }
        // puts common docs into one list
        List<Integer> commonDocs = new ArrayList<>();
        for (Set<Integer> eaDoc : getSearchPhrase.values()) {
            largeDoc.retainAll(eaDoc);
        }
        commonDocs.addAll(largeDoc);
        return commonDocs;
    }
}
