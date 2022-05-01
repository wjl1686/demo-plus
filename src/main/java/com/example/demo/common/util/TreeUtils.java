package com.example.demo.common.util;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {

    protected TreeUtils() {

    }

    public static <T> Tree<T> build(List<Tree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getPrntAdmdvs();
            if (pid == null || "100000".equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (Tree<T> parent : nodes) {
                String id = parent.getAdmdvs();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                   /* children.setHasParent(true);
                    parent.setChildren(true);*/
                    return;
                }
            }
        });

        Tree<T> root = new Tree<>();
        root.setAdmdvs("100000");
//		root.setLevel("0");
        root.setPrntAdmdvs("CN0001");
        //root.setHasParent(false);
        //root.setChildren(true);
        //root.setChecked(true);
        root.setChildren(topNodes);
        root.setAdmdvsName("国家");
        //Map<String, Object> state = new HashMap<>(16);
        //state.put("opened", true);
        //root.setState(state);
        return root;
    }

    public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getPrntAdmdvs();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);
                return;
            }
            nodes.forEach(parent -> {
                String id = parent.getAdmdvs();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    //children.setHasParent(true);
                   // parent.setChildren(true);
                }
            });
        });
        return topNodes;
    }

}