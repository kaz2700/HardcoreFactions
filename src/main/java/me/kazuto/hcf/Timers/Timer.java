/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Timers;

import lombok.Getter;
import me.kazuto.hcf.Main;
import org.bukkit.Bukkit;

public class Timer {
	@Getter
	private Double maxTime;

	@Getter
	private Runnable endTask;

	public Timer(Double maxTime) {
		this.maxTime = maxTime;
	}

	public Timer(Double maxTime, Runnable endTask) {
		this.maxTime = maxTime;
		this.endTask = endTask;
	}

	public boolean hasEndTask() {
		return getEndTask() != null;
	}

	public void runEndTask() {
		Bukkit.getScheduler().runTask(Main.getInstance(), getEndTask());
	}
}
