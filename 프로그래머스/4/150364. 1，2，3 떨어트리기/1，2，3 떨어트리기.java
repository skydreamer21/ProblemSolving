import java.util.*;

class Solution {
    static class Node {
        List<Integer> children;
        int pointer;
        int index;
        
        public Node(int index) {
            children = new ArrayList<>();
            pointer = -1; // This is Leaf Node
            this.index = index;
        }
        
        public void addChild(int index) {
            children.add(index);
        }
        
        public void sortChildren() {
            Collections.sort(children);
        }
        
        public int next() {
            // [Pre Throw] 리프 노드면 next() 가 불리면 안된다.
            if (pointer == -1) {
                throw new IllegalArgumentException();
            } // [PT]
            return children.get((pointer++) % children.size());
        }
    }
    
    static class Graph {
        // 0 부터 100까지 총 101개의 Node를 사용합니다.
        final int SIZE = 101;
        
        Node[] nodes;
        
        public Graph() {
            nodes = new Node[SIZE];
            for (int i=0; i<SIZE; i++) {
               nodes[i] = new Node(i); 
            }
        }
        
        public void addEdge(int parent, int child) {
            nodes[parent].addChild(child);
        }
        
        public void initEdges() {
            for (int i=0; i<SIZE; i++) {
                nodes[i].sortChildren();
                if (nodes[i].children.size() > 0) {
                    nodes[i].pointer = 0;
                }
            }
        }
        
        public int next() {
            Node now = nodes[0];
            while(now.pointer != -1) {
                int nextNodeIdx = now.next();
                now = nodes[nextNodeIdx];
            }
            return now.index;
        }
    }
    
    static class GameNumber {
        int number;
        
        public GameNumber(int num) {
            this.number = num;
        }
        
        public void decrease() {
            // [ERROR PASSED] number가 1이하인데 decrease 가 호출되는 경우
            number--;
        }
        
        public boolean isLimit() {
            return number == 1;
        }
    }
    
    static class LeafQueue {
        List<GameNumber> numbers;
        int sparePointer;
        
        public LeafQueue() {
            numbers = new ArrayList<>();
            sparePointer = -1;
        }
        
        public void add(int num) {
            numbers.add(new GameNumber(num));
            if (num > 1) {
                sparePointer++;
            }
        }
        
        public boolean divide() {
            if (sparePointer == -1) return false;
            numbers.get(sparePointer).decrease();
            add(1);
            if (numbers.get(sparePointer).isLimit()) {
                sparePointer--;
            }
            return true;
        }
        
        public int poll() {
            int lastIdx = numbers.size()-1;
            int number = numbers.get(lastIdx).number;
            numbers.remove(lastIdx);
            return number;
        }
    }
    
    static class Order {
        Map<Integer, LeafQueue> leafMap;
        List<Integer> visitOrder;
        int[] target;
        int total;
        
        public Order(int[] target) {
            leafMap = new HashMap();
            visitOrder = new ArrayList<>();
            this.target = target;
            this.total = getTotal(target);
        }
        
        private int getTotal(int[] target) {
            int tot = 0;
            for (int i=0; i<target.length; i++) {
                tot += target[i];
            }
            return tot;
        }
        
        public boolean decide(int nodeIdx) {
           // nodeIdx를 visitOrder에 추가하고, nodeIdx 에 해당하는 노드에 들어갈 숫자를 정함.
            if (!leafMap.containsKey(nodeIdx)) {
                leafMap.put(nodeIdx, new LeafQueue());
            }
            
            visitOrder.add(nodeIdx);
            if (target[nodeIdx] > 0) {
                int numberAdded = target[nodeIdx] >= 3 ? 3 : target[nodeIdx];
                leafMap.get(nodeIdx).add(numberAdded);
				// [ERROR PASSED] numberAdded가 0가 일 경우
                target[nodeIdx] -= numberAdded;
                total -= numberAdded;
            } else if (target[nodeIdx] == 0) { // 나눠 가지는 거라서 추가 되는 건 없음.
                LeafQueue pq = leafMap.get(nodeIdx);
                boolean isDividePossible = pq.divide();
                if (!isDividePossible) return false;
            } // [ERROR PASSED] target 값이 음수일 경우
            return true;
        }
        
        public int[] getOrder() {
            Deque<Integer> order = new ArrayDeque<>();
            int size = visitOrder.size();
            for (int i=0; i<size; i++) {
                int nodeIdx = visitOrder.get(i);
                order.add(leafMap.get(nodeIdx).poll());
            }
            int[] result = new int[order.size()];
            for (int i=0; i<size; i++) {
                result[i] = order.pollFirst();
                // [ERROR PASSED] result에 0이 들어가면 안된다.
            }
            return result;
        }
    }
    
    
    public int[] solution(int[][] edges, int[] target) {
        Graph g = new Graph();
        for (int[] edge : edges) {
            g.addEdge(edge[0]-1, edge[1]-1);
        }
        g.initEdges();
        
        Order order = new Order(target);
        boolean isPossible = true;
        while (isPossible && order.total > 0) {
            int visitNodeIdx = g.next();
            isPossible = isPossible && order.decide(visitNodeIdx);
        }
        
        if (isPossible) {
            return order.getOrder();
        } else {
            return new int[]{-1};
        }
    }
}