package com.khanh.controller;

import com.khanh.model.Chain;
import com.khanh.model.Node;
import com.khanh.service.BlockChainService;
import com.khanh.util.Mappings;
import com.khanh.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class MainController {

    // == fields ==
    private final BlockChainService blockChainService;

    // == constructors ==
    @Autowired
    public MainController(BlockChainService blockChainService) {
        this.blockChainService = blockChainService;
    }

    // == model attributes ==
    @ModelAttribute
    public Chain blockChain(){
        return blockChainService.getChain();
    }

    // == handler methods ==
    @GetMapping(Mappings.HOME)
    public String home(){
        return ViewNames.HOME;
    }

    @PostMapping(Mappings.ADD_NODES)
    public String addNextNode(@RequestParam(required = false, defaultValue = "") String data,
                              @RequestParam(required = false, defaultValue = "-1") int id){
        log.info("data = {}",data);
        log.info("id = {}", id);
        if (id==-1){
            blockChainService.addNode(new Node(data));
        }else{
            blockChainService.updateNode(id, data);
        }
        return Mappings.REDIRECT_HOME;
    }

}
