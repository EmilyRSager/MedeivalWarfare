package mw.server.gamelogic;



/**
 * @author emilysager
 */
public class Unit {
    
    private UnitType aUnitType;
    private ActionType aActionType;
    public Unit(UnitType pUnitType)
    {
    	aUnitType = pUnitType; 
    }
    
    /**
     * 
     * @param pUnitType 
     * Updates the unitType
     */
    public void setUnitType(UnitType pUnitType) {
      aUnitType = pUnitType; 
    }

    /**
     * 
     * @return unitType
     */
    public UnitType getUnitType() {
       
        return aUnitType;
    }

    public ActionType getActionType() {
       
        return aActionType;
    }

    public void setActionType(ActionType pActionType) {
        aActionType = pActionType; 
    }

   
}
