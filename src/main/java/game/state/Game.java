package game.state;

import game.*;
import game.helper.Asset;
import game.helper.ListScore;

public class Game extends State{
    private Display display;
    private EndGame endGame;
    private GameField field;
    private GameStage gameStage;
    private UI ui;
    private Player player;

    /**public Game(Display display){
        this.display = display;
    }**/

    public void loadGame(String path, Display display){
        GameSound.gameMusic();
        this.display = display;
        gameStage = GameStage.load(path, player);
        field = new GameField(gameStage, player);
        ui = new UI(display, field);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setEndGame(EndGame endGame) {
        this.endGame = endGame;
    }

    public GameField getField() {
        return field;
    }

    @Override
    public void update() {
        if (!ui.pauseGame()) {
            if (player.getLive() <= 0) {
                new ListScore(field.getMaptype()).saveScore(player.getName(), field.getCurrentLevel());
                endGame.draw("lose");
                State.setState(endGame);
                ui.clear();
            } else if (field.endGame()) {
                new ListScore(field.getMaptype()).saveScore(player.getName(), field.getCurrentLevel());
                int tick = 0;
                while (tick != 100000000) tick++;
                endGame.draw("win");
                ui.clear();
            }
            if (ui.nextWave()){
                if (!field.isContinue()) field.newLevel();
                ui.setclickNext(false);
            }
            field.update();
            if (ui.isOnMusic()) {
                GameSound.startGameMusic();
                GameSound.playSound(field.getSpawnList(), field.getDestroyList());
            } else GameSound.muteGameMusic();
        } else if (ui.savegame()) {
            GameSave.saveGame(field);
            System.exit(0);
        }
    }

    @Override
    public void render() {
        display.getGc().clearRect(0, 0, display.getWidth(), display.getHeight());
        ui.render();
        field.render(display.getGc());
    }

    @Override
    public void handle() {
        ui.setHandle();
    }
}
