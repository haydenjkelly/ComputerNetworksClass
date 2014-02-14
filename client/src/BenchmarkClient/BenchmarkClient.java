package BenchmarkClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class BenchmarkClient {

	private static final int MTPORT = 4444;
	private static final int STPORT = 3333;

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		//Benchmark for Single Thread Server
		Socket sts = new Socket("localhost", STPORT);
		Socket mts = new Socket("localhost", MTPORT);
		
		System.out.println("Client is connected to the servers");
		
		PrintStream stps = new PrintStream(sts.getOutputStream());
		PrintStream mtps = new PrintStream(mts.getOutputStream());
		
		BufferedReader str = new BufferedReader(new InputStreamReader(sts.getInputStream()));
		BufferedReader mtr = new BufferedReader(new InputStreamReader(mts.getInputStream()));

		String line;
		
		//Benchmark for Single Thread Server
		
		long st_startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++)
		{
			stps.println("lowercase string");
			if ((line = str.readLine()) != null) {
				//Client Received from SimpleServer
			}
		}
		long st_endTime = System.nanoTime();
		
		long st_avg_duration = (st_endTime - st_startTime)/1000;
		
		System.out.println(st_avg_duration + " Average Nanoseconds for Single thread");
		
		stps.close();
		
		//Benchmark for Multiple Thread Server
		
		long mt_startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++)
		{
			mtps.println("lowercase string");
			if ((line = mtr.readLine()) != null) {
				//Client Received from MTServer
			}
		}
		long mt_endTime = System.nanoTime();
		
		long mt_avg_duration = (mt_endTime - mt_startTime)/1000;
		
		System.out.println(mt_avg_duration + " Average Nanoseconds for Multithread");
		
		mtps.close();
	}
}
