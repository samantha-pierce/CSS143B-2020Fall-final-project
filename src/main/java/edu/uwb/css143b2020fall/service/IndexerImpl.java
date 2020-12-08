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
        List<List<Integer>> docPos;
        List<Integer> wordPos;
        String keyWord;
        Map<String, List<Integer>> oneDoc;
        // gets the document
        for (int docsIndex = 0; docsIndex < docs.size(); docsIndex++) {
            if (docs.get(docsIndex).isEmpty()) {
                return indexes;
            }
            String[] getWords = docs.get(docsIndex).trim().split("\\s+");
            oneDoc = new HashMap<>();
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
            // merges index of current document with the main indexer
            for (String key : oneDoc.keySet()) {
                docPos = indexes.get(key);
                if (docPos == null) {
                    docPos = new ArrayList<>();
                    for (int i = 0; i < docs.size(); i++) {
                        docPos.add(new ArrayList<>());
                    }
                }
                docPos.set(docsIndex, oneDoc.get(key));
                indexes.put(key, docPos);
            }
        }
        return indexes;
    }
}