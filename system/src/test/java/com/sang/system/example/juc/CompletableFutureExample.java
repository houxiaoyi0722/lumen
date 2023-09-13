package com.sang.system.example.juc;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CompletableFutureExample {


    @Test
    public void init (){
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //1、使用runAsync或supplyAsync发起异步调用
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            return "result1";
        }, executor);
        //2、CompletableFuture.completedFuture()直接创建一个已完成状态的CompletableFuture
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture("result2");
        //3、先初始化一个未完成的CompletableFuture，然后通过complete()、completeExceptionally()，完成该CompletableFuture
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("success");
    }

    /**
     * CompletableFuture的supplyAsync方法，提供了异步执行的功能，线程池也不用单独创建了。实际上，它CompletableFuture使用了默认线程池是ForkJoinPool.commonPool 。
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @Test
    public void completableFutureExample1() throws InterruptedException, ExecutionException, TimeoutException {

        long startTime = System.currentTimeMillis();


        //调用用户服务获取用户基本信息
        // supplyAsync 包含返回值
        // runAsync 不包含返回值
        CompletableFuture<UserInfo> completableUserInfoFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return new UserInfo("111","222");
        });

        Thread.sleep(300); //模拟主线程其它操作耗时

        CompletableFuture<MedalInfo> completableMedalInfoFuture = CompletableFuture.supplyAsync(() -> new MedalInfo("aaa","ccc"));

        UserInfo userInfo = completableUserInfoFuture.get(2, TimeUnit.SECONDS);//获取个人信息结果
        MedalInfo medalInfo = completableMedalInfoFuture.get();//获取勋章信息结果
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }

    /**
     * 创建异步任务
     * supplyAsync执行CompletableFuture任务，支持返回值
     * runAsync执行CompletableFuture任务，没有返回值。
     */
    @Test
    public void runAndSupply() {
        //可以自定义线程池,否则使用默认的 ForkJoinPool.commonPool
        ExecutorService executor = Executors.newCachedThreadPool();
        //runAsync的使用
        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> System.out.println("run"), executor);
        //supplyAsync的使用
        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.print("supply");
            return "supply";
        }, executor);
        //runAsync的future没有返回值，输出null
        System.out.println(runFuture.join());
        //supplyAsync的future，有返回值
        System.out.println(supplyFuture.join());
        executor.shutdown(); // 线程池需要关闭
    }

    /**
     * 做完第一个任务后，再做第二个任务 。某个任务执行完成后，执行回调方法
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void futureThen() throws ExecutionException, InterruptedException {

        /*
         * thenRun
         */
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("先执行第一个CompletableFuture方法任务");
                    return "1111";
                }
        );

        // thenRun前后两个任务没有参数传递，第二个任务也没有返回值
        // 调用thenRun方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池
        CompletableFuture<Void> thenRunFuture = orgFuture.thenRun(() -> {
            System.out.println("接着执行第二个任务");
        });

        // ***  带Async和不带的区别  *** 调用thenRunAsync执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是ForkJoin线程池
        CompletableFuture<Void> thenRunFutureAsync = orgFuture.thenRunAsync(() -> {
            System.out.println("接着执行第二个任务");
        });

        System.out.println(thenRunFuture.get());

        /*
         * thenAccept
         */
        CompletableFuture<String> orgFuture1 = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("原始CompletableFuture方法任务");
                    return "222";
                }
        );

        // thenAccept 有参数传递，回调方法无返回值
        CompletableFuture<Void> thenAcceptFuture = orgFuture1.thenAccept((a) -> {
            if ("222".equals(a)) {
                System.out.println("555");
            }

            System.out.println("测试");
        });

        System.out.println(thenAcceptFuture.get());


        /*
         * thenApply
         */
        CompletableFuture<String> orgFuture2 = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("原始CompletableFuture方法任务");
                    return "333";
                }
        );

        // thenApply 有参数传递，回调有返回值
        CompletableFuture<String> thenApplyFuture = orgFuture2.thenApply((a) -> {
            if ("333".equals(a)) {
                return "555";
            }

            return "thenApply";
        });

        System.out.println(thenApplyFuture.get());


        /*
         * exceptionally
         */
        // CompletableFuture的exceptionally方法表示，某个任务执行异常时，执行的回调方法;并且有抛出异常作为参数 ，传递到回调方法。
        // 由于异步执行的任务在其他线程上执行，而异常信息存储在线程栈中，因此当前线程除非阻塞等待返回结果，否则无法通过try\catch捕获异常。
        // CompletableFuture提供了异常捕获回调exceptionally，相当于同步调用中的try\catch。使用方法如下所示：
        CompletableFuture<String> orgFutureException = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("当前线程名称：" + Thread.currentThread().getName());
                    throw new RuntimeException();
                }
        );

        CompletableFuture<String> exceptionFuture = orgFutureException.exceptionally((e) -> {
            e.printStackTrace();
            return "程序异常";
        });

        System.out.println(exceptionFuture.get());

        /*
         * whenComplete
         */
        // CompletableFuture的whenComplete方法表示，某个任务执行完成后，执行的回调方法，无返回值 ；并且whenComplete方法返回的CompletableFuture的result是上个任务的结果 。
        CompletableFuture<String> orgFutureWhenComplete = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("当前线程名称：" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "444";
                }
        );

        CompletableFuture<String> rstFuture = orgFutureWhenComplete.whenComplete((a, throwable) -> {
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            System.out.println("参数： " + a);
            if ("444".equals(a)) {
                System.out.println("666");
            }
            System.out.println("233333");
        });

        System.out.println(rstFuture.get());

        /*
         * handle
         */
        // CompletableFuture的handle方法表示，某个任务执行完成后，执行回调方法，并且是有返回值的 ;并且handle方法返回的CompletableFuture的result是回调方法 执行的结果。
        CompletableFuture<String> orgFutureHandle = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("当前线程名称：" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "444";
                }
        );

        CompletableFuture<String> handleFuture = orgFutureHandle.handle((a, throwable) -> {
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            System.out.println("参数： " + a);
            if ("444".equals(a)) {
                System.out.println("666");
            }
            System.out.println("233333");
            return "555";
        });

        System.out.println(handleFuture.get());
    }

    /**
     * 多任务配合
     */
    @Test
    public void multitasking() {

        /**
         * 多个任务组合处理
         */
        /*
            AND组合关系
            thenCombine / thenAcceptBoth / runAfterBoth都表示：将两个CompletableFuture组合起来，只有这两个都正常执行完了，才会执行某个任务 。
            区别在于：
            thenCombine：会将两个任务的执行结果作为方法入参，传递到指定方法中，且有返回值
            thenAcceptBoth: 会将两个任务的执行结果作为方法入参，传递到指定方法中，且无返回值
            runAfterBoth 不会把执行结果当做方法入参 ，且没有返回值。
         */
        CompletableFuture<String> first = CompletableFuture.completedFuture("第一个异步任务");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<String> future = CompletableFuture
                //第二个异步任务
                .supplyAsync(() -> "第二个异步任务", executor)
                // (w, s) -> System.out.println(s) 是第三个任务
                .thenCombineAsync(first, (s, w) -> {
                    System.out.println(w);
                    System.out.println(s);
                    return "两个异步任务的组合";
                }, executor);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(future.join());
        executor.shutdown();

        /*
            OR 组合的关系
            applyToEither / acceptEither / runAfterEither 都表示：将两个CompletableFuture组合起来，只要其中一个执行完了,就会执行某个任务。
            applyToEither：会将已经执行完成的任务，作为方法入参，传递到指定方法中，且有返回值
            acceptEither: 会将已经执行完成的任务，作为方法入参，传递到指定方法中，且无返回值
            runAfterEither：不会把执行结果当做方法入参，且没有返回值。
         */
        //第一个异步任务，休眠2秒，保证它执行晚点
        CompletableFuture<String> firstOr = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(2000L);
                System.out.println("执行完第一个异步任务");}
            catch (Exception e){
                return "第一个任务异常";
            }
            return "第一个异步任务";
        });
        ExecutorService executorOr = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> futureOr = CompletableFuture
                //第二个异步任务
                .supplyAsync(() -> {
                    System.out.println("执行完第二个任务");
                    return "第二个任务";
                }, executorOr)
                //第三个任务
                .acceptEitherAsync(firstOr, System.out::println, executorOr);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorOr.shutdown();

        /**
         * AllOf
         */
        /*
            所有任务都执行完成后，才执行 allOf返回的CompletableFuture。如果任意一个任务异常，allOf的CompletableFuture，执行get方法，会抛出异常
         */
        CompletableFuture<Void> a = CompletableFuture.runAsync(()->{
            System.out.println("我执行完了");
        });
        CompletableFuture<Void> b = CompletableFuture.runAsync(() -> {
            System.out.println("我也执行完了");
            throw new RuntimeException("");
        });
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(a, b).whenComplete((m,k)->{
            System.out.println("finish " + m + " " + k);
        });

        /**
         * AnyOf
         */
        /*
            任意一个任务执行完，就执行anyOf返回的CompletableFuture。如果执行的任务异常，anyOf的CompletableFuture，执行get方法，会抛出异常
         */
        CompletableFuture<Void> a1 = CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我执行完了");
        });
        CompletableFuture<Void> b2 = CompletableFuture.runAsync(() -> {
            System.out.println("我也执行完了");
//            throw new RuntimeException("");
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(a1, b2).whenComplete((m,k)->{
            System.out.println("finish " + m + " " + k);
        });
        anyOfFuture.join();

        /**
         * thenCompose
         */
        /*
            thenCompose方法会在某个任务执行完成后，将该任务的执行结果,作为方法入参,去执行指定的方法。该方法会返回一个新的CompletableFuture实例
            如果该CompletableFuture实例的result不为null，则返回一个基于该result新的CompletableFuture实例；
            如果该CompletableFuture实例为null，然后就执行这个新任务
         */
        CompletableFuture<String> f = CompletableFuture.completedFuture("第一个任务");
        //第二个异步任务
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "第二个任务", executor1)
                .thenComposeAsync(data -> {
                    System.out.println(data);
                    return f; //使用第一个任务作为返回
                }, executor1);
        System.out.println(future1.join());
        executor1.shutdown();


        /**
         * 等待多任务完成
         */
//        CompletableFuture<Void> cf6 = CompletableFuture.allOf(cf3, cf4, cf5);
//        CompletableFuture<String> result = cf6.thenApply(v -> {
            //这里的join并不会阻塞，因为传给thenApply的函数是在CF3、CF4、CF5全部完成时，才会执行 。
//            result3 = cf3.join();
//            result4 = cf4.join();
//            result5 = cf5.join();
            //根据result3、result4、result5组装最终result;
//            return "result";
//        });
    }
    /**
     * 前面提到，异步回调方法可以选择是否传递线程池参数Executor，这里我们建议强制传线程池，且根据实际情况做线程池隔离。
     * 当不传递线程池时，会使用ForkJoinPool中的公共线程池CommonPool，这里所有调用将共用该线程池，核心线程数=处理器数量-1（单核核心线程数为1），
     * 所有异步回调都会共用该CommonPool，核心与非核心业务都竞争同一个池中的线程，很容易成为系统瓶颈。
     * 手动传递线程池参数可以更方便的调节参数，并且可以给不同的业务分配不同的线程池，以求资源隔离，减少不同业务之间的相互干扰。
     */

    /**
     * 线程池循环引用会导致死锁
     *
     * 以下方法第三行通过supplyAsync向threadPool1请求线程，
     * 并且内部子任务又向threadPool1请求线程。threadPool1大小为10，
     * 当同一时刻有10个请求到达，则threadPool1被打满，子任务请求线程时进入阻塞队列排队，
     * 但是父任务的完成又依赖于子任务，这时由于子任务得不到线程，父任务无法完成。主线程执行cf1.join()进入阻塞状态，并且永远无法恢复。
     * 为了修复该问题，需要将父任务与子任务做线程池隔离，两个任务请求不同的线程池，避免循环依赖导致的阻塞。
     */
    public void warn() {
        ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> {
            //do sth
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("child");
                return "child";
            }, threadPool1).join();//子任务
        }, threadPool1);
        System.out.println(cf1.join());
    }


    /**
     * 异常处理
     *
     * 有一点需要注意，CompletableFuture在回调方法中对异常进行了包装。大部分异常会封装成CompletionException后抛出，
     * 真正的异常存储在cause属性中，因此如果调用链中经过了回调方法处理那么就需要用Throwable.getCause()方法提取真正的异常。
     * 但是，有些情况下会直接返回真正的异常（Stack Overflow的讨论），最好使用工具类提取异常，如下代码所示：
     */
    public void exceptionHandling() {
        CompletableFuture<String> remarkResultFuture = CompletableFuture.supplyAsync(() -> "123");//业务方法，内部会发起异步rpc调用
        CompletableFuture<String> exceptionally = remarkResultFuture
                .thenApply(result -> {//这里增加了一个回调方法thenApply，如果发生异常thenApply内部会通过new CompletionException(throwable) 对异常进行包装
                    //这里是一些业务操作
                    System.out.println(111);
                    return "111";
                })
                .exceptionally(err -> {//通过exceptionally 捕获异常，这里的err已经被thenApply包装过，因此需要通过Throwable.getCause()提取异常
//                    log.error("WmOrderRemarkService.getCancelTypeAsync Exception orderId={}", extractRealException(err));
                    return "0";
                });
    }


    @AllArgsConstructor
    class MedalInfo {
        String name;
        String id;
    }


    @AllArgsConstructor
    class UserInfo {
        String name;
        String id;
    }

    static Throwable extractRealException(Throwable throwable) {
        //这里判断异常类型是否为CompletionException、ExecutionException，如果是则进行提取，否则直接返回。
        if (throwable instanceof CompletionException || throwable instanceof ExecutionException) {
            if (throwable.getCause() != null) {
                return throwable.getCause();
            }
        }
        return throwable;
    }


}
