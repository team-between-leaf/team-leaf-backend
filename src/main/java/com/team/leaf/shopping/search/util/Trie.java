package com.team.leaf.shopping.search.util;

import com.team.leaf.shopping.search.dto.UtilInitDto;
import org.springframework.stereotype.Component;

import java.util.*;

class Node {
    Map<Character, Node> childedNode = new HashMap<>();
    String value = "";
    boolean isContainWord = false;
    int frequency = 0;

    public Node(char value) {
        this.value = String.valueOf(value);
    }
}

public class Trie {

    Node rootNode = new Node('E');

    public Node insert(String str) {
        Node node = this.rootNode;

        for(int i = 0; i < str.length(); i++) {
            node = node.childedNode.computeIfAbsent(str.charAt(i), key -> new Node(key));
        }

        node.isContainWord = true;
        node.frequency += 1;

        return node;
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
                result.add(new UtilInitDto(currentNode.value, currentNode.frequency));
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
