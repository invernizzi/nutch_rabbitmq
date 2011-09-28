/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nutch.fetcher;

import java.io.*;
import org.apache.nutch.protocol.Content;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitmqProxy {

  	public static final Log LOG = LogFactory.getLog(RabbitmqProxy.class);
	static RabbitmqProxy instance = null;
	static Connection connection;
	static Channel channel;
	static String exchange_name;

	private RabbitmqProxy(Configuration configuration) {
		try{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(configuration.get("rabbitmq.ip"));
			factory.setUsername(configuration.get("rabbitmq.username"));
			factory.setPassword(configuration.get("rabbitmq.password"));
			factory.setVirtualHost(configuration.get("rabbitmq.virtualhost"));
			connection = factory.newConnection();
			channel = connection.createChannel();
			exchange_name = configuration.get("rabbitmq.exchange");
			channel.exchangeDeclare(exchange_name, "fanout");
		} catch (java.io.IOException e) {LOG.fatal(e);}

	}

	public static void send(Content content, Configuration configuration) {
		if (instance == null) {
			instance = new RabbitmqProxy(configuration);
		}
		instance._send(content);
	}

	public synchronized void _send(Content content) {
		try{
			String message = content.getUrl();
			channel.basicPublish(exchange_name, "", null, message.getBytes());

			//channel.close();
			//connection.close();
		} catch(Exception e){LOG.fatal(e);}

	}
}

