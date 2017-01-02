import java.util.ArrayList;

public class UniprogrammedScheduler extends Scheduler{
	
	public UniprogrammedScheduler (ArrayList<Process> list) {
		super(list);
	}
	
	public String getType() {
		return "Uniprocessing";
	}
	
	public Process getNextActiveProcess() {
		for (Process p: list) {
			if (p.getStatus() == Status.RUNNING) {
				cpuCyclesActive ++;
				return null;
			}
			if (p.getStatus() == Status.BLOCKED) {
				return null;
			} else {
				if (p.getStatus() == Status.READY) {
					return p;
				}
			}
		}
		return null;
	}
}
