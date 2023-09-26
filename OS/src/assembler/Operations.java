package assembler;

import kernel.ProcessState;
import memory.Ram;
import shell.Shell;

public class Operations {

	public static final String halt = "0000";
	public static final String mov = "0001";
	public static final String store = "0010";
	public static final String add = "0011";
	public static final String sub = "0100";
	public static final String mul = "0101";
	public static final String jmp = "0110";
	public static final String jmpl = "0111";
	public static final String jmpg = "1000";
	public static final String jmpe = "1001";
	public static final String load = "1010";
	public static final String jmpd = "1011";
	public static final String dec = "1100";
	public static final String inc = "1101";

    private static final Register[] registers = {
        new Register("R1", Constants.R1, 0),
        new Register("R2", Constants.R2, 0),
        new Register("R3", Constants.R3, 0),
        new Register("R4", Constants.R4, 0)
    };

	public static Register getRegister(int index) {
		if (index >= 0 && index < registers.length) {
			return registers[index];
		}
		return null;
	}
	
	public static void setRegister(int index, int value) {
		if (index >= 0 && index < registers.length) {
			registers[index].value = value;
		}
	}

	public static void store(String reg, String adr) {
		Register r = getRegister(reg);
		if (r != null)
			Ram.setAt(Integer.parseInt(adr, 2), r.value);
	}

	public static void load(String reg, String adr) {
		Register r = getRegister(reg);
		if (r != null) {
			if (!Ram.isOcupied(Integer.parseInt(adr, 2)))
				r.value = Ram.getAt(Integer.parseInt(adr, 2));
		}
	}

	// postavlja vrijednost registra1 na registar2
	public static void mov(String reg1, String reg2) {
		Register r1 = getRegister(reg1);
		Register r2 = getRegister(reg2);
		if (r1 != null && r2 != null) {
			r1.value = r2.value;
		}
	}

	// sabira vrijednosti 1. i 2. registra i cuva je na 1. registru
	public static void add(String reg, String val) {
		Register r = getRegister(reg);
		if (val.length() == 8) { // vrijednost
			if (r != null)
				r.value += Integer.parseInt(val, 2);
		} else if (val.length() == 4) { // registar
			Register r2 = getRegister(val);
			if (r != null && r2 != null)
				r.value += r2.value;
		}
	}

	// oduzima vrijednosti 1. i 2. registra i cuva je na 1. registru
	public static void sub(String reg, String val) {
		Register r = getRegister(reg);
		if (val.length() == 8) { // vrijednost
			if (r != null)
				r.value -= Integer.parseInt(val, 2);
		} else if (val.length() == 4) { // registar
			Register r2 = getRegister(val);
			if (r != null && r2 != null)
				r.value -= r2.value;
		}
	}

	// mnozi vrijednosti 1. i 2. registra i cuva je na 1. registru
	public static void mul(String reg, String val) {
		Register r = getRegister(reg);
		if (val.length() == 8) { // vrijednost
			if (r != null)
				r.value *= Integer.parseInt(val, 2);
		} else if (val.length() == 4) { // registar
			Register r2 = getRegister(val);
			if (r != null && r2 != null)
				r.value *= r2.value;
		}
	}

	public static void jmp(String adr) {
		int temp = Integer.parseInt(adr, 2);
		if (temp >= Shell.limit) {
			Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
			System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
			return;
		}
		Shell.PC = temp;
	}

	// jump if lesser
	public static boolean jmpl(String reg, String val, String adr) {
		Register r1 = getRegister(reg);
		if (val.length() == 8) { // drugi argument je vrijednost
			if (r1 != null && r1.value < Integer.parseInt(val, 2)) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		} else if (val.length() == 4) { // i drugi je registar
			Register r2 = getRegister(val);
			if (r1 != null && r2 != null && r1.value < r2.value) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		}
		return false;
	}

	// jump if greater
	public static boolean jmpg(String reg, String val, String adr) {
		Register r1 = getRegister(reg);
		if (val.length() == 8) { // drugi argument je vrijednost
			if (r1 != null && r1.value > Integer.parseInt(val, 2)) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		} else if (val.length() == 4) { // i drugi je registar
			Register r2 = getRegister(val);
			if (r1 != null && r2 != null && r1.value > r2.value) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		}
		return false;
	}

	// jump if equal
	public static boolean jmpe(String reg, String val, String adr) {
		Register r1 = getRegister(reg);
		if (val.length() == 8) { // drugi argument je vrijednost
			if (r1 != null && r1.value == Integer.parseInt(val, 2)) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		} else if (val.length() == 4) { // i drugi je registar
			Register r2 = getRegister(val);
			if (r1 != null && r2 != null && r1.value == r2.value) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		}
		return false;
	}

	// jump if different
	public static boolean jmpd(String reg, String val, String adr) {
		Register r1 = getRegister(reg);
		if (val.length() == 8) { // drugi argument je vrijednost
			if (r1 != null && r1.value != Integer.parseInt(val, 2)) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		} else if (val.length() == 4) { // i drugi je registar
			Register r2 = getRegister(val);
			if (r1 != null && r2 != null && r1.value != r2.value) {
				int temp = Integer.parseInt(adr, 2);
				if (temp >= Shell.limit) {
					Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
					System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
					return false;
				}
				Shell.PC = temp;
				return true;
			}
		}
		return false;
	}

	public static void inc(String reg) {
		Register r = getRegister(reg);
		r.value += 1;
	}

	public static void dec(String reg) {
		Register r = getRegister(reg);
		r.value -= 1;
	}

	public static void halt() {
		Shell.currentlyExecuting.setState(ProcessState.DONE);
	}

	// vraca registar na osnovu adrese registra
    private static Register getRegister(String adr) {
        for (Register r : registers) {
            if (r.getAddress().equals(adr)) {
                return r;
            }
        }
        return null;
    }

    public static void printRegisters() {
        System.out.println("Registers:");
        for (Register r : registers) {
            System.out.println(r.getName() + " value - [ " + r.value + " ]");
        }
    }

    public static void clearRegisters() {
        for (Register r : registers) {
            r.value = 0;
        }
    }

}