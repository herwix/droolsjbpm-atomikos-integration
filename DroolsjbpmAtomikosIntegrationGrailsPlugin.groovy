/*
 * Copyright (c) 2013. Alexander Herwix
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * You can also obtain a commercial license. Contact: alex@herwix.com for further details.
 */

import com.iterranux.droolsjbpmAtomikosIntegration.TransactionManagerJNDIRegistrator
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class DroolsjbpmAtomikosIntegrationGrailsPlugin {
    // the plugin version
    def version = "1.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def dependsOn = ['platformCore' : '* > 1.0.RC5',
                     'atomikos' : '* > 1.0']

    def loadBefore = ['droolsjbpmCore']

    // TODO Fill in these fields
    def title = "Droolsjbpm Atomikos Integration Plugin" // Headline display name of the plugin
    def author = "Alexander Herwix"
    def authorEmail = ""
    def description = '''\
Simple module that registers the Transaction Manager provided by the Grails Atomikos plugin with droolsjbpm-core.
This is intended to enable droolsjbpm-core based apps to run in 'run-app' development mode.
For production systems an application server with container-managed transaction system should be used.

Note: To make this work you need to install the Atomikos Plugin and add 'javax.transaction:jta:1.1' to the excludes
of your core dependencies. (Atomikos is only a JTA1.0 compliant Transaction Manager)
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/droolsjbpm-atomikos-integration"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "AGPL3"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {

        def droolsjbpmCorePluginConfig = application.config.plugin.droolsjbpmCore

        if(droolsjbpmCorePluginConfig.isEmpty()){
            throw new RuntimeException("DroolsjbpmCore plugin wasn't found. Please install it to use the 'droolsjbpm-atomikos-integration' plugin.")
        }else{
            droolsjbpmTransactionManagerJNDIRegistrator(TransactionManagerJNDIRegistrator){

                transactionManager = ref('atomikosTransactionManager')
                transactionManagerLookup = droolsjbpmCorePluginConfig.transactionManager.jndi.lookup.toString() ?: 'java:comp/TransactionManager'

                userTransaction = ref('atomikosUserTransaction')
                userTransactionLookup = droolsjbpmCorePluginConfig.transactionManager.userTransaction.jndi.lookup.toString() ?: 'java:comp/UserTransaction'
            }
        }
    }
}
