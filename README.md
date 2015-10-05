# Application-export-echosounder

Needs a properties file in "catalina.base"/conf/ called nmd_echosounder_loader.properties that must contain these properties:  


file.location=/path/to/where/output/is/stored  
 
 #location of configuration files (must be in catalina.home)   
file.configuration.persistance=persistence.properties  
file.configuration.activemq=activemq.properties  

 #jms queues   
queue.outgoing.error=error  
queue.outgoing.update-dataset=update-dataset  

cron.activation.time=* 2 * * *
