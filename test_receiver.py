#!/usr/bin/env python
import pika
creds = pika.PlainCredentials(username='USER', password='PASS')
connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='IP', credentials=creds, virtual_host='HOST'))
channel = connection.channel()

channel.exchange_declare(exchange='EXCHANGEs',
                                 type='fanout')
result = channel.queue_declare(exclusive=True)
queue_name = result.method.queue
channel.queue_bind(exchange='EXCHANGE',
                           queue=queue_name)

print ' [*] Waiting for messages. To exit press CTRL+C'

def callback(ch, method, properties, body):
    print " [x] Received %r" % (body,)

channel.basic_consume(callback,
                      queue=queue_name,
                      no_ack=True)

channel.start_consuming()
