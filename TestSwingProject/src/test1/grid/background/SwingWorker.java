package test1.grid.background;

import java.awt.Component;
import java.util.concurrent.ExecutionException;

public abstract class SwingWorker<T> {
	private Component component;
	private javax.swing.SwingWorker<T, Object> worker;

	public SwingWorker(final Component component) {
		this.component = component;
		worker = new javax.swing.SwingWorker<T, Object>() {

			@Override
			protected T doInBackground() throws Exception {
				try {
					return SwingWorker.this.doInBackground();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void done() {
				SwingWorker.this.done();
				super.done();
			}

		};
	}

	protected abstract T doInBackground() throws Exception;

	protected void done() {

	}

	public final T get() {
		try {
			return worker.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void start () {
		 worker.execute();
	 }
}
