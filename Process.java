
public class Process {
	
	private int a, b, c, m;
	private int timeRemaining;
	private int runningTimeRemaining;
	private int quantumTimeRemaining = -1;
	private int blockedTimeRemaining;
	private int readyTime;
	private Status s;
	
	private boolean wasBlocked = true;
	
	private int finishingTime = 0;
	private int turnaroundTime = 0;
	private int ioTime = 0;
	private int waitingTime = 0;
	
	
	public Process(int a, int b, int c, int m) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.m = m;
		s = Status.UNSTARTED;
		timeRemaining = c;
	}
	
	public void setRunning(int runningTimeRemaining, boolean isRR) {
		s = Status.RUNNING;
		if (isRR && runningTimeRemaining != 1) {
			quantumTimeRemaining = 2;
		} else if (isRR && runningTimeRemaining == 1){
			quantumTimeRemaining = 1;
		}
		if (wasBlocked || !isRR) {
			this.runningTimeRemaining = runningTimeRemaining;
			blockedTimeRemaining = m * runningTimeRemaining;
		}
		if (!wasBlocked && isRR) {
			
		}
	}
	
	public boolean wasBlocked() {
		return wasBlocked;
	}
	
	public Status getStatus() {
		return s;
	}
	
	public int getReadyTime() {
		return readyTime;
	}
	
	public void updateStatus(int cycle, Scheduler sched) {
		if (s == Status.RUNNING && timeRemaining <= 0) {
			s = Status.TERMINATED;
		}
		if (blockedTimeRemaining <= 0 && s == Status.BLOCKED) {
			wasBlocked = true;
			readyTime = 0;
			s = Status.READY;
		}
		if (s == Status.RUNNING && quantumTimeRemaining == 0 && runningTimeRemaining > 0) {
			wasBlocked = false;
			readyTime = 0;
			s = Status.READY;
		}
		if (s == Status.RUNNING && runningTimeRemaining <= 0) {
			s = Status.BLOCKED;
		}
		if (s == Status.UNSTARTED && a <= cycle) {
			readyTime = 0;
			s = Status.READY;
		}
	}
	
	public void runProcess() {
		
		switch (s) {
			case UNSTARTED:
				finishingTime++;
				break;
				
			case READY:
				waitingTime ++;
				turnaroundTime ++;
				finishingTime ++;
				readyTime++;
				break;
				
			case RUNNING:
				turnaroundTime ++;
				runningTimeRemaining --;
				timeRemaining --;
				finishingTime ++;
				if (quantumTimeRemaining > 0) {
					quantumTimeRemaining --;
				}
				break;
				
			case BLOCKED:
				ioTime ++;
				turnaroundTime ++;
				blockedTimeRemaining --;
				finishingTime ++;
				break;
				
			case TERMINATED:
				break;
				
			default:
				System.err.println("Error: case default");
				break;
		}
		
	}
	

	public void printProcess() {
		System.out.println("\t(A,B,C,M) = " + toString().replace(' ', ','));
		System.out.println("\tFinishing time: " + (finishingTime - 1));
		System.out.println("\tTurnaround time: " + turnaroundTime);
		System.out.println("\tI/O time: " + ioTime);
		System.out.println("\tWaiting time: " + waitingTime);
	}
	
	public int getTimeRemaining(boolean isRR) {
		if (s == Status.TERMINATED) {
			return 0;
		}
		if (runningTimeRemaining > 0) {
			if (isRR) {
				return quantumTimeRemaining;
			} else {
				return runningTimeRemaining;
			}
		} else {
			return blockedTimeRemaining;
		}
	}
	
	public int getJobLeft() {
		return timeRemaining;
	}
	
	public int getTurnaroundTime() {
		return turnaroundTime;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public String toString() {
		return "(" + a + " " + b + " " + c + " " + m + ")";
	}

	public int getA() {
		return a;
	}
	
	public int getB() {
		return b;
	}
	
	public int getC() {
		return c;
	}
	
	public int getM() {
		return m;
	}
	
	
}
