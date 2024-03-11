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
    Map<Character, Node> childedNode = new HashMap<>();
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
        Node node = this.rootNode;

        for(int i = 0; i < str.length(); i++) {
            int finalI = i;

            node = node.childedNode.computeIfAbsent(str.charAt(i), key -> new Node(str.substring(0 , finalI + 1)));
        }

        Node currentNode = createOrLoadAutoComplete(node.word);
        node.isContainWord = true;
        node.frequency = currentNode.frequency;

        return node;
    }

    public void initTrie() {
        List<AutoComplete> result = autoCompleteRepository.findAll();

        for(AutoComplete autoComplete : result) {
            Node node = this.rootNode;
            String str = autoComplete.getWord();

            for(int i = 0; i < str.length(); i++) {
                int finalI = i;

                node = node.childedNode.computeIfAbsent(str.charAt(i), key -> new Node(str.substring(0 , finalI + 1)));
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

            for(char ch : currentNode.childedNode.keySet()) {
                queue.offer(currentNode.childedNode.get(ch));
            }
        }

        return result;
    }

    private Node searchSearchWord(String str) {
        Node node = this.rootNode;

        for(int i = 0; i < str.length(); i++) {
            node = node.childedNode.getOrDefault(str.charAt(i), null);

            if(node == null) {
                return null;
            }
        }

        return node;
    }

}
