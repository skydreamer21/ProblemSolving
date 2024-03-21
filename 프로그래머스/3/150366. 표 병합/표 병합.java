import java.util.*;

class Solution {
    static final int LEN = 2500;
    
    static class Cell {
        String value;
        int index;
        Cell prev;
        Cell next;
        
        public Cell(int index) {
            value = null;
            prev = null;
            next = null;
            this.index = index;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
        
        public void updateLinkedCells(String newValue) {
            setValue(newValue);
            
            Cell temp = this;
//            int cnt = 0;
            while(temp.next != null) {
                temp = temp.next;
                temp.setValue(newValue);
//                cnt++;
//                if (cnt > LEN) break;
            }
            
            temp = this;
//            cnt = 0;
            while (temp.prev != null) {
                temp = temp.prev;
                temp.setValue(newValue);
//                cnt++;
//                if (cnt > LEN) break;
            }
            
        }
        
		public String toString() {
            return String.format("[Cell %d] prev : %d, next : %d", index, prev==null ? -1 : prev.index, 
                                next==null ? -1 : next.index);
        }
        
        public void updateLinkedCellWithVisited(String newValue, boolean[] visited) {
            setValue(newValue);
            visited[index] = true;
            
            Cell temp = this;
//            int cnt = 0;
            while(temp.next != null) {
                temp = temp.next;
                temp.setValue(newValue);
                visited[temp.index] = true;
//                cnt++;
//                if (cnt > LEN) break;
            }
            
            temp = this;
//            cnt = 0;
            while (temp.prev != null) {
                temp = temp.prev;
                temp.setValue(newValue);
                visited[temp.index] = true;
//                cnt++;
//                if (cnt > LEN) break;
                
            }
        }
        
        public void addCell(Cell cell) {
            if (this.next != null || cell.prev != null) {
                throw new IllegalArgumentException();
            }
            this.next = cell;
            cell.prev = this;
        }
        
        public Cell findLastCell() {
            Cell now = this;
//            int cnt = 0;
            while(now.next != null) {
                now = now.next;
//                cnt++;
//                if (cnt > LEN) break;
            }
            return now;
        }
        
        public Cell findFirstCell() {
            Cell now = this;
//            int cnt = 0;
            while(now.prev != null) {
                now = now.prev;
//                cnt++;
//                if (cnt > LEN) break;
            }
            return now;
        }
        
        public boolean isMerged(Cell cell) {
            Cell now = this;
            
            while(now.next != null) {
                now = now.next;
                if (now.index == cell.index) return true;
            }
            
            now = this;
            while(now.prev != null) {
                now = now.prev;
                if (now.index == cell.index) return true;
            }
            
            return false;
        }
        
        public void initLinkedCells() {
            Cell now = this;
//            int cnt = 0;
            
            // right
            while(now.next != null) {
                now = now.next;
                now.prev.next = null;
                now.prev = null;
                now.value = null;
//                cnt++;
//                if (cnt > LEN) break;
            }
            
            now = this;
//            cnt =0;
            while(now.prev != null) {
                now = now.prev;
                now.next.prev = null;
                now.next = null;
                now.value = null;
//                cnt++;
//                if (cnt > LEN) break;
            }
        }     
        
        public Set<Integer> findAllLinkedCells() {
            Set<Integer> cells = new HashSet<>();
            cells.add(index);
            Cell now = this;
            
            // right
            while(now.next != null) {
                now = now.next;
                cells.add(now.index);
            }
            
            now = this;
            while(now.prev != null) {
                now = now.prev;
                cells.add(now.index);
            }
            return cells;
        }
    }
    
    static class Table {
        static final String UPDATE = "UPDATE";
        static final String MERGE = "MERGE";
        static final String UNMERGE = "UNMERGE";
        static final String PRINT = "PRINT";
        
        static final int SIZE = 50;
        static final int LEN = 2500;
        
        Cell[] cells;
        List<String> outputs;
        
        public Table() {
            cells = new Cell[LEN];
            for (int i=0; i<LEN; i++) {
                cells[i] = new Cell(i);
            }
            outputs = new ArrayList<>();
        }
        
