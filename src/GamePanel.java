import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
    private final int TILE_SIZE = 100; 
    private final int GAP = 16;
    private int currentSize; 

    public GamePanel() {
        Object[] options = {"4x4", "6x6"};
        int choice = JOptionPane.showOptionDialog(null,
                "Pilih ukuran peta bos:",
                "Pilih size dulu cu8y",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == JOptionPane.YES_OPTION) { 
            currentSize = 4;
        } else if (choice == JOptionPane.NO_OPTION) { 
            currentSize = 6;
        } else { 
            System.out.println("Tidak ada ukuran dipilih, keluar.");
            System.exit(0); 
            return; 
        }

        game = new Game(currentSize); 
        setTitle("Calyx - " + currentSize + "x" + currentSize); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        int windowMargin = 30; 
        int totalGapSize = GAP * (currentSize + 1);
        setSize(TILE_SIZE * currentSize + totalGapSize,
                TILE_SIZE * currentSize + totalGapSize + windowMargin); 
        setResizable(false);
        
        // listener keyboard utk handle input
        addKeyListener(new KeyAdapter() { 
            @Override
            public void keyPressed(KeyEvent e) {
            	int dir = -1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        dir = 0;
                        break; 
                    case KeyEvent.VK_RIGHT:
                        dir = 1;
                        break; 
                    case KeyEvent.VK_UP:
                        dir = 2;
                        break; 
                    case KeyEvent.VK_DOWN:
                        dir = 3;
                        break; 
                }

                if (dir != -1) {
                    game.move(dir);
                    setTitle("Calyx - " + currentSize + "x" + currentSize + " | Skor: " + game.getScore());
                    repaint();
                    if (game.has2048()) {
                        JOptionPane.showMessageDialog(GamePanel.this, "You win!\nSkor Akhir: " + game.getScore()); 
                        askNewGame();
                    } else if (game.isGameOver()) {
                        JOptionPane.showMessageDialog(GamePanel.this, "Game Over!\nSkor Akhir: " + game.getScore()); 
                        askNewGame();
                    }
                }
            }
        });
        setVisible(true); 
    }
    
    // Dialog utk mulai game baru
    private void askNewGame() {
        int newGameChoice = JOptionPane.showConfirmDialog(GamePanel.this,
                "Mulai permainan baru?",
                "Permainan Selesai",
                JOptionPane.YES_NO_OPTION);
        if (newGameChoice == JOptionPane.YES_OPTION) {
            this.dispose();
            javax.swing.SwingUtilities.invokeLater(() -> {
                new GamePanel();
            });
        } else {
            System.exit(0);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        g.translate(GAP, GAP + 30);

        Game.Tile[][] board = game.getBoard(); 

        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) { 
                drawTile(g, board[i][j], j, i); 
            }
        }
    }

    private void drawTile(Graphics g, Game.Tile tile, int x, int y) {
        int value = tile.getValue();
        int xOffset = x * TILE_SIZE + x * GAP; 
        int yOffset = y * TILE_SIZE + y * GAP; 

        g.setColor(getTileColor(value)); 
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14); 

        g.setColor(value < 16 ? Color.BLACK : Color.WHITE); 
        g.setFont(new Font("SansSerif", Font.BOLD, value > 1000 ? 18 : 24)); 

        if (value != 0) { 
            String s = String.valueOf(value); 
            FontMetrics fm = g.getFontMetrics(); 
            int w = fm.stringWidth(s); 
            int h = fm.getAscent(); 
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + (TILE_SIZE - h) / 2 + h - (h/4)); 
        }
    }

    private Color getTileColor(int value) {
        Color color;
        
        // utk set warna buat tiap angka
        switch (value) {
            case 2:    
            	color = new Color(0xEEE4DA); 
            	break; 
            case 4:    
            	color = new Color(0xEDE0C8); 
            	break; 
            case 8:    
            	color = new Color(0xF2B179); 
            	break; 
            case 16:   
            	color = new Color(0xF59563); 
            	break; 
            case 32:   
            	color = new Color(0xF67C5F); 
            	break; 
            case 64:   
            	color = new Color(0xF65E3B); 
            	break; 
            case 128: 
            	color = new Color(0xEDCF72); 
            	break; 
            case 256:  
            	color = new Color(0xEDCC61);
            	break; 
            case 512: 
            	color = new Color(0xEDC850); 
            	break; 
            case 1024:
            	color = new Color(0xEDC53F); 
            	break; 
            case 2048: 
            	color = new Color(0xEDC22E); 
            	break; 
            default:   
            	color = new Color(0xCDC1B4); 
            	break; 
        }

        if (value > 2048) {
            if (value == 4096) color = new Color(0x3c3a32); 
        }
        return color;
    }
    
}