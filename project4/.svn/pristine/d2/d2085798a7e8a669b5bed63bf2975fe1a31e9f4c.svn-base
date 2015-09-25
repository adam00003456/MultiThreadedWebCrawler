/**
 * QueryWords class makes objects that can be used to compare based upon
 * frequency, position, and file name of a certain keyword.
 * 
 * @author Adam
 * 
 */

public class SearchResult implements Comparable<SearchResult> {
	private int frequency;
	private int position;
	private final String file;

	/**
	 * Constructor
	 * */
	public SearchResult(String filepath, int freq, int p) {
		file = filepath;
		frequency = freq;
		position = p;

	}

	/**
	 * Update a matching word's frequency and position
	 * 
	 * @param frequency
	 * @param position
	 */
	public void update(int frequency, int position) {
		this.frequency = this.frequency + frequency;
		if (this.position > position) {
			this.position = position;
		}

	}

	/**
	 * Return position.
	 * 
	 * @return
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Return filename.
	 * 
	 * @return
	 */
	public String getFilename() {
		return file;
	}

	/**
	 * 
	 * print out the filename, the frequency or length of locations in an array,
	 * and the first number in the locations array for the particular search
	 * result
	 */
	@Override
	public String toString() {
		String result = "\"" + file + "\"" + ", " + frequency + ", " + position;
		return result;
	}

	/**
	 * Have compare to compare QueryWords object with each other based upon
	 * their frequency, position, and filename.
	 */
	@Override
	public int compareTo(SearchResult searchresult) {
		if (this.frequency == searchresult.frequency) {

			if (this.position == searchresult.position) {

				return this.file.compareToIgnoreCase(searchresult.file);

			} else
				return this.position - searchresult.position;
		} else
			return searchresult.frequency - this.frequency;
	}

}
