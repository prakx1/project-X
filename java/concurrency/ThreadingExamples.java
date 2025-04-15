/**
 * Examples of different threading and concurrency mechanisms in Java.
 * 
 * Threading and concurrency are common topics in Java technical interviews, especially
 * for senior roles. This file demonstrates various ways to create and manage threads,
 * as well as different synchronization mechanisms to avoid common concurrency issues.
 */
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.*;

public class ThreadingExamples {

    /**
     * Example 1: Creating threads using Thread class
     */
    static class ExtendThreadExample extends Thread {
        @Override
        public void run() {
            System.out.println("Thread running: " + Thread.currentThread().getName());
        }
    }

    /**
     * Example 2: Creating threads using Runnable interface
     */
    static class RunnableExample implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable running: " + Thread.currentThread().getName());
        }
    }

    /**
     * Example 3: Creating threads using Callable interface (returns a value)
     */
    static class CallableExample implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("Callable running: " + Thread.currentThread().getName());
            return "Result from " + Thread.currentThread().getName();
        }
    }

    /**
     * Example 4: Demonstrating race condition and synchronization
     */
    static class Counter {
        private int count = 0;

        // Method without synchronization - will cause race conditions
        public void incrementUnsafe() {
            count++;
        }

        // Method with synchronization - thread-safe
        public synchronized void incrementSafe() {
            count++;
        }

        // Using synchronized block instead of method
        public void incrementWithSyncBlock() {
            synchronized(this) {
                count++;
            }
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * Example 5: Using AtomicInteger for thread-safe operations
     */
    static class AtomicCounter {
        private AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            count.incrementAndGet();
        }

        public int getCount() {
            return count.get();
        }
    }

    /**
     * Example 6: Using ReentrantLock for more control over locking
     */
    static class LockCounter {
        private int count = 0;
        private final Lock lock = new ReentrantLock();

        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock(); // Always release lock in finally block
            }
        }

        public int getCount() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Example 7: Using ReadWriteLock for concurrent reads
     */
    static class ReadWriteCounter {
        private int count = 0;
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final Lock readLock = rwLock.readLock();
        private final Lock writeLock = rwLock.writeLock();

        public void increment() {
            writeLock.lock();
            try {
                count++;
            } finally {
                writeLock.unlock();
            }
        }

        public int getCount() {
            readLock.lock();
            try {
                return count;
            } finally {
                readLock.unlock();
            }
        }
    }

    /**
     * Example 8: Producer-Consumer problem using wait() and notify()
     */
    static class SharedQueue {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        public SharedQueue(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int item) throws InterruptedException {
            // Wait while queue is full
            while (queue.size() == capacity) {
                wait();
            }

            queue.add(item);
            System.out.println("Produced: " + item);

            // Notify consumer that item is available
            notify();
        }

        public synchronized int consume() throws InterruptedException {
            // Wait while queue is empty
            while (queue.isEmpty()) {
                wait();
            }

            int item = queue.remove();
            System.out.println("Consumed: " + item);

            // Notify producer that space is available
            notify();

            return item;
        }
    }

    /**
     * Example 9: Using BlockingQueue for producer-consumer pattern
     */
    static class BlockingQueueExample {
        private final BlockingQueue<Integer> queue;

        public BlockingQueueExample(int capacity) {
            this.queue = new ArrayBlockingQueue<>(capacity);
        }

        public void produce(int item) throws InterruptedException {
            queue.put(item);
            System.out.println("Produced with BlockingQueue: " + item);
        }

        public int consume() throws InterruptedException {
            int item = queue.take();
            System.out.println("Consumed with BlockingQueue: " + item);
            return item;
        }
    }

    /**
     * Example 10: Using CompletableFuture for asynchronous operations
     */
    public static void completableFutureExample() {
        // Create a CompletableFuture that runs asynchronously
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1); // Simulate long-running task
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Result from async operation";
        });

        // Add a callback to be executed when the future completes
        future.thenAccept(result -> System.out.println("Got result: " + result));

        // Chain multiple operations
        CompletableFuture<String> transformedFuture = future
                .thenApply(result -> result + " - transformed")
                .thenApply(transformed -> transformed + " - again");

        // Wait for the result
        try {
            String finalResult = transformedFuture.get(2, TimeUnit.SECONDS);
            System.out.println("Final result: " + finalResult);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Example 11: Using CountDownLatch for thread coordination
     */
    public static void countDownLatchExample() {
        final int COUNT = 5;
        CountDownLatch latch = new CountDownLatch(COUNT);

        // Start multiple threads
        for (int i = 0; i < COUNT; i++) {
            final int taskId = i;
            new Thread(() -> {
                try {
                    System.out.println("Task " + taskId + " started");
                    Thread.sleep((long) (Math.random() * 1000)); // Random work duration
                    System.out.println("Task " + taskId + " completed");
                    latch.countDown(); // Decrement the count
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        try {
            // Wait for all tasks to complete
            System.out.println("Waiting for all tasks to complete...");
            latch.await();
            System.out.println("All tasks completed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Example 12: Using CyclicBarrier for thread synchronization
     */
    public static void cyclicBarrierExample() {
        final int PARTIES = 3;
        CyclicBarrier barrier = new CyclicBarrier(PARTIES, () -> {
            // This is executed when the barrier is tripped
            System.out.println("All threads reached the barrier!");
        });

        for (int i = 0; i < PARTIES; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadId + " is doing phase 1 work");
                    Thread.sleep((long) (Math.random() * 1000));
                    System.out.println("Thread " + threadId + " waiting at barrier");
                    barrier.await(); // Wait at barrier

                    System.out.println("Thread " + threadId + " is doing phase 2 work");
                    Thread.sleep((long) (Math.random() * 1000));
                    System.out.println("Thread " + threadId + " waiting at barrier again");
                    barrier.await(); // Wait at barrier again

                    System.out.println("Thread " + threadId + " completed all phases");
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    /**
     * Example 13: Using Semaphore to control resource access
     */
    public static void semaphoreExample() {
        // Create a semaphore with 3 permits
        Semaphore semaphore = new Semaphore(3);

        // Start 5 threads that will try to acquire the semaphore
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("Thread " + threadId + " is trying to acquire a permit");
                    semaphore.acquire();
                    System.out.println("Thread " + threadId + " acquired a permit");

                    // Simulate work with the resource
                    Thread.sleep((long) (Math.random() * 2000));

                    System.out.println("Thread " + threadId + " is releasing the permit");
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    /**
     * Example 14: Using ThreadLocal for thread-isolated variables
     */
    public static void threadLocalExample() {
        // ThreadLocal to store a user ID for each thread
        ThreadLocal<String> userId = new ThreadLocal<>();

        Runnable task = () -> {
            // Set thread-specific value
            userId.set("User-" + Thread.currentThread().getId());
            
            // Do some processing
            System.out.println(Thread.currentThread().getName() + 
                    " processing for " + userId.get());
            
            // Clean up ThreadLocal to prevent memory leaks
            userId.remove();
        };

        // Run in multiple threads
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }
    }

    /**
     * Example 15: Thread pool using ExecutorService
     */
    public static void executorServiceExample() {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit tasks to the pool
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " executed by " + 
                        Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Result of task " + taskId;
            });
        }

        // Shutdown the executor
        executor.shutdown();
        try {
            // Wait for all tasks to complete
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Force shutdown if tasks take too long
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Example 16: ForkJoinPool for recursive tasks (useful for divide and conquer algorithms)
     */
    static class RecursiveSum extends RecursiveTask<Long> {
        private final long[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 1000; // When to switch to sequential

        public RecursiveSum(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                // Do the work sequentially
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            }

            // Split the work
            int mid = start + length / 2;
            RecursiveSum leftTask = new RecursiveSum(array, start, mid);
            RecursiveSum rightTask = new RecursiveSum(array, mid, end);

            // Fork the left task (run asynchronously)
            leftTask.fork();
            
            // Compute the right task directly
            long rightResult = rightTask.compute();
            
            // Join the left task (wait for its result)
            long leftResult = leftTask.join();
            
            // Combine the results
            return leftResult + rightResult;
        }
    }

    public static void forkJoinExample() {
        // Create an array with 10 million elements
        long[] array = new long[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        // Create a ForkJoinPool with default parallelism
        ForkJoinPool pool = ForkJoinPool.commonPool();
        
        // Create the task
        RecursiveSum task = new RecursiveSum(array, 0, array.length);
        
        // Start timing
        long start = System.currentTimeMillis();
        
        // Execute the task and get the result
        long sum = pool.invoke(task);
        
        // End timing
        long end = System.currentTimeMillis();
        
        System.out.println("Sum: " + sum);
        System.out.println("Time taken: " + (end - start) + " ms");
        
        // Verify with sequential sum
        start = System.currentTimeMillis();
        long seqSum = 0;
        for (long value : array) {
            seqSum += value;
        }
        end = System.currentTimeMillis();
        
        System.out.println("Sequential sum: " + seqSum);
        System.out.println("Sequential time: " + (end - start) + " ms");
    }

    /**
     * Example 17: Deadlock demonstration and prevention
     */
    public static void deadlockExample() {
        final Object resource1 = new Object();
        final Object resource2 = new Object();

        // Thread 1: Tries to lock resource1 then resource2
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1: Attempting to lock resource 1");
            synchronized (resource1) {
                System.out.println("Thread 1: Locked resource 1");
                
                try {
                    Thread.sleep(100); // Delay to ensure both threads overlap
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                System.out.println("Thread 1: Attempting to lock resource 2");
                synchronized (resource2) {
                    System.out.println("Thread 1: Locked resource 2");
                }
            }
        });

        // Thread 2: Tries to lock resource2 then resource1 (opposite order)
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2: Attempting to lock resource 2");
            synchronized (resource2) {
                System.out.println("Thread 2: Locked resource 2");
                
                try {
                    Thread.sleep(100); // Delay to ensure both threads overlap
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                System.out.println("Thread 2: Attempting to lock resource 1");
                synchronized (resource1) {
                    System.out.println("Thread 2: Locked resource 1");
                }
            }
        });

        System.out.println("Starting threads that will cause deadlock...");
        thread1.start();
        thread2.start();
        
        // Wait for threads to complete (they won't due to deadlock)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Threads are likely deadlocked now. In real code, you would:");
        System.out.println("1. Always acquire locks in the same order");
        System.out.println("2. Use tryLock() with timeout");
        System.out.println("3. Use higher-level concurrency utilities");
        
        // Interrupt the deadlocked threads
        thread1.interrupt();
        thread2.interrupt();
    }

    public static void main(String[] args) {
        System.out.println("===== Java Threading Examples =====\n");

        // Example 1: Creating threads using Thread class
        System.out.println("Example 1: Creating threads using Thread class");
        Thread thread1 = new ExtendThreadExample();
        thread1.start();
        
        // Example 2: Creating threads using Runnable interface
        System.out.println("\nExample 2: Creating threads using Runnable interface");
        Thread thread2 = new Thread(new RunnableExample());
        thread2.start();
        
        // Alternative using lambda
        Thread thread3 = new Thread(() -> {
            System.out.println("Lambda Runnable running: " + Thread.currentThread().getName());
        });
        thread3.start();
        
        // Example 3: Creating threads using Callable interface
        System.out.println("\nExample 3: Creating threads using Callable interface");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new CallableExample());
        
        try {
            String result = future.get();
            System.out.println("Callable result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        
        // Example 4: Demonstrating race condition and synchronization
        System.out.println("\nExample 4: Race condition and synchronization");
        Counter counter = new Counter();
        
        // Create threads that increment without synchronization
        Runnable unsafeTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementUnsafe();
            }
        };
        
        Thread unsafeThread1 = new Thread(unsafeTask);
        Thread unsafeThread2 = new Thread(unsafeTask);
        
        unsafeThread1.start();
        unsafeThread2.start();
        
        try {
            unsafeThread1.join();
            unsafeThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Unsafe count (expected 2000): " + counter.getCount());
        
        // Reset counter
        counter = new Counter();
        
        // Create threads that increment with synchronization
        Runnable safeTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementSafe();
            }
        };
        
        Thread safeThread1 = new Thread(safeTask);
        Thread safeThread2 = new Thread(safeTask);
        
        safeThread1.start();
        safeThread2.start();
        
        try {
            safeThread1.join();
            safeThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Safe count (expected 2000): " + counter.getCount());
        
        // Example 5: Using AtomicInteger
        System.out.println("\nExample 5: Using AtomicInteger");
        AtomicCounter atomicCounter = new AtomicCounter();
        
        Runnable atomicTask = () -> {
            for (int i = 0; i < 1000; i++) {
                atomicCounter.increment();
            }
        };
        
        Thread atomicThread1 = new Thread(atomicTask);
        Thread atomicThread2 = new Thread(atomicTask);
        
        atomicThread1.start();
        atomicThread2.start();
        
        try {
            atomicThread1.join();
            atomicThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Atomic count (expected 2000): " + atomicCounter.getCount());
        
        // Example 6-9: More advanced examples
        System.out.println("\nExample 10: CompletableFuture");
        completableFutureExample();
        
        System.out.println("\nExample 11: CountDownLatch");
        countDownLatchExample();
        
        System.out.println("\nExample 12: CyclicBarrier");
        cyclicBarrierExample();
        
        System.out.println("\nExample 13: Semaphore");
        semaphoreExample();
        
        System.out.println("\nExample 14: ThreadLocal");
        threadLocalExample();
        
        System.out.println("\nExample 15: ExecutorService");
        executorServiceExample();
        
        // Wait for all examples to complete
        try {
            Thread.sleep(5000);
            System.out.println("\nAll examples completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
