package test1.grid.observer;

public enum FiredProperties {
	KEY_UP("KEY_UP"), KEY_DOWN("KEY_DOWN"), KEY_LEFT("KEY_LEFT"), KEY_RIGHT("KEY_RIGHR"), MOUSE_R_CLICK("MOUSE_R_CLICK"),MOUSE_L_CLICK("MOUSE_L_CLICK"),
	MOUSE_HOVER("MOUSE_HOVER"), HIGHLIGHT_CHANGE ("HIGHLIGHT_CHANGE"), HIGHLIGHT_LABEL("HIGHLIGHT_LABEL");

	private final String text;

	private FiredProperties(final String text) {
		this.text = text;
	}


	@Override
	public String toString() {
		return text;
	}
}
