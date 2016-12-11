import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


/**
 * A simple custom lock that allows simultaneously read operations, but
 * disallows simultaneously write and read/write operations.
 * 
 * You do not need to implement any form or priority to read or write
 * operations. The first thread that acquires the appropriate lock should be
 * allowed to continue.
 */
public class MultiReaderLock {
	private int readers;
	private int writers;
	//Logger.getLogger
	public static final Logger logger = LogManager.getLogger(MultiReaderLock.class.getName());

	/**
	 * Initializes a multi-reader (single-writer) lock.
	 */
	public MultiReaderLock() {
		readers = 0;
		writers = 0;

	}

	/**
	 * Will wait until there are no active writers in the system, and then will
	 * increase the number of active readers.
	 */
	public synchronized void lockRead() {
		while (writers > 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				logger.log(Level.DEBUG, "Interrupted Exception for lockRead.");
			}
		}
		readers++;
	}

	/**
	 * Will decrease the number of active readers, and notify any waiting
	 * threads if necessary.
	 */
	public synchronized void unlockRead() {
		try {
			readers--;
			if (readers == 0) {
				notifyAll();
			}
		} catch (Exception e) {
			logger.log(Level.DEBUG, "Exception for unlockRead.");
		}
	}

	/**
	 * Will wait until there are no active readers or writers in the system, and
	 * then will increase the number of active writers.
	 */
	public synchronized void lockWrite() {
		while (readers > 0 || writers > 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				logger.log(Level.DEBUG, "Interrupted Exception for lockWrite.");
			}
		}
		writers++;
	}

	/**
	 * Will decrease the number of active writers, and notify any waiting
	 * threads if necessary.
	 */
	public synchronized void unlockWrite() {
		try {
			writers--;
			notifyAll();
		} catch (Exception e) {
			logger.log(Level.DEBUG, "Exception for unlockWrite.");
		}

	}
}
