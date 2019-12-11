package game.state;

public abstract class State {
    private static State state = null;

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        State.state = state;
        state.handle();
    }

    public abstract void update();
    public abstract void render();
    public abstract void handle();
}
