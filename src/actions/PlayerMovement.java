package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import adventuregame.Input;
import gamelogic.NewObjectStorage;
import objects.NewPlayer;

public class PlayerMovement extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String action = "";
    private int id = 0;
    private NewPlayer player;
    private boolean released;
    
    public PlayerMovement(String a, boolean r, int playerid) {
        action = a;
        id = playerid;
        released = r;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        System.out.println(released);
        if (Input.getMovementEnabled()) {
            if (action.equals("moveleft")) {
                NewObjectStorage.getPlayer(id).moveLeft(released);
            }
            if (action.equals("moveright")) {
                NewObjectStorage.getPlayer(id).MoveRight(released);
            }
            if (action.equals("sit")) {
                NewObjectStorage.getPlayer(id).sit(released);
            }
            if (action.equals("jump")) {
                NewObjectStorage.getPlayer(id).jump(released);
            }
        }
	}
}
