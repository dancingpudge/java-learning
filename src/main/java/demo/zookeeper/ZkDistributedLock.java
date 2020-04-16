package demo.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 基于zk的分布式锁
 *
 * @author liuh
 */
public class ZkDistributedLock {
    public static void main(String[] args) throws Exception {

        ZkWatcher zkWatcher = new ZkWatcher();
        Stat stat = new Stat();
        // zookeeper conn is async, so wait to trigger the watcher
        ZooKeeper zk = new ZooKeeper("192.168.11.131:2181", 3000, zkWatcher);
        zkWatcher.waitToConn();

        System.out.println(zk.getState());

        String path = "/";
        List<String> children = zk.getChildren(path, null);
        for (String child : children) {
            System.out.println("find " + child + " :");
            System.out.println("   " + new String(zk.getData(path + child, true, stat)));
        }
        zk.close();
    }

}

class ZkWatcher implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public void waitToConn() throws Exception {
        connectedSemaphore.await();
    }
}