        public void executeCommand(String command) {
            String[] parsedCommand = command.split(" ");
            if (parsedCommand[0].equals(UPDATE)) {
                if (parsedCommand.length == 4) {
                    update(Integer.parseInt(parsedCommand[1])-1, Integer.parseInt(parsedCommand[2])-1, parsedCommand[3]);
                    
                } else if (parsedCommand.length == 3) {
                    update(parsedCommand[1], parsedCommand[2]);
                }
            } else if (parsedCommand[0].equals(MERGE)) {
                merge(Integer.parseInt(parsedCommand[1])-1,
                      Integer.parseInt(parsedCommand[2])-1,
                      Integer.parseInt(parsedCommand[3])-1,
                      Integer.parseInt(parsedCommand[4])-1);
            } else if (parsedCommand[0].equals(UNMERGE)) {
               unmerge(Integer.parseInt(parsedCommand[1])-1,
                       Integer.parseInt(parsedCommand[2])-1); 
            } else if (parsedCommand[0].equals(PRINT)) {
                int r = Integer.parseInt(parsedCommand[1])-1;
                int c = Integer.parseInt(parsedCommand[2])-1;
                String value = cells[toLineIdx(r, c)].value;
                outputs.add(value == null ? "EMPTY" : value);
            } else {
                throw new IllegalArgumentException();
            }
        }
        
        public int toLineIdx(int r, int c) {
            return r * SIZE + c;
        }
        
        private void update(int r, int c, String newValue) {
            
            // [TEST given] 
            Cell target = cells[toLineIdx(r, c)];
            Set<Integer> originCells = target.findAllLinkedCells(); 
            
            int lineIdx = toLineIdx(r, c);
            cells[lineIdx].updateLinkedCells(newValue);        
            
            Set<Integer> afterCells = target.findAllLinkedCells();
            // TEST desc : 업데이트 이전과 이후의 셀들 구성은 달라지지 않는다.
//            if (originCells.size() != afterCells.size() || !originCells.containsAll(afterCells)) {
//                throw new IllegalArgumentException();
//            }
            
            // TEST desc : 업데이트 이후 셀들은 모두 newValue 값을 가지고 있다.
//            for (Integer num : originCells) {
//                if (!cells[num].value.equals(newValue)) {
//                    throw new IllegalArgumentException();
//                }
//            }
            
        }
        
        private void update(String value1, String value2) {
            // [TEST given]
            Set<Integer> originCells = new HashSet<>(); 
            for (int i=0; i<LEN; i++) {
                if (cells[i].value != null && cells[i].value.equals(value1)) {
                    originCells.add(i);
                }
            }
            
                    
            
            boolean[] visited = new boolean[LEN];
            for (int i=0; i<LEN; i++) {
               if (cells[i].value != null && !visited[i] && cells[i].value.equals(value1)) {
                   cells[i].updateLinkedCellWithVisited(value2, visited);
               }
            }
            
            // 검증1 - value1 값과 value2 값이 다르다면 update 후 value1 값은 없어야 한다.
//            if (value1.equals(value2)) return;
//            for (int i=0; i<LEN; i++) {
//                if (cells[i].value != null && cells[i].value.equals(value1)) {
//                    //System.out.printf("[ERROR in Cell %d] visited : %b\n", i, visited[i]);
//                    throw new IllegalArgumentException();
//                }
//            }
            
            // TEST desc : 기존 셀들의 값은 모두 value2와 같다.
//            for (Integer num : originCells) {
//                if (!cells[num].value.equals(value2)) {
//                    throw new IllegalArgumentException();
//                }
//            }
        }
        
