import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class Main {
	
	private static int numProcesses = 0;
	
	public static void main(String[] args) {
		
		try {
			boolean verbose = (args[0].equals("--verbose"));
			File input;
			if (verbose) {
				input = new File(args[1]);
			} else {
				input = new File(args[0]);
			}
			Scanner sc = new Scanner(input);
			ArrayList<Process> list1 = new ArrayList<Process>();
			ArrayList<Process> list2 = new ArrayList<Process>();
			ArrayList<Process> list3 = new ArrayList<Process>();
			ArrayList<Process> list4 = new ArrayList<Process>();
			numProcesses = sc.nextInt();
			int a, b, c, m;
			for (int i = 0; i < numProcesses; i ++) {
				a = sc.nextInt();
				b = sc.nextInt();
				c = sc.nextInt();
				m = sc.nextInt();
				list1.add(new Process(a, b, c, m));
				list2.add(new Process(a, b, c, m));
				list3.add(new Process(a, b, c, m));
				list4.add(new Process(a, b, c, m));
			}
			sc.close();
			
			Scheduler sched = new FCFSScheduler(list1);
			runScheduler(sched, verbose);
			System.out.println("\n\n");
			
			
			sched = new RRQ2Scheduler(list2);
			runScheduler(sched, verbose);
			System.out.println("\n\n");
			
			
			sched = new UniprogrammedScheduler(list3);
			runScheduler(sched, verbose);
			System.out.println("\n\n");
			
			
			sched = new SJFScheduler(list4);
			runScheduler(sched, verbose);
			System.out.println("\n\n");
			
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
	}
	
	
	public static void runScheduler(Scheduler scheduler, boolean verbose) {
		
		System.out.print("The original input was: " + numProcesses);
		for (Process p: scheduler.getList()) {
			System.out.print(" " + p);
		}
		System.out.println();
		
		scheduler.sortList();
		
		System.out.print("The (sorted) input was: " + numProcesses);
		
		for (Process p: scheduler.getList()) {
			System.out.print(" " + p);
		}
		System.out.println("\n");
		if (verbose) {
			System.out.println("This detailed printout gives the state and remaining burst for each process\n");
		}
		
		
		while(scheduler.doCycle(verbose)) {
		}
		
		System.out.println("The scheduling algorithm used was " + scheduler.getType());
		
		if (verbose) {
			scheduler.printProcesses();
		}
		System.out.println();
		scheduler.printSummary();
	}
}
