import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.*;

/**
 * Implementation of common Java concurrency patterns and concepts.
 * 
 * Java concurrency is a critical topic for technical interviews, especially for
 * senior developer positions and companies working with high-performance systems.
 * 
 * This class demonstrates key concurrency concepts including:
 * - Thread creation and lifecycle
 * - Synchronization
 * - Locks and atomic variables
 * - Thread pools and executors
 * - CompletableFuture for asynchronous programming
 * - Common concurrency patterns
 */
public class ConcurrencyExamples {

    public static void main(String[] args) throws Exception {
        System.out.println("===== Basic Thread Creation =====");
        basicThreadCreation();
        
        System.out.println("\n===== Synchronization Methods =====");
        synchronizationExamples();
        
        System.out.println("\n===== Lock Examples =====");
        lockExamples();
        
        System.out.println("\n===== Atomic Variables =====");
        atomicVariablesExample();
        
        System.out.println("\n===== Thread Pool and Executor =====");
        executorServiceExample();
        
        System.out.println("\n===== CompletableFuture Example =====");
        completableFutureExample();
        
        System.out.println("\n===== Producer-Consumer Pattern =====");
        producerConsumerExample();
        
        System.out.println("\n===== Read-Write Lock Example =====");
        readWriteLockExample();
        
        System.out.println("\n===== Countdown Latch Example =====");
        countdownLatchExample();
        
        System.out.println("\n===== Cyclic Barrier Example =====");
        cyclicBarrierExample();
        
        System.out.println("\n===== Semaphore Example =====");
        semaphoreExample();
    }
    
