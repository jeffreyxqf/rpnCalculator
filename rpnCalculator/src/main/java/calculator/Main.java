package calculator;

import java.util.Scanner;


public class Main {

	public static void main(String[] args) {

		Calculator calculator = new Calculator();
		Scanner scan = new Scanner(System.in);
		System.out.println("====== RPN Calculator =========");
		while (scan.hasNextLine()) {
			String s = scan.nextLine();
			try {
				calculator.preform(s);
			} catch (CalculatorException e) {
				System.out.println(e.getMessage());
			}finally {
				System.out.println(calculator.parseResult());
			}
		}
	}
}
