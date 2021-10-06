package com.khanh.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
public class Node {

    // == fields ==
    private int id;
    private String name;
    private String data;
    private String hash;
    private String previousHash;
    private LocalDateTime timestamp;
    private int nonce;

    // == constructors ==
    public Node(String data) {
        this.data = data;
    }

}
