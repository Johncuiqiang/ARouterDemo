package arouter.cuiqiang.com.baselib.timer;

import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author cuiqiang01
 * Created on 18-9-26
 */
public class TimerManager {

     private static volatile TimerManager INSTANCE;

     private ScheduledExecutorService mTimerPool;

     public static TimerManager getInstance(){
          if (INSTANCE == null){
              synchronized (TimerManager.class){
                  if (INSTANCE == null){
                      INSTANCE = new TimerManager();
                  }
              }
          }
          return INSTANCE;
     }

     private TimerManager(){
         this.mTimerPool = Executors.newScheduledThreadPool(1);
     }

    /**
     * 执行定时任务
     *
     * @param runnable 需要创建一个runnable
     * @param delay    延时时间     单位ms
     * @param period   周期执行时间  单位ms
     */
     public void startTimerTask(long delay,long period,Runnable runnable){
         this.mTimerPool.scheduleAtFixedRate(runnable,delay,period, TimeUnit.MILLISECONDS);
     }

    /**
     * 执行定时任务
     *
     * @param delay 延时时间  单位ms
     */
     public void startTimerTask(long delay,Runnable runnable){
         this.mTimerPool.schedule(runnable,delay,TimeUnit.MILLISECONDS);//延迟1s执行
     }

    /**
     * 执行定时任务
     *
     * @param delay 延时时间  单位ms
     */
    public <T> void startTimerTask(long delay,Callable<T> callable){
        this.mTimerPool.schedule(callable,delay,TimeUnit.MILLISECONDS);
    }


}
