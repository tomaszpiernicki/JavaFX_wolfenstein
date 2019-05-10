package view;

import javafx.scene.PerspectiveCamera;
import utils.DimentionUtils;

public class PerspectiveCameraWrapper extends PerspectiveCamera {

	public PerspectiveCameraWrapper() {
		super(true);

		this.setFarClip(DimentionUtils.blockSize * 20);
		this.setFieldOfView(50);
	}
}
