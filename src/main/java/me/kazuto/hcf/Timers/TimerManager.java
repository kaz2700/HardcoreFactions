/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Timers;

import java.util.HashMap;
import lombok.Getter;
import me.kazuto.hcf.Main;
import org.bukkit.Bukkit;

public class TimerManager {

	@Getter
	private HashMap<Timer, Double> timers = new HashMap<>();

	private TimerManager() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this::checkTimerTimeout, 0, 2);
	}

	public void addTimer(Timer timer) {
		timers.put(timer, timer.getMaxTime());
	}

	public void checkTimerTimeout() {
		timers.forEach((timer, time) -> {
			timers.put(timer, time - 0.1);
			if (timers.get(timer) <= 0) {
				if (timer.getEndTask() != null)
					Bukkit.getScheduler().runTask(Main.getInstance(), timer.getEndTask());
				timers.remove(timer);
			}
		});
	}

	public boolean isActive(Timer timer) {
		return timers.containsKey(timer);
	}

	private static TimerManager instance;
	public static TimerManager getInstance() {
		if (instance == null)
			instance = new TimerManager();
		return instance;
	}
}