    /**
     * Demonstrates basic thread creation using different approaches
     */
    private static void basicThreadCreation() throws InterruptedException {
        // Method 1: Extending Thread class
        Thread t1 = new CustomThread();
        t1.start();
        
        // Method 2: Implementing Runnable interface
        Thread t2 = new Thread(new CustomRunnable());
        t2.start();
        
        // Method 3: Using anonymous inner class
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread using anonymous inner class is running");
            }
        });
        t3.start();
        
        // Method 4: Using lambda expression (Java 8+)
        Thread t4 = new Thread(() -> System.out.println("Thread using lambda expression is running"));
        t4.start();
        
        // Wait for all threads to complete
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        
        System.out.println("All basic threads completed");
    }
    
    // Example of extending Thread class
    static class CustomThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread extending Thread class is running");
        }
    }
    
    // Example of implementing Runnable interface
    static class CustomRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread implementing Runnable interface is running");
        }
    }
    
    /**
     * Demonstrates different synchronization techniques
     */
    private static void synchronizationExamples() throws InterruptedException {
        Counter counter = new Counter();
        
        // Create multiple threads to increment the counter
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.incrementSynchronized();
                    counter.incrementUnsynchronized();
                }
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("Synchronized count: " + counter.getSynchronizedCount());
        System.out.println("Unsynchronized count: " + counter.getUnsynchronizedCount());
    }
    
    static class Counter {
        private int synchronizedCount = 0;
        private int unsynchronizedCount = 0;
        
        // Synchronized method
        public synchronized void incrementSynchronized() {
            synchronizedCount++;
        }
        
        // Unsynchronized method (will have race conditions)
        public void incrementUnsynchronized() {
            unsynchronizedCount++;
        }
        
        public int getSynchronizedCount() {
            return synchronizedCount;
        }
        
        public int getUnsynchronizedCount() {
            return unsynchronizedCount;
        }
    }
    
    /**
     * Demonstrates different lock implementations
     */
    private static void lockExamples() throws InterruptedException {
        BankAccount account = new BankAccount(1000);
        
        // Simulate concurrent withdrawals using different threads
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    account.withdrawWithLock(100);
                }
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("Final balance: $" + account.getBalance());
    }
    
    static class BankAccount {
        private int balance;
        private final Lock lock = new ReentrantLock();
        
        public BankAccount(int initialBalance) {
            this.balance = initialBalance;
        }
        
        public void withdrawWithLock(int amount) {
            lock.lock();
            try {
                if (balance >= amount) {
                    // Simulate some processing time
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    balance -= amount;
                    System.out.println("Withdrew $" + amount + ", new balance: $" + balance);
                } else {
                    System.out.println("Insufficient funds: $" + balance);
                }
            } finally {
                lock.unlock();
            }
        }
        
        public int getBalance() {
            return balance;
        }
    }
    
    /**
     * Demonstrates the use of atomic variables
     */
    private static void atomicVariablesExample() throws InterruptedException {
        AtomicCounter atomicCounter = new AtomicCounter();
        
        // Create multiple threads to increment the counter
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicCounter.increment();
                }
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("Atomic counter value: " + atomicCounter.getValue());
    }
    
    static class AtomicCounter {
        private AtomicInteger count = new AtomicInteger(0);
        
        public void increment() {
            count.incrementAndGet();
        }
        
        public int getValue() {
            return count.get();
        }
    }
    
    /**
     * Demonstrates the use of ExecutorService and thread pools
     */
    private static void executorServiceExample() throws InterruptedException {
        // Create a fixed thread pool with 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        // Submit 10 tasks to the executor
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                try {
                    // Simulate some work
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Task " + taskId + " completed";
            });
        }
        
        // Shutdown the executor
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("All executor tasks completed");
    }
    
    /**
     * Demonstrates the use of CompletableFuture for asynchronous programming
     */
    private static void completableFutureExample() throws ExecutionException, InterruptedException {
        // Create a CompletableFuture
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate some computation
                Thread.sleep(200);
                return "Hello";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Error";
            }
        }).thenApply(result -> {
            // Transform the result
            return result + " World";
        }).thenApply(result -> {
            // Another transformation
            return result + "!";
        });
        
        // Wait for the future to complete and get the result
        String result = future.get();
        System.out.println("CompletableFuture result: " + result);
        
        // Example of combining multiple CompletableFutures
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");
        
        CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2);
        
        System.out.println("Combined result: " + combinedFuture.get());
    }
    
    /**
     * Demonstrates the Producer-Consumer pattern using a BlockingQueue
     */
    private static void producerConsumerExample() throws InterruptedException {
        // Create a blocking queue with capacity 5
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        
        // Create producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Producer producing: " + i);
                    queue.put(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Create consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    int value = queue.take();
                    System.out.println("Consumer consuming: " + value);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Start both threads
        producer.start();
        consumer.start();
        
        // Wait for both threads to complete
        producer.join();
        consumer.join();
        
        System.out.println("Producer-Consumer example completed");
    }
    
    /**
     * Demonstrates the use of ReadWriteLock for scenarios with multiple readers and occasional writers
     */
    private static void readWriteLockExample() throws InterruptedException {
        ReadWriteCache<String, String> cache = new ReadWriteCache<>();
        
        // Initialize cache
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        
        // Create reader threads
        Thread[] readers = new Thread[5];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println("Reader thread " + Thread.currentThread().getName() +
                                      " reads key1: " + cache.get("key1"));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        
        // Create writer thread
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                String newValue = "updatedValue" + i;
                cache.put("key1", newValue);
                System.out.println("Writer updated key1 to: " + newValue);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        // Start all threads
        for (Thread reader : readers) {
            reader.start();
        }
        writer.start();
        
        // Wait for all threads to complete
        for (Thread reader : readers) {
            reader.join();
        }
        writer.join();
        
        System.out.println("Final value for key1: " + cache.get("key1"));
    }
    
    static class ReadWriteCache<K, V> {
        private final Map<K, V> cache = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();
        
        public V get(K key) {
            readLock.lock();
            try {
                return cache.get(key);
            } finally {
                readLock.unlock();
            }
        }
        
        public void put(K key, V value) {
            writeLock.lock();
            try {
                cache.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }
    }
    
    /**
     * Demonstrates the use of CountDownLatch to coordinate multiple threads
     */
    private static void countdownLatchExample() throws InterruptedException {
        // Create a latch with count 3
        CountDownLatch latch = new CountDownLatch(3);
        
        // Create worker threads
        for (int i = 0; i < 3; i++) {
            final int workerId = i;
            new Thread(() -> {
                try {
                    // Simulate work
                    Thread.sleep(1000 + new Random().nextInt(1000));
                    System.out.println("Worker " + workerId + " completed");
                    
                    // Count down the latch
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        // Wait for all workers to complete
        System.out.println("Main thread waiting for workers...");
        latch.await();
        System.out.println("All workers completed, main thread proceeding");
    }
    
    /**
     * Demonstrates the use of CyclicBarrier to coordinate multiple threads
     */
    private static void cyclicBarrierExample() {
        final int numWorkers = 3;
        
        // Create a barrier that requires 3 parties to trip
        CyclicBarrier barrier = new CyclicBarrier(numWorkers, 
            () -> System.out.println("All workers reached the barrier, executing barrier action!"));
        
        // Create worker threads
        for (int i = 0; i < numWorkers; i++) {
            final int workerId = i;
            new Thread(() -> {
                try {
                    for (int phase = 0; phase < 2; phase++) {
                        // Simulate work for this phase
                        System.out.println("Worker " + workerId + " working in phase " + phase);
                        Thread.sleep(1000 + new Random().nextInt(1000));
                        
                        System.out.println("Worker " + workerId + " completed phase " + phase + 
                                          ", waiting at barrier");
                        
                        // Wait at the barrier
                        barrier.await();
                        
                        System.out.println("Worker " + workerId + " proceeding to next phase");
                    }
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
    
    /**
     * Demonstrates the use of Semaphore for limiting concurrent access
     */
    private static void semaphoreExample() throws InterruptedException {
        // Create a semaphore with 3 permits
        Semaphore semaphore = new Semaphore(3);
        
        // Create more threads than available permits
        Thread[] threads = new Thread[8];
        for (int i = 0; i < threads.length; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                try {
                    System.out.println("Thread " + threadId + " trying to acquire permit");
                    semaphore.acquire();
                    
                    System.out.println("Thread " + threadId + " acquired permit");
                    // Simulate work
                    Thread.sleep(1000);
                    
                    System.out.println("Thread " + threadId + " releasing permit");
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("All threads completed semaphore example");
    }
}
