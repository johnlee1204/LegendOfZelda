import java.io.Serializable;

public class Screen implements Serializable{
    private TileManager tileManager;
    private  ObstructionManager obstructionManager;
    private DoorManager doorManager;
    private String screenName;
    private static final long serialVersionUID = 2049047115029990321L;
    public Screen(String screenName, TileManager tileManager, ObstructionManager obstructionManager, DoorManager doorManager)
    {
        this.tileManager = tileManager;
        this.obstructionManager = obstructionManager;
        this.screenName = screenName;
        this.doorManager = doorManager;
    }

    public TileManager getTileManager(){
        return tileManager;
    }
    public ObstructionManager getObstructionManager(){
        return obstructionManager;
    }
    public DoorManager getDoorManager(){
        return doorManager;
    }
}