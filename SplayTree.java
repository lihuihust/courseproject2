/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;
import java.util.ArrayList;

/**
 * this is a test class to implement SplayTree has integers as elements
 * @author Hui
 */
public class SplayTree {
    Node root;
    /**
     * construct SplayTree
     */
    public SplayTree() {        
        root = null;
    }
    /**
     * This is the SplayTree Node.
     */
    private class Node {        
        int val;
        Node left;
        Node right;
        Node parent;
        Node(int value) {
            val = value;
            left = null;
            right = null;
            parent = null;
        }
    }
    /**
     * Add new element into the tree.
     * @param v the element to add.
     */
    public void add(int v) {
        Node t = new Node(v);
        if(root==null)
            root = t;
        else
            add(t, root);
    }
    private void add(Node t, Node r){
        if (r.val > t.val){
            if(r.left==null) {
                r.left = t;
                t.parent = r;
                rotate(t);
            }
            else
                add(t,r.left);
        }
        else if (r.val < t.val){
            if(r.right==null) {
                r.right = t;
                t.parent = r;
                rotate(t);
            }
            else
                add(t, r.right);
        }
        else 
            rotate(r);        
    }
    
    /**
     * check the element in the tree or not.
     * @param v the element to check
     * @return true if v is in the tree otherwise false.
     */    
    public boolean find(int v) {
        return find(v, root);        
    }
    
    private boolean find(int v, Node r) {
        if(r==null) 
            return false;
        if (v==r.val) {
            rotate(r);
            return true;
        }
        if (v>r.val) {
            if(r.right==null){
                rotate(r);
                return false;
            }
            return find(v, r.right);                   
        }
        else {
            if (r.left==null) {
                rotate(r);
                return false;
            }
            return find(v, r.left);
        }        
    }
    /**
     * count the number of leaves.
     * @return the number of leaves.
     */
    public int leafCount() {
        return leafCount(root);        
    }
    private int leafCount(Node r) {
        if (r==null)
            return 0;
        if (r.left == null && r.right == null)
            return 1;
        return leafCount(r.left) + leafCount(r.right);
    }
    
    /**
     * sum the elements in the tree
     * @return the sum of the tree
     */
    public int treeSum() {
        return treeSum(root);
    }
    
    private int treeSum(Node r) {
        if(r==null)
            return 0;
        if (r.left == null && r.right == null)
            return r.val;
        return r.val + treeSum(r.left) +treeSum(r.right);
    }
    
    /**
     * convert the tree to string for printing
     * @return the string of the tree
     */
    @Override
    public String toString() { 
        StringBuilder sb = new StringBuilder( "[ " );
        sb.append(toString(root));
        sb.append( "]" );
        return new String( sb );
    }
    private String toString(Node r){        
        if (r==null)
            return "";
        return toString(r.left) + Integer.toString(r.val) + ", " + toString(r.right);
    }
    
    /**
     * print the tree level-by-level
     */
    public void printLevels() {
        //use two list to moniter the parents level and children level
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        
        if(root==null)
            return;
        first.add(root);
        while(!first.isEmpty()||!second.isEmpty()) {
            if(!first.isEmpty()){
                for(Node e : first){
                    System.out.print(e.val+ " ");
                    if(e.left!=null)
                        second.add(e.left);
                    if(e.right!=null)
                        second.add(e.right);                    
                }
                System.out.println();
                first.clear();                
            }
            if(!second.isEmpty()){
                for(Node e : second){
                    System.out.print(e.val +" ");
                    if(e.left!=null)
                        first.add(e.left);
                    if(e.right!=null)
                        first.add(e.right);                    
                }
                System.out.println();
                second.clear();
            }
        }
    }
    
    /**
     * leftZigZag rotation
     * @param t the node to be rotated
     * @param parent the parent node
     * @param temp the grandparent node
     */
    private void rotateLeftZigZag(Node t, Node parent, Node temp){
        parent.right=t.left;
        if(t.left!=null)
            t.left.parent=parent;
        parent.parent = t;
        t.left=parent;        
        temp.left=t.right;
        if(t.right!= null)
            t.right.parent=temp;
        t.right=temp;
        t.parent=temp.parent;
        temp.parent=t;    
    }
    
