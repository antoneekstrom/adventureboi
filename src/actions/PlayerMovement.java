package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import adventuregame.Input;
import gamelogic.ObjectStorage;
import objects.Player;

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
                ObjectStorage.getPlayer(id).moveLeft(released);
            }
            if (action.equals("moveright")) {
                ObjectStorage.getPlayer(id).MoveRight(released);
            }
            if (action.equals("sit")) {
                ObjectStorage.getPlayer(id).sit(released);
            }
            if (action.equals("jump")) {
                ObjectStorage.getPlayer(id).doJump(released);
            }
            if (action.equals("sprint")) {
                ObjectStorage.getPlayer(id).sprint(released);
            }
            if (action.equals("abilityright")) {
                ObjectStorage.getPlayer(id).useAbility("right", released);
            }
            if (action.equals("abilityleft")) {
                ObjectStorage.getPlayer(id).useAbility("left", released);
            }
            if (action.equals("ability1")) {
            }
        }
	}
}
