import java.util.ArrayList;

public class RRQ2Scheduler extends Scheduler {

	public RRQ2Scheduler (ArrayList<Process> list) {
		super(list);
	}
	
	public String getType() {
		return "Round Robin";
	}
	
	public Process getNextActiveProcess() {
		int maxReadyTime = -1;
		Process pro = null;
		for (Process p: list) {
			if (p.getStatus() == Status.RUNNING) {
				cpuCyclesActive ++;
				return null;
			}
			if (p.getStatus() == Status.READY) {
				if (p.getReadyTime() > maxReadyTime) {
					pro = p;
					maxReadyTime = p.getReadyTime();
				}
			}
		}
		return pro;
	}
}
