import java.util.Map;
import java.util.TreeMap;

public class ArgumentParser {

	private final Map<String, String> argumentMap;

	/**
	 * Read in an array of string arguments from the command line output error
	 * message if wrong commands were inputted
	 * 
	 * @param array
	 *            of strings that contains the arguments
	 */
	public ArgumentParser(String[] args) {
		argumentMap = new TreeMap<String, String>();
		String current = "";
		String next = "";
		for (int i = 0; i < (args.length); i++) {
			if (isFlag(args[i]) == true) {
				current = args[i];
				argumentMap.put(current, null);
			}
			if (isValue(args[i]) == true) {
				next = args[i];
				if (current.length() > 0 && next.length() > 0) {
					argumentMap.put(current, next);
					current = "";
					next = "";
				}
			}

		}

	}

	/**
	 * check if a flag string starts with a dash and has a proper value
	 * associated with it
	 * 
	 * @param String
	 *            arg from the argumentMap object
	 * 
	 * @return true if the flag exists and has a non-null value else, return
	 *         false
	 */
	public static boolean isFlag(String arg) {
		arg = arg.replaceAll("\\s", "");
		if (arg.startsWith("-") && (arg.length() > 1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if a flag's value fulfills the proper format
	 * 
	 * @param string
	 *            arg variable
	 * 
	 * @return true if the arg matches to a valid value; else return false
	 */
	public static boolean isValue(String arg) {
		if (!arg.startsWith("-") && !arg.startsWith(" ")) {
			if (arg.length() >= 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * method will return the number of flags or keys that the arguementMap has
	 * contained
	 * 
	 * @return the size of the argumentMap if there is something in it, else
	 *         return 0 if the map is empty and return -1 if there is no
	 *         argumentMap
	 */
	public int numFlags() {
		return argumentMap.size();
	}

	/**
	 * Tests if the provided flag is stored in the map.
	 * 
	 * @param flag
	 *            - flag to check
	 * @return value if flag exists and has a value, or null if the flag does
	 *         not exist or does not have a value
	 */
	public boolean hasFlag(String flag) {
		return (argumentMap.containsKey(flag));
	}

	/**
	 * Tests if the provided flag has a value.
	 * 
	 * @param flag
	 *            - flag to check
	 * @return true if the flag exists and has a non-null value
	 */
	public boolean hasValue(String flag) {
		if (!(argumentMap.get(flag) == null)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the value of a flag if it exists, and null otherwise.
	 * 
	 * @param flag
	 *            - flag to check
	 * @return value of flag or null if flag does not exist or has no value
	 */
	public String getValue(String flag) {
		return argumentMap.get(flag);
	}

	/**
	 * create a printable string object of the argumentMap object
	 * 
	 * 
	 * @return printable argumentMap object
	 */

	@Override
	public String toString() {
		return argumentMap.toString();
	}

	/**
	 * gets a specified string object's value from the arguementMap
	 * 
	 * 
	 * @param string
	 *            to get from the argumentMap
	 * @return the value associated to the String key
	 */
	public String get(String string) {
		return argumentMap.get(string);
	}
}