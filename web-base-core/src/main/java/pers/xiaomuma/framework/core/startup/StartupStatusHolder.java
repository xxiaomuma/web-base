package pers.xiaomuma.framework.core.startup;

import java.util.concurrent.atomic.AtomicBoolean;

public class StartupStatusHolder {

	private static AtomicBoolean startup = new AtomicBoolean(false);

	public static boolean tryStartup() {
		return startup.compareAndSet(false, true);
	}

	public static boolean isStartup() {
		return startup.get();
	}
}