    /**
     * rightZigZag rotation
     * @param t the node to be rotated
     * @param parent the parent node
     * @param temp the grandparent node
     */
    private void rotateRightZigZag(Node t, Node parent, Node temp){
        parent.left=t.right;
        if(t.right!=null)
            t.right.parent=parent;
        parent.parent = t;
        t.right=parent;        
        temp.right=t.left;
        if(t.left!= null)
            t.left.parent=temp;
        t.left=temp;
        t.parent=temp.parent;
        temp.parent=t;
    }
    
    /**
     * leftZigZig rotation
     * @param t the node to be rotated
     * @param parent the parent node
     * @param temp the grandparent node
     */
    private void rotateLeftZigZig(Node t, Node parent, Node temp) {
        temp.left=parent.right;
        if(temp.left!=null)
            temp.left.parent=temp;
        parent.right=temp;
        parent.parent=t;
        t.parent=temp.parent;
        temp.parent=parent;        
        parent.left=t.right;
        if(parent.left!=null)
            parent.left.parent=parent;
        t.right=parent;
    }
    
    /**
     * rightZigZig rotation
     * @param t the node to be rotated
     * @param parent the parent node
     * @param temp the grandparent node
     */
    private void rotateRightZigZig(Node t, Node parent, Node temp) {        
        temp.right=parent.left;
        if(temp.right!=null)
            temp.right.parent=temp;
        parent.left=temp;
        parent.parent=t;
        t.parent=temp.parent;
        temp.parent=parent;        
        parent.right=t.left;
        if(parent.right!= null)
            parent.right.parent=parent;
        t.left=parent;
    }
    
    /**
     * rotate Node t to the top of the tree
     * @param t the node to be rotated     
     */
    private void rotate(Node t) {
        while (t.parent != null){
            Node temp = t.parent.parent;
            Node parent = t.parent;
            if(temp==null) {
                if(t.val<parent.val) {
                    parent.left=t.right;
                    if(t.right!=null)
                        t.right.parent=t.parent;
                    t.right=parent;                    
                }
                else {
                    parent.right=t.left;
                    if(t.left!=null)
                        t.left.parent=parent;
                    t.left=parent;                    
                }
                parent.parent=t;
                t.parent=null;
            }
            else if (t.val>t.parent.val) {                
                if(temp.val>t.parent.val) 
                    rotateLeftZigZag(t, parent, temp);
                else 
                    rotateRightZigZig(t, parent, temp);
            }
            else {
                if(temp.val<t.parent.val)
                    rotateRightZigZag(t, parent, temp);
                else 
                    rotateLeftZigZig(t, parent, temp);
            }
            if(t.parent!=null) {
                if(t.parent.val>t.val)
                    t.parent.left=t;
                else
                    t.parent.right=t;
            }
            root=t;
        }
    }

    /**
     * test the SplayTree class and the methods
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SplayTree sp = new SplayTree();
        sp.add(5);
        sp.printLevels();
        System.out.println();
        sp.add(9);
        sp.printLevels();
        System.out.println();
        sp.add(8);
        sp.printLevels();
        System.out.println();
        sp.add(7);
        sp.printLevels();
        System.out.println();
        sp.add(22);
        sp.printLevels();
        System.out.println();
        sp.add(13);
        sp.printLevels();
        System.out.println();
        sp.add(3);
        sp.printLevels();
        System.out.println();
        sp.add(4);
        sp.printLevels();
        System.out.println();
        sp.add(9);
        sp.printLevels();
        System.out.println();
        sp.add(10);
        sp.printLevels();
        System.out.println();
        sp.add(21);  
        System.out.println("The tree is: ");        
        sp.printLevels();
        System.out.println("The inorder travesal of the tree is:");
        System.out.println(sp);        
        if(sp.find(5))
            System.out.println("5 is in the tree.");
        else
            System.out.println("5 is not in the tree.");
        System.out.println("The tree now becomes: ");
        sp.printLevels();
        System.out.println("This tree has " + sp.leafCount()+ " leaves.");
        System.out.println("The sum of the tree is: " + sp.treeSum());
        if (sp.find(11))
            System.out.println("11 is in the tree.");
        else
            System.out.println("11 is not in the tree.");
        System.out.println("The tree now becomes: ");
        sp.printLevels();
    }
    
}
