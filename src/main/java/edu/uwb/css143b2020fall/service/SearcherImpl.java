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
            }
        }
        if (sepPhrase.length > 1) {
            List<Integer> commonDocs = getCommonDocs(sepPhrase, index);
            Map<Integer, List<List<Integer>>> locIdx = getWordLoc(sepPhrase, commonDocs, index);
            Map<Integer, List<List<Integer>>> locIdxComputed = computeLocList(locIdx);
            result = findIntersectingLists(sepPhrase, locIdxComputed);
        } else {
            List<List<Integer>> value = index.get(sepPhrase[0]);
            for (int i = 0; i < value.size(); i++) {
                if (!value.get(i).isEmpty()) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    private List<Integer> getCommonDocs(String[] wordList, Map<String, List<List<Integer>>> index) {
        // combines list of doc indexes for each word in the search phrase into hashmap
        Map<String, Set<Integer>> getSearchPhrase = new HashMap<>();
        Set<Integer> oneDocList;
        for (String s : wordList) {
            oneDocList = new HashSet<>();
            List<List<Integer>> value = index.get(s);
            for (int docIndex = 0; docIndex < value.size(); docIndex++) {
                if (!value.get(docIndex).isEmpty()) {
                    oneDocList.add(docIndex);
                }
            }
            getSearchPhrase.put(s, oneDocList);
        }
        // gets the largest list
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
        for (Set<Integer> eaDoc : getSearchPhrase.values()) {
            largeDoc.retainAll(eaDoc);
        }
        return new ArrayList<>(largeDoc);
    }

    private Map<Integer, List<List<Integer>>> getWordLoc(String[] searchPhrase, List<Integer> commonDocs, Map<String, List<List<Integer>>> index) {
        Map<Integer, List<List<Integer>>> wordLoc = new HashMap<>();
        List<List<Integer>> indexValues;
        List<List<Integer>> docList;
        for (Integer docID : commonDocs) {
            docList = new ArrayList<>();
            for (String wordInPhrase : searchPhrase) {
                indexValues = index.get(wordInPhrase);
                List<Integer> wordLocInDoc = indexValues.get(docID);
                docList.add(wordLocInDoc);
            }
            wordLoc.put(docID, docList);
        }
        return wordLoc;
    }

    private List<Integer> findIntersectingLists(String[] searchPhrase, Map<Integer, List<List<Integer>>> locIdx) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> listInter;
        int counter;
        for (Integer docID : locIdx.keySet()) {
            listInter = new HashMap<>();
            List<List<Integer>> locIdxValues = locIdx.get(docID);
            counter = 0;
            for (List<Integer> oneList : locIdxValues) {
                for (Integer key : oneList) {
                    if (!listInter.containsKey(key)) {
                        counter = 0;
                    }
                    listInter.put(key, ++counter);
                }
            }
            for (Integer num : listInter.keySet()) {
                if (listInter.get(num) == searchPhrase.length) {
                    result.add(docID);
                }
            }
        }
        return result;
    }

    private Map<Integer, List<List<Integer>>> computeLocList (Map<Integer, List<List<Integer>>> locList) {
        Map<Integer, List<List<Integer>>> newLocList = new HashMap<>();
        List<Integer> newCalcList;
        List<List<Integer>> compCalcList;
        for (Integer docID : locList.keySet()) {
            List<List<Integer>> values = locList.get(docID);
            compCalcList = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                List<Integer> oneValueList = values.get(i);
                newCalcList = new ArrayList<>();
                for (int j = 0; j < oneValueList.size(); j++) {
                    Integer value = oneValueList.get(j) - i;
                    newCalcList.add(j, value);
                }
                compCalcList.add(newCalcList);
            }
            newLocList.put(docID, compCalcList);
        }
        return newLocList;
    }
}
