	package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.Observable;

import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.UnitType;





/**
 * @author emilysager
 */
public class Unit extends Observable implements Serializable{
    
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
    	System.out.println("[Game] The Unit's actionType has been set to " + pActionType);
        aActionType = pActionType; 
        setChanged();
    }

   
}
