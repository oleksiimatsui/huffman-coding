package com.example.huffman;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Huffman {
    public static void setTable(HuffmanNode root, Map<Character, HuffmanData> table, String s)
    {
        if (root.left == null && root.right == null
                && Character.isLetter(root.c)) {
            table.put(root.c, new HuffmanData(s,root.data));
            return;
        }
        setTable(root.left,table, s + "0");
        setTable(root.right, table, s + "1");
    }

    private static char[] GetChars(String text){
        Set<Character> set = new LinkedHashSet<Character>();
        for(int i=0; i<text.length();i++){
            char a = text.charAt(i);
            set.add(a);
        }

        int n = set.size();
        char[] charArray = new char[n];
        int i = 0;
        for (char x : set)
            charArray[i++] = x;
        return charArray;
    }

    public static HuffmanEncodeResult Encode(String text){
        char[] charArray = GetChars(text);
        HuffmanNode root = Huffman.GetTreeRoot(text, charArray);
        Map<Character, HuffmanData> table = new HashMap<Character, HuffmanData>();
        Huffman.setTable(root, table, "");
        String encoded = "";
        for(int i=0; i<text.length();i++){
            char a = text.charAt(i);
            String code = table.get(a).code;
            encoded += code;
        }
        HuffmanEncodeResult huffmanEncodeResult = new HuffmanEncodeResult(table, encoded);
        return huffmanEncodeResult;
    }


    public static HuffmanNode GetTreeRoot(String text, char[] charArray)
    {
        Set<Character> set = new LinkedHashSet<Character>();
        int n = charArray.length;

        PriorityQueue<HuffmanNode> q
                = new PriorityQueue<HuffmanNode>(
                n, new MyComparator());
        for (int i = 0; i < n; i++) {
            HuffmanNode hn = new HuffmanNode();
            hn.c = charArray[i];
            hn.data = getCount(text, charArray[i]);
            hn.left = null;
            hn.right = null;
            q.add(hn);
        }
        HuffmanNode root = null;

        while (q.size() > 1) {
            HuffmanNode x = q.peek();
            q.poll();
            HuffmanNode y = q.peek();
            q.poll();
            HuffmanNode f = new HuffmanNode();
            f.data = x.data + y.data;
            f.c = '-';
            f.left = x;
            f.right = y;
            root = f;
            q.add(f);
        }
        return root;
    }


    private static int  getCount(String text, char c){
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}

class HuffmanNode {
    int data;
    char c;
    HuffmanNode left;
    HuffmanNode right;
}
class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y)
    {
        return x.data - y.data;
    }
}

