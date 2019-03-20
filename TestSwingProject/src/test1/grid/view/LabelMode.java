package test1.grid.view;

public enum LabelMode {
	COMMON ("COMMON"), BLINKED("BLINKED"), RUNNING_STR("RUNNING_STR");

	private final String text;

	private LabelMode(final String text) {
		this.text = text;
	}


	@Override
	public String toString() {
		return text;
	}
}
