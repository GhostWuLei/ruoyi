package com.ruoyi.common.utils;


import com.ruoyi.common.utils.demo.Ctree;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形表格工具类
 *
 * @author yanggb
 */
public class TreeTableUtils {/**
     * 把列表转换为树结构
     *
     * @param originalList      原始list数据
     * @param idFieldName       作为唯一标示的字段名称
     * @param pidFieldName      父节点标识字段名
     * @param childrenFieldName 子节点（列表）标识字段名
     * @return 树结构列表
     */
    public static <T> List<T> list2TreeList(List<T> originalList, String idFieldName, String pidFieldName,
                                            String childrenFieldName) {
        String id = null;
        List<String> idList = new ArrayList<>();
        for (T t : originalList) {
            try {
                id = BeanUtils.getProperty(t, idFieldName);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            idList.add(id);
        }
        // 获取根节点，即找出父节点为空的对象
        List<T> rootNodeList = new ArrayList<>();
        for (T t : originalList) {
            String parentId = null;
            try {
                parentId = BeanUtils.getProperty(t, pidFieldName);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (!idList.contains(parentId)) {
                rootNodeList.add(0, t);
            }
        }

        // 将根节点从原始list移除，减少下次处理数据
        originalList.removeAll(rootNodeList);

        // 递归封装树
        try {
            packTree(rootNodeList, originalList, idFieldName, pidFieldName, childrenFieldName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootNodeList;
    }

    /**
     * 判断是不是根节点 如果父级节点在list集合中不存在 则说明是根节点
     *
     * @param originalList
     * @param idFieldName
     * @param parentId
     * @return
     */
//    public static boolean isRootNode(List<T> originalList, String idFieldName, String parentId){
//        List idList = null;
//        for (T t : originalList) {
//            String id = null;
//            try {
//                id = BeanUtils.getProperty(t, idFieldName);
//            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            idList.add(id);
//        }
//        if (idList.contains(parentId)){
//            return false;
//        }
//        return true;
//    }



    /**
     * 封装树（向下递归）
     *
     * @param parentNodeList    要封装为树的父节点对象集合
     * @param originalList      原始list数据
     * @param keyName           作为唯一标示的字段名称
     * @param pidFieldName      父节点标识字段名
     * @param childrenFieldName 子节点（列表）标识字段名
     */
    private static <T> void packTree(List<T> parentNodeList, List<T> originalList, String keyName,
                                     String pidFieldName, String childrenFieldName) throws Exception {
        for (T parentNode : parentNodeList) {
            // 找到当前父节点的子节点列表
            List<T> children = packChildren(parentNode, originalList, keyName, pidFieldName, childrenFieldName);
            if (children.isEmpty()) {
                continue;
            }

            // 将当前父节点的子节点从原始list移除，减少下次处理数据
            originalList.removeAll(children);

            // 开始下次递归
            packTree(children, originalList, keyName, pidFieldName, childrenFieldName);
        }
    }

    /**
     * 封装子对象
     *
     * @param parentNode        父节点对象
     * @param originalList      原始list数据
     * @param keyName           作为唯一标示的字段名称
     * @param pidFieldName      父节点标识字段名
     * @param childrenFieldName 子节点（列表）标识字段名
     */
    private static <T> List<T> packChildren(T parentNode, List<T> originalList, String keyName, String pidFieldName,
                                            String childrenFieldName) throws Exception {
        // 找到当前父节点下的子节点列表
        List<T> childNodeList = new ArrayList<>();
        String parentId = BeanUtils.getProperty(parentNode, keyName);
        for (T t : originalList) {
            String childNodeParentId = BeanUtils.getProperty(t, pidFieldName);
            if (parentId.equals(childNodeParentId)) {
                childNodeList.add(t);
            }
        }

        // 将当前父节点下的子节点列表写入到当前父节点下（给子节点列表字段赋值）
        if (!childNodeList.isEmpty()) {
            FieldUtils.writeDeclaredField(parentNode, childrenFieldName, childNodeList, true);
        }

        return childNodeList;
    }

    public static void main(String[] args) {
        List<Ctree> ctrees = new ArrayList<>();
        Ctree ctree1 = new Ctree("1001", "0", "中国", "中国.com");
        Ctree ctree2 = new Ctree("1002", "1", "美国", "美国.com");
        Ctree ctree3 = new Ctree("1003", "1001", "湖南", "湖南.com");
        Ctree ctree4 = new Ctree("1004", "1001", "江苏", "江苏.com");
        Ctree ctree5 = new Ctree("1005", "1003", "邵阳市", "邵阳市.com");
        Ctree ctree6 = new Ctree("1006", "1005", "邵阳县", "邵阳县.com");
        Ctree ctree7 = new Ctree("1007", "1006", "五峰铺", "五峰铺.com");
        Ctree ctree8 = new Ctree("1008", "1004", "扬州", "扬州.com");
        Ctree ctree9 = new Ctree("1009", "1004", "盐城", "盐城.com");

        ctrees.add(ctree1);
        ctrees.add(ctree2);
        ctrees.add(ctree3);
        ctrees.add(ctree4);
        ctrees.add(ctree5);
        ctrees.add(ctree6);
        ctrees.add(ctree7);
        ctrees.add(ctree8);
        ctrees.add(ctree9);

        List<Ctree> tree = list2TreeList(ctrees, "id", "pid", "children");
        System.out.println(tree);

    }


}
