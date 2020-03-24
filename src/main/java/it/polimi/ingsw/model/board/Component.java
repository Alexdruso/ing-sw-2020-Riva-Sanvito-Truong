package it.polimi.ingsw.model.board;

public enum Component {
    BLOCK {
        public Buildable getInstance(){
            return new Block();
        }
    },
    DOME {
        public Buildable getInstance() { return new Dome(); }
    };

    public abstract Buildable getInstance();
}
