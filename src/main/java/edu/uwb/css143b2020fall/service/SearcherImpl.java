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
                // get the location index for each word
                Map<Integer, List<List<Integer>>> locIdx = getWordLoc(sepPhrase, commonDocs, index);
//                List<List<Integer>> wordLocLists = new ArrayList<>();
//                for (int i = 0; i < commonDocs.size(); i++) {
//                    int docIdx = commonDocs.get(i);
//                    for (String word : sepPhrase) {
//                        List<List<Integer>> getCommonDocList = index.get(word);
//                        for (int docList = 0; docList < getCommonDocList.size(); docList++) {
//                            if (docList == docIdx) {
//                                wordLocLists.add(getCommonDocList.get(docList));
//                            }
//                        }
//                    }
//                }
//                // compute location lists
                for (Integer docID : locIdx.keySet()) {
                    List<List<Integer>> values = locIdx.get(docID);
                    for (int i = 0; i < values.size(); i++) {
                        List<Integer> oneValueList = values.get(i);
                        for (int j = 0; j < oneValueList.size(); j++) {
                            oneValueList.set(j, oneValueList.get(j) - i);
                        }
                    }
                }
//                for (int listIdx = 0; listIdx < wordLocLists.size(); listIdx++) {
//                    List<Integer> oneList = wordLocLists.get(listIdx);
//                    for (int idx = 0; idx < oneList.size(); idx++) {
//                        oneList.set(idx, oneList.get(idx) - listIdx);
//                    }
//                }
//                // find common indexes
//                List<Integer> aList = wordLocLists.get(0);
//                for (int l = 1; l < wordLocLists.size(); l++) {
//                    if (aList.equals(wordLocLists.get(l))) {
//                        result.add(commonDocs.get(0));
//                    }
//                }
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

    private Map<Integer, List<List<Integer>>> getWordLoc(String[] searchPhrase, List<Integer> commonDocs, Map<String, List<List<Integer>>> index) {
        Map<Integer, List<List<Integer>>> wordLoc = new HashMap<>();
        List<List<Integer>> indexValues;
        List<List<Integer>> commonDocValues = new ArrayList<>();
        List<List<Integer>> docValues;
        for (String wordInPhrase : searchPhrase) {
            docValues = new ArrayList<>();
            indexValues = index.get(wordInPhrase);
            for (int i = 0; i < commonDocs.size(); i++) {
                Integer docID = commonDocs.get(i);
                docValues.add(indexValues.get(docID));
                commonDocValues.addAll(docValues);
                wordLoc.put(docID, commonDocValues);
            }
        }
        return wordLoc;
    }

 
}
