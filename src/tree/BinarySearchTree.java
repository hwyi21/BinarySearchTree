package tree;

public class BinarySearchTree {
	Node root;
	
	public BinarySearchTree() {
		this.root = null;
	}

	//search
	public boolean search(int value) {
		if (root == null) return false;
		
		Node current = root; //root Node 부터 탐색
		while(current!=null) {
			if(current.getData()==value) { //현재 node의 값이 찾으려는 value와 같은 값일 때
				return true;
			}else  if(value<current.getData()){ //찾으려는 value의 값이 현재 node의 값 보다 작으면 왼쪽 child Node와 비교해야 함
				current = current.getLeft();
			}else {
				current = current.getRight(); //찾으려는 value의 값이 현재 node의 값 보다 크면 오른쪽 child Node와 비교해야 함
			}
		}
		return false;
	}
	
	//insert
	public void insert (int value) {
		Node newNode = new Node(value);
		
		if(root==null) { //root가 null이라면 트리에 아무 것도 없는 상태
			root = newNode;
		}else {
			Node current = root; //탐색할 노드
			Node parent = null; //탐색할 노드의 부모 노드
		
			while(true) {
				parent = current; //부모 노드 = 탐색할 노드
				if(value < parent.getData()) { //삽입할 데이터가 부모 데이터보다 작다면 왼쪽에 삽입
					if(parent.getLeft()!=null) { //부모의 왼쪽 노드가 null이 아니면, 탐색할 데이터(current) = 현재 데이터의 왼쪽 (비교 대상을 childNode의 왼쪽으로 이동)
						current = current.getLeft();
					}else {
						parent.setLeft(newNode);
						 return;
					}
					
				}else { //삽입할 데이터가 부모 데이터보다 크다면 오른쪽에 삽입
					if(parent.getRight()!=null) { //부모의  오른쪽 노드가 null이 아니면, 탐색할 데이터(current) = 현재 데이터의 오른쪽(비교 대상을 childNode의 오른쪽으로 이동)
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
		
		//삭제할 node 탐색
		//while문 종료 후 current는 삭제할 데이터 parent는 삭제할 데이터의 부모 node(삭제할 데이터가 parnet의 왼쪽/오른쪽 child Node인지를 알기위해 필요)
		while(current.getData()!=value) {
			parent = current;
            if(value<current.getData()) { 
            	isLeft = true; //삭제할 데이터가 왼쪽에 있음
            	current = current.getLeft();
            }else {
            	isLeft = false; //삭제할 데이터가 오른쪽에 있음
            	current = current.getRight();
            }
            if(current == null){
                return false;
            }
		}
		
		Node replacement;
		//case1 : 삭제하려는 node가 leafNode 인 경우(자식인 없는 Node)
		if(current.getLeft()==null && current.getRight()==null) {
			if(current==root) { //root node만 존재하는 경우
				root = null;
			}
			if(isLeft) { //parent Node의 왼쪽을 지워야하는 경우
				parent.setLeft(null);
			}else {//parent Node의 오른쪽을 지워야하는 경우
				parent.setRight(null);
			}
		}
		
		//case2: 삭제할 Node가 Child Node를 한 개 가지고 있을 경우
		else if(current.getLeft()==null) {//왼쪽 childNode가 null인 경우 = 오른쪽 childNode만 있는 경우
			replacement = current.getRight();
			if(current==root) {
				root = replacement;
			}else if(isLeft) {
				parent.setLeft(replacement);
			}else {
				parent.setRight(replacement);
			}
		}else if(current.getRight()==null) {//오른쪽 childNode가 null인 경우 = 왼쪽 childNode만 있는 경우
			replacement = current.getLeft();
			if(current==root) {
				root = replacement;
			}else if(isLeft) {
				parent.setLeft(replacement);
			}else {
				parent.setRight(replacement);
			}
		}
		
		//case3: 삭제할 Node가 Child Node를 두 개 가지고 있을 경우
		// 삭제할 node의 오른쪽 Child Node 중 가장 작은 값을 찾는다!
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
	
	//가장 작은 Node 찾기
	public Node getMin(Node node) {
		Node minimum = null;
		Node parent = null;
		Node current = node.getRight();
		
		while(current!=null) {
			parent = minimum;
			minimum = current;
			current = current.getLeft();	
		}
		
		//최소값이 삭제하려는 childNode의 오른쪽 node와 값이 다르다면 최소값의 오른쪽 child Node가 존재한다는 뜻
		// => 최소값의 오른쪽 child Node는 최소값의 부모 node의 왼쪽 child Node로 변경
		// => 최소값이 삭제하려는 child Node로 변경되기 때문에 최소값의 오른쪽 child Node는 삭제하려는 Node의 오른쪽 child Node로 변경
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
        
        System.out.println("트리 삽입 결과 : ");
        b.show(b.root);
        System.out.println("50 탐색 : " + b.search(50));
        System.out.println("50 삭제 : " + b.delete(50));
        b.show(b.root);
        
        System.out.println("48 삽입 결과");
        b.insert(48);
        b.show(b.root);
        
	}
}
