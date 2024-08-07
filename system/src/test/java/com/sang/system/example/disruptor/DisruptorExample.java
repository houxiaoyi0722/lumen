package com.sang.system.example.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.sang.common.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DisruptorExample {


    /**
     * 队列数量, handleEventsWith多个队列数据一致 为一对多广播 可对每个队列编号，使用数据id取hash分配对列id，每个队列只处理属于自己队列的数据
     * handleEventsWithWorkerPool 为多队列分发
     *
     */
    private static final int disruptorBatchNum = 2;

    /**
     * 队列容量
     * RingBuffer 大小，必须是 2 的 N 次方;
     */
    private static final int disruptorBufferSize = 2048;

    RingBuffer<EventItem> ringBuffer = null;
    @Test
    public void test() {
        // config
        // 初始化Disruptor对象，单线程
        Disruptor<EventItem> disruptor =
                new Disruptor<>(EventItem::new, disruptorBufferSize,
                        DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

//        Disruptor<EventItem> disruptor =
//                new Disruptor<>(EventItem::new, disruptorBufferSize,
//                        DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());
        //错误处理
        disruptor.setDefaultExceptionHandler(new DisruptorExceptionHandler());

        // 监控数据
//        long bufferSize = disruptor.getBufferSize(); // 总大小
//        long cursor = disruptor.getCursor(); // 游标
//        long l = disruptor.getRingBuffer().remainingCapacity(); // 剩余容量


        //定义并发消费及并发清理对象
        List<EventItemHandler> followHandlerArray = new ArrayList<>();
        for (int i = 0; i < disruptorBatchNum; i++) {
            followHandlerArray.add(new EventItemHandler());
        }

        EventItemHandler[] array = new EventItemHandler[followHandlerArray.size()];
        EventItemHandler[] eventItemHandlers = followHandlerArray.toArray(array);

        //并行消费handleEventsWith,多队列广播
//        disruptor.handleEventsWith(eventItemHandlers);
        // 多队列分发
        disruptor.handleEventsWithWorkerPool(eventItemHandlers);


        // 获得RingBuffer对象
        ringBuffer = disruptor.start();


        // 业务- 生产者

        for (int i = 0; i < 1000; i++) {
            BusinessItem arg0 = new BusinessItem();
            arg0.setId((long) i);
            // 转换为事件对象并发布
            ringBuffer.publishEvent((EventItem target, long sequence, BusinessItem arg) -> {
                target.setId(arg.getId());
                //获得ringbuffer中的令牌
                target.setSequence(sequence);
            }, arg0);
        }

        disruptor.shutdown();
    }


    class DisruptorExceptionHandler implements ExceptionHandler<EventItem> {

        @Override
        public void handleEventException(Throwable ex, long sequenceOld, EventItem event) {
            log.error("event id {} {}",
                    event.getId(),
                    JSON.toJSONString(ex));
            // 重试
            ringBuffer.publishEvent((EventItem target, long sequence, EventItem arg) -> {
                target.setId(arg.getId());
                //获得ringbuffer中的令牌
                target.setSequence(sequence);
                target.setRetryNum(++arg.retryNum);
            }, event);

        }

        @Override
        public void handleOnStartException(Throwable ex) {
            log.error("",ex);
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            log.error("",ex);
        }
    }

    // 业务- 消费者
    @Slf4j
    static class EventItemHandler implements EventHandler<EventItem>, WorkHandler<EventItem> {

        @Override
        public void onEvent(EventItem event, long sequence, boolean endOfBatch) throws Exception {
//            log.info("partitionId:{}, event:{}", partitionId, event.getPartitionId());
            onEvent(event);
        }

        @Override
        public void onEvent(EventItem event) throws Exception {
            // 错误重试
            if (event.getId() == 10 && event.getRetryNum() < 3) {
                throw new BusinessException(500,"");
            }
            // 业务代码
            log.info(event.getId() + " " + event.getSequence());
        }
    }

    /**
     * 传输处理对象
     */
    @Getter
    @Setter
    class EventItem {

        //排序值
        private long sequence;

        //业务键值 自增id
        private Long id;

        private Long retryNum = 0L;

    }


    @Getter
    @Setter
    class BusinessItem {

        //排序值
        private long sequence;

        //业务键值 自增id
        private Long id;

    }

}
