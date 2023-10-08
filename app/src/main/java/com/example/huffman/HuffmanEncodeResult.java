package com.example.huffman;

import java.util.Map;

public class HuffmanEncodeResult {
    public String encoded;
    public Map<Character, HuffmanData> table;
    public HuffmanNode root;

    public HuffmanEncodeResult(Map<Character, HuffmanData> _table, String _encoded, HuffmanNode _root){
        encoded = _encoded;
        table = _table;
        root = _root;
    }
}

