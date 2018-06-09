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
    private boolean released;
    
    public PlayerMovement(String a, boolean r, int playerid) {
        action = a;
        id = playerid;
        released = r;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
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
                NewObjectStorage.getPlayer(id).doJump(released);
            }
            if (action.equals("sprint")) {
                NewObjectStorage.getPlayer(id).sprint(released);
            }
            if (action.equals("abilityright")) {
                if (!released) {
                    NewObjectStorage.getPlayer(id).releaseAbility();
                }
                else {
                    NewObjectStorage.getPlayer(id).useAbility("right", released);
                }
            }
            if (action.equals("abilityleft")) {
                if (!released) {
                    NewObjectStorage.getPlayer(id).releaseAbility();
                }
                else {
                    NewObjectStorage.getPlayer(id).useAbility("left", released);
                }
            }
        }
	}
}
