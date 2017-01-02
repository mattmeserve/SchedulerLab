import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Scheduler {
	
	protected int cycle = 0;
	protected ArrayList<Process> list = new ArrayList<Process>();
	protected int cpuCyclesActive = 0;
	protected int ioCyclesActive = 0;
	protected Scanner scan;
	
	public Scheduler(ArrayList<Process> list) {
		this.list = list;
		try {
			scan = new Scanner(new File("random-numbers"));
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
	}
	
	public ArrayList<Process> getList() {
		return list;
	}
	
	public void sortList() {
		boolean sorted = false;
		while (!sorted) {
			sorted = true;
			for (int i = 0; i < list.size() - 1; i ++) {
				if (list.get(i).getA() > list.get(i+1).getA()) {
					Process temp = new Process(list.get(i).getA(), list.get(i).getB(), list.get(i).getC(), list.get(i).getM());
					list.set(i, list.get(i+1));
					list.set(i + 1, new Process(temp.getA(), temp.getB(), temp.getC(), temp.getM()));
					sorted = false;
				}
			}
		}
	}
	
	public int randomOS(int u) {
		return (1 + (scan.nextInt() % u));
	}
	
	public void printProcesses() {
		for (int i = 0; i < list.size(); i ++) {
			System.out.println("\nProcess " + i + ":");
			list.get(i).printProcess();
		}
		
	}
	
	public void printSummary() {
		System.out.println("Summary Data:");
		System.out.println("\tFinishing time: " + (cycle - 1));
		System.out.println("\tCPU Utilization: " + ((double) cpuCyclesActive / (cycle - 1)));
		System.out.println("\tI/O Utilization: " + ((double) ioCyclesActive / (cycle - 1)));
		System.out.println("\tThroughput: " + ((100.0 * list.size()) / (cycle - 1)) + " processes per hundred cycles");
		int totalTurnaroundTime = 0;
		for (Process p: list) {
			totalTurnaroundTime += p.getTurnaroundTime();
		}
		System.out.println("\tAverage turnaround time: " + ((double) totalTurnaroundTime / list.size()));
		int totalWaitingTime = 0;
		for (Process p: list) {
			totalWaitingTime += p.getWaitingTime();
		}
		System.out.println("\tAverage waiting time: " + ((double) totalWaitingTime / list.size()));
	}
	
	public String getType() {
		System.err.println("Error: called Scheduler getType()");
		return null;
	}
	
	public Process getNextActiveProcess() {
		System.err.println("Error: called Scheduler getNextActiveProcess");
		return null;
	}
	
	public boolean doCycle(boolean verbose) {
		boolean hasFoundBlockedProcess = false;
		int numTerminatedProcesses = 0;
		int timeToSetRunning = 0;
		Process active = getNextActiveProcess();
		if (verbose) {
			System.out.print("Before cycle\t" + cycle + ":");
		}
		for (Process p: list) {
			if (p.getStatus() == Status.BLOCKED && !hasFoundBlockedProcess) {
				ioCyclesActive ++;
				hasFoundBlockedProcess = true;
			}
			if (p == active) {
				cpuCyclesActive ++;
				if (p.wasBlocked()) {
					timeToSetRunning = randomOS(p.getB());
				}
				
				p.setRunning(timeToSetRunning, (this instanceof RRQ2Scheduler));
			}
			if (verbose) {
				if (p.getStatus() == Status.READY) {
					System.out.print("\t" + p.getStatus() + " 0\t");
				} else {
					System.out.print("\t" + p.getStatus() + " " + p.getTimeRemaining(this instanceof RRQ2Scheduler));
				}
			}
			p.runProcess();
			p.updateStatus(cycle, this);
			if (p.getStatus() == Status.TERMINATED) {
				numTerminatedProcesses ++;
			}
		}
		if (verbose) {
			System.out.println();
		}
		cycle ++;
		return numTerminatedProcesses != list.size();
	}
}
