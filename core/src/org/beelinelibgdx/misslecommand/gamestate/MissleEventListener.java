package org.beelinelibgdx.misslecommand.gamestate;

import java.util.List;

public interface MissleEventListener {

    void onMissleReachedTarget(Missle missle);
    void onMissleDestroyed(Missle missle);

}
