	package mw.server.gamelogic;

import java.util.Observable;





/**
 * @author emilysager
 */
public class Unit extends Observable {
    
	private UnitType aUnitType;
    private ActionType aActionType;
    
    public Unit(UnitType pUnitType)
    {
    	aUnitType = pUnitType; 
    	aActionType = ActionType.READY;
    }

    /**
     * 
     * @return unitType
     */
    public UnitType getUnitType() 
    {
        return aUnitType;
    }

    public void setUnitType(UnitType pUnitType)
    {
    	aUnitType = pUnitType;
    	setChanged();
    }
    public ActionType getActionType() 
    {
        return aActionType;
    }

    public void setActionType(ActionType pActionType) 
    {
        aActionType = pActionType; 
        setChanged();
        
    }

   
}