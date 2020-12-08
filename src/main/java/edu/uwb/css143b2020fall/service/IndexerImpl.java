package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexerImpl implements Indexer {
    public Map<String, List<List<Integer>>> index(List<String> docs) {
        Map<String, List<List<Integer>>> indexes = new HashMap<>();
        // add your code
        //List<List<Integer>> docPos = new ArrayList<>();
        List<Integer> wordPos;
        String keyWord;
        Map<String, List> oneDoc = new HashMap<>();
        // gets the document
        for (int docsIndex = 0; docsIndex < docs.size(); docsIndex++) {
            String[] getWords = docs.get(docsIndex).trim().split("\\s");
            // gets each word in the document and builds index for current document
            for (int wordIndex = 0; wordIndex < getWords.length; wordIndex++) {
                keyWord = getWords[wordIndex];
                wordPos = oneDoc.get(keyWord);
                if (wordPos == null) {
                    wordPos = new ArrayList<>();
                }
                wordPos.add(wordIndex);
                oneDoc.put(keyWord, wordPos);
            }
            //docPos.add(wordPos);
            //oneDoc.put(keyWord, docPos);
            //indexes.putAll(oneDoc);
        }
        return indexes;
    }
}