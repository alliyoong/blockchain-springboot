package com.khanh.service;

import com.khanh.model.Chain;
import com.khanh.model.Node;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class BlockChainServiceImpl implements BlockChainService{
    // == fields ==
    @Getter
    private final Chain chain =  new Chain();

    // == public methods ==
    @Override
    public void addNode(Node node) {
        chain.addNode(node);
    }

    @Override
    public Node getNode(int id) {
        return chain.getNode(id);
    }

    @Override
    public void updateNode(int id, String data) {
        chain.updateNode(id, data);

    }
}
