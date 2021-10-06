package com.khanh.service;

import com.khanh.model.Node;
import com.khanh.model.Chain;

public interface BlockChainService {

    void addNode(Node node);

    Node getNode(int id);

    void updateNode(int id, String data);

    Chain getChain();
}
