import java.util.*;

public class StudentManagement {
    private AVL tree;
    private Stack<Node> undoState;
    private Stack<Node> redoState;

    public StudentManagement() {
        this.tree = new AVL();
        this.undoState = new Stack<Node>();
        this.redoState = new Stack<Node>();
    }

    public AVL getTree() {
        return this.tree;
    }

    public void updateUndo() {
        this.undoState.push(cloneBinaryTree(this.tree.getRoot()));
    }

    public static Node cloneBinaryTree(Node root) {
        if (root == null)
            return null;
        Node copy = new Node(root.getData());
        copy.setLeft(cloneBinaryTree(root.getLeft()));
        copy.setRight(cloneBinaryTree(root.getRight()));
        copy.setHeight(root.getHeight());
        return copy;
    }

    // Requirement 1
    public boolean addStudent(Student st) {
        this.updateUndo();
        this.tree.insert(st);
        return true;
    }

    // Requirement 2
    public Student searchStudentById(int id) {
        return tree.search(id) == null ? null : tree.search(id).getData();
    }

    // Requirement 3
    public boolean removeStudent(int id) {
        Student student = searchStudentById(id);
        if (student != null) {
            this.updateUndo();
            this.tree.delete(student);
            return true;
        }
        return false;
    }

    // Requirement 4
    public void undo() {
        if (!undoState.isEmpty()) {
            this.redoState.push(this.tree.getRoot());
            this.tree.setRoot(this.undoState.pop());
        } else
            return;
    }

    // Requirement 5
    public void redo() {
        if (!redoState.isEmpty()) {
            this.tree.setRoot(this.redoState.pop());
        } else
            return;
    }

    // Requirement 6
    public ScoreAVL scoreTree(AVL tree) {
        ScoreAVL sc = new ScoreAVL();
        Queue<Node> queueNode = new LinkedList<>();
        queueNode.add(tree.getRoot());
        while (!queueNode.isEmpty()) {
            Node cur = queueNode.poll();
            if (cur.getLeft() != null)
                queueNode.add(cur.getLeft());
            if (cur.getRight() != null)
                queueNode.add(cur.getRight());
            sc.insert(cur.getData());
        }
        return sc;
    }
}