        private void merge(int r1, int c1, int r2, int c2) {
            if (r1 == r2 && c1 == c2) return;
            Cell fromCell = cells[toLineIdx(r1, c1)];
            Cell toCell = cells[toLineIdx(r2,  c2)];
            if (fromCell.isMerged(toCell)) return;
            
            // [TEST] : 이전 셀들을 합친 후 모두 포함하고 있다.
            // [TEST] given
            Set<Integer> fromCellNum = fromCell.findAllLinkedCells();
            Set<Integer> toCellNum = toCell.findAllLinkedCells();
            String targetValue = cells[toLineIdx(r1, c1)].value == null ? cells[toLineIdx(r2, c2)].value : cells[toLineIdx(r1, c1)].value;
            
            toCell.updateLinkedCells(targetValue);
            if (cells[toLineIdx(r1, c1)].value == null) {
                fromCell.updateLinkedCells(targetValue);
            }
            fromCell.findLastCell().addCell(toCell.findFirstCell());
            
            // [TEST] then
            Set<Integer> totalCellNum1 = fromCell.findAllLinkedCells();
            Set<Integer> totalCellNum2 = toCell.findAllLinkedCells();
            
            // TEST desc : fromCell 로 찾은 모든 병합된 셀들에 병합 전의 셀들이 포함된다.
//            if (!(totalCellNum1.containsAll(fromCellNum) && totalCellNum1.containsAll(toCellNum))) {
//                throw new IllegalArgumentException();
//            }
            
            // TEST desc : toCell 로 찾은 모든 병합된 셀들에 병합 전의 셀들이 포함된다.
//            if (!(totalCellNum2.containsAll(fromCellNum) && totalCellNum2.containsAll(toCellNum))) {
//                throw new IllegalArgumentException();
//            }
            
            // TEST desc : 병합된 셀들의 수는 그 전의 각각의 병합 셀들이 가지고 있던 수의 합이다.
//            if (fromCellNum.size() + toCellNum.size() != totalCellNum1.size()) {
//                throw new IllegalArgumentException();
//            }
            
            // TEST desc : 병합된 셀들의 값은 모두 (r1, c1) 의 셀 값으로 같다.
//            for (Integer testCell : totalCellNum1) {
//                if (cells[testCell].value != targetValue) {
//                    throw new IllegalArgumentException();
//                }
//            } 
        }
        
        
        private void unmerge(int r, int c) {
            // [TEST given]
            Cell target = cells[toLineIdx(r, c)];
            Set<Integer> originCells = cells[toLineIdx(r, c)].findAllLinkedCells();
            String originValue = target.value;
            
            cells[toLineIdx(r, c)].initLinkedCells();
            
            // TEST desc : (r, c) 의 셀을 제외하고 모두 value로 null을 가져야 한다.
//            for (Integer cellNum : originCells) {
//                if (cells[cellNum].index == cells[toLineIdx(r, c)].index) continue;
//                if (cells[cellNum].value != null) {
//                    throw new IllegalArgumentException();
//                }
//            }
            
            // TEST desc : (r, c)의 셀은 원래 자신이 갖고 있던 값을 가져야 한다.
            // target.value = originValue;
            
//            if (originValue == null && target.value != null) {
//                throw new IllegalArgumentException();
//            }
//            
//            if (originValue != null && !originValue.equals(target.value)) {
//                throw new IllegalArgumentException();
//            }
            
            // TEST desc : unmerge 후 모든 셀들은 가지고 있는 링크가 없다.
//            for (Integer cellNum : originCells) {
//                if (cells[cellNum].next != null || cells[cellNum].prev != null) {
//                    throw new IllegalArgumentException();
//                }
//            }
        }
        
        private String select(int r, int c) {
           return cells[toLineIdx(r, c)].value; 
        }
    }
    
    public String[] solution(String[] commands) {
        // Test Table
        Table testTable = new Table();
        testTable.merge(0, 0, 0, 2);
        testTable.merge(0, 4, 0, 7);
        testTable.merge(0, 7, 0, 2);
        int idx = testTable.toLineIdx(0, 0);
        Set<Integer> goalCells = new HashSet<>();
        goalCells.add(0);
        goalCells.add(2);
        goalCells.add(4);
        goalCells.add(7);
        
        // TEST desc : findAllLinkedCells 를 통해 병합된 셀들의 인덱스를 가져올 수 있다.
//        Set<Integer> linkedCells = testTable.cells[idx].findAllLinkedCells();
//        if (linkedCells.size() != goalCells.size()) {
//            throw new IllegalArgumentException();
//        }

        // TEST desc : 모든 Cell의 인덱스를 포함하고 있다.
//        if (!linkedCells.containsAll(goalCells)) {
//            throw new IllegalArgumentException();
//        }
        
        Table table = new Table();
        for (String command : commands) {
            table.executeCommand(command);
        }
        for (int i=0; i<10; i++) {
            System.out.println(table.cells[i]);
        }
        
        // TEST desc : output에는 null이 없어야 한다.
//        for (String output : table.outputs) {
//            if (output == null) {
//                throw new IllegalArgumentException();
//            }
//        }
        return table.outputs.toArray(new String[0]);
    }
}