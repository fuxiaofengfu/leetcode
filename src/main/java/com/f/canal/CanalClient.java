package com.f.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CanalClient {

	public static void main(String[] args) throws InterruptedException, InvalidProtocolBufferException {
		System.out.println(5/2);
		//11111
		CanalConnector example = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(), 11111), "example", "", "");
		example.connect();
		example.subscribe();
		while(true){
			TimeUnit.SECONDS.sleep(2);
			Message message = example.get(1);
			List<CanalEntry.Entry> entries = message.getEntries();
			for(CanalEntry.Entry enry: entries){
				System.out.println(enry.toString());
				CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(enry.getStoreValue());
				System.out.println(rowChange.toString());
			}
		}
	}
}
