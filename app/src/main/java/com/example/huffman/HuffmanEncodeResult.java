package com.example.huffman;

import java.util.Map;

public class HuffmanEncodeResult {
    public String encoded;
    public Map<String, HuffmanData> table;
    public HuffmanNode root;

    public HuffmanEncodeResult(Map<String, HuffmanData> _table, String _encoded, HuffmanNode _root){
        encoded = _encoded;
        table = _table;
        root = _root;
    }
}

