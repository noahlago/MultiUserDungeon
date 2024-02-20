package model;

public class MUD {
    private Map map;
    private Character player;

    /**
     * Instance of a MUD game
     * @param map -- map to use
     * @param name -- name of user
     */
    public MUD(Map map, String name){
        this.map = map;
        this.player = new Pc(100, 10, name, new Inventory(), 0);
    }

    /**
     * @return map toString
     */
    public Map getMap(){
        return map;
    }

    /**
     * @return character toString
     */
    public Character getPlayerStats(){
        return player; // make sure player has a toString
    }
}