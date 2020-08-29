package tree;

public class BinarySearchTree {
	Node root;
	
	public BinarySearchTree() {
		this.root = null;
	}

	//search
	public boolean search(int value) {
		if (root == null) return false;
		
		Node current = root; //root Node ���� Ž��
		while(current!=null) {
			if(current.getData()==value) { //���� node�� ���� ã������ value�� ���� ���� ��
				return true;
			}else  if(value<current.getData()){ //ã������ value�� ���� ���� node�� �� ���� ������ ���� child Node�� ���ؾ� ��
				current = current.getLeft();
			}else {
				current = current.getRight(); //ã������ value�� ���� ���� node�� �� ���� ũ�� ������ child Node�� ���ؾ� ��
			}
		}
		return false;
	}
	
	//insert
	public void insert (int value) {
		Node newNode = new Node(value);
		
		if(root==null) { //root�� null�̶�� Ʈ���� �ƹ� �͵� ���� ����
			root = newNode;
		}else {
			Node current = root; //Ž���� ���
			Node parent = null; //Ž���� ����� �θ� ���
		
			while(true) {
				parent = current; //�θ� ��� = Ž���� ���
				if(value < parent.getData()) { //������ �����Ͱ� �θ� �����ͺ��� �۴ٸ� ���ʿ� ����
					if(parent.getLeft()!=null) { //�θ��� ���� ��尡 null�� �ƴϸ�, Ž���� ������(current) = ���� �������� ���� (�� ����� childNode�� �������� �̵�)
						current = current.getLeft();
					}else {
						parent.setLeft(newNode);
						 return;
					}
					
				}else { //������ �����Ͱ� �θ� �����ͺ��� ũ�ٸ� �����ʿ� ����
					if(parent.getRight()!=null) { //�θ���  ������ ��尡 null�� �ƴϸ�, Ž���� ������(current) = ���� �������� ������(�� ����� childNode�� ���������� �̵�)
						current = current.getRight();
					}else {
						parent.setRight(newNode);
						 return;
					}
				}
				
			}
		}
	}
	
	public boolean delete(int value) {
		boolean isLeft = false;
		Node current = root;
		Node parent = root;
		
		//������ node Ž��
		//while�� ���� �� current�� ������ ������ parent�� ������ �������� �θ� node(������ �����Ͱ� parnet�� ����/������ child Node������ �˱����� �ʿ�)
		while(current.getData()!=value) {
			parent = current;
            if(value<current.getData()) { 
            	isLeft = true; //������ �����Ͱ� ���ʿ� ����
            	current = current.getLeft();
            }else {
            	isLeft = false; //������ �����Ͱ� �����ʿ� ����
            	current = current.getRight();
            }
            if(current == null){
                return false;
            }
		}
		
		Node replacement;
		//case1 : �����Ϸ��� node�� leafNode �� ���(�ڽ��� ���� Node)
		if(current.getLeft()==null && current.getRight()==null) {
			if(current==root) { //root node�� �����ϴ� ���
				root = null;
			}
			if(isLeft) { //parent Node�� ������ �������ϴ� ���
				parent.setLeft(null);
			}else {//parent Node�� �������� �������ϴ� ���
				parent.setRight(null);
			}
		}
		
		//case2: ������ Node�� Child Node�� �� �� ������ ���� ���
		else if(current.getLeft()==null) {//���� childNode�� null�� ��� = ������ childNode�� �ִ� ���
			replacement = current.getRight();
			if(current==root) {
				root = replacement;
			}else if(isLeft) {
				parent.setLeft(replacement);
			}else {
				parent.setRight(replacement);
			}
		}else if(current.getRight()==null) {//������ childNode�� null�� ��� = ���� childNode�� �ִ� ���
			replacement = current.getLeft();
			if(current==root) {
				root = replacement;
			}else if(isLeft) {
				parent.setLeft(replacement);
			}else {
				parent.setRight(replacement);
			}
		}
		
		//case3: ������ Node�� Child Node�� �� �� ������ ���� ���
		// ������ node�� ������ Child Node �� ���� ���� ���� ã�´�!
		else {
			replacement = getMin(current);
			if(current == root) {
				root = replacement;
			}else if(isLeft) {
				parent.setLeft(replacement);
			}else {
				parent.setRight(replacement);
			}
			replacement.setLeft(current.getLeft());
		}
		return true;
	}
	
	//���� ���� Node ã��
	public Node getMin(Node node) {
		Node minimum = null;
		Node parent = null;
		Node current = node.getRight();
		
		while(current!=null) {
			parent = minimum;
			minimum = current;
			current = current.getLeft();	
		}
		
		//�ּҰ��� �����Ϸ��� childNode�� ������ node�� ���� �ٸ��ٸ� �ּҰ��� ������ child Node�� �����Ѵٴ� ��
		// => �ּҰ��� ������ child Node�� �ּҰ��� �θ� node�� ���� child Node�� ����
		// => �ּҰ��� �����Ϸ��� child Node�� ����Ǳ� ������ �ּҰ��� ������ child Node�� �����Ϸ��� Node�� ������ child Node�� ����
		if(minimum!=node.getRight()) {
			parent.setLeft(minimum.getRight());
			minimum.setRight(node.getRight());
		}
		return minimum;
	}
	
	public void show(Node root) {
		if(root!=null) {
			show(root.getLeft());
			System.out.println(" "+root.getData());
			show(root.getRight());
		}
	}
	
	public static void main(String[] args) {
		BinarySearchTree b = new BinarySearchTree();
		
		b.insert(50);
        b.insert(25);
        b.insert(75);
        b.insert(15);
        b.insert(30);
        b.insert(70);
        b.insert(85);
        b.insert(2);
        b.insert(18);
        b.insert(26);
        b.insert(32);
        
        System.out.println("Ʈ�� ���� ��� : ");
        b.show(b.root);
        System.out.println("50 Ž�� : " + b.search(50));
        System.out.println("50 ���� : " + b.delete(50));
        b.show(b.root);
        
        System.out.println("48 ���� ���");
        b.insert(48);
        b.show(b.root);
        
	}
}
