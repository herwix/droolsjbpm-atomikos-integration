droolsjbpm-atomikos-integration
===============

Simple module that registers the Transaction Manager provided by the Grails Atomikos plugin with droolsjbpm-core.
This is intended to enable droolsjbpm-core based apps to run in 'run-app' development mode.
For production systems an application server with container-managed transaction system should be used.

Note: To make this work you need to install the Atomikos Plugin and add 'javax.transaction:jta:1.1' to the excludes
of your core dependencies. (Atomikos is only a JTA1.0 compliant Transaction Manager)

LICENSE
=======
This plugin is licensed under an open-source (AGPLv3)/commerical dual-License.
See the license file for details.
