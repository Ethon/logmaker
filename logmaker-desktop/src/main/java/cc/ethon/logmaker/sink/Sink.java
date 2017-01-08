package cc.ethon.logmaker.sink;

import java.io.OutputStream;

public interface Sink {

	public OutputStream getOutputStream();

	public void applyContent();

}
