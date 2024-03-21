package com.team.leaf.shopping.search.util;

import com.team.leaf.shopping.search.dto.UtilInitDto;
import com.team.leaf.shopping.search.entity.AutoComplete;
import com.team.leaf.shopping.search.repository.AutoCompleteRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ToString
class Node {
    Map<String, Node> childedNode = new HashMap<>();
    String word;
    boolean isContainWord;
    int frequency;

    public Node(String word) {
        this.word = word;
        this.frequency = 0;
    }

    public Node(AutoComplete autoComplete) {
        word = autoComplete.getWord();
        frequency = autoComplete.getFrequency();
    }
}

@Component
@RequiredArgsConstructor
public class Trie {

    Node rootNode = new Node("Empty");
    private final AutoCompleteRepository autoCompleteRepository;

    @Transactional
    public Node insert(String str) {
        Node node;
        Node currentNode;

        if(isHangul(str)) {
            node = addTrieNode(str);
            currentNode = createOrLoadAutoComplete(node.word);
        } else {
            node = addTrieNode(str);
            currentNode = createOrLoadAutoComplete(node.word);
        }

        node.isContainWord = true;
        node.frequency = currentNode.frequency;

        return node;
    }

    public static boolean isHangul(String str) {
        return str.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    }

    public Node addTrieNode_Hangul(String word) {
        Node node = this.rootNode;
        List<Map<String, Integer>> separatedWord = GraphemeSeparation.separation(word);

        String completeWord = "";
        for(int i = 0; i < separatedWord.size(); i++) {
            List<String> resultWord = GraphemeSeparation.absorption(separatedWord.get(i));

            for(int j = 0; j < resultWord.size(); j++) {
                String targetWord = resultWord.get(j);

                completeWord += targetWord;
                String finalCompleteWord = completeWord;

                node = node.childedNode.computeIfAbsent(completeWord, key -> new Node(finalCompleteWord));
            }
        }

        return node;
    }

    public Node addTrieNode(String str) {
        Node node = this.rootNode;

        for(int i = 0; i < str.length(); i++) {
            int finalI = i;

            node = node.childedNode.computeIfAbsent(str.substring(i , i + 1), key -> new Node(str.substring(0 , finalI + 1)));
        }

        return node;
    }

    public void initTrie() {
        List<AutoComplete> result = autoCompleteRepository.findAll();

        for(AutoComplete autoComplete : result) {
            Node node = this.rootNode;
            String str = autoComplete.getWord();

            for(int i = 0; i < str.length(); i++) {
                int finalI = i;

                node = node.childedNode.computeIfAbsent(str.substring(i , i + 1), key -> new Node(str.substring(0 , finalI + 1)));
            }

            node.isContainWord = true;
            node.frequency = autoComplete.getFrequency();
        }
    }

    protected Node createOrLoadAutoComplete(String word) {
        AutoComplete autoComplete = autoCompleteRepository.findAutoCompleteByWord(word)
                .orElseGet(() -> autoCompleteRepository.save(AutoComplete.createAutoComplete(word)));

        autoComplete.increaseFrequency(1);

        return new Node(autoComplete);
    }

    public List<UtilInitDto> searchComplete(String searchWord) {
        List<UtilInitDto> result = new ArrayList<>();
        Node node = searchSearchWord(searchWord);
        if(node == null) {
            return result;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while(!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if(currentNode.isContainWord) {
                result.add(new UtilInitDto(currentNode.word, currentNode.frequency));
            }

            for(String str : currentNode.childedNode.keySet()) {
                queue.offer(currentNode.childedNode.get(str));
            }
        }

        Collections.sort(result, new Comparator<UtilInitDto>() {
            @Override
            public int compare(UtilInitDto o1, UtilInitDto o2) {
                return o2.getFrequency() - o1.getFrequency();
            }
        });

        if(result.size() > 10) {
            result.subList(10, result.size()).clear();
        }
        System.out.println(rootNode);

        return result;
    }

    private Node searchSearchWord(String str) {
        Node node = this.rootNode;

        for(int i = 0; i < str.length(); i++) {
            node = node.childedNode.getOrDefault(str.substring(i , i + 1), null);

            if(node == null) {
                return null;
            }
        }

        return node;
    }

}
