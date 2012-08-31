/*******************************************************************************
* Copyright (c) 2011 GigaSpaces Technologies Ltd. All rights reserved
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

import static apache_addRemoveNode.*
import org.cloudifysource.dsl.context.ServiceContextFactory


context = ServiceContextFactory.getServiceContext()
config = new ConfigSlurper().parse(new File("apache-service.properties").toURL())

def instanceID = context.instanceId
def portIncrement =  context.isLocalCloud() ? instanceID-1 : 0			
def currentPort = config.port + portIncrement

println "apache_postStart.groovy: Starting..."

if ( config.useLoadBalancer == true ) {
	println "apache_postStart.groovy: invoking apache_addRemoveNode.addOrRemoveNode..."
	addOrRemoveNode(config,"postStart","addNode",180,context,currentPort,instanceID)
	println "apache_postStart.groovy: ${config.serviceName} added itself to ${config.loadBalancerServiceName}"
}

println "apache_postStart.groovy: Ended"