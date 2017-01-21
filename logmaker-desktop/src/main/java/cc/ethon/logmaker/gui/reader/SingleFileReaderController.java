package cc.ethon.logmaker.gui.reader;

import java.io.File;

public abstract class SingleFileReaderController<M extends SingleFileReaderModel> extends LogReaderController {

	protected final M model;

	public SingleFileReaderController(M model) {
		this.model = model;
	}

	@Override
	public void postProcess() {
		if (model.getDeleteFile().get()) {
			final File file = new File(model.getFile().get());
			if (file.exists()) {
				file.delete();
			}
		}
	}

}
