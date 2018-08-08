package eu.vre4eic.evre.nodeservice.nodemanager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.Common.Topics;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.messages.ControlMessage;

import org.apache.zookeeper.ZooKeeper;

public class ServiceTracker implements Watcher {
	ZooKeeper zk ;
	ChildrenCallback workersGetChildrenCallback;
	
	public ServiceTracker() throws IOException {
		
		zk = new ZooKeeper("localhost:2181", 2181, this);

		workersGetChildrenCallback = new ChildrenCallback() {
			public void processResult(int rc, String path, Object ctx,
					List<String> children) {
				switch (Code.get(rc)) {
				case CONNECTIONLOSS:
					getWorkers();
					break;
				case OK:
					System.out.println("Succesfully got a list of workers: "
							+ children.size()
							+ " Clients:" + children);
					break;
				default:
					System.out.println("getChildren failed");
				}	}

		};

		getWorkers();
	}
	
	public void process(WatchedEvent e) {
		System.out.println("## whatched event: "+ e);
		if(e.getType() == EventType.NodeChildrenChanged) {
			getWorkers();
		}
	}

	void getWorkers() {
		
		zk.getChildren("/evre/production/clients",
				this,
				workersGetChildrenCallback,
				null);
	}
	
	
	// to enable watchers using curator framework
	private static void setWatchers(CuratorFramework client){

		Stat exists = null;
		Publisher<ControlMessage> controlPublisher = new Publisher<ControlMessage>(Topics.CONTROL_Channel);

		CuratorWatcher watcher = new CuratorWatcher() {
			
			@Override
			public void process(WatchedEvent event) throws Exception {
				System.out.println("#### watched event" + event);
				if( event.getType() == EventType.NodeChildrenChanged){
					client.getChildren().usingWatcher(this).forPath(Common.EvreServicePATH);
				}
				
			}
		};
		

		try {
			System.out.println("#### setting watchers #####" );

			exists = client.checkExists().forPath(Common.EvreServicePATH);
			if (exists == null) {
				client.create()
				.creatingParentContainersIfNeeded()
				.forPath(Common.EvreServicePATH);
				// log
//				log.info("## Created " +Common.EvreServicePATH);
			} 
	
		client.getChildren().usingWatcher(watcher).forPath(Common.EvreServicePATH);
		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args)  {
		try {
			new ServiceTracker();
			TimeUnit.SECONDS.sleep(120);		
			System.out.println("##### closing");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}