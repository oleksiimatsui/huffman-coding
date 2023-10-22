package com.example.huffman;

import android.util.Pair;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
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
    private final static String[] cmpztSmbls = {"ch", "sh", "th", "wh"};
    private static ArrayList<String> compositeSymbols = new ArrayList<String>(Arrays.asList(cmpztSmbls));
    public static Pair<Set<String>,ArrayList<String>> getCharsAndTextArray(String textStr){
        Set<String> chars = new LinkedHashSet<String>();
        ArrayList<String> text = new ArrayList<String>();
        int i =0 ;
        for (i = 0; i<textStr.length()-1; i++){
            char c = textStr.charAt(i);
            char c1 = textStr.charAt(i+1);
            String composite = "" + c + c1;
            String symb = "";
            if(compositeSymbols.contains(composite)){
                symb = composite;
                i = i+1;
            }else{
                symb = ""+c;
            }
            if(chars.contains(symb)){

            }else{
                chars.add(symb);
            }
            text.add(symb);
        }
        if(i==textStr.length()-1){
            text.add(textStr.charAt(i) + "");
            chars.add(textStr.charAt(i) + "");
        }
        return new Pair<Set<String>,ArrayList<String>>(chars, text);
    }

    public static HuffmanEncodeResult Encode(String textStr){
        Pair<Set<String>, ArrayList<String>> charsAndArray = getCharsAndTextArray(textStr);
        ArrayList<String> text = charsAndArray.second;
        Set<String> charArray = charsAndArray.first;
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
        for(int i=0; i<text.size();i++){
            String a = "" + text.get(i);
            System.out.println("char " + a);
            String code = table.get(a).code;
            encoded += code;
        }
        HuffmanEncodeResult huffmanEncodeResult = new HuffmanEncodeResult(table, encoded, root);
        return huffmanEncodeResult;
    }


    public static HuffmanNode GetTreeRoot(ArrayList<String> text, Set<String> chars)
    {

        int n = chars.size();
        String[] symbols = new String[n];
        chars.toArray(symbols);
        System.out.println("String is " + text);
        PriorityQueue<HuffmanNode> q
                = new PriorityQueue<HuffmanNode>(
                n, new MyComparator());
        for (int i = 0; i < n; i++) {
            HuffmanNode hn = new HuffmanNode();
            hn.c = symbols[i];
            hn.data = getCount(text, symbols[i]);
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


    private static int  getCount(ArrayList<String> text, String c){
        int count = 0;
        for (int i = 0; i < text.size(); i++) {
            if (Objects.equals(text.get(i), c)) {
                count++;
            }
        }
        System.out.println(text + " contains " + c + " " + count + " times!");
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

