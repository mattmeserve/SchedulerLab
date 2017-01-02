import java.util.ArrayList;

public class SJFScheduler extends Scheduler{

	public SJFScheduler (ArrayList<Process> list) {
		super(list);
	}
	
	public String getType() {
		return "Shortest Job First";
	}
	
	public Process getNextActiveProcess() {
		int minJobRemaining = 10000000;
		Process pro = null;
		for (Process p: list) {
			if (p.getStatus() == Status.RUNNING) {
				cpuCyclesActive ++;
				return null;
			}
			if (p.getStatus() == Status.READY && p.getJobLeft() > 0) {
				if (p.getJobLeft() < minJobRemaining) {
					pro = p;
					minJobRemaining = p.getJobLeft();
				}
				if (p.getJobLeft() == minJobRemaining) {
					if (p.getReadyTime() > pro.getReadyTime()) {
						pro = p;
					}
				}
			}
		}
		return pro;
	}
}
