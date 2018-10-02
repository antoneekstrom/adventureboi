package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gamelogic.ObjectStorage;

public class InputInteract extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String action = "";
    private int id = 0;
    private boolean released;
    
    public InputInteract(String a, boolean r, int playerid) {
        action = a;
        id = playerid;
        released = r;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (action.equals("interact1") && !released) {
            ObjectStorage.getPlayer(1).interact();
        }
	}

}