## Inspektr [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jasig.inspektr/inspektr/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/org.jasig.inspektr/inspektr) [![Build Status](https://travis-ci.org/Jasig/inspektr.png?branch=master)](https://travis-ci.org/Jasig/inspektr)

Inspektr is a very small library designed to capture and record the following pieces of
runtime information from Spring framework's managed beans i.e. running inside Spring DI container:

* The **WHO**: who performed an action being audited.
* The **WHAT**: what system resource being targeted by this audited action
* The **ACTION**: what audited action is being performed
* The **APPLICATION_CODE**: an arbitrary string token identifying application running an audited action
* The **WHEN**: a timestamp of an audited action
* The **CLIENT_IP**: an IP address of the client invoking an audited action
* The **SERVER_IP**: an IP address of the server running an audited action

Nothing more, nothing less.

## The architecture

The architecture of Inspektr Auditing library is very simple. At its core it has 3 main components:

* `AuditActionContext`: an immutable value object holding the auditing information mentioned above
 which has been gathered at audit points

* `AuditTrailManager`: a central Service Provider Interface that describes the recording of the audit data contract.

* `AuditTrailManagementAspect`: a POJO-style aspect responsible for capturing the auditing data
 at the configured audit points and passing it to the configured list of *AuditTrailManagers* for saving it

In addition to the main components, Inspektr defines the following pluggable strategy interfaces to
 resolve the **WHO**, **WHAT**, **ACTION**, **CLIENT_IP** and **SERVER_IP** audit data, namely:

* `PrincipalResolver`: resolves principals performing an audited action i.e. the **WHO** at audit points
* `AuditResourceResolver`: resolves the system resource being targeted by an audit action i.e. the **WHAT** at audit points
* `AuditActionResolver`: resolves audited actions i.e. **ACTION** at audit points
* `ClientInfoResolver`: resolves **CLIENT_IP** and **SERVER_IP** for audited actions at audit points
* `Audit` and `Audits`: runtime method annotations used to mark audit points. Note that at this time,
 the only audit points supported by Inspektr are around method executions i.e. defined by method AspectJ
 joinpoints marked with these annotations.

Out of the box, Inspektr comes with the following default implementations for all the pluggable components:

```java
    /**
     * Simple <code>AuditTrailManager</code> that dumps auditable information to output stream.
     * <p>
     * Useful for testing.
     */
     public final class ConsoleAuditTrailManager extends AbstractStringAuditTrailManager { .. }
***

    /**
     * <code>AuditTrailManager</code> that dumps auditable information to a configured logger.
     * 
     */
     public final class Slf4jLoggingAuditTrailManager extends AbstractStringAuditTrailManager { .. }
***

     /**
     * Implementation of {@link org.jasig.inspektr.audit.AuditTrailManager} to persist the
     * audit trail to the  AUDIT_TRAIL table in RDBMS of choice
     * <p/>
     * <pre>
     * CREATE TABLE COM_AUDIT_TRAIL
     * (
     *  AUD_USER      VARCHAR2(100) NOT NULL,
     *  AUD_CLIENT_IP VARCHAR(15)   NOT NULL,
     *  AUD_SERVER_IP VARCHAR(15)   NOT NULL,
     *  AUD_RESOURCE  VARCHAR2(100) NOT NULL,
     *  AUD_ACTION    VARCHAR2(100) NOT NULL,
     *  APPLIC_CD     VARCHAR2(5)   NOT NULL,
     *  AUD_DATE      TIMESTAMP     NOT NULL
     * )
     * </pre>
     *
     */
     public final class JdbcAuditTrailManager extends SimpleJdbcDaoSupport implements AuditTrailManager, Cleanable { .. }
```

In addition to the above `AuditTrailManager` imlementations, the default resolvers are available in the
`org.jasig.inspektr.audit.spi.support` package

## Configuration

1. First enable `AuditTrailManagementAspect` via either Spring proxy-based AOP by including the following directive
in the application context definition file: `<aop:aspectj-autoproxy/>` or via AspectJ compile-time weaving by using
 ajc compiler e.g by using AspectJ compiler Maven plugin if using Maven, etc.

2. Configure `org.jasig.inspektr.common.web.ClientInfoThreadLocalFilter` in `web.xml` (if deploying Servlet-based web application)

3. Configure `AuditTrailManager`s, all the resolvers and wire all of this into the `AuditTrailManagementAspect
` in the Spring application context definition file. The following example configuration is taken from 
[Apereo CAS](https://github.com/Jasig/cas/blob/master/cas-server-webapp/src/main/webapp/WEB-INF/spring-configuration/auditTrailContext.xml)

4. Define audit points which would expose auditing data to the Inspektr auditing facility by annotating methods
 of Spring-managed beans with `Audit` or `Audits` annotations:

```java
@Audit(action="SERVICE_TICKET",
       actionResolverName="GRANT_SERVICE_TICKET_RESOLVER",
       resourceResolverName="GRANT_SERVICE_TICKET_RESOURCE_RESOLVER")
public String grantServiceTicket(..)
```
