# NodeManager
The Node Service has been developed as a Java Maven project, the code is separate in two main packages:

* eu.vre4eic.evre.nodeservice where there is the code that implements the functionalities of the Node Service building block.
* eu.vre4eic.evre.core where we implement the code implementing functionalities commons to all building blocks, in  particular  classes implementing messages and the classes implementing Node Manager clients. 

Javadoc here:

	http://v4e-lab.isti.cnr.it:8080/NodeService/doc/index.html

e-VRE Web Services are documented via Swagger:

	http://v4e-lab.isti.cnr.it:8080/NodeService/swagger-ui.html#
	
##### Set up instructions

To set up the Node Service MongoDB and ActiveMQ are required in your environment. 
At the moment to install a Node Service you need to clone or download the source code, then manually change property values in the file:

[home_dir]/NodeManager/src/main/resources/Settings.properties

In particular the MongoDB and ActiveMQ address and credentials must be set. 
When the file Settings.properties has been updated a Web ARchive (WAR) must be created using maven and deployed on a application container.
