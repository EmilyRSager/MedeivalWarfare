package mw.shared.servercommands;

public class LoadGameCommand extends AbstractServerCommand{
	private final String aType = "LoadGameCommand";
	private String aGameName;
	
	/**
	 * Constructors
	 * @param pGameName
	 */
	public LoadGameCommand(String pGameName) {
		aGameName = pGameName;
	}
	
	@Override
	public void execute(Integer pClientID) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
