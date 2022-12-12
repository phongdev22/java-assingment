public class ScoreAVL extends AVL {

    public ScoreAVL() {
        super();
    }

    public void insert(Student key) {
        this.root = insert(this.root, key);
    }

    private Node insert(Node node, Student key) {
        // code here
        if (node == null)
            return new Node(key);

        int cmp = key.compareToByScore(node.getData());

        if (cmp < 0) {
            node.setLeft(insert(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(insert(node.getRight(), key));
        } else {
            node.setData(key);
        }
        node.setHeight(1 + Math.max(super.height(node.getLeft()), super.height(node.getRight())));
        return super.balance(node);
    }
}
