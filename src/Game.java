import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Tile[][] board;
    private int SIZE;
    private boolean moved;
    private int score; 

    public Game(int size) {
        this.SIZE = size;
        board = new Tile[SIZE][SIZE]; 
        score = 0;
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) { 
                board[i][j] = new Tile();
            }
        }
        spawn(); 
        spawn(); 
    }

    public Tile[][] getBoard() {
        return board; 
    }

    public int getBoardSize() {
        return this.SIZE;
    }

    public int getScore() {
        return this.score;
    }

    public void move(int dir) {
        moved = false;
        if (dir == 0) {
            moveLeft();
        } else if (dir == 1) {
            rotateRight();
            rotateRight();
            moveLeft();
            rotateRight();
            rotateRight();
        } else if (dir == 2) {
            rotateRight();
            rotateRight();
            rotateRight();
            moveLeft();
            rotateRight();
        } else if (dir == 3) {
            rotateRight();
            moveLeft();
            rotateRight();
            rotateRight();
            rotateRight();
        }

        if (moved) {
            spawn();
        }
    }

    private void moveLeft() {
        for (int i = 0; i < SIZE; i++) { 
            int[] line = new int[SIZE]; 
            int index = 0;

            for (int j = 0; j < SIZE; j++) { 
                if (!board[i][j].isEmpty()) {
                    line[index++] = board[i][j].getValue(); 
                }
            }

            for (int j = 0; j < index - 1; j++) {
                if (line[j] == line[j + 1] && line[j] != 0) { 
                    line[j] *= 2; 
                    score += line[j];
                    line[j + 1] = 0; 
                    moved = true; 
                }
            }

            int[] newLine = new int[SIZE]; 
            index = 0; 
            for (int j = 0; j < SIZE; j++) {
                if (line[j] != 0) {
                    newLine[index++] = line[j]; 
                }
            }

            for (int j = 0; j < SIZE; j++) { 
                if (board[i][j].getValue() != newLine[j]) {
                    moved = true; 
                }
                board[i][j].setValue(newLine[j]); 
            }
        }
    }

    private void rotateRight() {
        Tile[][] newBoard = new Tile[SIZE][SIZE]; 
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) { 
                newBoard[j][SIZE - 1 - i] = board[i][j]; 
            }
        }
        board = newBoard; 
    }

    private void spawn() {
        List<int[]> empty = new ArrayList<>(); 
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) { 
                if (board[i][j].isEmpty()) {
                    empty.add(new int[]{i, j}); 
                }
            }
        }
        if (!empty.isEmpty()) {
            int[] pos = empty.get(new Random().nextInt(empty.size())); 

            board[pos[0]][pos[1]].setValue(new Random().nextInt(2) == 0 ? 2 : 4); 
        }
    }

    public boolean isGameOver() {
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) { 
                if (board[i][j].isEmpty()) {
                    return false; 
                }
                if (j < SIZE - 1 && board[i][j].getValue() == board[i][j + 1].getValue()) { 
                    return false; 
                }
                if (i < SIZE - 1 && board[i][j].getValue() == board[i + 1][j].getValue()) { 
                    return false;
                }
            }
        }
        return true;
    }

    public boolean has2048() {
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) { 
                if (board[i][j].getValue() >= 2048) { 
                    return true; 
                }
            }
        }
        return false;
    }
    
    public class Tile {
        private int value;

        public Tile() {
            this(0);
        }

        public Tile(int value) {
            this.value = value;
        }

        public boolean isEmpty() {
            return value == 0;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}