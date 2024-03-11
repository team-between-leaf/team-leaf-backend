package com.team.leaf.shopping.search.util;

import com.team.leaf.shopping.search.dto.UtilInitDto;
import com.team.leaf.shopping.search.entity.AutoComplete;
import com.team.leaf.shopping.search.repository.AutoCompleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

class Node {
    Map<Character, Node> childedNode = new HashMap<>();
    String word;
    boolean isContainWord;
    int frequency;

    public Node(char word) {
        this.word = String.valueOf(word);
        this.isContainWord = false;
        this.frequency = 0;
    }

    public Node(AutoComplete autoComplete) {
        word = autoComplete.getWord();
        frequency = autoComplete.getFrequency();
        isContainWord = true;
    }
}

@Component
@RequiredArgsConstructor
public class Trie {

    Node rootNode = new Node('E');
    private final AutoCompleteRepository autoCompleteRepository;

    @Transactional
    public Node insert(String str) {
        Node node = this.rootNode;

        for(int i = 0; i < str.length(); i++) {
            node = node.childedNode.computeIfAbsent(str.charAt(i), key -> new Node(key));
        }

        Node currentNode = createOrLoadAutoComplete(node.word);
        currentNode.isContainWord = true;
        currentNode.frequency += 1;

        return currentNode;
    }

    protected Node createOrLoadAutoComplete(String word) {
        AutoComplete autoComplete = autoCompleteRepository.findAutoCompleteByWord(word)
                .orElseGet(() -> autoCompleteRepository.save(AutoComplete.createAutoComplete(word)));

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
