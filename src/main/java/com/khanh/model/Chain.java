package com.khanh.model;

import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Chain {

    // == fields ==
    private static int idValue = 0;
    private static int difficulty = 2;
    private final List<Node> nodes = new ArrayList<>();

    // == constructors ==
    public Chain() {
        addNode(new Node("This is the genesis block"));
    }

    // == generate new block methods ==
    private String calculateHash(String data, int id, String previousHash, LocalDateTime timestamp, int nonce){
        String input = String.valueOf(id) + previousHash + timestamp + data + String.valueOf(nonce);
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest){
                hexString.append(String.format("%02x",b));
            }

            return hexString.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    private boolean isValidHash(int difficulty, String hash){
        String valid = "";
        for (int i = 0; i < difficulty; i++){
           valid += "0";
        }
        return hash.startsWith(valid);
    }

    private Node generateNewNode(int id, String data){
        Node toAdd = new Node(data);
        toAdd.setId(id);
        toAdd.setName("BLOCK #"+String.valueOf(id));

        if (id==0){
            toAdd.setPreviousHash("0");
        }else{
            toAdd.setPreviousHash(getNode(id-1).getHash());
        }

        int nonce = 0;
        LocalDateTime timestamp = LocalDateTime.now();
        String hash = calculateHash(toAdd.getData(),toAdd.getId(),toAdd.getPreviousHash(),timestamp,nonce);
        while(!isValidHash(difficulty, hash)){
            nonce++;
            timestamp = LocalDateTime.now();
            hash = calculateHash(toAdd.getData(),toAdd.getId(),toAdd.getPreviousHash(),timestamp,nonce);
        }

        toAdd.setNonce(nonce);
        toAdd.setHash(hash);
        toAdd.setTimestamp(timestamp);
        return toAdd;
    }

    // == main function methods ==
    public List<Node> getNodes(){
        return Collections.unmodifiableList(nodes);
    }

    public void addNode(@NonNull Node toAdd){
        toAdd = generateNewNode(idValue,toAdd.getData());
        nodes.add(toAdd);
        idValue++;
    }

    public void updateNode(int id, String data){
        Node toUpdate = generateNewNode(id, data);
        ListIterator<Node> nodeIterator = nodes.listIterator();
        while(nodeIterator.hasNext()){
            Node node = nodeIterator.next();
            if(node.equals(toUpdate)){
                nodeIterator.set(toUpdate);
            }
            if(node.getId()>id){
                nodeIterator.set(generateNewNode(node.getId(),node.getData()));
            }
        }

    }

    public Node getNode(int id){
        for(Node node : nodes){
            if (node.getId()==id){
                return node;
            }
        }
        return null;
    }

}
