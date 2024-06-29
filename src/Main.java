public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
        	GameLauncher launcher = new GameLauncher(); 
            launcher.start();
        });
    }
}

class GameLauncher {
    public void start() {
        new GamePanel(); 
    }
}