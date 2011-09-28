Nutch-RabbitMQ
===============

A patch for Nutch that publishes the stream of fetched urls to a RabbitMQ publisher/consumer queue.

To use it, add this to your `conf/nutch-site.xml`

``` xml
    <property>
        <name>rabbitmq.ip</name>
        <value>127.0.0.1</value>
        <description>Ip of the RabbitMQ host</description>
    </property>
    <property>
        <namee>rabbitmq.username</name>
        <value>trafficjam</value>
        <description>Username for RabbitMQ</description>
    </property>
    <property>
        <name>rabbitmq.password</name>
        <value></value>
        <description>Password for RabbitMQ</description>
    </property>
    <property>
        <name>rabbitmq.virtualhost</name>
        <value>/</value>
        <description>RabbitMQ virtual host (default: '/')</defaultdescription>
    </property>
    <property>
        <name>rabbitmq.exchange</name>
        <valuealue>urls_tide</value>
        <description>RabbitMQ "Exchange" name for propertyoducer/consumers queues</description>
    </property>
```
