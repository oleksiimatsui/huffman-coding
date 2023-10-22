package com.example.huffman;

import com.google.gson.Gson;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Huffman {
    public static void setTable(HuffmanNode root, Map<String, HuffmanData> table, String s)
    {
        if (root.left == null && root.right == null) {
            table.put(root.c, new HuffmanData(s,root.data));
            return;
        }
        if(root.left != null) setTable(root.left,table, s + "0");
        if(root.right != null) setTable(root.right, table, s + "1");
    }

    private static char[] getChars(char[] text){
        Set<Character> set = new LinkedHashSet<Character>();
        for(int i=0; i<text.length;i++){
            char a = text[i];
            set.add(a);
        }

        int n = set.size();
        char[] charArray = new char[n];
        int i = 0;
        for (char x : set)
            charArray[i++] = x;
        return charArray;
    }

    public static HuffmanEncodeResult Encode(String textStr){
        char[] text = textStr.toCharArray();
        char[] charArray = getChars(text);
        HuffmanNode root = Huffman.GetTreeRoot(text, charArray);
        System.out.println("ROOT");
        System.out.println(new Gson().toJson(root));

        Map<String, HuffmanData> table = new HashMap<String, HuffmanData>();
        if(root.left == null & root.right==null){
            table.put(root.c, new HuffmanData("0",root.data));
        }else {
            Huffman.setTable(root, table, "");
        }
        System.out.println("TABLE");
        table.forEach((key, value) -> {
            System.out.println(key + ": " + value.code);
        });
        String encoded = "";
        for(int i=0; i<text.length;i++){
            String a = "" + text[i];
            System.out.println("char " + a);
            String code = table.get(a).code;
            encoded += code;
        }
        HuffmanEncodeResult huffmanEncodeResult = new HuffmanEncodeResult(table, encoded, root);
        return huffmanEncodeResult;
    }


    public static HuffmanNode GetTreeRoot(char[] text, char[] charArray)
    {
        Set<Character> set = new LinkedHashSet<Character>();
        int n = charArray.length;

        PriorityQueue<HuffmanNode> q
                = new PriorityQueue<HuffmanNode>(
                n, new MyComparator());
        for (int i = 0; i < n; i++) {
            HuffmanNode hn = new HuffmanNode();
            hn.c = "" + charArray[i];
            hn.data = getCount(text, charArray[i]);
            hn.left = null;
            hn.right = null;
            System.out.println(hn.c + ": " + hn.data);
            q.add(hn);
        }
        HuffmanNode root = null;
        if(q.size()==1){
            root = q.peek();
            System.out.println(root.c + ": " + root.data);
        }else
        while (q.size() > 1) {
            HuffmanNode x = q.peek();
            q.poll();
            HuffmanNode y = q.peek();
            q.poll();
            HuffmanNode f = new HuffmanNode();
            f.data = x.data + y.data;
            f.c = "-";
            f.left = x;
            f.right = y;
            root = f;
            System.out.println(root.c + ": " + root.data);
            q.add(f);
        }
        return root;
    }


    private static int  getCount(char[] text, char c){
        int count = 0;
        for (int i = 0; i < text.length; i++) {
            if (text[i] == c) {
                count++;
            }
        }
        return count;
    }

    public static String Decode(String code, HuffmanNode root){
        int n = code.length();
        HuffmanNode curr = root;
        String text = "";
        for (int i = 0; i < n; i++) {
            if (code.charAt(i) == '0') {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr.left == null && curr.right == null) {
                text += curr.c;
                curr = root;
            }
        }
        return text;
    }
}

class HuffmanNode {
    int data;
    String c;
    HuffmanNode left;
    HuffmanNode right;
}
class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y)
    {
        return x.data - y.data;
    }
}

